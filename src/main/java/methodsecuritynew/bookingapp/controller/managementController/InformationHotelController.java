package methodsecuritynew.bookingapp.controller.managementController;

import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.Hotel;
import methodsecuritynew.bookingapp.entity.ImageHotel;
import methodsecuritynew.bookingapp.entity.Room;
import methodsecuritynew.bookingapp.service.HotelService;
import methodsecuritynew.bookingapp.service.ImageService;
import methodsecuritynew.bookingapp.service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.awt.*;
import java.util.List;

@Controller
@RequestMapping("/hotel-manager")
@RequiredArgsConstructor
public class InformationHotelController {
    public final HotelService hotelService ;
    public final ImageService imageService ;
    public final RoomService roomService ;


    @GetMapping("/information")
    public String informationHotelDetail(Model model){
        Hotel hotel  = hotelService.getHotelByIdUser();
        List<ImageHotel> imageHotelList = imageService.getAllImageByIdHotel(2);
        List<Room> roomList  = roomService.getRoomByIdHotel(hotel.getId());
//        for (int i = 0; i < roomList.size(); i++) {
//            System.out.println("----------------------------------- nè" + roomList.get(i));
//        }

//        System.out.println("--------------------------------------- nè " +  roomList.get(1).getRoomType().getValue());

        model.addAttribute("roomList" , roomList);
        model.addAttribute("imageHotelList" , imageHotelList);
        model.addAttribute("hotel" , hotel);
        return "/hotel-management/information-hotel";
    }

}
