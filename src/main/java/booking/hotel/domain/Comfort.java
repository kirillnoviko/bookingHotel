package booking.hotel.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "comforts")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {
        "room"
})
public class Comfort {

    @Id
    private Long id;

    @Column(name = "name_comfort")
    private String nameComfort;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "comforts_rooms",
            joinColumns = @JoinColumn(name = "id_comfort"),
            inverseJoinColumns = @JoinColumn(name = "id_room")
    )
    @JsonIgnoreProperties("additional_comfort")
    private Set<Room> room = Collections.emptySet();

}