package methodsecuritynew.bookingapp.controller;

import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.AmenityHotel;
import methodsecuritynew.bookingapp.entity.City;
import methodsecuritynew.bookingapp.entity.Hotel;
import methodsecuritynew.bookingapp.service.AmenityHotelService;
import methodsecuritynew.bookingapp.service.CityService;
import methodsecuritynew.bookingapp.service.HotelService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class WebController {
    private final CityService cityService;
    private final HotelService hotelService;
    private final AmenityHotelService amenityHotelService;

    @GetMapping("/")
    public String getHome(Model model) {
        List<City> cityList = cityService.findCityFavourite();

//        List<Hotel> outstandingHotelHa = hotelService.findOutstandingHotel("Hà Nội");
//        List<Hotel> outstandingHotelDa= hotelService.findOutstandingHotel("Đà Nẵng");
//        List<Hotel> outstandingHotelHo = hotelService.findOutstandingHotel("Hồ Chí Minh");
//        List<Hotel> outstandingHotelVu = hotelService.findOutstandingHotel("Vũng Tàu");
//        List<Hotel> outstandingHotelHg = hotelService.findOutstandingHotel("Hà Giang");


        model.addAttribute("cityFavourite", cityList);
//        model.addAttribute("outstandingHotelHa", outstandingHotelHa);
//        model.addAttribute("outstandingHotelDa", outstandingHotelDa);
//        model.addAttribute("outstandingHotelHg", outstandingHotelHg);
//        model.addAttribute("outstandingHotelVu", outstandingHotelVu);
//        model.addAttribute("outstandingHotelHo", outstandingHotelHo);

        return "web/home";
    }

    @GetMapping("/danh-sach-khach-san")
    public String getHotelList(@RequestParam String nameCity,
                               @RequestParam(required = false) String checkIn,
                               @RequestParam(required = false) String checkOut,
                               @RequestParam(required = false,defaultValue = "1") Integer numberGuest,
                               @RequestParam(required = false , defaultValue = "1") Integer numberRoom,
                               Model model) {

       List<Hotel> hotelList = hotelService.getHotelBySearch(nameCity,checkIn,checkOut,numberGuest,numberRoom);

       model.addAttribute("nameCity" , nameCity);

       model.addAttribute("hotelList",hotelList);

        return "web/hotel-list";
    }


    @GetMapping("/chi-tiet-khach-san/{id}")
    public String getHotelDetail(@PathVariable Integer id, Model model) {

        Hotel hotel = hotelService.getHotelById(id);
        model.addAttribute("hotel" , hotel);




        return "web/hotel-detail";
    }
}
