package methodsecuritynew.bookingapp.repository;

import methodsecuritynew.bookingapp.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel,Integer> {
    List<Hotel> findHotelByCity_NameIgnoreCase(String name);
}

