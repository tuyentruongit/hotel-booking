package methodsecuritynew.bookingapp.model.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class UpsertCreateHotelRequest {
    UpsertHotelRequest upsertHotelRequest;
    UpsertRoomRequest upsertRoomRequest;
    UpsertPolicyRequest upsertPolicyRequest;

}
