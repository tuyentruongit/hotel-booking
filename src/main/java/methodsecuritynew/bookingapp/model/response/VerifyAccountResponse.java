package methodsecuritynew.bookingapp.model.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import methodsecuritynew.bookingapp.model.enums.TypeVerifyToken;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerifyAccountResponse {
    Boolean success;
    String message;
}
