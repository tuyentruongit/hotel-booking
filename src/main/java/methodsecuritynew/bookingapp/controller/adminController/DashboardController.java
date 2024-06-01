package methodsecuritynew.bookingapp.controller.adminController;
import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.Booking;
import methodsecuritynew.bookingapp.entity.User;
import methodsecuritynew.bookingapp.service.BookingService;
import methodsecuritynew.bookingapp.service.DashboardService;
import methodsecuritynew.bookingapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;
    private final UserService userService;
    private final BookingService bookingService;

    @GetMapping()
    public String getPageDashBoard(Model model){
        List<User> userList = userService.getUserNew();
        List<Booking> bookingList = bookingService.getBookingNew();
        model.addAttribute("userRegisterByMonth" , dashboardService.getUserByMonth(5));
        model.addAttribute("totalBookingMonth" , dashboardService.getBookingByMonth(5));
        model.addAttribute("userList" , userList);
        model.addAttribute("totalUser" , userList.size());
        model.addAttribute("bookingList" , bookingList);
        model.addAttribute("totalBooking" , bookingList.size());
        return "admin/dashboard/index";
    }



}
