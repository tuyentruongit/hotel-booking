package methodsecuritynew.bookingapp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import methodsecuritynew.bookingapp.model.statics.AmenityRoomType;
import methodsecuritynew.bookingapp.model.statics.AmenityHotelType;

import java.time.LocalDate;

@Entity
@FieldDefaults(level = AccessLevel.PROTECTED)
@Table(name = "amenitis")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public abstract class Amenity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String name;

    LocalDate createdAt;
    LocalDate updateAt;


}
