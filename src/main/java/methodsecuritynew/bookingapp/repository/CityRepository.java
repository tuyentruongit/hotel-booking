package methodsecuritynew.bookingapp.repository;

import methodsecuritynew.bookingapp.entity.City;
import methodsecuritynew.bookingapp.model.dto.CityDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface CityRepository extends JpaRepository<City,Integer> {
    City findCityByName(String name);
    @Query("SELECT NEW methodsecuritynew.bookingapp.model.dto.CityDto(COALESCE(o.name, 'Unknown'), COALESCE(o.imageCity, 'default_image_url'), COUNT(h)) " +
            "FROM City o LEFT JOIN Hotel h ON h.city = o " +
            "GROUP BY o.name, o.imageCity " +
            "ORDER BY COUNT(h) DESC")
    List<CityDto> findAllCitiesOrderByTotalHotelsAsc();
}
