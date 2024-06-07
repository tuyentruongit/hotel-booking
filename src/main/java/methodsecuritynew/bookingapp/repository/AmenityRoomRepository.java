package methodsecuritynew.bookingapp.repository;

import methodsecuritynew.bookingapp.entity.AmenityRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AmenityRoomRepository extends JpaRepository<AmenityRoom, Integer> {

    List<AmenityRoom> findAmenityRoomByHotel_Id(int id);

    AmenityRoom findByName(String name);
}
