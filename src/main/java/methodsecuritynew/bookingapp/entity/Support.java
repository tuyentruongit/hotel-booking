package methodsecuritynew.bookingapp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import methodsecuritynew.bookingapp.model.statics.SupportType;

@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "support")
public class Support {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "title")
    String title;

    @Column(name = "content")
    String content;

    @Column(name = "description")
    String description;

    @Column(name = "slug")
    String slug;

    @Column(name = "status")
    Boolean status;

    @Column(name = "created_at")
    String createdAt;

    @Column(name = "updated_at")
    String updatedAt;

    @Column(name = "published_at")
    String publishedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "support_type")
    SupportType supportType;



}
