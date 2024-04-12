package methodsecuritynew.bookingapp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import methodsecuritynew.bookingapp.model.statics.Gender;
import methodsecuritynew.bookingapp.model.statics.UserRole;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String name;

    @Column(name = "email", unique = true, nullable = false)
    String email;

    String password;
    String avatar;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    UserRole role;

    LocalDate birthDay;

    String phoneNumber;

    @Enumerated(EnumType.STRING)
    Gender gender;

    @OneToOne(cascade = CascadeType.ALL)
    PayCard payCard;

    Boolean enabled;

    LocalDate createdAt;

    LocalDate updateAt;




}
