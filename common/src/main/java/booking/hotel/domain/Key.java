package booking.hotel.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "uuid_key_registration")
@Data
@NoArgsConstructor
public class Key {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Timestamp created;

    @Column
    private String uuid;

    @Column(name = "id_user")
    private Long idUser;

}
