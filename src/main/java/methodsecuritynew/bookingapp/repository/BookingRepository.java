package methodsecuritynew.bookingapp.repository;

import methodsecuritynew.bookingapp.entity.Booking;
import methodsecuritynew.bookingapp.model.dto.RevenueDayDto;
import methodsecuritynew.bookingapp.model.dto.RevenueMonthDto;
import methodsecuritynew.bookingapp.model.dto.TotalBookingMonthDto;
import methodsecuritynew.bookingapp.model.statics.StatusBooking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    Page<Booking> findAllByUser_IdOrderByCreateAtDesc(Integer id, Pageable pageable);

    List<Booking> findByHotel_Id(Integer hotelId);

    @Query(value = "SELECT new  methodsecuritynew.bookingapp.model.dto.TotalBookingMonthDto(MONTH(o.createAt), YEAR(o.createAt), COUNT(o.id))" +
            " FROM Booking o WHERE o.statusBooking = 'COMPLETE'" +
            " GROUP BY MONTH(o.createAt), YEAR(o.createAt) " +
            "ORDER BY YEAR(o.createAt) ASC, MONTH(o.createAt) ASC")
    List<TotalBookingMonthDto> finTotalBookingMonth();

    @Query(value = "SELECT new methodsecuritynew.bookingapp.model.dto.RevenueDayDto(DAY(o.createAt),MONTH(o.createAt),YEAR(o.createAt), SUM(o.price)) " +
            "FROM Booking o WHERE o.statusBooking = 'COMPLETE' " +
            "GROUP BY YEAR(o.createAt), MONTH(o.createAt), DAY(o.createAt) " +
            "ORDER BY YEAR(o.createAt) DESC, MONTH(o.createAt) DESC, DAY(o.createAt) DESC")
    List<RevenueDayDto> getTotalRevenueDay();

    @Query(value = "SELECT new methodsecuritynew.bookingapp.model.dto.RevenueMonthDto(MONTH(o.createAt),YEAR(o.createAt), SUM(o.price)) " +
            "FROM Booking o WHERE o.statusBooking = 'COMPLETE' " +
            "GROUP BY YEAR(o.createAt), MONTH(o.createAt) " +
            "ORDER BY YEAR(o.createAt) DESC, MONTH(o.createAt) DESC")
    List<RevenueMonthDto> getTotalRevenueByMonth();


//    @Query(value = "SELECT new methodsecuritynew.bookingapp.model.dto.RevenueDto(YEAR(o.createAt),YEAR(o.createAt), SUM(o.price)) " +
//            "FROM Booking o WHERE o.statusBooking = 'COMPLETE' " +
//            "GROUP BY YEAR(o.createAt) " +
//            "ORDER BY YEAR(o.createAt) DESC")
//    List<RevenueDto> getTotalRevenueByYear();

    List<Booking> findBookingByCreateAtBetweenAndStatusBookingOrderByCreateAtDesc(LocalDate star, LocalDate end, StatusBooking statusBooking);

}



