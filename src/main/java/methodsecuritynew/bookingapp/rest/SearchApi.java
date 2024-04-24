package methodsecuritynew.bookingapp.rest;

import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.Hotel;
import methodsecuritynew.bookingapp.service.HotelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
public class SearchApi {
    private final HotelService hotelService;
    @GetMapping("/{city}")
    public ResponseEntity<?> getHotel (@PathVariable String city){
       List<Hotel> hotelList =  hotelService.getHotelHomPage(city);
       return ResponseEntity.ok(hotelList);
    }
}
