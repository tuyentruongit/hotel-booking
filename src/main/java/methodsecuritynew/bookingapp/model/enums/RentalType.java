package methodsecuritynew.bookingapp.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RentalType {
    HOTEL("Khách sạn"), // Loại hình khách sạn
    MOTEL("Nhà nghỉ"); // Loại hình nhà nghỉ
    private final String value;


}
