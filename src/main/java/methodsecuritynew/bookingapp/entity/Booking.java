package methodsecuritynew.bookingapp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import methodsecuritynew.bookingapp.model.statics.PaymentMethod;
import methodsecuritynew.bookingapp.model.statics.StatusBooking;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "booking")
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

    Integer guests;

    LocalDate checkIn;

    LocalDate checkOut;

    Integer price;

    @Enumerated(EnumType.STRING)
    PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    StatusBooking status;

    LocalDate createAt;



    //id int [pk, increment]
    //  id_customer integer [ref: > user.id]
    //  hotel_id integer [ref: > hotel.id]
    //  room_id integer [ref: > Room.id]
    //  guests  int
    //  checkin  localdate
    //  checkout localdate
    //  price  int
    //  paymentmethod PaymentMethod // enum :hình thức thanh toán
    //  status  StatusBooking // Enum: chờ xác nhận , đã xác nhận
    //  createAt  localdate
}
