package methodsecuritynew.bookingapp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import methodsecuritynew.bookingapp.model.statics.ImageType;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Entity
public class ImageHotel extends Image {
    @Enumerated(EnumType.STRING)
    ImageType imageType;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;



}
