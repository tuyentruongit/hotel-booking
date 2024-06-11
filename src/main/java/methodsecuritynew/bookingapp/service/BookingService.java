package methodsecuritynew.bookingapp.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.Booking;
import methodsecuritynew.bookingapp.entity.Hotel;
import methodsecuritynew.bookingapp.entity.Room;
import methodsecuritynew.bookingapp.entity.User;
import methodsecuritynew.bookingapp.model.dto.RevenueDayDto;
import methodsecuritynew.bookingapp.model.dto.RevenueMonthDto;
import methodsecuritynew.bookingapp.model.dto.TotalBookingByRoomTypeDto;
import methodsecuritynew.bookingapp.model.dto.TotalBookingMonthDto;
import methodsecuritynew.bookingapp.model.request.UpsertBookingRequest;
import methodsecuritynew.bookingapp.model.enums.PaymentMethod;
import methodsecuritynew.bookingapp.model.enums.StatusBooking;
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

    public Page<Booking> getAllBookingByIdUer(Integer id, Integer pageNumber, Integer limit) {
        Pageable pageable = PageRequest.of(pageNumber - 1, limit, Sort.by("createAt").descending());
        return bookingRepository.findAllByUser_IdOrderByCreateAtDesc(id, pageable);
    }

    public Booking bookingHotel(UpsertBookingRequest bookingRequest) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate checkIn = LocalDate.parse(bookingRequest.getCheckIn(), dateTimeFormatter);
        LocalDate checkOut = LocalDate.parse(bookingRequest.getCheckOut(), dateTimeFormatter);
        // lấy ra phòng mà người dùng đã yêu cầu đặt
        Room room = roomService.getRoomById(bookingRequest.getIdRoom());
        if (room.getCapacity()< bookingRequest.getGuest()/ bookingRequest.getNumberRoom() ||
                !roomService.roomCheck(room,checkIn,checkOut,bookingRequest.getNumberRoom())){
            throw new RuntimeException("Phòng này đã có người đặt trước đó, vui lòng chọn phòng khác");
        }

        String strPaymentMethod = bookingRequest.getPaymentMethod().trim();
        PaymentMethod paymentMethod = PaymentMethod.valueOf(strPaymentMethod);
        User user = authService.getUserCurrent();
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
                .statusBooking(StatusBooking.PENDING)
                .createAt(LocalDate.now())
                .build();
        return bookingRepository.save(booking);
    }

    public List<Booking> getBookingById(Integer id) {
        return List.of(bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Không thấy booking nào có id như trên ")));
    }

    public Booking getBooking(Integer id) {
        return bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Không thể tìm thấy booking như trên "));
    }

    public void cancelBooking(Integer id) {
        Booking booking = getBooking(id);
        booking.setStatusBooking(StatusBooking.CANCELLED);
        bookingRepository.save(booking);
    }

    public Page<Booking> getBookingAdmin(Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by("createAt").descending());
        return bookingRepository.findAll(pageable);
    }


    @Transactional
    public void updateBooking(Integer id, UpsertBookingRequest bookingRequest) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Không thể tìm thấy booking có id :" + id));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate checkIn = LocalDate.parse(bookingRequest.getCheckIn(), dateTimeFormatter);
        LocalDate checkOut = LocalDate.parse(bookingRequest.getCheckOut(), dateTimeFormatter);
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
        return bookingRepository.findBookingByCreateAtBetweenAndStatusBookingOrderByCreateAtDesc(star, end, StatusBooking.CONFIRMED);
    }

    public List<RevenueDayDto> getRevenueByDay(Integer year, Integer month) {
        List<RevenueDayDto> revenueDtoList = bookingRepository.getTotalRevenueDay();
        List<RevenueDayDto> result = new ArrayList<>();
        LocalDate dateSelect = LocalDate.of(year, month, 1);

        for (int i = 0; i < dateSelect.lengthOfMonth(); i++) {
            LocalDate localDate = dateSelect.plusDays(i);
            boolean check = false;
            for (RevenueDayDto revenueDto1 : revenueDtoList) {
                if (revenueDto1.getMonth() == localDate.getMonthValue() &&
                        revenueDto1.getDay() == localDate.getDayOfMonth() &&
                        revenueDto1.getYear() == localDate.getYear()) {
                    result.add(revenueDto1);
                    check = true;
                    break;

                }
            }
            if (!check) {
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
        LocalDate current = LocalDate.of(year, 1, 1);

        for (int i = 0; i < 12; i++) {
            LocalDate localDate = current.plusMonths(i);
            boolean check = false;
            for (RevenueMonthDto revenueDto1 : revenueDtoList) {
                if (revenueDto1.getMonth() == localDate.getMonthValue() &&
                        revenueDto1.getYear() == localDate.getYear()) {
                    result.add(revenueDto1);
                    check = true;
                    break;

                }
            }
            if (!check) {
                RevenueMonthDto revenueDto = new RevenueMonthDto();
                revenueDto.setMonth(localDate.getMonthValue());
                revenueDto.setYear(year);
                revenueDto.setTotalPrice(0);
                result.add(revenueDto);
            }
        }
        return result;
    }

    public int totalRevenueYear(int year) {
        List<RevenueMonthDto> result = getRevenueByMonth(year);
        int sum = 0;
        for (RevenueMonthDto revenueMonthDto : result) {
            sum += (int) revenueMonthDto.getTotalPrice();
        }
        return sum;

    }

    public long totalMonthCurrent(int year, int monthValue) {
        List<RevenueDayDto> result = getRevenueByDay(year, monthValue);
        long sum = 0;
        for (RevenueDayDto revenueDayDto : result) {
            sum += revenueDayDto.getTotalPrice();
        }
        return sum;
    }

    public List<Booking> getAllBookingHotel() {
        Hotel hotel = hotelService.getHotelByAccountCurrent();
        return bookingRepository.findAllByHotel_Id(hotel.getId());
    }

    public void confirmBooking(Integer id ) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy booking nào có id : " + id));
       booking.setStatusBooking(StatusBooking.CONFIRMED);
       bookingRepository.save(booking);
    }
    public void rejectBooking(Integer id ) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy booking nào có id : " + id));
        booking.setStatusBooking(StatusBooking.REJECTED);
        bookingRepository.save(booking);
    }

    public void confirmCheckOutBooking(Integer id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy booking nào có id : " + id));
        booking.setStatusBooking(StatusBooking.COMPLETE);
        bookingRepository.save(booking);
    }

    public List<RevenueDayDto> getRevenueByMonthAndHotel(Integer idHotel, Integer month, Integer year) {
        List<RevenueDayDto> revenueMonthDtoList =bookingRepository.getTotalRevenueByMonth(idHotel);

        List<RevenueDayDto> result = new ArrayList<>();
        LocalDate dateSelect = LocalDate.of(year, month, 1);

        for (int i = 0; i < dateSelect.lengthOfMonth(); i++) {
            LocalDate localDate = dateSelect.plusDays(i);
            boolean check = false;
            for (RevenueDayDto revenueDto1 : revenueMonthDtoList) {
                if (revenueDto1.getMonth() == localDate.getMonthValue() &&
                        revenueDto1.getDay() == localDate.getDayOfMonth() &&
                        revenueDto1.getYear() == localDate.getYear()) {
                    result.add(revenueDto1);
                    check = true;
                    break;

                }
            }
            if (!check) {
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

    public List<TotalBookingByRoomTypeDto> getTotalBookingByRoomType (Integer idHotel , Integer year){
        Hotel hotel  = hotelService.getHotelById(idHotel);
        List<Room> roomList = roomService.getRoomByIdHotel(idHotel);
        List<TotalBookingByRoomTypeDto> result = new ArrayList<>();
       List<TotalBookingByRoomTypeDto> totalBookingByRoomTypeDtoList = bookingRepository.getTotalBookingByRoomTypeAndYear(idHotel,year);
        for (int i = 0; i < roomList.size(); i++) {
            boolean check = false;
            for (TotalBookingByRoomTypeDto totalBookingByRoomTypeDto : totalBookingByRoomTypeDtoList){
                if (totalBookingByRoomTypeDto.getTypeRoom().equals(roomList.get(i).getName())){
                    result.add(totalBookingByRoomTypeDto);
                    check=true;
                    break;
                }
            }
            if (!check){
                TotalBookingByRoomTypeDto totalBookingByRoomTypeDtoNew = new TotalBookingByRoomTypeDto();
                totalBookingByRoomTypeDtoNew.setTotalBooking(0);
                totalBookingByRoomTypeDtoNew.setTypeRoom(roomList.get(i).getName());
                result.add(totalBookingByRoomTypeDtoNew);
            }

        }
        return result;
    }

    public List<RevenueMonthDto> getRevenueByYearAndHotel(Integer idHotel, Integer year) {
        List<RevenueMonthDto> revenueDtoList = bookingRepository.getTotalRevenueByMonthAndHotelId(idHotel,year);
        List<RevenueMonthDto> result = new ArrayList<>();
        LocalDate current = LocalDate.of(year, 1, 1);
        for (int i = 0; i < 12; i++) {
            LocalDate localDate = current.plusMonths(i);
            boolean check = false;
            for (RevenueMonthDto revenueDto1 : revenueDtoList) {
                if (revenueDto1.getMonth() == localDate.getMonthValue()) {
                    result.add(revenueDto1);
                    check = true;
                    break;

                }
            }
            if (!check) {
                RevenueMonthDto revenueDto = new RevenueMonthDto();
                revenueDto.setMonth(localDate.getMonthValue());
                revenueDto.setYear(year);
                revenueDto.setTotalPrice(0);
                result.add(revenueDto);
            }
        }
        return result;
    }

    public TotalBookingMonthDto getTotalBookingByMonCurrent(int monthValue, int year , int idHotel) {
        return bookingRepository.findTotalBookingMonthByHotel(idHotel,monthValue,year);
    }
    public TotalBookingMonthDto getTotalBookingPendingMonthByHotel( int idHotel) {
        return bookingRepository.findTotalBookingPendingMonthByHotel(idHotel);
    }

    public long getTotalRevenueYearCurrent(Integer idHotel,Integer year) {
        List<RevenueMonthDto> revenueMonthDtoList = getRevenueByYearAndHotel(idHotel,year);
        long sum = 0;
        for (RevenueMonthDto revenueMonthDto : revenueMonthDtoList){
            sum += revenueMonthDto.getTotalPrice();
        }
        return sum;
    }

    public RevenueMonthDto getTotalRevenueMonth(Integer id, int year, int monthValue) {
        List<RevenueMonthDto> revenueMonthDtoList = bookingRepository.getTotalRevenueByMonthAndHotelId(id,year);
        for (RevenueMonthDto revenueMonthDto : revenueMonthDtoList){
            if (revenueMonthDto.getMonth()==monthValue && revenueMonthDto.getYear()==year){
                return revenueMonthDto;
            }
        }
        RevenueMonthDto revenueMonthDtoNew = new RevenueMonthDto();
        revenueMonthDtoNew.setYear(year);
        revenueMonthDtoNew.setTotalPrice(0);
        revenueMonthDtoNew.setMonth(monthValue);
        return  revenueMonthDtoNew;
    }

}
