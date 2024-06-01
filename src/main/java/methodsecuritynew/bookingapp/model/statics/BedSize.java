package methodsecuritynew.bookingapp.model.statics;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BedSize {

    MEDIUM("Vừa"),
    KING("King"),
    FULL("Giường lớn"),
    QUEEN("Giường Queen"),
    SMALL("Nhỏ");


    private final String value;
}
