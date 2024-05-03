package methodsecuritynew.bookingapp.entity;

import jakarta.persistence.*;
import lombok.*;
import methodsecuritynew.bookingapp.model.statics.RoomType;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "discount")
@Builder
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    LocalDate startDate;
    LocalDate endDate;
    @Enumerated(EnumType.STRING)
    RoomType roomType;
    Double discountPercentage; // phần trăm giảm giá
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    Hotel hotel;

    LocalDate createdAt;

    LocalDate updatedAt;
}
