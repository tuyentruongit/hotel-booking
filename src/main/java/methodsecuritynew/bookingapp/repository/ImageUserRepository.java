package methodsecuritynew.bookingapp.repository;

import methodsecuritynew.bookingapp.entity.Image;
import methodsecuritynew.bookingapp.entity.ImageUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageUserRepository extends JpaRepository<ImageUser, String> {
   ImageUser findAllByUser_Id(Integer id);
}
//public interface ImageUserRepository extends JpaRepository<ImageUser, String> {
//    List<ImageUser> findAllByUserId (Integer userId);
//}