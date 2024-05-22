package methodsecuritynew.bookingapp.service;

import jakarta.persistence.EntityNotFoundException;


import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.City;
import methodsecuritynew.bookingapp.entity.Hotel;
import methodsecuritynew.bookingapp.entity.Room;
import methodsecuritynew.bookingapp.entity.User;
import methodsecuritynew.bookingapp.exception.BadRequestException;
import methodsecuritynew.bookingapp.model.request.UpsertHotelRequest;
import methodsecuritynew.bookingapp.model.statics.RentalType;
import methodsecuritynew.bookingapp.model.statics.UserRole;
import methodsecuritynew.bookingapp.repository.AmenityHotelRepository;
import methodsecuritynew.bookingapp.repository.HotelRepository;
import methodsecuritynew.bookingapp.repository.RoomRepository;
import methodsecuritynew.bookingapp.repository.UserRepository;
import methodsecuritynew.bookingapp.security.CustomUserDetailService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class HotelService {
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CityService cityService;
    private final PasswordEncoder passwordEncoder;

    public List<Hotel> hotelListSearch = new ArrayList<>();

    public void getHotelBySearch(String nameCity, String checkIn, String checkOut, Integer numberGuest, Integer numberRoom) {
        String name = nameCity.trim();
        List<Hotel> hotelList = new ArrayList<>();
        for (Hotel hotel : hotelRepository.findHotelByCity_NameIgnoreCase(name)){
            int countGuest = 0 ;
            List<Room> list = roomRepository.findRoomByHotel_Id(hotel.getId());
            for (Room room : list){
                countGuest+=room.getCapacity();
            }
            if (countGuest>=numberGuest && list.size()>=numberRoom)
            {
                hotelList.add(hotel);
            }
        }
        hotelListSearch = hotelList;
    }

    @Transactional
    public Page<Hotel> getPaginationHotel(Integer pageNumber, Integer limit) {

        Pageable pageable = PageRequest.of(pageNumber-1,limit);
        return PageableExecutionUtils.getPage(
                hotelListSearch.subList((int) pageable.getOffset(), Math.min((int) pageable.getOffset() + pageable.getPageSize(), hotelListSearch.size())),
                pageable,() -> hotelListSearch.size()
        );
    }



    public List<Hotel> findOutstandingHotel(String name) {
        return hotelRepository.findHotelByCity_NameIgnoreCase(name);
    }

    public Hotel getHotelById(Integer id) {
        return hotelRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Không tìm thấy khách sạn nào tương ứng"));
    }

    public List<Hotel> getHotelHomPage(String city) {
        return hotelRepository.findHotelByCity_NameIgnoreCase(city);
    }

    public Page<Hotel> findHotelFavouriteByCity(Integer id, String city , Integer pageNumber , Integer limit) {
        PageRequest pageRequest = PageRequest.of(pageNumber-1,limit);
        List<Hotel> hotelList =   userRepository.findById(id).orElseThrow(()-> new RuntimeException("Không tìm thấy khách sạn nào có id " + id)).getHotelList().stream()
                .filter(hotel -> Objects.equals(hotel.getCity().getName(),city))
                .toList();

        return PageableExecutionUtils.getPage(
                hotelList.subList((int) pageRequest.getOffset(),Math.min((int) pageRequest.getOffset() + pageRequest.getPageSize() , hotelList.size())), pageRequest, hotelList::size

        );

    }

    public Map<String , Integer> getNameCityHotelFavourite(Integer id) {
        List<Hotel> hotelList = userRepository.findById(id).get().getHotelList();
        Map<String , Integer> myMap = new HashMap<>();
        for (Hotel hotel : hotelList){
            if (!myMap.containsKey(hotel.getCity().getName())){
                int count = 0 ;
                for (Hotel value : hotelList) {
                    if (value.getCity().getName().contains(hotel.getCity().getName())) {
                        count++;
                    }
                }
                myMap.put(hotel.getCity().getName(),count);
            }
        }
        return myMap;
    }

    public List<Hotel> getAllHotelFavourite(String email) {
        User optionalUser = userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("Không tìm thấy người dùng nào có email như :" + email ));
        return optionalUser.getHotelList();

    }

    public List<Hotel> getAllHotel() {
        return hotelRepository.findAll().stream()
                .sorted((ht1, ht2)-> ht2.getCreatedAt().compareTo(ht1.getCreatedAt()))
                .toList();
    }

    public Hotel updateHotelAdmin(Integer id, UpsertHotelRequest request) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Không tìm thấy khách sạn nào có id : " + id ));
        City city = cityService.getCityById(request.getIdCity());
        RentalType rentalType = RentalType.valueOf(request.getRentalType());
        hotel.setUpdatedAt(LocalDate.now());
        hotel.setCity(city);
        hotel.setAddress(request.getAddressHotel());
        hotel.setHotline(request.getPhoneHotel());
        hotel.setEmail(request.getEmail());
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

    public Hotel createHotelAdmin( UpsertHotelRequest request) {
        User user =  authService.createUserHotel(request);
        String regex = "^0([0-9]{9})";
        String phone = "";
        if (request.getPhoneHotel().matches(regex)){
            phone = request.getPhoneHotel();
        }else {
            throw new BadRequestException("Số điện thoại của bạn không hợp lệ ");
        }
        if (hotelRepository.findHotelByName(request.getName())!=null){
           throw new RuntimeException("Tên khách sạn trên đã tồn tại");
        }
        City city = cityService.getCityById(request.getIdCity());
        RentalType rentalType = RentalType.valueOf(request.getRentalType());
        Hotel hotel = Hotel.builder()
                .name(request.getName())
                .status(request.getStatus())
                .city(city)
                .rentalType(rentalType)
                .email(request.getEmail())
                .description(request.getDescription())
                .address(request.getAddressHotel())
                .createdAt(LocalDate.now())
                .hotline(phone)
                .user(user)
                .build();
        return hotelRepository.save(hotel);
    }

    public void deleteHotel(Integer id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Không tìm thấy khách sạn nào có id : " + id ));

        List<Room> roomList = roomRepository.findReviewByHotel_Id(id);
        roomRepository.deleteAll(roomList);
        hotelRepository.delete(hotel);
    }
}
