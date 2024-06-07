package methodsecuritynew.bookingapp.repository;

import methodsecuritynew.bookingapp.entity.RoomPrice;
import methodsecuritynew.bookingapp.model.enums.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RoomPriceRepository extends JpaRepository<RoomPrice ,Integer> {
    List<RoomPrice> findAllByDate (LocalDate date);
    RoomPrice findByDateAndRoom_Id (LocalDate date , Integer idRoom);
}
