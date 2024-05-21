package methodsecuritynew.bookingapp.rest;

import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.Hotel;
import methodsecuritynew.bookingapp.security.CustomUserDetailService;
import methodsecuritynew.bookingapp.service.HotelService;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotel")
@RequiredArgsConstructor
public class HotelApi {

    private final CustomUserDetailService customUserDetailService;
    private final HotelService hotelService;



    @PostMapping("/favourite/{id}")
    public ResponseEntity<List<Hotel>> saveHotelFavourite(@PathVariable Integer id){
        customUserDetailService.saveHotelFavourite(id);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/favourite/{id}")
    public ResponseEntity<List<Hotel>> deleteHotelFavourite(@PathVariable Integer id){
        customUserDetailService.deleteHotelFavourite(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping()
    public ResponseEntity<?> getPaginationHotel(@RequestParam(required = false , defaultValue = "1") Integer pageNumber ,
                                                          @RequestParam(required = false , defaultValue = "10") Integer limit){
        Page<Hotel> paginationHotel= hotelService.getPaginationHotel(pageNumber,limit);
        return new ResponseEntity<>(paginationHotel, HttpStatus.OK);
    }

}
