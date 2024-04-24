package methodsecuritynew.bookingapp.model.statics;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DateType {
    WEEKEND("Cuối tuần"),
    HOLIDAY ("Ngày lễ"),
    WEEKDAY("Ngày thường");
    private final String value;
}
