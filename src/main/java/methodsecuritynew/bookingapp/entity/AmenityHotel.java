package methodsecuritynew.bookingapp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import methodsecuritynew.bookingapp.model.enums.AmenityHotelType;

@Entity
@Getter
@Setter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class AmenityHotel extends Amenity {

    @Enumerated(EnumType.STRING)
    AmenityHotelType amenityHotelType;
}
