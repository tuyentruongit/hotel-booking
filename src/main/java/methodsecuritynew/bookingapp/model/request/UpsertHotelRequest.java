package methodsecuritynew.bookingapp.model.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpsertHotelRequest {
    String name ;
    String description;
    String rentalType ;
    String phoneHotel ;
    String email ;
    String addressHotel;
    Integer idCity ;
    Boolean status;
}
