package methodsecuritynew.bookingapp.controller.adminController;

import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.City;
import methodsecuritynew.bookingapp.service.CityService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/city")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;
    @GetMapping()
    public String viewPageCity(Model model){
        model.addAttribute("cityList", cityService.getAllCity());
        return "/admin/city/index" ;
    }
}
