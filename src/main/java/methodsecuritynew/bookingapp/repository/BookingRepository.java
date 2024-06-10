package methodsecuritynew.bookingapp.repository;

import methodsecuritynew.bookingapp.entity.Booking;
import methodsecuritynew.bookingapp.model.dto.*;
import methodsecuritynew.bookingapp.model.enums.StatusBooking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    Page<Booking> findAllByUser_IdOrderByCreateAtDesc(Integer id, Pageable pageable);

    List<Booking> findAllByHotel_Id(Integer hotelId);

    // tổng booking theo ngày tháng
    @Query(value = "SELECT new  methodsecuritynew.bookingapp.model.dto.TotalBookingMonthDto(MONTH(o.createAt), YEAR(o.createAt), COUNT(o.id))" +
            " FROM Booking o WHERE o.statusBooking = 'COMPLETE'" +
            " GROUP BY MONTH(o.createAt), YEAR(o.createAt) " +
            "ORDER BY YEAR(o.createAt) ASC, MONTH(o.createAt) ASC")
    List<TotalBookingMonthDto> finTotalBookingMonth();

    // tổng doanh thu theo ngày
    @Query(value = "SELECT new methodsecuritynew.bookingapp.model.dto.RevenueDayDto(DAY(o.createAt),MONTH(o.createAt),YEAR(o.createAt), SUM(o.price)) " +
            "FROM Booking o WHERE o.statusBooking = 'COMPLETE' " +
            "GROUP BY YEAR(o.createAt), MONTH(o.createAt), DAY(o.createAt) " +
            "ORDER BY YEAR(o.createAt) DESC, MONTH(o.createAt) DESC, DAY(o.createAt) DESC")
    List<RevenueDayDto> getTotalRevenueDay();

    // tổng daonh thu theo tháng
    @Query(value = "SELECT new methodsecuritynew.bookingapp.model.dto.RevenueMonthDto(MONTH(o.createAt),YEAR(o.createAt), SUM(o.price)) " +
            "FROM Booking o WHERE o.statusBooking = 'COMPLETE' " +
            "GROUP BY YEAR(o.createAt), MONTH(o.createAt) " +
            "ORDER BY YEAR(o.createAt) DESC, MONTH(o.createAt) DESC")
    List<RevenueMonthDto> getTotalRevenueByMonth();

    //  doanh thu từng ngay của  tháng
    @Query(value = "SELECT new methodsecuritynew.bookingapp.model.dto.RevenueDayDto(DAY(o.createAt), MONTH(o.createAt), YEAR(o.createAt), SUM(o.price)) " +
            "FROM Booking o WHERE o.hotel.id = :id AND o.statusBooking = 'COMPLETE' " +
            "GROUP BY YEAR(o.createAt), MONTH(o.createAt), DAY(o.createAt) " +
            "ORDER BY YEAR(o.createAt) DESC, MONTH(o.createAt) DESC, DAY(o.createAt) DESC")
    List<RevenueDayDto> getTotalRevenueByMonth(@Param("id") Integer id);


    // tổng đơn đặt phongfg theo từng loại phòng của khách sạn
    @Query(value = "SELECT new methodsecuritynew.bookingapp.model.dto.TotalBookingByRoomTypeDto(o.room.name, count(o.id)) " +
            "FROM Booking o WHERE o.hotel.id = :id AND YEAR(o.createAt) = :year AND o.statusBooking = 'COMPLETE' " +
            "GROUP BY o.room.name " +
            "ORDER BY count(o.id) DESC")
    List<TotalBookingByRoomTypeDto> getTotalBookingByRoomTypeAndYear(@Param("id") Integer id, @Param("year") int year);

    // tổng doanh thu từng tháng của năm
    @Query(value = "SELECT new methodsecuritynew.bookingapp.model.dto.RevenueMonthDto(MONTH(o.createAt),YEAR(o.createAt), SUM(o.price)) " +
            "FROM Booking o WHERE o.hotel.id = :id AND YEAR(o.createAt) = :year AND  o.statusBooking = 'COMPLETE'" +
            "GROUP BY YEAR(o.createAt), MONTH(o.createAt) " +
            "ORDER BY YEAR(o.createAt) DESC, MONTH(o.createAt) DESC")
    List<RevenueMonthDto> getTotalRevenueByMonthAndHotelId(@Param("id") Integer id, @Param("year") int year);

    // tổng booking từng tháng trong năm của khách sạn
    @Query(value = "SELECT new  methodsecuritynew.bookingapp.model.dto.TotalBookingMonthDto(MONTH(o.createAt), YEAR(o.createAt), COUNT(o.id))" +
            " FROM Booking o WHERE o.statusBooking = 'COMPLETE' AND o.hotel.id = :id AND YEAR(o.createAt) = :year AND MONTH(o.createAt) = :month"   +
            " GROUP BY MONTH(o.createAt), YEAR(o.createAt)")
    TotalBookingMonthDto findTotalBookingMonthByHotel(@Param("id") Integer id , @Param("month") int month , @Param("year") int year);
    // tổng booking đang chờ xác nhận
    @Query(value = "SELECT new  " +
            "methodsecuritynew.bookingapp.model.dto.TotalBookingMonthDto(MONTH(o.createAt), YEAR(o.createAt), COUNT(o.id))" +
            " FROM Booking o WHERE o.statusBooking = 'PENDING' AND o.hotel.id = :id"+
            " GROUP BY MONTH(o.createAt), YEAR(o.createAt)")
    TotalBookingMonthDto findTotalBookingPendingMonthByHotel(@Param("id") Integer id );


    // tìm kiếm các booking theo từng phòng và ngày check in
    @Query(value = "SELECT new methodsecuritynew.bookingapp.model.dto.BookingDto(o.guests, o.checkIn, o.checkOut, o.numberRoom, o.room.name, o.room.roomType) " +
            "FROM Booking o " +
            "WHERE o.room.id = :id AND o.statusBooking = 'CONFIRM' AND o.checkIn BETWEEN :checkIn AND :checkOut")
    List<BookingDto> findBookingByRoomAndCreateAtBetweenCheckinCheckOut(@Param("id") Integer id, @Param("checkIn") LocalDate checkIn, @Param("checkOut") LocalDate checkOut);


    List<Booking> findAllByRoom_IdAndCheckInBetween(Integer roomId, LocalDate star , LocalDate end );
    List<Booking> findBookingByCreateAtBetweenAndStatusBookingOrderByCreateAtDesc(LocalDate star, LocalDate end, StatusBooking statusBooking);

}



