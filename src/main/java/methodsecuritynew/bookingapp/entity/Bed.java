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



    @Column(columnDefinition = "TEXT")
    @Enumerated(EnumType.STRING)
    BedType bedType;

    @Column(columnDefinition = "TEXT")
    @Enumerated(EnumType.STRING)
    BedSize bedSize;

    LocalDate createAt;
    LocalDate updateAt;


}
