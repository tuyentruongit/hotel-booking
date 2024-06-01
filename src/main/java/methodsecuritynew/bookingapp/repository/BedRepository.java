package methodsecuritynew.bookingapp.repository;

import methodsecuritynew.bookingapp.entity.Bed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BedRepository extends JpaRepository<Bed,Integer> {

    List<Bed> findByHotel_Id(Integer id);
}
