package methodsecuritynew.bookingapp.entity;

import jakarta.persistence.*;
import lombok.*;
import methodsecuritynew.bookingapp.model.statics.DateType;
import methodsecuritynew.bookingapp.model.statics.RoomType;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "priceRoom")
public class PriceRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    Integer id;
    LocalDate date;


    @Enumerated(EnumType.STRING)
    RoomType roomType;


    Double price;

    @ManyToOne
    @JoinColumn(name = "room_id")
    Hotel hotel;

    LocalDate createdAt;
    LocalDate updatedAt;


}
