package methodsecuritynew.bookingapp.service;

import jakarta.persistence.EntityNotFoundException;


import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.*;
import methodsecuritynew.bookingapp.exception.BadRequestException;
import methodsecuritynew.bookingapp.model.dto.*;
import methodsecuritynew.bookingapp.model.enums.StatusBooking;
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
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
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
    private final ImageService imageService;
    private final ImageHotelRepository imageHotelRepository;

    private final MailService mailService;

    public List<HotelDto> hotelListSearch = new ArrayList<>();

    // logic tìm kiếm khách sạn
    public void getHotelBySearch(String nameCity, String checkIn, String checkOut, Integer numberGuest, Integer numberRoom) {
        // lấy ngày checkIn và checkOut từ string
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate checkInDay = LocalDate.parse(checkIn, dateTimeFormatter);
        LocalDate checkOutDay = LocalDate.parse(checkOut, dateTimeFormatter);

        // lấy danh sách khách sạn theo city
        List<Hotel> hotelList = hotelRepository.findHotelByCity_NameIgnoreCaseAndStatusTrue(nameCity);
        List<HotelDto> availableHotels = new ArrayList<>();

        // duyệt từng khách sạn để kiểm tra các điều kiện
        for (Hotel hotel : hotelList) {
            // laays ra các phòng trống của khách sạn
            List<RoomDto> dataRoom = roomService.getDataRoom(hotel.getId(), checkInDay, checkOutDay, numberRoom, numberGuest);
            if (!dataRoom.isEmpty()) {
                // lấy ra các thông tin của phòng và giá theo thời điểm của ngày chekIn và checkOut
                HotelDto hotelDto = createHotelDto(hotel, dataRoom);
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
        hotelDto.setRentalType(hotel.getRentalType());
        hotelDto.setStar(hotel.getStar());
        hotelDto.setPoster(hotel.getPoster());
        hotelDto.setRating(hotel.getRating());
        hotelDto.setRatingText(hotel.getRatingText());
        hotelDto.setEstimatedPrice(price);
        hotelDto.setNameAmenity(hotel.getAmenityHotelList().stream().map(Amenity::getName).toList());
        return hotelDto;
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
        Pageable pageable = PageRequest.of(pageNumber - 1, limit);
        return PageableExecutionUtils.getPage(
                hotelListSearch.subList((int) pageable.getOffset(), Math.min((int) pageable.getOffset() + pageable.getPageSize(), hotelListSearch.size())),
                pageable, () -> hotelListSearch.size()
        );
    }

    public Hotel getHotelById(Integer id) {
        return hotelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Không tìm thấy khách sạn nào tương ứng"));
    }

    public List<HotelDto> getHotelHomPage(String city, String checkIn, String checkOut, Integer numberGuest, Integer numberRoom) {
        getHotelBySearch(city, checkIn, checkOut, numberGuest, numberRoom);
        if (hotelListSearch.size() > 8) {
            return hotelListSearch.subList(0, 8);
        }
        return hotelListSearch;
    }

    // tìm kiếm các khách sạn yêu thích theo thành phố
    public Page<Hotel> findHotelFavouriteByCity(Integer id, String city, Integer pageNumber, Integer limit) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, limit);
        // lấy ra danh sách khách sạn được yêu thích của user
        List<Hotel> hotelList = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy khách sạn nào có id " + id)).getHotelList().stream()
                .filter(hotel -> Objects.equals(hotel.getCity().getName(), city))
                .toList();

        return PageableExecutionUtils.getPage(
                hotelList.subList((int) pageRequest.getOffset(), Math.min((int) pageRequest.getOffset() + pageRequest.getPageSize(), hotelList.size())), pageRequest, hotelList::size

        );

    }

    public Map<City, Integer> getNameCityHotelFavourite(Integer id) {
        List<Hotel> hotelList = userRepository.findById(id).get().getHotelList();
        Map<City, Integer> myMap = new HashMap<>();
        for (Hotel hotel : hotelList) {
            if (!myMap.containsKey(hotel.getCity())) {
                int count = 0;
                for (Hotel value : hotelList) {
                    if (value.getCity().getName().contains(hotel.getCity().getName())) {
                        count++;
                    }
                }
                myMap.put(hotel.getCity(), count);
            }
        }
        return myMap;
    }

    public List<Hotel> getAllHotelFavourite(String email) {
        User optionalUser = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng nào có email như :" + email));
        return optionalUser.getHotelList();

    }

    public Page<Hotel> getAllHotel(Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by("createdAt").descending());
        return hotelRepository.findAll(pageable);
    }

    public Hotel updateHotelAdmin(Integer id, UpsertHotelRequest request) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách sạn nào có id : " + id));
        City city = cityService.getCityById(request.getIdCity());
        RentalType rentalType = RentalType.valueOf(request.getRentalType());
        hotel.setUpdatedAt(LocalDate.now());
        hotel.setCity(city);
        hotel.setAddress(request.getAddressHotel());
        hotel.setHotline(request.getPhoneHotel());
        hotel.setEmail(request.getEmailHotel());
        hotel.setStar(request.getStar());
        hotel.setName(request.getNameHotel());
        hotel.setRentalType(rentalType);
        hotel.setDescription(request.getDescriptionHotel());
        hotel.setStatus(request.getStatus());
        String regex = "^0([0-9]{9})";
        if (request.getPhoneHotel().matches(regex)) {
            hotel.setHotline(request.getPhoneHotel());
        } else {
            throw new BadRequestException("Số điện thoại của bạn không hợp lệ ");
        }

        return hotelRepository.save(hotel);

    }

    // admin xóa khác sạn
    public void deleteHotel(Integer id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách sạn nào có id : " + id));
        roomService.deleteAllRoom(id);
        List<Review> reviewList = reviewRepository.findReviewByHotel_Id(id);
        List<ImageHotel> imageHotelList = imageService.getAllImageByIdHotel(hotel.getId());
        User user = hotel.getUser();
        user.setUserRole(UserRole.ROLE_USER);
        userRepository.save(user);
        imageHotelRepository.deleteAll(imageHotelList);
        reviewRepository.deleteAll(reviewList);
        hotelRepository.delete(hotel);
    }


    public Hotel getHotelByAccountCurrent() {
//        User user = userRepository.findByEmail(session.getAttribute("MY_SESSION").toString())
//                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy user trên "));

//        return hotelRepository.findHotelByUser_Id(user.getId());
        return hotelRepository.findById(2).orElseThrow(() -> new RuntimeException("Không tìm thấy hotel trên "));
    }


    // hotel-manager update khách sạn
    public Hotel updateHotel(Integer id, UpsertHotelRequest request) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách sạn nào có id : " + id));
        hotel.setDescription(request.getDescriptionHotel());
        List<AmenityHotel> list = new ArrayList<>();
        request.getAmenityHotelList().forEach(idAme -> {
            AmenityHotel amenityHotel = amenityHotelRepository.findById(idAme)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy amenity nào có id : " + idAme));
            list.add(amenityHotel);
        });
        hotel.setAmenityHotelList(list);
        return hotelRepository.save(hotel);
    }

    public List<Hotel> getAllHotelByCity(Integer id) {
        return hotelRepository.findHotelByCity_Id(id);
    }

    // tạo khách sạn dựa trên thông tin đã có
    private Hotel createHotel(UpsertHotelRequest upsertHotelRequest, City city, User user, PolicyHotel policyHotel, List<AmenityHotel> amenityHotelList) {
        Hotel hotelNew = Hotel.builder()
                .name(upsertHotelRequest.getNameHotel())
                .email(upsertHotelRequest.getEmailHotel())
                .description(upsertHotelRequest.getDescriptionHotel())
                .address(upsertHotelRequest.getAddressHotel())
                .city(city)
                .policyHotel(policyHotel)
                .user(user)
                .star(upsertHotelRequest.getStar())
                .hotline(upsertHotelRequest.getPhoneHotel())
                .amenityHotelList(amenityHotelList)
                .createdAt(LocalDate.now())
                .rating(0.0f)
                .status(false)
                .rentalType(RentalType.valueOf(upsertHotelRequest.getRentalType()))
                .build();
        hotelRepository.save(hotelNew);
        return hotelNew;
    }

    public void updatePolicyHotel(UpsertPolicyRequest request) {
        Hotel hotel = getHotelByAccountCurrent();
        policyService.updatePolicyHotel(request, hotel);
    }

    public List<Room> getAllRoom() {
        Hotel hotel = getHotelByAccountCurrent();
        return roomService.getRoomByIdHotel(hotel.getId());

    }


    @Transactional
    public Hotel createHotelUser(UpsertHotelRequest upsertHotelRequest, UpsertRoomRequest upsertRoomRequest, UpsertPolicyRequest upsertPolicyRequest, MultipartFile fileHotel, MultipartFile fileRoom) {
        if (hotelRepository.findByHotline(upsertHotelRequest.getPhoneHotel()) != null) {
            throw new RuntimeException("Số điện thoại đã tồn tại ");
        }
        // lây danh sách các tiện ích mà khách sạn chọn để lưu vào tiện ích của khách sạn đó
        List<AmenityHotel> amenityHotelList = amenityService.getAllAmenityHotelById(upsertHotelRequest.getAmenityHotelList());
        // Lấy thành phố  mà khách sạn đã chọn
        City city = cityService.getCityById(upsertHotelRequest.getIdCity());
        // tạo chính sách mới cho khách sạn
        PolicyHotel policyHotel = policyService.createPolicyHotel(upsertPolicyRequest);
        // cập nhật role mới cho user
        User user = userService.getUserCurrent();
        user.setUserRole(UserRole.ROLE_HOTEL);
        userRepository.save(user);
        // tạo dữ liệu khách sạn
        Hotel hotelNew = createHotel(upsertHotelRequest, city, user, policyHotel, amenityHotelList);
        // tạo dối tượng image hotel
        ImageHotel imageHotel = imageService.uploadImageHotel(hotelNew.getId(), fileHotel);
        // đặt poster cho khách sạn là ảnh đâầu tiên
        hotelNew.setPoster(imageHotel.getUrl());
        // tạo dữ liệu cho phòng
        Room room = roomService.createRoom(upsertRoomRequest);
        ImageRoom imageRoom = imageService.saveImageRoom(room, fileRoom);
        room.setThumbnailRoom(imageRoom.getUrl());
        roomRepository.save(room);
        String link = "http://localhost:9000/hotel-manager/information" ;
        mailService.sendMail(user.getEmail(),
                "Đăng ký tài khoản đối tác",
                "Chào " +user.getName()+"! \n" +
                        "\n" +
                        "Chúc mừng bạn đã trở thành đối tác của WebFindTravel.\n" +
                        "\n" +
                        "Truy cập đường link sau để chuyển đến trang quản lý nơi lưu trú của bạn :\n" +
                        "\n" +
                        link+"\n" +
                        "\n" +
                        "Trân trọng.\n" );
        return hotelNew;
    }

    public List<Hotel> getHotelNew() {
        LocalDate star = LocalDate.now().withDayOfMonth(1);
        LocalDate end = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        return hotelRepository.findAllByCreatedAtBetweenOrderByCreatedAtDesc(star, end);
    }
}
