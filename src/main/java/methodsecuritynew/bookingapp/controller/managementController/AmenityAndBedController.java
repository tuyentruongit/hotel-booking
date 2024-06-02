package methodsecuritynew.bookingapp.controller.managementController;

import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.*;
import methodsecuritynew.bookingapp.model.statics.AmenityHotelType;
import methodsecuritynew.bookingapp.model.statics.AmenityRoomType;
import methodsecuritynew.bookingapp.model.statics.BedSize;
import methodsecuritynew.bookingapp.model.statics.BedType;
import methodsecuritynew.bookingapp.service.AmenityService;
import methodsecuritynew.bookingapp.service.HotelService;
import methodsecuritynew.bookingapp.service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/hotel-manager")
@RequiredArgsConstructor
public class AmenityAndBedController {

    private final AmenityService amenityService;
    private final RoomService roomService;
    private final HotelService hotelService;

    @GetMapping("/amenity-bed")
    public String getAmenityBedPage(Model model ){
        List<AmenityRoom> amenityRoomList = amenityService.getAllAmenityRoomByHotel();
        List<AmenityHotel> amenityHotelList = amenityService.getAllAmenityHotelByHotel();
        model.addAttribute("amenityRoomList" ,amenityRoomList );
        model.addAttribute("amenityHotelList" ,amenityHotelList );
        model.addAttribute("amenityHotelType" , AmenityHotelType.values());
        model.addAttribute("amenityRoomType" , AmenityRoomType.values());
        model.addAttribute("bedSize" , BedSize.values());
        model.addAttribute("bedType" , BedType.values());
        return "/hotel-management/amenity-and-bed/index";
    }
}
