package methodsecuritynew.bookingapp.controller.managementController;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/hotel-manager/dashboard")
public class DashboardHotelController {
    @GetMapping()
    public String getDashboardHotel(Model model){
        return "/hotel-management/dashboard/index";
    }
}
