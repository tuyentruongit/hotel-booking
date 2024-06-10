package methodsecuritynew.bookingapp.controller.managementController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import methodsecuritynew.bookingapp.entity.AmenityRoom;
import methodsecuritynew.bookingapp.entity.Room;
import methodsecuritynew.bookingapp.model.enums.BedSize;
import methodsecuritynew.bookingapp.model.enums.BedType;
import methodsecuritynew.bookingapp.model.enums.RoomType;
import methodsecuritynew.bookingapp.service.AmenityService;
import methodsecuritynew.bookingapp.service.HotelService;
import methodsecuritynew.bookingapp.service.ImageService;
import methodsecuritynew.bookingapp.service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@Slf4j
@Controller
@RequestMapping("/hotel-manager/room")
@RequiredArgsConstructor
public class RoomHotelController {
    private final RoomService roomService;
    private final AmenityService amenityService;
    private final ImageService imageService;
    private final HotelService hotelService;


    @GetMapping()
    public String viewPageRoomHotel(Model model) {
        List<Room> roomList = hotelService.getAllRoom();
        model.addAttribute("roomList", roomList);
        return "hotel-management/room/index";
    }

    @GetMapping("/detail/{id}")
    public String viewRoomDetail(Model model, @PathVariable Integer id) {
        List<AmenityRoom> amenityRoomList = amenityService.getAllAmenityRoom();
        Room room = roomService.getRoomById(id); //  logic này ạ
        model.addAttribute("room", room);
        model.addAttribute("roomHotel", room.getAmenityRoomList());
        model.addAttribute("listRoomType", RoomType.values());
        model.addAttribute("amenityRoomAll", amenityRoomList);
        model.addAttribute("bedType", BedType.values());
        model.addAttribute("bedSize", BedSize.values());
        return "hotel-management/room/detail";
    }
    @GetMapping("/create")
    public String viewRoomCreate(Model model) {
        List<AmenityRoom> amenityRoomList = amenityService.getAllAmenityRoom();
        model.addAttribute("amenityRoom", amenityRoomList);
        model.addAttribute("bedType", BedType.values());
        model.addAttribute("bedSize", BedSize.values());
        model.addAttribute("listRoomType", RoomType.values());
        return "hotel-management/room/create";
    }

}
