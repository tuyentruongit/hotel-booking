package methodsecuritynew.bookingapp.model.statics;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusBooking {
    PENDING("Chờ xác nhận"),
    CONFIRMED("Đã xác nhận"),
    CANCELLED("Đã hủy"),
    CHECKED_IN("Đã nhận phòng"),
    CHECKED_OUT("Đã trả phòng"),
    COMPLETE("Hoàn tất");

    private final String value;
}
