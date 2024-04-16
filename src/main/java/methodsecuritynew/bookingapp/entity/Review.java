package methodsecuritynew.bookingapp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer id;



    @ManyToOne
    @JoinColumn(name = "id_user")
    User user;

    @ManyToOne
    @JoinColumn(name = "id_hotel")
    Hotel hotel;

    String comment;

    Integer rating;

    @Column(name = "create_at")
    LocalDate createAt;

    @Column(name = "update_at")
    LocalDate updateAt;

    @ManyToMany
    @JoinTable(name = "reviews_hotelLikes",
            joinColumns = @JoinColumn(name = "id_review"),
            inverseJoinColumns = @JoinColumn(name = "id_hotelLike")
    )
    List<HotelLikes> reviews;

    //id int [pk, increment]
    //  id_user  int [ref: > user.id]
    //  id_hotel  int [ref: > hotel.id]
    //  comment  varchar
    //  rating  int
    //  createAt  localdate
    //  updateAt localdate
}
