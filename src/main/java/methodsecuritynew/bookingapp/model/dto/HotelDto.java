package methodsecuritynew.bookingapp.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import methodsecuritynew.bookingapp.entity.AmenityHotel;
import methodsecuritynew.bookingapp.model.enums.RentalType;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HotelDto {
    Integer id;
    String name;
    String address;
    Integer star;
    Float rating ;
    String ratingText;
    RentalType rentalType;
    Integer estimatedPrice;
    Integer totalReviews;
    List<String> nameAmenity;
    Set<String> nameAmenityRoom;
    String poster;
}
