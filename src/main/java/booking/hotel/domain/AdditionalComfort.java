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
@Table(name = "additional_comfort")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {
        "room"
})
public class AdditionalComfort {

    @Id
    private Long id;

    @Column(name = "name_additional")
    private String nameAdditional;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "additional_in_section",
            joinColumns = @JoinColumn(name = "id_additional_comfort"),
            inverseJoinColumns = @JoinColumn(name = "id_section_hotel_room")
    )
    @JsonIgnoreProperties("additional_comfort")
    private Set<Room> room = Collections.emptySet();

}