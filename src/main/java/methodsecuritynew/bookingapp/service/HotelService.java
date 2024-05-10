package methodsecuritynew.bookingapp.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.Hotel;
import methodsecuritynew.bookingapp.entity.Room;
import methodsecuritynew.bookingapp.entity.User;
import methodsecuritynew.bookingapp.repository.HotelRepository;
import methodsecuritynew.bookingapp.repository.RoomRepository;
import methodsecuritynew.bookingapp.repository.UserRepository;
import methodsecuritynew.bookingapp.security.CustomUserDetailService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class HotelService {
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final CustomUserDetailService customUserDetailService;
    private final HttpSession session;


    public List<Hotel> findOutstandingHotel(String name) {
        return hotelRepository.findHotelByCity_NameIgnoreCase(name);
    }

    public List<Hotel> getHotelBySearch(String nameCity, String checkIn, String checkOut, Integer numberGuest, Integer numberRoom) {
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
        return hotelList;
    }

    public Hotel getHotelById(Integer id) {
        return hotelRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Không tìm thấy khách sạn nào tương ứng"));
    }

    public List<Hotel> getHotelHomPage(String city) {
        return hotelRepository.findHotelByCity_NameIgnoreCase(city);
    }

    public List<Hotel> findHotelFavouriteByCity(Integer id, String city) {

        return userRepository.findById(id).get().getHotelList().stream()
                .filter(hotel -> Objects.equals(hotel.getCity().getName(),city))
                .toList();
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

    public List<Hotel> getAllHotelFavourite() {
        User user = (User) customUserDetailService.loadUserByUsername((String)session.getAttribute("MY_SESION"));
        return user.getHotelList();
    }
}
