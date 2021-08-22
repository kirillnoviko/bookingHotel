package booking.hotel.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToMany;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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