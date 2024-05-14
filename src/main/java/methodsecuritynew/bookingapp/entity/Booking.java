package methodsecuritynew.bookingapp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import methodsecuritynew.bookingapp.model.statics.PaymentMethod;
import methodsecuritynew.bookingapp.model.statics.StatusBooking;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "booking")
@Builder
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "user")
    User user;


    @ManyToOne
    @JoinColumn(name = "hotel")
    Hotel hotel;

    @ManyToOne
    @JoinColumn(name = "room")
    Room room;

    String nameCustomer;
    String emailCustomer;
    String phoneCustomer;

    Integer guests;

    LocalDate checkIn;

    LocalDate checkOut;

    Integer price;
    Integer numberRoom;

    @Enumerated(EnumType.STRING)
    PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    StatusBooking statusBooking;

    LocalDateTime createAt;
}
