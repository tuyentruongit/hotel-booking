package methodsecuritynew.bookingapp.model.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpsertPolicyRequest {
    String checkIn;
    String checkOut;
    String service;
    String cancel;
    String animal;
    String age;
    String other;

}
