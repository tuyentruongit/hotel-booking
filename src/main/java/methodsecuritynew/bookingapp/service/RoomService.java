package methodsecuritynew.bookingapp.service;

import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.Room;
import methodsecuritynew.bookingapp.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;

    public List<Room> getRoomByIdHotel(Integer id) {
        return roomRepository.findRoomByHotel_Id(id);
    }
}
