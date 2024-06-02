package methodsecuritynew.bookingapp.service;

import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.model.dto.TotalBookingMonthDto;
import methodsecuritynew.bookingapp.model.dto.RegisterDto;
import methodsecuritynew.bookingapp.repository.BookingRepository;
import methodsecuritynew.bookingapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    public List<RegisterDto> getUserByMonth (int limit){
        List<RegisterDto> userDtos = userRepository.findUserRegistrationsByMonthAndYear();
        if (userDtos.size()>limit){
            return userDtos.subList(0,limit);
        }
        return userDtos;
    }
    public List<TotalBookingMonthDto> getBookingByMonth (int limit){
        List<TotalBookingMonthDto> totalBookingMonthDto = bookingRepository.finTotalBookingMonth();
        if (totalBookingMonthDto.size()>limit){
            return totalBookingMonthDto.subList(0,limit);
        }
        return totalBookingMonthDto;
    }



}
