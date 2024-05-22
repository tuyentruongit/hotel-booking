package methodsecuritynew.bookingapp.service;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.TokenConfirm;
import methodsecuritynew.bookingapp.entity.User;
import methodsecuritynew.bookingapp.exception.BadRequestException;
import methodsecuritynew.bookingapp.model.request.*;

import methodsecuritynew.bookingapp.model.response.VerifyAccountResponse;
import methodsecuritynew.bookingapp.model.statics.Gender;
import methodsecuritynew.bookingapp.model.statics.TokenType;
import methodsecuritynew.bookingapp.model.statics.UserRole;
import methodsecuritynew.bookingapp.repository.TokenConfirmRepository;
import methodsecuritynew.bookingapp.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {



    private final HttpSession httpSession;

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final TokenConfirmRepository tokenConfirmRepository;
    private final MailService mailService;

    public void login(LoginRequest loginRequest) {
        // Taạo đối tượng xác thực
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword());

        try {
            // Tiến hành xác tực
            Authentication authentication = authenticationManager.authenticate(token);
//            // lưu vào securityContextHolder
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // lưu vào session
            httpSession.setAttribute("MY_SESSION",authentication.getName());

        }
        catch(DisabledException disabledException){
            throw new DisabledException("Tài khoản chưa được xác thực");
        }
        catch (AuthenticationException  e){
           throw new RuntimeException ("Tài khoản hoặc mật khẩu không đúng");
        }
    }

    @Transactional
    public String register(RegisterRequest registerRequest) {
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()){
            throw new BadRequestException("Email đã tồn tại");
        }
        User user = User.builder()
                .name(registerRequest.getName())
                .userRole(UserRole.ROLE_USER)
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getEmail())
                .createdAt(LocalDate.now())
                .avatar("/web/assets/image/avatar-default.jpg")
                .enable(false)
                .build();
        userRepository.save(user);
        TokenConfirm tokenConfirm = TokenConfirm.builder()
                .nameToken(UUID.randomUUID().toString())
                .tokenType(TokenType.REGISTRATION)
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusHours(1))
                .user(user)
                .build();

        tokenConfirmRepository.save(tokenConfirm);
        // link xác thực gửi đến email
        String link = "http://localhost:9000/account/xac-minh-tai-khoan?token=" + tokenConfirm.getNameToken();
        mailService.sendMail(user.getEmail(),
                "Xác thực tài khoản",
                "Chào " +user.getName()+"! \n" +
                        "\n" +
                        "Chúng tôi xác nhận rằng bạn đã đăng ký tài khoản thành công tại StayEase.\n" +
                        "\n" +
                        "Xin vui lòng nhấp vào liên kết sau để xác nhận đăng ký của bạn và kích hoạt tài khoản:\n" +
                        "\n" +
                        link+"\n" +
                        "\n" +
                        "Trân trọng.\n" );
        return "Vui lòng kiểm tra email để xác thực tài khoản";


    }


    @Transactional
    public VerifyAccountResponse verifyAccount(String token) {
        Optional<TokenConfirm> optionalTokenConfirm = tokenConfirmRepository
                .findByNameTokenAndTokenType(token, TokenType.REGISTRATION);

        if (optionalTokenConfirm.isEmpty()){
            return VerifyAccountResponse.builder()
                    .success(false)
                    .message("Link xác thực không tồn tại")
                    .build();
        }
        TokenConfirm tokenConfirm = optionalTokenConfirm.get();
        if (tokenConfirm.getExpiredAt().isBefore(LocalDateTime.now())){
            return VerifyAccountResponse.builder()
                    .success(false)
                    .message("Link xác thực đã hết hạn ")
                    .build();
        }
        if (tokenConfirm.getConfirmedAt()!=null){
            return VerifyAccountResponse.builder()
                    .success(false)
                    .message("Link xác thực đã được sử dụng trước đó")
                    .build();
        }
        tokenConfirm.getUser().setEnable(true);
        userRepository.save(tokenConfirm.getUser());

        tokenConfirm.setConfirmedAt(LocalDateTime.now());
        tokenConfirmRepository.save(tokenConfirm);
            return VerifyAccountResponse.builder()
                    .success(true)
                    .message("Xác thực tài khoản thành công")
                    .build();
    }


    @Transactional
    public void updateUser(Integer id, ChangeInformationUserRequest changeInformationUserRequest) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Không thể tìm thấy user trên ") );
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/M/yyyy");
        LocalDate birthday = LocalDate.parse(changeInformationUserRequest.getBirthDay(),dateTimeFormatter);
        user.setAddress(changeInformationUserRequest.getAddress());
        user.setName(changeInformationUserRequest.getName());
        String regex = "^0([0-9]{8})";
        if (changeInformationUserRequest.getPhone().matches(regex)){
            user.setPhoneNumber(changeInformationUserRequest.getPhone());
        }else {
            throw new BadRequestException("Số điện thoại của bạn không hợp lệ ");
        }
        user.setUpdateAt(LocalDate.now());
        user.setBirthDay(birthday);
        Gender gender  = Gender.valueOf(changeInformationUserRequest.getGender());
        user.setGender(gender);
        userRepository.save(user);
    }

    public void changePassword(Integer id, ChangePasswordRequest changePasswordRequest) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy user") );

        // kiểm tra xem mật khẩu có đúng với mật khẩu được lưu trong db không
        if (passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())){
            // kiểm tra xem 2 mật khẩu có giống nhau hay không
            if (changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())){
                user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
                userRepository.save(user);
            }
            else {
                throw new BadRequestException("Mật khẩu không trùng khớp");
            }

        }else {
            throw new BadRequestException("Mật khẩu không chính xác");
        }

    }
    public void changePasswordForForgetPassword(UpsertPasswordRetrieval upsertPasswordRetrieval) {
        TokenConfirm tokenConfirm = tokenConfirmRepository
                .findByNameTokenAndTokenType(upsertPasswordRetrieval.getNameToken(), TokenType.FORGOT_PASSWORD).orElseThrow(()-> new BadRequestException("Không tìm thấy token trên "));
        tokenConfirm.getUser().setPassword(passwordEncoder.encode(upsertPasswordRetrieval.getPassword()));
        tokenConfirm.setConfirmedAt(LocalDateTime.now());
        userRepository.save(tokenConfirm.getUser());
    }


    public void forgotPassword(String email) {
       User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy user"));

        TokenConfirm tokenConfirm = TokenConfirm.builder()
                .nameToken(UUID.randomUUID().toString())
                .tokenType(TokenType.FORGOT_PASSWORD)
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusHours(1))
                .user(user)
                .build();
        tokenConfirmRepository.save(tokenConfirm);
        String link = "http://localhost:9000/account/quen-mat-khau?token=" + tokenConfirm.getNameToken();
        mailService.sendMail(user.getEmail(),
                "Quên mật khẩu ",
                "Chào " +user.getName()+"! \n" +
                        "\n" +
                        "Chúng tôi xác nhận rằng bạn đã yêu cầu cấp lại mật khẩu cho tài khoản sử dụng email này.\n" +
                        "\n" +
                        "Xin vui lòng nhấp vào liên kết sau để chuyến tới trang lấy lại mật khẩu cho bạn:\n" +
                        "\n" +
                        link+"\n" +
                        "\n" +
                        "Trân trọng.\n" );
    }

    public String verifyForgotPassword(String token) {
        Optional<TokenConfirm> optionalTokenConfirm = tokenConfirmRepository
                .findByNameTokenAndTokenType(token, TokenType.FORGOT_PASSWORD);
        if (optionalTokenConfirm.isEmpty()){
            return "Link lấy lại mật khẩu không tồn tại";
        }
        TokenConfirm tokenConfirm = optionalTokenConfirm.get();
        if (tokenConfirm.getExpiredAt().isBefore(LocalDateTime.now())){
            return "Link lấy lại mật khẩu đã hết hạn ";
        }
        if (tokenConfirm.getConfirmedAt()!=null){
            return "Link lấy lại mật khẩu đã được sử dụng trước đó";
        }
        tokenConfirmRepository.save(tokenConfirm);
        return tokenConfirm.getNameToken();
    }

    public User getUserCurrent (){
        return userRepository.findByEmail(httpSession.getAttribute("MY_SESSION").toString()).orElseThrow(()->new UsernameNotFoundException("Không tìm thấy user hiện tại "));
    }
    public User createUserHotel(UpsertHotelRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new BadRequestException("Email đã tồn tại");
        }
        if (userRepository.findByPhoneNumber(request.getPhoneHotel()).isPresent()){
            throw new BadRequestException("Số điện thoại đã tồn tại");
        }
        String regex = "^0([0-9]{9})";
        String phone = "";
        if (request.getPhoneHotel().matches(regex)){
            phone = request.getPhoneHotel();
        }else {
            throw new BadRequestException("Số điện thoại của bạn không hợp lệ ");
        }
        User user = User.builder()
                .name(request.getName())
                .userRole(UserRole.ROLE_HOTEL)
                .password(passwordEncoder.encode("12345678"))
                .email(request.getEmail())
                .createdAt(LocalDate.now())
                .avatar("/web/assets/image/avatar-default.jpg")
                .enable(true)
                .phoneNumber(phone)
                .build();
        return userRepository.save(user);
    }

}
