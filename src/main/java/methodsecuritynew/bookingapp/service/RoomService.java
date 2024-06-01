package methodsecuritynew.bookingapp.service;

import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.AmenityRoom;
import methodsecuritynew.bookingapp.entity.Hotel;
import methodsecuritynew.bookingapp.entity.Room;
import methodsecuritynew.bookingapp.entity.User;
import methodsecuritynew.bookingapp.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final AuthService authService;
    private final HotelService hotelService;


    public List<Room> getRoomByIdHotel(Integer id) {
        return roomRepository.findRoomByHotel_Id(id);
    }

    public Room getRoomById(Integer idRoom) {

        return  roomRepository.findById(idRoom).orElseThrow(()-> new RuntimeException("Không tìm thấy phòng có id: " + idRoom));
    }

    public List<Room> getRoomHotelManager() {
        Hotel hotel = hotelService.getHotelByIdUser();
        return roomRepository.findRoomByHotel_Id(hotel.getId());
    }

    public List<AmenityRoom> getAmenityRoomByIdHotel(Integer id) {
        List<Room> roomList = getRoomByIdHotel(id);
        List<AmenityRoom> amenityRoomList = new ArrayList<>();
        for (Room room : roomList) {
            amenityRoomList.addAll(room.getAmenityRoomList());
        }
        return amenityRoomList;
    }
}
