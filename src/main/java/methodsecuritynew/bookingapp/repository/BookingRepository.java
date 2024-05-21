package methodsecuritynew.bookingapp.repository;

import methodsecuritynew.bookingapp.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,Integer> {
    Page<Booking> findAllByUser_IdOrderByCreateAtDesc(Integer id , Pageable pageable);

}
