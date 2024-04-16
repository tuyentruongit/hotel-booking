package methodsecuritynew.bookingapp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "policy_hotel")
@NoArgsConstructor
@AllArgsConstructor
public class PolicyHotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String checkIn;
    String checkOut;

    String service;
    String cancelPolicy;

    String animal;
    String ageLimit;
    String note;

    LocalDate createdAt;
    LocalDate updatedAt;

}
