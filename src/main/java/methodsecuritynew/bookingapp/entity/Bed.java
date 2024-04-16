package methodsecuritynew.bookingapp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import methodsecuritynew.bookingapp.model.statics.BedSize;
import methodsecuritynew.bookingapp.model.statics.BedType;

import java.time.LocalDate;

@Entity
@FieldDefaults(level = AccessLevel.PROTECTED)
@Table(name = "bed")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Bed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;


    @Enumerated(EnumType.STRING)
    BedType bedType;

    @Enumerated(EnumType.STRING)
    BedSize bedSize;

    LocalDate createAt;
    LocalDate updateAt;


    // id int [pk, increment]
    //  BedType   BedType // enum
    //  size  BedSize // enum
    //  style  BedStyle   // enum
    //  createAt  localdate
    //  updateAt localdate
}
