package methodsecuritynew.bookingapp.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum type {
    HUY("Hủy"),
    XAC_NHAN("Xác Nhận");

    public final String value;
}
