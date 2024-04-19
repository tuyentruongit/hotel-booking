package methodsecuritynew.bookingapp.service;

import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.AmenityHotel;
import methodsecuritynew.bookingapp.entity.Hotel;
import methodsecuritynew.bookingapp.repository.AmenityHotelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AmenityHotelService {

    private final AmenityHotelRepository amenityHotelRepository;


}
