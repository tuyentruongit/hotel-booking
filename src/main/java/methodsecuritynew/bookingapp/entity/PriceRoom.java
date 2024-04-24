package methodsecuritynew.bookingapp.entity;

import jakarta.persistence.*;
import lombok.*;
import methodsecuritynew.bookingapp.model.statics.DateType;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PriceRoom")
public class PriceRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    Integer id;

    @Enumerated(EnumType.STRING)
    DateType dateType;

    Double price;
    @ManyToOne
    @JoinColumn(name = "room_id")
    Room room;

    LocalDate createdAt;
    LocalDate updatedAt;


}
