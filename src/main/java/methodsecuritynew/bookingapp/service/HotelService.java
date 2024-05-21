package methodsecuritynew.bookingapp.service;

import jakarta.persistence.EntityNotFoundException;


import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.Hotel;
import methodsecuritynew.bookingapp.entity.Room;
import methodsecuritynew.bookingapp.entity.User;
import methodsecuritynew.bookingapp.repository.HotelRepository;
import methodsecuritynew.bookingapp.repository.RoomRepository;
import methodsecuritynew.bookingapp.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class HotelService {
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
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
        Optional<User> optionalUser = userRepository.findByEmail(email);
        return optionalUser.get().getHotelList();

    }
}
