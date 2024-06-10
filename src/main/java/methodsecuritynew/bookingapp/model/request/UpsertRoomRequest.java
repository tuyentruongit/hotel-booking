package methodsecuritynew.bookingapp.model.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpsertRoomRequest {
    String name;
    String description;
    List<Integer> amenityRoom;
    String roomType;
    Integer area;
    Integer capacity;
    Integer quantity;
    String bedType;
    String bedSize;
    Integer priceDefault;
    Boolean status;

}
