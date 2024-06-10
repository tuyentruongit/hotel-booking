package methodsecuritynew.bookingapp.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import methodsecuritynew.bookingapp.model.enums.RoomType;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomPriceDto {
    String nameRoom;
    Integer price;
    String typeRoom;
    LocalDate day;
}
