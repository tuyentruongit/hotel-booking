package methodsecuritynew.bookingapp.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Gender {
    MALE("Nam"),
    FEMALE("Nữ"),
    OTHER("Khác");
    private final String value;
}
