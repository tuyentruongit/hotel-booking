package methodsecuritynew.bookingapp.controller;

import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.*;
import methodsecuritynew.bookingapp.model.statics.SupportType;
import methodsecuritynew.bookingapp.service.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class WebController {
    private final CityService cityService;
    private final HotelService hotelService;
    private final RoomService roomService;
    private final SupportService supportService;


    @GetMapping("/")
    public String getHome(Model model) {
        List<City> cityList = cityService.findCityFavourite();
        model.addAttribute("cityFavourite", cityList);
        return "web/home";
    }

    @GetMapping("/danh-sach-khach-san")
    public String getHotelList(@RequestParam String nameCity,
                               @RequestParam(required = false) String checkIn,
                               @RequestParam(required = false) String checkOut,
                               @RequestParam(required = false, defaultValue = "1") Integer numberGuest,
                               @RequestParam(required = false, defaultValue = "1") Integer numberRoom,
                               Model model) {

        List<Hotel> hotelList = hotelService.getHotelBySearch(nameCity, checkIn, checkOut, numberGuest, numberRoom);
        model.addAttribute("nameCity", nameCity);
        model.addAttribute("checkIn", checkIn);
        model.addAttribute("checkOut", checkOut);
        model.addAttribute("numberGuest", numberGuest);
        model.addAttribute("numberRoom", numberRoom);
        model.addAttribute("hotelList", hotelList);

        return "web/hotel-list";
    }


    @GetMapping("/chi-tiet-khach-san/{id}")
    public String getHotelDetail(@PathVariable Integer id, Model model) {
        Hotel hotel = hotelService.getHotelById(id);
        List<Room> roomList = roomService.getRoomByIdHotel(id);
        model.addAttribute("hotel", hotel);
        model.addAttribute("roomList", roomList);
        return "web/hotel-detail";
    }


    @GetMapping("/support")
    public String getSupport(Model model) {
        List<SupportType> supportTypes = List.of(SupportType.values());

        List<Support> supportList = supportService.getAllSupport();
        model.addAttribute("supportTypes", supportTypes);
        model.addAttribute("supportList", supportList);
        return "web/support";
    }
    @GetMapping("/thong-tin-khach-hang")
    public String getProfile(Model model) {
        return "web/profile-user";
    }

    @GetMapping("/yeu-thich")
    public String getFavouriteHotel(Model model) {
        return "web/favourite";
    }

    @GetMapping("/danh-sach-dat-phong")
    public String getHistoryBooking(Model model) {
        return "web/booking";
    }
    @GetMapping("/chi-tiet-booking")
    public String getBookingDetail(Model model) {
        return "web/booking-detail";
    }

    @GetMapping("/thanh-toan")
    public String getPayment(Model model) {
        return "web/thong-tin-thanh-toan";
    }
    @GetMapping("/danh-sach-yeu-thich")
    public String getFavouriteList(Model model) {
        return "web/favourite-list";
    }
    @GetMapping("/account/login")
    public String getLogin(Model model) {
        return "web/auth/login";
    }
    @GetMapping("/account/dang-ky")
    public String getRegister(Model model) {
        return "web/auth/register";
    }
    @GetMapping("/account/quen-mat-khau")
    public String getForgotPassword(Model model) {
        return "web/auth/forgot-password";
    }

}
