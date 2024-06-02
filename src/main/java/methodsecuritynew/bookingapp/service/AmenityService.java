package methodsecuritynew.bookingapp.service;

import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.AmenityHotel;
import methodsecuritynew.bookingapp.entity.AmenityRoom;
import methodsecuritynew.bookingapp.entity.Hotel;
import methodsecuritynew.bookingapp.entity.Room;
import methodsecuritynew.bookingapp.repository.AmenityHotelRepository;
import methodsecuritynew.bookingapp.repository.AmenityRoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AmenityService {

    private final AmenityHotelRepository amenityHotelRepository;
    private final AmenityRoomRepository amenityRoomRepository;
    private final HotelService hotelService;

    public List<AmenityHotel> getAllAmenityHotelByHotel (){
        Hotel hotel = hotelService.getHotelByAccountCurrent();
       return amenityHotelRepository.findAllByHotel_Id(hotel.getId());
    }


    public List<AmenityRoom> getAllAmenityRoomByHotel() {
        Hotel hotel = hotelService.getHotelByAccountCurrent();
        return amenityRoomRepository.findAmenityRoomByHotel_Id(hotel.getId());
    }
}
