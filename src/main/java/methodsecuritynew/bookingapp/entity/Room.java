package methodsecuritynew.bookingapp.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import methodsecuritynew.bookingapp.model.enums.BedSize;
import methodsecuritynew.bookingapp.model.enums.BedType;
import methodsecuritynew.bookingapp.model.enums.RoomType;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rooms")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hotel_id")
    Hotel hotel;

    @Column(columnDefinition = "TEXT")
    String description;

    Integer capacity;

    @Enumerated(EnumType.STRING)
    RoomType roomType;

    Integer area;

    Integer quantity;

    @Column(columnDefinition = "TEXT")
    @Enumerated(EnumType.STRING)
    BedType bedType;

    @Column(columnDefinition = "TEXT")
    @Enumerated(EnumType.STRING)
    BedSize bedSize;


    @ManyToMany(cascade = {CascadeType.PERSIST ,CascadeType.MERGE})
    @JoinTable(
            name = "amenity_room",
            joinColumns = @JoinColumn(name = "id_room"),
            inverseJoinColumns = @JoinColumn(name = "id_amenity")
    )
    List<AmenityRoom> amenityRoomList ;

    Integer priceDefault;

    Boolean status;
    LocalDate createdAt;
    LocalDate updatedAt;

}
