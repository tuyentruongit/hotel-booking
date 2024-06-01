package methodsecuritynew.bookingapp.controller.managementController;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/hotel-manager/room-price")
public class SetRoomPriceController {
    @GetMapping()
    public String getPageRoomPrice (){
        return "/hotel-management/price/index";
    }
}
