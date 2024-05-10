package methodsecuritynew.bookingapp.rest;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.Hotel;
import methodsecuritynew.bookingapp.security.CustomUserDetail;
import methodsecuritynew.bookingapp.security.CustomUserDetailService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotel")
@RequiredArgsConstructor
public class HotelApi {

    private final CustomUserDetailService customUserDetailService;
    private final HttpSession session;



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
}
