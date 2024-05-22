package methodsecuritynew.bookingapp.service;

import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.City;
import methodsecuritynew.bookingapp.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

    // tìm kiếm thành phố nổi nật Home Page
    public List<City> findCityFavourite() {
         return  cityRepository.findAll().stream()
               .filter(city -> city.getId()<10)
               .toList();
    }

    public List<City> getAllCity() {
            return cityRepository.findAll().stream().sorted(Comparator.comparing(City::getName)).toList();
    }
    public City getCityById (Integer id){
        return  cityRepository.findById(id).orElseThrow(()->new RuntimeException("Không tìm thấy thành phố nào có id : " + id));
    }
}
