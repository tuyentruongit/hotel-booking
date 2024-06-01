package methodsecuritynew.bookingapp.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.Booking;
import methodsecuritynew.bookingapp.entity.User;
import methodsecuritynew.bookingapp.model.dto.RevenueDayDto;
import methodsecuritynew.bookingapp.model.dto.RevenueMonthDto;
import methodsecuritynew.bookingapp.model.request.UpsertBookingRequest;
import methodsecuritynew.bookingapp.model.statics.PaymentMethod;
import methodsecuritynew.bookingapp.model.statics.StatusBooking;
import methodsecuritynew.bookingapp.repository.BookingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

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
               .createAt(LocalDate.now())
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

    public Page<Booking> getBookingAdmin(Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page-1,limit , Sort.by("createAt").descending());
        return bookingRepository.findAll(pageable);
    }

    public List<Booking> getBookingByIdHotel(Integer id) {
        return bookingRepository.findByHotel_Id(id);
    }

    @Transactional
    public void updateBooking(Integer id, UpsertBookingRequest bookingRequest) {
        Booking booking= bookingRepository.findById(id).orElseThrow(()-> new RuntimeException("Không thể tìm thấy booking có id :"+id));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate checkIn = LocalDate.parse(bookingRequest.getCheckIn(),dateTimeFormatter);
        LocalDate checkOut  = LocalDate.parse(bookingRequest.getCheckOut(),dateTimeFormatter);
        String strPaymentMethod = bookingRequest.getPaymentMethod().trim();
        PaymentMethod paymentMethod = PaymentMethod.valueOf(strPaymentMethod);
        booking.setRoom(roomService.getRoomById(bookingRequest.getIdRoom()));
        booking.setNameCustomer(bookingRequest.getNameCustomer());
        booking.setEmailCustomer(bookingRequest.getEmailCustomer());
        booking.setPhoneCustomer(bookingRequest.getPhoneCustomer());
        booking.setGuests(bookingRequest.getGuest());
        booking.setCheckIn(checkIn);
        booking.setCheckOut(checkOut);
        booking.setPrice(bookingRequest.getPrice());
        booking.setNumberRoom(bookingRequest.getNumberRoom());
        booking.setPaymentMethod(paymentMethod);
        booking.setStatusBooking(StatusBooking.CONFIRMED);
        booking.setUpdateAt(LocalDate.now());
        bookingRepository.save(booking);
    }

    public List<Booking> getBookingNew() {

        LocalDate star = LocalDate.now().withDayOfMonth(1);
        LocalDate end = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        return  bookingRepository.findBookingByCreateAtBetweenAndStatusBookingOrderByCreateAtDesc(star,end,StatusBooking.CONFIRMED);
    }

    public List<RevenueDayDto> getRevenueByDay(Integer year , Integer month){
        List<RevenueDayDto> revenueDtoList = bookingRepository.getTotalRevenueDay();
        List<RevenueDayDto> result = new ArrayList<>();
        LocalDate dateSelect = LocalDate.of(year,month,1);

        for (int i = 0; i < dateSelect.lengthOfMonth(); i++) {
            LocalDate localDate = dateSelect.plusDays(i);
            boolean check = false;
            for (RevenueDayDto revenueDto1 : revenueDtoList) {
                if (    revenueDto1.getMonth() == localDate.getMonthValue() &&
                        revenueDto1.getDay() == localDate.getDayOfMonth() &&
                        revenueDto1.getYear() == localDate.getYear()) {

                    result.add(revenueDto1);
                    check=true;
                    break;

                }
            }
           if (!check){
               RevenueDayDto revenueDto = new RevenueDayDto();
               revenueDto.setMonth(localDate.getMonthValue());
               revenueDto.setDay(localDate.getDayOfMonth());
               revenueDto.setYear(localDate.getYear());
               revenueDto.setTotalPrice(0);
               result.add(revenueDto);
           }
        }
        return result;
    }

    public List<RevenueMonthDto> getRevenueByMonth(Integer year) {
        List<RevenueMonthDto> revenueDtoList = bookingRepository.getTotalRevenueByMonth();
        List<RevenueMonthDto> result = new ArrayList<>();
        LocalDate current = LocalDate.of(year,1,1);

        for (int i = 0; i < 12; i++) {
            LocalDate localDate = current.plusMonths(i);
            boolean check = false;
            for (RevenueMonthDto revenueDto1 : revenueDtoList) {
                if (revenueDto1.getMonth() == localDate.getMonthValue() &&
                        revenueDto1.getYear() == localDate.getYear()) {
                    result.add(revenueDto1);
                    check=true;
                    break;

                }
            }
            if (!check){
                RevenueMonthDto revenueDto = new RevenueMonthDto();
                revenueDto.setMonth(localDate.getMonthValue());
                revenueDto.setYear(year);
                revenueDto.setTotalPrice(0);
                result.add(revenueDto);
            }
        }
        return result;
    }

    public int sumTotalRevenueYear(int year) {
        List<RevenueMonthDto> result = getRevenueByMonth(year);
        int sum = 0;
        for (RevenueMonthDto revenueMonthDto : result){
            sum+= (int) revenueMonthDto.getTotalPrice();
        }
        return sum;

    }

    public int sumTotalMonthCurrent(int year, int monthValue) {
        List<RevenueDayDto> result = getRevenueByDay(year,monthValue);
        int sum = 0;
        for (RevenueDayDto revenueDayDto : result){
            sum+= (int) revenueDayDto.getTotalPrice();
        }
        return sum;
    }
//
//    public List<RevenueDayDto> getRevenueByYear() {
//        List<RevenueDayDto> revenueDtoList = bookingRepository.getTotalRevenueByYear();
//        if (revenueDtoList.size()>2){
//            return revenueDtoList.subList(0,1);
//        }
//        return revenueDtoList;
//    }

}
