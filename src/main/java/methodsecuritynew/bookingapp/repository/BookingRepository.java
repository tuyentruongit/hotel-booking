package methodsecuritynew.bookingapp.repository;

import methodsecuritynew.bookingapp.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,Integer> {
    List<Booking> findAllByUser_IdOrderByCreateAtDesc(Integer id);
}
