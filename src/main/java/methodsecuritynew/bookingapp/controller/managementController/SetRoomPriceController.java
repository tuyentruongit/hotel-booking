package methodsecuritynew.bookingapp.controller.managementController;

import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.Room;
import methodsecuritynew.bookingapp.entity.RoomPrice;
import methodsecuritynew.bookingapp.model.enums.RoomType;
import methodsecuritynew.bookingapp.repository.RoomPriceRepository;
import methodsecuritynew.bookingapp.service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/hotel-manager/room-price")
public class SetRoomPriceController {
    private final RoomService roomService;
    private final RoomPriceRepository roomPriceRepository;
    @GetMapping()
    public String getPageRoomPrice (Model model){
        List<Room > roomList = roomService.getRoomHotelManager();
        System.out.println("Có vào đây không 1");
        model.addAttribute("roomList" , roomList);
//        RoomPrice roomPrice = roomPriceRepository.findById(1).orElseThrow(()-> new RuntimeException("Không tìm thấy"));
//        roomPriceRepository.delete(roomPrice);
        System.out.println("Có vào đây không 2");
        return "/hotel-management/price/index";
    }
}
