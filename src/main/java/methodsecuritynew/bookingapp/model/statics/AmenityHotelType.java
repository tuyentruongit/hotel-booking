package methodsecuritynew.bookingapp.model.statics;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AmenityHotelType {
    RESTAURANT("Nhà hàng"), // Nhà hàng
    INTERNET("Internet"), // Internet
    FITNESS_CENTER("Phòng tập thể dục"), // Phòng tập thể dục
    SWIMMING_POOL("Hồ bơi"), // Hồ bơi
    SPA("Spa"), // Spa
    PLAYGROUND("Sân chơi"), // Sân chơi

    OTHER("Khác"); // Khác

    private final String value;

}
