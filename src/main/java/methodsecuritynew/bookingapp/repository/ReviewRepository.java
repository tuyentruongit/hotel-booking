package methodsecuritynew.bookingapp.repository;

import methodsecuritynew.bookingapp.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review , Integer> {
    List<Review> findAllByHotel_IdOrderByCreateAtDesc(Integer id);
}
