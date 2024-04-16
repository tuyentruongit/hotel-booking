package methodsecuritynew.bookingapp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import methodsecuritynew.bookingapp.model.statics.RentalType;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hotels")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "name" , unique = true)
    String name;

    @Column(name = "email" , unique = true)
    String email;

    String description;

    String address;
    @ManyToOne
    @JoinColumn(name = "city_id" )
    City City ;

    @OneToOne
    @JoinColumn(name = "policyHotel_id" )
    PolicyHotel policyHotel;

    Integer star;
    String hotline;
    Float rating;

    @Enumerated(EnumType.STRING)
    RentalType rentalType;

    LocalDate createdAt;
    LocalDate updatedAt;
    Boolean status;



}
