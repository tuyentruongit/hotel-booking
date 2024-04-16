package methodsecuritynew.bookingapp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PROTECTED)
@Table(name = "images")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String name;
    String description;
    String url;

    Integer size;


}