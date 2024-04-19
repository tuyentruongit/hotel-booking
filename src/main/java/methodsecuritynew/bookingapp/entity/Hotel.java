package methodsecuritynew.bookingapp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import methodsecuritynew.bookingapp.model.statics.RentalType;

import java.time.LocalDate;
import java.util.List;


@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hotels")
@Getter
@Setter
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "name" , unique = true)
    String name;

    @Column(name = "email" , unique = true)
    String email;

    @Column(columnDefinition = "TEXT")
    String description;

    String address;

    @ManyToOne
    @JoinColumn(name = "city_id")
    City city;


    @OneToOne
    @JoinColumn(name = "policyHotel_id" )
    PolicyHotel policyHotel;

    Integer star;
    String hotline;
    Float rating;

    @Enumerated(EnumType.STRING)
    RentalType rentalType;
    @ManyToMany
    @JoinTable(
            name = "amenity_hotel",
            joinColumns = @JoinColumn(name = "id_hotel"),
            inverseJoinColumns = @JoinColumn(name = "id_amenity")

    )
    List<AmenityHotel> amenityHotelList;



    LocalDate createdAt;
    LocalDate updatedAt;
    Boolean status;



}
