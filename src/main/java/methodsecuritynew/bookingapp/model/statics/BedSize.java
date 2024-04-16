package methodsecuritynew.bookingapp.model.statics;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BedSize {
    MEDIUM("Vừa"),
    SMALL("Nhỏ");

    private final String value;
}
