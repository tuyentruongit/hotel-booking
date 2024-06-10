package methodsecuritynew.bookingapp.service;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.TokenConfirm;
import methodsecuritynew.bookingapp.entity.User;
import methodsecuritynew.bookingapp.exception.BadRequestException;
import methodsecuritynew.bookingapp.model.request.*;

import methodsecuritynew.bookingapp.model.response.VerifyAccountResponse;
import methodsecuritynew.bookingapp.model.enums.Gender;
import methodsecuritynew.bookingapp.model.enums.TokenType;
import methodsecuritynew.bookingapp.model.enums.UserRole;
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
import java.util.List;
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
           throw new RuntimeException ("Tài khoản hoặc mật khẩu không chính xác");
        }
    }


    // logic xủ lý khi người dùng đăng ký tài khoản mới
    @Transactional
    public String register(RegisterRequest registerRequest) {
        // kiểm tra email có tồn tại trong hệ thống hay không
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()){
            throw new BadRequestException("Email đã tồn tại");
        }
        // confirm password người dùng đã nhập
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())){
            throw new BadRequestException("Mật khẩu không trùng khớp");
        }
        // tạo user
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
        // tạo token để xác thực
        TokenConfirm tokenConfirm = TokenConfirm.builder()
                .nameToken(UUID.randomUUID().toString())
                .tokenType(TokenType.REGISTRATION)
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusHours(1))
                .user(user)
                .build();
        tokenConfirmRepository.save(tokenConfirm);
        // gửi  link xác thực gửi đến email
        String link = "http://localhost:9000/account/xac-minh-tai-khoan?token=" + tokenConfirm.getNameToken();
        mailService.sendMail(user.getEmail(),
                "Xác thực tài khoản",
                "Chào " +user.getName()+"! \n" +
                        "\n" +
                        "Chúng tôi xác nhận rằng bạn đã đăng ký tài khoản thành công tại WebFindTravel.\n" +
                        "\n" +
                        "Xin vui lòng nhấp vào liên kết sau để xác nhận đăng ký của bạn và kích hoạt tài khoản:\n" +
                        "\n" +
                        link+"\n" +
                        "\n" +
                        "Trân trọng.\n" );
        return "Đăng ký thành công. Vui lòng kiểm tra email để xác thực tài khoản";
    }



    // xác minh token
    @Transactional
    public VerifyAccountResponse verifyAccount(String token) {
        // lấy ra token ,  thuộc type registation hay không
        Optional<TokenConfirm> optionalTokenConfirm = tokenConfirmRepository
                .findByNameTokenAndTokenType(token, TokenType.REGISTRATION);

        // kiểm tra token có tồn tại hay không
        if (optionalTokenConfirm.isEmpty()){
            return VerifyAccountResponse.builder()
                    .success(false)
                    .message("Link xác thực không tồn tại")
                    .build();
        }
        // kiểm tra xem token đã hết hạn hay chưa
        TokenConfirm tokenConfirm = optionalTokenConfirm.get();
        if (tokenConfirm.getExpiredAt().isBefore(LocalDateTime.now())){
            return VerifyAccountResponse.builder()
                    .success(false)
                    .message("Link xác thực đã hết hạn ")
                    .build();
        }
        // kiểm tra xem token đã được xác thực trước đó hayy chưa
        if (tokenConfirm.getConfirmedAt()!=null){
            return VerifyAccountResponse.builder()
                    .success(false)
                    .message("Link xác thực đã được sử dụng trước đó")
                    .build();
        }

        // set enable cho user là true để tài khoản đó có thể đăng nhập
        tokenConfirm.getUser().setEnable(true);
        userRepository.save(tokenConfirm.getUser());

        // set thời gian đã xác thực cho token
        tokenConfirm.setConfirmedAt(LocalDateTime.now());
        tokenConfirmRepository.save(tokenConfirm);
        // trả về một instance VerifyAccountResponse
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


    // thay đổi mật khẩu
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
    // thay ddooir mật khẩu khi người dùng chọn chức nang quên mật khẩu
    public void changePasswordForForgetPassword(UpsertPasswordRetrieval upsertPasswordRetrieval) {
        TokenConfirm tokenConfirm = tokenConfirmRepository
                .findByNameTokenAndTokenType(upsertPasswordRetrieval.getNameToken(), TokenType.FORGOT_PASSWORD).orElseThrow(()-> new BadRequestException("Không tìm thấy token trên "));
        tokenConfirm.getUser().setPassword(passwordEncoder.encode(upsertPasswordRetrieval.getPassword()));
        tokenConfirm.setConfirmedAt(LocalDateTime.now());
        userRepository.save(tokenConfirm.getUser());
    }


    // logic xử lý chức năng quên mật khẩu
    public void forgotPassword(String email) {
        // lấy user với email người dùng nhập
       User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Tài khoản không tồn tại ."));

       // tạo một token với type ForGot Password
        TokenConfirm tokenConfirm = TokenConfirm.builder()
                .nameToken(UUID.randomUUID().toString())
                .tokenType(TokenType.FORGOT_PASSWORD)
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusHours(1))
                .user(user)
                .build();
        tokenConfirmRepository.save(tokenConfirm);
        // gửi link xác thực tới email
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


    // logic xử lý link với chức năng quên mật khẩu
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


    // lấy user đang đăng nhập
    public User getUserCurrent (){
        return userRepository.findByEmail(httpSession.getAttribute("MY_SESSION").toString()).orElseThrow(()->new UsernameNotFoundException("Không tìm thấy user hiện tại "));
    }

    // TODO:Cần sửa
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

    public List<User> getAllUserRoleUser() {
        return userRepository.findAllByUserRole(UserRole.ROLE_USER);
    }


    // lấy thông tin user với id được cung cấp
    public User getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(()-> new UsernameNotFoundException("Không tìm thấy user nào có id :"+id));
    }



//    public void resetPassword(String email) {
//        // check email exist
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy email"));
//
//        // Create token confirm
//        TokenConfirm tokenConfirm = new TokenConfirm();
//        tokenConfirm.setNameToken(UUID.randomUUID().toString());
//        tokenConfirm.setUser(user);
//        tokenConfirm.setType(TokenType.PASSWORD_RESET);
//        // set expiry date after 1 day
//        tokenConfirm.setExpiryDate(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000));
//        tokenConfirmRepository.save(tokenConfirm);
//
//        // send email
//        log.info("Send email");
//        Map<String, String> data = new HashMap<>();
//        data.put("email", user.getEmail());
//        data.put("username", user.getName());
//        data.put("token", tokenConfirm.getToken());
//
//        mailService.sendMailResetPassword(data);
//
//        log.info("Send mail success");
//    }


//    public void submitReasonDelete(Integer id, String reason) {
//        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy user"));
//        UserDelete userDelete = UserDelete.builder()
//                .user(user)
//                .reason(reason)
//                .createdAt(LocalDate.now())
//                .status(false)
//                .build();
//        userDeleteRepository.save(userDelete);
//    }
}
