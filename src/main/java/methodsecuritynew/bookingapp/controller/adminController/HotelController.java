package methodsecuritynew.bookingapp.controller.adminController;

import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.City;
import methodsecuritynew.bookingapp.entity.Hotel;
import methodsecuritynew.bookingapp.model.enums.RentalType;
import methodsecuritynew.bookingapp.service.CityService;
import methodsecuritynew.bookingapp.service.HotelService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;


@Controller
@RequestMapping("/admin/hotels")
@RequiredArgsConstructor
public class HotelController {
    private final HotelService hotelService;
    private final CityService cityService ;

    @GetMapping
    public String viewHomePage(Model model, @RequestParam(required = false , defaultValue = "1") Integer page ,
                                            @RequestParam(required = false , defaultValue = "10") Integer limit){
        Page<Hotel> hotelPage = hotelService.getAllHotel(page, limit);
        model.addAttribute("hotelPage" , hotelPage);
        model.addAttribute("currentPage" , page);
        return "admin/hotel/index";
    }

    @GetMapping("/create")
    public String viewCreateHotelPage(Model model){
        List<City> cityList = cityService.getAllCity();
        System.out.println("nè"+cityList);
        model.addAttribute("rentalTypes" , RentalType.values());
        model.addAttribute("cityList" ,cityList);
        return "admin/hotel/create";
    }
    @GetMapping("/{id}/detail")
    public String viewDetailHotelPage(@PathVariable Integer id , Model model){
        Hotel hotel = hotelService.getHotelById(id);
        List<City> cityList = cityService.getAllCity();
        model.addAttribute("hotel" , hotel);
        model.addAttribute("rentalTypes" , RentalType.values());
        model.addAttribute("cityList" ,cityList);
        return "admin/hotel/detail";
    }
}
