package methodsecuritynew.bookingapp.controller.managementController;

import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.service.BookingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hotel-manager/bookings")
@RequiredArgsConstructor
public class BookingHotelController {
    private final BookingService bookingService;

    @GetMapping()
    public String getBookingByHotel ( Model model){
//        List<Booking> bookingList =  bookingService.getBookingByIdHotel(id);
//        model.addAttribute(bookingList);
        return "/hotel-management/bookings/booking-hotel";
    }
}
