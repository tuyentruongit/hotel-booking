package methodsecuritynew.bookingapp.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import methodsecuritynew.bookingapp.entity.AmenityRoom;
import methodsecuritynew.bookingapp.model.enums.BedSize;
import methodsecuritynew.bookingapp.model.enums.BedType;
import methodsecuritynew.bookingapp.model.enums.RoomType;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomDto {
    Integer id;
    String name;
    String description;
    Integer capacity;
    RoomType roomType;
    Integer area;
    String bedType;
    String bedSize ;
    String thumbnailRoom ;
    Integer priceAverage;
    List<AmenityRoom> amenityRoomList ;
}
