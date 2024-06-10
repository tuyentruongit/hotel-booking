package methodsecuritynew.bookingapp.controller.managementController;

import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.PolicyHotel;
import methodsecuritynew.bookingapp.service.HotelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/hotel-manager")
public class PolicyHotelController {

    private final HotelService hotelService;

    @GetMapping("/policy")
    public String getPagePolicyHotel(Model  model){
        PolicyHotel policyHotel = hotelService.getHotelByAccountCurrent().getPolicyHotel();
        model.addAttribute("policy" ,policyHotel);
        return "hotel-management/policy-hotel/index";
    }

}
