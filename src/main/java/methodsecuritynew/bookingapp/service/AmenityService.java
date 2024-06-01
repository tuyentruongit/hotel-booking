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
    private final RoomService roomService;

    public List<AmenityHotel> getAllAmenityHotelByHotel (Integer idHotel){
       return amenityHotelRepository.findAllByHotel_Id(idHotel);
    }


}
