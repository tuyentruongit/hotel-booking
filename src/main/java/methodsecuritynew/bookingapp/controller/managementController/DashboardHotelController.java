package methodsecuritynew.bookingapp.controller.managementController;

import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.Hotel;
import methodsecuritynew.bookingapp.model.dto.RevenueMonthDto;
import methodsecuritynew.bookingapp.model.dto.TotalBookingMonthDto;
import methodsecuritynew.bookingapp.service.BookingService;
import methodsecuritynew.bookingapp.service.HotelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@RequestMapping("/hotel-manager/dashboard")
public class DashboardHotelController {
    private final HotelService hotelService;
    private final BookingService bookingService;
    @GetMapping()
    public String getDashboardHotel(Model model){
        Hotel hotel = hotelService.getHotelByAccountCurrent();
        // Tổng booking theo tháng
        TotalBookingMonthDto totalBookingMonthDto   = bookingService.getTotalBookingByMonCurrent(
                LocalDate.now().getMonthValue(),LocalDate.now().getYear(),hotel.getId());
        // tổng booking đang chờ xác nhận
        TotalBookingMonthDto totalBookingMonthDtoPending = bookingService.getTotalBookingPendingMonthByHotel(hotel.getId());
        long totalRevenueYear = bookingService.getTotalRevenueYearCurrent(hotel.getId(),LocalDate.now().getYear());
        RevenueMonthDto totalRevenueMonth = bookingService.getTotalRevenueMonth(hotel.getId(),LocalDate.now().getYear(),LocalDate.now().getMonthValue());

        System.out.println(totalBookingMonthDto + " Tổng doanh thu nè ");
        System.out.println(totalBookingMonthDtoPending + " Tổng doanh thu nè ");

        model.addAttribute("hotel" , hotel);
        model.addAttribute("totalRevenueYear" , totalRevenueYear);
        model.addAttribute("totalRevenueMonth" , totalRevenueMonth);
        model.addAttribute("totalBookingMonthDto" , totalBookingMonthDto);
        model.addAttribute("totalBookingMonthDtoPending" , totalBookingMonthDtoPending);
        return "/hotel-management/dashboard/index";
    }
}
