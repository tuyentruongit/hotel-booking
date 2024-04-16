package methodsecuritynew.bookingapp.repository;

import methodsecuritynew.bookingapp.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel,Integer> {
}
