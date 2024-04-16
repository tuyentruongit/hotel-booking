package methodsecuritynew.bookingapp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import methodsecuritynew.bookingapp.model.statics.AmenityHotelType;
import methodsecuritynew.bookingapp.model.statics.AmenityRoomType;

import java.util.List;

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

    String icon;

    @ManyToMany
    @JoinTable(
            name = "amenity_hotel",
            joinColumns = @JoinColumn(name = "id_amenity"),
            inverseJoinColumns = @JoinColumn(name = "id_hotel")

    )
    List<Hotel> hotelList;
}
