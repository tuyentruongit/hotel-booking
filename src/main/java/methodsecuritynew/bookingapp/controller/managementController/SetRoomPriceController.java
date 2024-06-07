package methodsecuritynew.bookingapp.controller.managementController;

import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.Room;
import methodsecuritynew.bookingapp.model.enums.RoomType;
import methodsecuritynew.bookingapp.service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/hotel-manager/room-price")
public class SetRoomPriceController {
    private final RoomService roomService;
    @GetMapping()
    public String getPageRoomPrice (Model model){
        List<Room > roomList = roomService.getRoomHotelManager();
        model.addAttribute("roomList" , roomList);
        return "/hotel-management/price/index";
    }
}
