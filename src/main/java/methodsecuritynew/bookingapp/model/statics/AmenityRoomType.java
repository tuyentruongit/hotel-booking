package methodsecuritynew.bookingapp.model.statics;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AmenityRoomType {
    BATHROOM( "Phòng tắm"), // Phòng tắm
    BEDROOM( "Phòng ngủ"), // Phòng ngủ
    ENTERTAINMENT( "Giải trí"), // Giải trí
    DINING( "Ăn uống"), // Ăn uống
    BATHROOM_EQUIPMENT( "Dụng cụ vệ sinh"), // Dụng cụ vệ sinh
    KITCHEN( "Bếp"), // Bếp
    INTERNET( "Internet"), // Internet
    OTHERS( "Khác"); // Khác
    private final String value;
}
