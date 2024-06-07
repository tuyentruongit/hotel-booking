package methodsecuritynew.bookingapp.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import methodsecuritynew.bookingapp.model.enums.RoomType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomDto {
    String name ;
    String description;
    Integer capacity;
    RoomType roomType;

}
