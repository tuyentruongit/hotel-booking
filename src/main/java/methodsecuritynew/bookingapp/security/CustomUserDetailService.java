package methodsecuritynew.bookingapp.security;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.Hotel;
import methodsecuritynew.bookingapp.entity.User;
import methodsecuritynew.bookingapp.exception.BadRequestException;
import methodsecuritynew.bookingapp.model.request.UpsertHotelRequest;
import methodsecuritynew.bookingapp.model.statics.UserRole;
import methodsecuritynew.bookingapp.repository.HotelRepository;
import methodsecuritynew.bookingapp.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;
    private final HttpSession session;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User Not Found with Email : " + email));
        return new  CustomUserDetail(user);
    }

    public void saveHotelFavourite(Integer id) {
        User user = userRepository.findByEmail((String) session.getAttribute("MY_SESSION")).orElseThrow(()-> new UsernameNotFoundException("User Not Found "));
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách sạn trên"));
        user.getHotelList().add(hotel);
        userRepository.save(user);
    }
    public Page<Hotel> deleteHotelFavourite(Integer id, Integer pageNumber) {
        if (pageNumber < 1){
           throw  new RuntimeException("Số trang không hợp lệ ");
        }
        Pageable pageable = PageRequest.of(pageNumber-1,8);
        User user = userRepository.findByEmail((String) session.getAttribute("MY_SESSION")).orElseThrow(()-> new UsernameNotFoundException("User Not Found "));
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách sạn trên"));

        if (!user.getHotelList().contains(hotel)) {
            throw new IllegalStateException("Khách sạn trên không phải khách sạn được yêu thích của người dùng");
        }
        user.getHotelList().remove(hotel);
        userRepository.save(user);
        List<Hotel> favouriteHotels = user.getHotelList();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), favouriteHotels.size());

        return PageableExecutionUtils.getPage(favouriteHotels.subList(start,end),pageable, favouriteHotels::size);
    }

}
