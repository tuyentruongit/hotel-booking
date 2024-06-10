package methodsecuritynew.bookingapp.controller.adminController;

import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.*;
import methodsecuritynew.bookingapp.model.enums.AmenityHotelType;
import methodsecuritynew.bookingapp.model.enums.AmenityRoomType;
import methodsecuritynew.bookingapp.model.enums.BedSize;
import methodsecuritynew.bookingapp.model.enums.BedType;
import methodsecuritynew.bookingapp.service.AmenityService;
import methodsecuritynew.bookingapp.service.HotelService;
import methodsecuritynew.bookingapp.service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AmenityController {

    private final AmenityService amenityService;

    @GetMapping("/amenity")
    public String getAmenityBedPage(Model model ){
        List<AmenityRoom> amenityRoomList = amenityService.getAllAmenityRoom();
        List<AmenityHotel> amenityHotelList = amenityService.getAllAmenityHotel();
        model.addAttribute("amenityRoomList" ,amenityRoomList );
        model.addAttribute("amenityHotelList" ,amenityHotelList );
        model.addAttribute("amenityHotelType" , AmenityHotelType.values());
        model.addAttribute("amenityRoomType" , AmenityRoomType.values());
        return "/admin/amenity/index";
    }
}
