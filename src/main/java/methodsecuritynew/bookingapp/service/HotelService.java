package methodsecuritynew.bookingapp.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.Hotel;
import methodsecuritynew.bookingapp.entity.Room;
import methodsecuritynew.bookingapp.repository.HotelRepository;
import methodsecuritynew.bookingapp.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HotelService {
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;


    public List<Hotel> findOutstandingHotel(String name) {
        return hotelRepository.findHotelByCity_Name(name);
    }

    public List<Hotel> getHotelBySearch(String nameCity, String checkIn, String checkOut, Integer numberGuest, Integer numberRoom) {
        List<Hotel> hotelList = new ArrayList<>();
        for (Hotel hotel : hotelRepository.findHotelByCity_Name(nameCity)){
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
}
