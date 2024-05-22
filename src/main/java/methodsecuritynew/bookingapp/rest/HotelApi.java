package methodsecuritynew.bookingapp.rest;

import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.Hotel;
import methodsecuritynew.bookingapp.model.request.UpsertHotelRequest;
import methodsecuritynew.bookingapp.security.CustomUserDetailService;
import methodsecuritynew.bookingapp.service.HotelService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @PostMapping("/admin/create")
    public ResponseEntity<?> createHotelAdmin(@RequestBody UpsertHotelRequest upsertHotelRequest ) {
        Hotel hotel = hotelService.createHotelAdmin(upsertHotelRequest);
        return new ResponseEntity<>(hotel,HttpStatus.CREATED);
    }



    @PostMapping("/favourite/{id}")
    public ResponseEntity<List<Hotel>> saveHotelFavourite(@PathVariable Integer id){
        customUserDetailService.saveHotelFavourite(id);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/favourite/{id}")
    public ResponseEntity<Page<Hotel>> deleteHotelFavourite(@PathVariable Integer id,@RequestParam Integer pageNumber){
//        System.out.println("-------------------- pageabled" + pageable.getPageSize());
//        System.out.println("-------------------- pageabled" + pageable.getOffset());
        Page<Hotel> hotelPage = customUserDetailService.deleteHotelFavourite(id,pageNumber);
        return ResponseEntity.ok(hotelPage);
    }
    @GetMapping()
    public ResponseEntity<?> getPaginationHotel(@RequestParam(required = false , defaultValue = "1") Integer pageNumber ,
                                                          @RequestParam(required = false , defaultValue = "10") Integer limit){
        Page<Hotel> paginationHotel= hotelService.getPaginationHotel(pageNumber,limit);
        return new ResponseEntity<>(paginationHotel, HttpStatus.OK);
    }
    @PutMapping("/admin/update/{id}")
    public ResponseEntity<?> updateHotelAdmin(@PathVariable Integer id , @RequestBody UpsertHotelRequest request) {
        return new ResponseEntity<>( hotelService.updateHotelAdmin(id, request), HttpStatus.OK);
    }
   @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> updateHotelAdmin(@PathVariable Integer id ) {
       System.out.println(id +"---------------------------------------------------------- id nè");
        hotelService.deleteHotel(id);
        return ResponseEntity.noContent().build();
    }


}
