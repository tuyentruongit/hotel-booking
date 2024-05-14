package methodsecuritynew.bookingapp.rest;

import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.Booking;
import methodsecuritynew.bookingapp.model.request.UpsertBookingRequest;
import methodsecuritynew.bookingapp.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/booking")
@RequiredArgsConstructor
public class BookingApi {

    private final BookingService bookingService;

    @PostMapping("/add")
    public ResponseEntity<?> bookingHotel(@RequestBody UpsertBookingRequest bookingRequest){
        Booking booking = bookingService.bookingHotel(bookingRequest);
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getBookingById(@PathVariable Integer id){
       return ResponseEntity.ok(bookingService.getBookingById(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBookingById(@PathVariable Integer id){
        bookingService.deleteBooking(id);
        return ResponseEntity.ok().build();
    }
}
