package methodsecuritynew.bookingapp.model.statics;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoomType {
    SINGLE("Phòng Đơn"),
    DOUBLE("Phòng Đôi"),
    TRIPLE("Phòng Triple"),
    QUAD("Phòng Quad"),
    STANDARD("Phòng Tiêu Chuẩn"),
    SUITE("Phòng Suite"),
    DELUXE("Phòng Cao Cấp"),
    EXECUTIVE("Phòng Hạng Cao Cấp");


    private final String value;
}
