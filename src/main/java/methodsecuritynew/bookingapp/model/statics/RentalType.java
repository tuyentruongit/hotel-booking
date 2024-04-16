package methodsecuritynew.bookingapp.model.statics;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RentalType {
    HOTEL("Khách sạn"), // Loại hình khách sạn
    RESORT("Resort"), // Loại hình khu nghỉ dưỡng
    MOTEL("Nhà nghỉ"), // Loại hình nhà nghỉ
    VILLA("Biệt thự"), // Loại hình biệt thự
    APARTMENT("Căn hộ"), // Loại hình căn hộ
    RYOKAN("Ryokan"), // Loại hình kiểu truyền thống Nhật Bản
    HOLIDAY_HOME("Nhà nghỉ dưỡng"); // Loại hình nhà nghỉ dưỡng

    private final String value;


}
