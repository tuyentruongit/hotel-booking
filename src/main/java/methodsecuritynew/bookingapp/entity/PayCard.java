package methodsecuritynew.bookingapp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class PayCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String cardNumber;


    String expiryDate;


    String cardHolderName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;


}
