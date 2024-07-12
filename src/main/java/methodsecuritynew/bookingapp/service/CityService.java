package methodsecuritynew.bookingapp.service;

import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.City;
import methodsecuritynew.bookingapp.entity.Hotel;
import methodsecuritynew.bookingapp.exception.BadRequestException;
import methodsecuritynew.bookingapp.model.dto.CityDto;
import methodsecuritynew.bookingapp.model.request.UpsertCityRequest;
import methodsecuritynew.bookingapp.repository.CityRepository;
import methodsecuritynew.bookingapp.repository.HotelRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

    private final HotelRepository hotelRepository;


    // tìm kiếm thành phố nổi nật Home Page
    public List<CityDto> findCityFavourite() {
        System.out.println( "haihaihai"+cityRepository.findAllCitiesOrderByTotalHotelsAsc());
        if (cityRepository.findAllCitiesOrderByTotalHotelsAsc().size()>10){
            return cityRepository.findAllCitiesOrderByTotalHotelsAsc().subList(0,10);
        }
        return cityRepository.findAllCitiesOrderByTotalHotelsAsc();
    }

    // lấy tất cả các thành được tạo trong trang web
    public List<City> getAllCity() {
        return cityRepository.findAll().stream().sorted(Comparator.comparing(City::getName)).toList();
    }

    public City getCityById(Integer id) {
        return cityRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy thành phố nào có id : " + id));
    }

//    public Page<City> getPageCity(Integer page, Integer limit) {
//        PageRequest pageRequest = PageRequest.of(page - 1, limit, Sort.by("name"));
//        return cityRepository.findAll(pageRequest);
//    }

    public City createCity(UpsertCityRequest request) {
        if (cityRepository.findCityByName(request.getNameCity()) != null) {
            throw new BadRequestException("Thành phố trên đã tồn tại");
        }
        City city = City.builder()
                .name(request.getNameCity())
                .country(request.getCountry())
                .createdAt(LocalDate.now())
                .build();

        return cityRepository.save(city);

    }

    public City updateCity(UpsertCityRequest request, Integer id) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không có thành phố nào có id là " + id));

        city.setName(request.getNameCity());
        city.setCountry(request.getCountry());
        city.setUpdatedAt(LocalDate.now());


        return cityRepository.save(city);
    }

    public void deleteCity(Integer id) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không có thành phố nào có id là " + id));
        List<Hotel> hotel = hotelRepository.findHotelByCity_Id(id);
        if (!hotel.isEmpty()){
            throw new BadRequestException("Không thể xóa thành phố này do có khách sạn đang áp dụng");
        }
        cityRepository.delete(city);
    }

    public City getCityByName(String nameCity) {
        return  cityRepository.findCityByName(nameCity);
    }
}
