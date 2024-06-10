package methodsecuritynew.bookingapp.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import methodsecuritynew.bookingapp.model.enums.RoomType;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingDto {
    Integer guests;
    LocalDate checkIn;
    LocalDate checkOut;
    Integer numberRoom;
    String nameRoom;
    RoomType typeRoom;
}
