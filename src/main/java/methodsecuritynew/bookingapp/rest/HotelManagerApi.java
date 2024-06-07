package methodsecuritynew.bookingapp.rest;

import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.model.request.UpsertPolicyRequest;
import methodsecuritynew.bookingapp.service.BookingService;
import methodsecuritynew.bookingapp.service.PolicyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/hotel-manager/")
@RequiredArgsConstructor
public class HotelManagerApi {

    private final BookingService bookingService;
    private final PolicyService policyService;

    @GetMapping("/revenue-day/{idHotel}")
    public ResponseEntity<?> getRevenueMonthByHotel (@RequestParam Integer month
            , @RequestParam Integer year, @PathVariable Integer idHotel){
        return ResponseEntity.ok(bookingService.getRevenueByMonthAndHotel(idHotel,month,year));

    }
    @GetMapping("/revenue-month/{idHotel}")
    public ResponseEntity<?> getRevenueYearByHotel ( @RequestParam Integer year, @PathVariable Integer idHotel){
        System.out.println("hàm ở đây à");
        return ResponseEntity.ok(bookingService.getRevenueByYearAndHotel(idHotel,year));

    }
    @GetMapping("/total-booking/{idHotel}")
    public ResponseEntity<?> getTotalBookingByNameRoom (@RequestParam Integer year, @PathVariable Integer idHotel){
        return ResponseEntity.ok(bookingService.getTotalBookingByRoomType(idHotel,year));

    }
    @PostMapping("/update-policy")
    public ResponseEntity<?> updatePolicyHotel ( UpsertPolicyRequest request){
        policyService.updatePolicyHotel(request);
        return ResponseEntity.noContent().build();


    }
}
