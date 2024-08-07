package methodsecuritynew.bookingapp.repository;

import methodsecuritynew.bookingapp.entity.Hotel;
import methodsecuritynew.bookingapp.model.dto.RevenueDayDto;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel,Integer> {
    List<Hotel> findHotelByCity_NameIgnoreCaseAndStatusTrue(String name);
    List<Hotel> findHotelByCity_Id(Integer id);
    List<Hotel> findAllByCreatedAtBetweenOrderByCreatedAtDesc(LocalDate start , LocalDate end);
    Hotel findHotelByName(String name);
    Hotel findHotelByUser_Id(Integer id);
    Hotel findByHotline (String hotline);
    long countByAmenityHotelList_Id(Integer id);


}

