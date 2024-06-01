package methodsecuritynew.bookingapp.service;

import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.User;
import methodsecuritynew.bookingapp.exception.BadRequestException;
import methodsecuritynew.bookingapp.exception.ResourceNotFoundException;
import methodsecuritynew.bookingapp.model.request.AdminUpdateUserRequest;
import methodsecuritynew.bookingapp.model.request.CreateUserRequest;
import methodsecuritynew.bookingapp.model.statics.UserRole;
import methodsecuritynew.bookingapp.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public User adminResetPasswordUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy user"));

        user.setPassword(passwordEncoder.encode("123"));

        return userRepository.save(user);
    }
    public User adminUpdateUser(Integer id, AdminUpdateUserRequest request) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy user có id = " + id));

        existingUser.setName(request.getName());
        existingUser.setPhoneNumber(request.getPhone());
        existingUser.setUserRole(UserRole.valueOf(request.getRole()));
        return userRepository.save(existingUser);
    }

    public User createUser(CreateUserRequest request) {

        // find user by email -> throw exception if exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BadRequestException("Email đã tồn tại");

        }
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phoneNumber(request.getPhone())
                .password(passwordEncoder.encode("123"))
                .avatar("/web/assets/image/avata-default.jpg")
                .userRole(UserRole.valueOf(request.getRole()))
                .enable(false)
                .createdAt(LocalDate.now())

                .build();
        return userRepository.save(user);
    }

    public List<User> getUserNew() {
        LocalDate star = LocalDate.now().withDayOfMonth(1);
        LocalDate end = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
            return  userRepository.findUserByCreatedAtBetweenAndUserRoleOrderByCreatedAtDesc(star,end,UserRole.ROLE_USER);
    }

    public int totalUser() {
      return   userRepository.findAll().size();
    }
}
