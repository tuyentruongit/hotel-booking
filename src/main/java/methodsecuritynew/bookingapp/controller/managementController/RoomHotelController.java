package methodsecuritynew.bookingapp.controller.managementController;

import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.Room;
import methodsecuritynew.bookingapp.model.statics.RoomType;
import methodsecuritynew.bookingapp.service.AmenityService;
import methodsecuritynew.bookingapp.service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/hotel-manager/room")
@RequiredArgsConstructor
public class RoomHotelController {
    private final RoomService roomService;
    private final AmenityService amenityService;

    @GetMapping()
    public String viewPageRoomHotel(Model model) {
        List<Room> roomList = roomService.getRoomHotelManager();
        model.addAttribute("roomList", roomList);
        return "hotel-management/room/index";
    }

    @GetMapping("/detail/{id}")
    public String viewRoomDetail(Model model, @PathVariable Integer id) {
        Room room = roomService.getRoomById(id);
        model.addAttribute("room", room);
        model.addAttribute("listRoomType", RoomType.values());
        model.addAttribute("amenityRoom", room.getAmenityRoomList());

        return "hotel-management/room/detail";
    }
    @GetMapping("/create")
    public String viewRoomCreate(Model model) {
        Room room = roomService.getRoomById(4);
        model.addAttribute("listRoomType", RoomType.values());
        model.addAttribute("amenityRoom", room.getAmenityRoomList());
        return "hotel-management/room/create";
    }

}
