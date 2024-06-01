package methodsecuritynew.bookingapp.controller.adminController;

import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.model.dto.RevenueDayDto;
import methodsecuritynew.bookingapp.model.dto.RevenueMonthDto;
import methodsecuritynew.bookingapp.service.BookingService;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/revenue")
public class RevenueController {
    private final BookingService bookingService ;
    @GetMapping
    public String pageRevenue (Model model){
        List<RevenueDayDto> revenueDtoListDay = bookingService.getRevenueByDay(LocalDate.now().getYear(), LocalDate.now().getMonthValue());
        int revenueYear = bookingService.sumTotalRevenueYear(LocalDate.now().getYear());
        int revenueMonth = bookingService.sumTotalMonthCurrent(LocalDate.now().getYear(),LocalDate.now().getMonthValue());
        model.addAttribute("revenueDay" , revenueDtoListDay.get(LocalDate.now().getDayOfMonth()-1));
        model.addAttribute("revenueYear" ,revenueYear);
        model.addAttribute("revenueMonth" ,revenueMonth);
        return "/admin/revenue/index" ;
    }
}
