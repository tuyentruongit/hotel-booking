package methodsecuritynew.bookingapp.rest;

import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.Hotel;
import methodsecuritynew.bookingapp.model.dto.HotelDto;
import methodsecuritynew.bookingapp.service.HotelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
public class SearchApi {
    private final HotelService hotelService;
    @GetMapping("/{city}")
    public ResponseEntity<?> getHotel ( @PathVariable String city,
                                        @RequestParam(name = "checkIn") String checkIn,
                                        @RequestParam(name = "checkOut") String checkOut,
                                        @RequestParam(required = false, defaultValue = "1") Integer numberGuest,
                                        @RequestParam(required = false, defaultValue = "1") Integer numberRoom ){
        List<HotelDto> hotelList =  hotelService.getHotelHomPage(city,checkIn,checkOut,numberGuest,numberRoom);
       return ResponseEntity.ok(hotelList);
    }
}
