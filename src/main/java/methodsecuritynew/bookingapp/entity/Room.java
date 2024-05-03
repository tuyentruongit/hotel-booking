package methodsecuritynew.bookingapp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import methodsecuritynew.bookingapp.model.statics.RentalType;
import methodsecuritynew.bookingapp.model.statics.RoomType;

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
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String name;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    Hotel hotel;

    @Column(columnDefinition = "TEXT")
    String description;

    Integer capacity;

    @Enumerated(EnumType.STRING)
    RoomType roomType;



    Integer area;
    @ManyToMany
    @JoinTable(
            name = "amenity_room",
            joinColumns = @JoinColumn(name = "id_room"),
            inverseJoinColumns = @JoinColumn(name = "id_amenity")
    )
    List<AmenityRoom> amenityRoomList ;
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Bed> bedList;



    Boolean status;
    LocalDate createdAt;
    LocalDate updatedAt;




}
