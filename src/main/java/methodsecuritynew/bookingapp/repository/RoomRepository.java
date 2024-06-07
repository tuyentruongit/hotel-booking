package methodsecuritynew.bookingapp.repository;

import methodsecuritynew.bookingapp.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room,Integer> {

    List<Room> findRoomByHotel_Id(Integer id);

    Room findRoomByName(String id);
}
