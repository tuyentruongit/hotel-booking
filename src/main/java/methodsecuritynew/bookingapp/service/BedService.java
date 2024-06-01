package methodsecuritynew.bookingapp.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.Bed;
import methodsecuritynew.bookingapp.entity.Hotel;
import methodsecuritynew.bookingapp.entity.User;
import methodsecuritynew.bookingapp.repository.BedRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BedService {
    private final BedRepository bedRepository;
    private final HttpSession httpSession;
    private final HotelService hotelService;


    public List<Bed> getAllBedByHotel() {
        Hotel hotel = hotelService.getHotelByIdUser();
            return bedRepository.findByHotel_Id(hotel.getId());
    }

}
