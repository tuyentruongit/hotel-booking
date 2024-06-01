package methodsecuritynew.bookingapp.rest;

import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.model.dto.RevenueDayDto;
import methodsecuritynew.bookingapp.model.dto.RevenueMonthDto;
import methodsecuritynew.bookingapp.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin/revenue")
@RequiredArgsConstructor
public class RevenueApi {
    private final BookingService bookingService;

    @GetMapping("/day/{year}/{month}")
    public ResponseEntity<List<RevenueDayDto>> getRevenueDayByMonth (@PathVariable Integer year, @PathVariable Integer month){
        return ResponseEntity.ok(bookingService.getRevenueByDay(year,month));
    }

    @GetMapping("/month/{year}")
    public ResponseEntity<List<RevenueMonthDto>> getRevenueMonthByYear (@PathVariable Integer year){
        return ResponseEntity.ok(bookingService.getRevenueByMonth(year));
    }
}
