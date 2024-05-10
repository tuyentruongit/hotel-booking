package methodsecuritynew.bookingapp.security;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.User;
import methodsecuritynew.bookingapp.repository.HotelRepository;
import methodsecuritynew.bookingapp.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
        user.getHotelList().add(hotelRepository.findById(id).get());
        userRepository.save(user);
    }
    public void deleteHotelFavourite(Integer id) {
        User user = userRepository.findByEmail((String) session.getAttribute("MY_SESSION")).orElseThrow(()-> new UsernameNotFoundException("User Not Found "));
        user.getHotelList().remove(hotelRepository.findById(id).get());
        userRepository.save(user);
    }
}
