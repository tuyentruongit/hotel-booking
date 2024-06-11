package methodsecuritynew.bookingapp.service;

import jakarta.persistence.EntityNotFoundException;


import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.*;
import methodsecuritynew.bookingapp.exception.BadRequestException;
import methodsecuritynew.bookingapp.model.dto.*;
import methodsecuritynew.bookingapp.model.enums.UserRole;
import methodsecuritynew.bookingapp.model.request.UpsertCreateHotelRequest;
import methodsecuritynew.bookingapp.model.request.UpsertHotelRequest;
import methodsecuritynew.bookingapp.model.enums.RentalType;
import methodsecuritynew.bookingapp.model.request.UpsertPolicyRequest;
import methodsecuritynew.bookingapp.model.request.UpsertRoomRequest;
import methodsecuritynew.bookingapp.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class HotelService {
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final AmenityHotelRepository amenityHotelRepository;
    private final CityService cityService;
    private final AmenityService amenityService;
    private final PolicyService policyService;
    private final UserService userService;
    private final RoomService roomService;
    private final BookingRepository bookingRepository;



    public List<HotelDto> hotelListSearch = new ArrayList<>();

    // logic tìm kiếm khách sạn
    public void getHotelBySearch(String nameCity, String checkIn, String checkOut, Integer numberGuest, Integer numberRoom) {
        // lấy ngày checkIn và checkOut từ string
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate checkInDay = LocalDate.parse(checkIn,dateTimeFormatter);
        LocalDate checkOutDay = LocalDate.parse(checkOut,dateTimeFormatter);

        // lấy danh sách khách sạn theo city
        List<Hotel> hotelList = hotelRepository.findHotelByCity_NameIgnoreCase(nameCity);
        List<HotelDto> availableHotels  = new ArrayList<>();

        // duyệt từng khách sạn để kiểm tra các điều kiện
        for (Hotel hotel : hotelList){
            // laays ra các phòng trống của khách sạn
            List<RoomDto> dataRoom = roomService.getDataRoom(hotel.getId(),checkInDay,checkOutDay,numberRoom,numberGuest);
            if (!dataRoom.isEmpty()){
                // lấy ra các thông tin của phòng và giá theo thời điểm của ngày chekIn và checkOut
                HotelDto hotelDto = createHotelDto(hotel,dataRoom);
                availableHotels.add(hotelDto);
            }
        }
      hotelListSearch = availableHotels;
    }

    // tạo một HotelDto từ một hotel
    private HotelDto createHotelDto(Hotel hotel, List<RoomDto> dataRoom) {
        // lấy giá thấp nhất của khách sạn để hiện thị cho người dùng
        int price = getMinPrice(dataRoom);
        int totalReview = reviewRepository.findReviewByHotel_Id(hotel.getId()).size();
        // tạo một dối tượng chữa các thông tin cần thiết của khách sạn
        HotelDto hotelDto = new HotelDto();
        hotelDto.setId(hotel.getId());
        hotelDto.setName(hotel.getName());
        hotelDto.setAddress(hotel.getAddress());
        hotelDto.setTotalReviews(totalReview);
        hotelDto.setStar(hotel.getStar());
        hotelDto.setPoster(hotel.getPoster());
        hotelDto.setRating(hotel.getRating());
        hotelDto.setRatingText(hotel.getRatingText());
        hotelDto.setEstimatedPrice(price);
        hotelDto.setNameAmenity(hotel.getAmenityHotelList().stream().map(Amenity::getName).toList());
        return  hotelDto;
    }

    // logic lấy giá phòng nhỏ nhất của thời gian người dùng chọn hiển thị ra ngoài giao diện
    public int getMinPrice(List<RoomDto> roomDtoList) {
         // kiểm tra các thông tin của phòng có null hoặc rỗng hay không
        if (roomDtoList == null || roomDtoList.isEmpty()) {
            throw new NoSuchElementException("Không tìm thấy dữ liệu");
        }
        // duyệt từng đối tượng để lấy ra giá phòng rẻ nhất ngày hôm đó
        int minPrice = Integer.MAX_VALUE;
        for (RoomDto roomDto : roomDtoList) {
            if (roomDto.getPriceAverage() < minPrice) {
                minPrice = roomDto.getPriceAverage();
            }
        }
        return minPrice;
    }

    // phân trang cho trang danh sách khách sạn sau khi tìm kiếm
    @Transactional
    public Page<HotelDto> getPaginationHotel(Integer pageNumber, Integer limit) {
        Pageable pageable = PageRequest.of(pageNumber-1,limit);
        return PageableExecutionUtils.getPage(
                hotelListSearch.subList((int) pageable.getOffset(), Math.min((int) pageable.getOffset() + pageable.getPageSize(), hotelListSearch.size())),
                pageable,() -> hotelListSearch.size()
        );

    }

    public Hotel getHotelById(Integer id) {
        return hotelRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Không tìm thấy khách sạn nào tương ứng"));
    }

    public List<HotelDto> getHotelHomPage(String city , String checkIn, String checkOut, Integer numberGuest, Integer numberRoom) {
        getHotelBySearch(city,checkIn,checkOut,numberGuest,numberRoom);
        if (hotelListSearch.size()>8){
            return hotelListSearch.subList(0,8);
        }
        return hotelListSearch;
    }

    // tìm kiếm các khách sạn yêu thích theo thành phố
    public Page<Hotel> findHotelFavouriteByCity(Integer id, String city , Integer pageNumber , Integer limit) {
        PageRequest pageRequest = PageRequest.of(pageNumber-1,limit);
        // lấy ra danh sách khách sạn được yêu thích của user
        List<Hotel> hotelList =   userRepository.findById(id).orElseThrow(()-> new RuntimeException("Không tìm thấy khách sạn nào có id " + id)).getHotelList().stream()
                .filter(hotel -> Objects.equals(hotel.getCity().getName(),city))
                .toList();

        return PageableExecutionUtils.getPage(
                hotelList.subList((int) pageRequest.getOffset(),Math.min((int) pageRequest.getOffset() + pageRequest.getPageSize() , hotelList.size())), pageRequest, hotelList::size

        );

    }

    public Map<City , Integer> getNameCityHotelFavourite(Integer id) {
        List<Hotel> hotelList = userRepository.findById(id).get().getHotelList();
        Map<City , Integer> myMap = new HashMap<>();
        for (Hotel hotel : hotelList){
            if (!myMap.containsKey(hotel.getCity())){
                int count = 0 ;
                for (Hotel value : hotelList) {
                    if (value.getCity().getName().contains(hotel.getCity().getName())) {
                        count++;
                    }
                }
                myMap.put(hotel.getCity(),count);
            }
        }
        return myMap;
    }

    public List<Hotel> getAllHotelFavourite(String email) {
        User optionalUser = userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("Không tìm thấy người dùng nào có email như :" + email ));
        return optionalUser.getHotelList();

    }

    public Page<Hotel> getAllHotel(Integer page , Integer limit) {
        Pageable pageable = PageRequest.of( page -1 , limit , Sort.by("createdAt").descending());
        return hotelRepository.findAll(pageable);
    }

    public Hotel updateHotelAdmin(Integer id, UpsertHotelRequest request) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Không tìm thấy khách sạn nào có id : " + id ));
        String regexEmail ="([a-zA-Z0-9]+)@([a-zA-Z0-9]+)\\.([a-zA-Z0-9]+)";
        String email = "";
        System.out.println("nè---------------------------------"+request.getEmail().matches(regexEmail));
        if (request.getEmail().matches(regexEmail)){
            email = request.getEmail();
        }else {
            throw new BadRequestException("Email không hợp lệ ");
        }
        City city = cityService.getCityById(request.getIdCity());
        RentalType rentalType = RentalType.valueOf(request.getRentalType());
        hotel.setUpdatedAt(LocalDate.now());
        hotel.setCity(city);
        hotel.setAddress(request.getAddressHotel());
        hotel.setHotline(request.getPhoneHotel());
        hotel.setEmail(email);
        hotel.setStar(request.getStar());
        hotel.setName(request.getName());
        hotel.setRentalType(rentalType);
        hotel.setDescription(request.getDescription());
        hotel.setStatus(request.getStatus());
        String regex = "^0([0-9]{9})";
        if (request.getPhoneHotel().matches(regex)){
            hotel.setHotline(request.getPhoneHotel());
        }else {
            throw new BadRequestException("Số điện thoại của bạn không hợp lệ ");
        }

        return hotelRepository.save(hotel);

    }

//    public Hotel createHotelAdmin( UpsertHotelRequest request) {
//        User user =  authService.createUserHotel(request);
//        String regex = "^0([0-9]{9})";
//        String phone = "";
//        if (request.getPhoneHotel().matches(regex)){
//            phone = request.getPhoneHotel();
//        }else {
//            throw new BadRequestException("Số điện thoại không hợp lệ ");
//        }
//        String regexEmail ="([a-zA-Z0-9]+)@([a-zA-Z0-9]+)\\.([a-zA-Z0-9]+)";
//        String email = "";
//        System.out.println("nè---------------------------------"+request.getEmail().matches(regexEmail));
//        if (request.getEmail().matches(regexEmail)){
//            email = request.getEmail();
//        }else {
//            throw new BadRequestException("Email không hợp lệ ");
//        }
//        if (hotelRepository.findHotelByName(request.getName())!=null){
//           throw new RuntimeException("Tên khách sạn trên đã tồn tại");
//        }
//        City city = cityService.getCityById(request.getIdCity());
//        RentalType rentalType = RentalType.valueOf(request.getRentalType());
//        Hotel hotel = Hotel.builder()
//                .name(request.getName())
//                .status(request.getStatus())
//                .city(city)
//                .rentalType(rentalType)
//                .email(email)
//                .star(request.getStar())
//                .description(request.getDescription())
//                .address(request.getAddressHotel())
//                .createdAt(LocalDate.now())
//                .hotline(phone)
//                .user(user)
//                .build();
//        return hotelRepository.save(hotel);
//    }

    // admin xóa khác sạn
    public void deleteHotel(Integer id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Không tìm thấy khách sạn nào có id : " + id ));

        List<Room> roomList = roomService.getRoomByIdHotel(id);
        List<Review> reviewList = reviewRepository.findReviewByHotel_Id(id);
        reviewRepository.deleteAll(reviewList);
        roomRepository.deleteAll(roomList);
        hotelRepository.delete(hotel);
    }


    public Hotel getHotelByAccountCurrent() {
//        User user = userRepository.findByEmail(session.getAttribute("MY_SESSION").toString())
//                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy user trên "));

//        return hotelRepository.findHotelByUser_Id(user.getId());
        return  hotelRepository.findById(2).orElseThrow(()-> new RuntimeException("Không tìm thấy hotel trên ") );
    }


    // hotel-manager update khách sạn
    public Hotel updateHotel(Integer id, UpsertHotelRequest request) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Không tìm thấy khách sạn nào có id : " + id ));
        hotel.setDescription(request.getDescription());
        List<AmenityHotel> list = new ArrayList<>();
        request.getAmenityHotelList().forEach(idAme ->{
            AmenityHotel amenityHotel = amenityHotelRepository.findById(idAme)
                    .orElseThrow(()-> new RuntimeException("Không tìm thấy amenity nào có id : " + idAme));
            list.add(amenityHotel);
        });
        hotel.setAmenityHotelList(list);
        return hotelRepository.save(hotel);
    }

    public List<Hotel> getAllHotelByCity(Integer id) {
        return  hotelRepository.findHotelByCity_Id(id);
    }

    // tạo khách sạn khi người người dùng đăng ký cho thuê phòng
    @Transactional
    public Hotel createHotelUser(UpsertCreateHotelRequest upsertCreateHotelRequest) {
        UpsertHotelRequest upsertHotelRequest = upsertCreateHotelRequest.getUpsertHotelRequest();
        UpsertPolicyRequest upsertPolicyRequest = upsertCreateHotelRequest.getUpsertPolicyRequest();
        UpsertRoomRequest upsertRoomRequest = upsertCreateHotelRequest.getUpsertRoomRequest();
        Hotel hotel = hotelRepository.findByHotline(upsertHotelRequest.getPhoneHotel());
        if (hotel!=null){
            throw  new RuntimeException("Số điện thoại đã tồn tại ");
        }
        List<AmenityHotel> amenityHotelList = amenityService.getAllAmenityHotelById(upsertHotelRequest.getAmenityHotelList());
        City city = cityService.getCityById(upsertHotelRequest.getIdCity());
        PolicyHotel policyHotel = policyService.createPolicyHotel(upsertPolicyRequest);
        User user = userService.getUserCurrent();
        user.setUserRole(UserRole.ROLE_HOTEL);
        userRepository.save(user);
        Hotel hotelNew = Hotel.builder()
                .name(upsertHotelRequest.getName())
                .email(upsertHotelRequest.getEmail())
                .description(upsertHotelRequest.getDescription())
                .address(upsertHotelRequest.getAddressHotel())
                .poster("/web/assets/image/avata-default.jpg")
                .city(city)
                .policyHotel(policyHotel)
                .user(user)
                .star(upsertHotelRequest.getStar())
                .hotline(upsertHotelRequest.getPhoneHotel())
                .amenityHotelList(amenityHotelList)
                .createdAt(LocalDate.now())
                .status(false)
                .rentalType(RentalType.valueOf(upsertHotelRequest.getRentalType()))
                .build();
        hotelRepository.save(hotelNew);
        Room room= roomService.createRoom(upsertRoomRequest);
        roomRepository.save(room);
        return hotelNew;
    }

    public void updatePolicyHotel(UpsertPolicyRequest request) {
        Hotel hotel = getHotelByAccountCurrent();
        policyService.updatePolicyHotel(request,hotel);
    }

    public List<Room> getAllRoom() {
        Hotel hotel= getHotelByAccountCurrent();
        return roomService.getRoomByIdHotel(hotel.getId());

    }
}
