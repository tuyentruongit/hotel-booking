package methodsecuritynew.bookingapp.repository;

import methodsecuritynew.bookingapp.entity.Bed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BedRepository extends JpaRepository<Bed,Integer> {
}
