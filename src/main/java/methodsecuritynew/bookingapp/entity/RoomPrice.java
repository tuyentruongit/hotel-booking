package methodsecuritynew.bookingapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import methodsecuritynew.bookingapp.model.enums.RoomType;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "priceRoom")
public class RoomPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    Integer id;

    LocalDate date;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    @JsonIgnore
     Room room;


    Double price;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    @JsonIgnore
    Hotel hotel;

    LocalDate createdAt;
    LocalDate updatedAt;


}
