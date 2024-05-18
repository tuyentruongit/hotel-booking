package methodsecuritynew.bookingapp.repository;

import methodsecuritynew.bookingapp.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image,String> {
}
