package methodsecuritynew.bookingapp.security;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.Hotel;
import methodsecuritynew.bookingapp.entity.User;
import methodsecuritynew.bookingapp.repository.HotelRepository;
import methodsecuritynew.bookingapp.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;
    private final HttpSession session;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("Không tìm thấy user có Email là : " + email));
        return new  CustomUserDetail(user);
    }


}
