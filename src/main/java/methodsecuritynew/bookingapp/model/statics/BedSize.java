package methodsecuritynew.bookingapp.model.statics;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BedSize {
    MEDIUM("Vừa"),
    KING("Lớn");

    private final String value;
}
