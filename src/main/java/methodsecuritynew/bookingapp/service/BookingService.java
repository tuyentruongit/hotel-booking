package methodsecuritynew.bookingapp.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.Booking;
import methodsecuritynew.bookingapp.entity.User;
import methodsecuritynew.bookingapp.model.request.UpsertBookingRequest;
import methodsecuritynew.bookingapp.model.statics.PaymentMethod;
import methodsecuritynew.bookingapp.model.statics.StatusBooking;
import methodsecuritynew.bookingapp.repository.BookingRepository;
import methodsecuritynew.bookingapp.repository.HotelRepository;
import methodsecuritynew.bookingapp.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final AuthService authService;
    private final HotelService hotelService;
    private final RoomService roomService;

    public Page<Booking> getAllBookingByIdUer(Integer id , Integer pageNumber , Integer limit) {
        Pageable pageable = PageRequest.of(pageNumber-1,limit, Sort.by("createAt").descending());
        return bookingRepository.findAllByUser_IdOrderByCreateAtDesc(id,pageable);
    }

    public Booking bookingHotel(UpsertBookingRequest bookingRequest) {

        String strPaymentMethod = bookingRequest.getPaymentMethod().trim();
        PaymentMethod paymentMethod = PaymentMethod.valueOf(strPaymentMethod);
        User user = authService.getUserCurrent();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate checkIn = LocalDate.parse(bookingRequest.getCheckIn(),dateTimeFormatter);
        LocalDate checkOut = LocalDate.parse(bookingRequest.getCheckOut(),dateTimeFormatter);
         Booking booking = Booking.builder()
                .user(user)
                .hotel(hotelService.getHotelById(bookingRequest.getIdHotel()))
                .room(roomService.getRoomById(bookingRequest.getIdRoom()))
                .nameCustomer(bookingRequest.getNameCustomer())
                .emailCustomer(bookingRequest.getEmailCustomer())
                .phoneCustomer(bookingRequest.getPhoneCustomer())
                .guests(bookingRequest.getGuest())
                .checkIn(checkIn)
                .checkOut(checkOut)
                .price(bookingRequest.getPrice())
                .numberRoom(bookingRequest.getNumberRoom())
                .paymentMethod(paymentMethod)
               .statusBooking(StatusBooking.COMPLETE)
               .createAt(LocalDateTime.now())
                .build();
       return bookingRepository.save(booking);
    }

    public List<Booking> getBookingById(Integer id) {
        return List.of(bookingRepository.findById(id).orElseThrow(()-> new RuntimeException("Không thấy booking nào có id như trên ")));
    }
     public Booking getBooking(Integer id) {
        return bookingRepository.findById(id).orElseThrow(()-> new RuntimeException("Không thể tìm thấy booking như trên "));
    }

    public void cancelBooking(Integer id) {
        Booking booking= getBooking(id);
        booking.setStatusBooking(StatusBooking.CANCELLED);
        bookingRepository.save(booking);
    }
}
