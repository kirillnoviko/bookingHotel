package booking.hotel.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "rooms")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {
        "comforts"
})
public class Room {

    @Id
    private Long id;

    @Column
    private String name;

    @Column
    private Long price;

    @Column (name = "principle_of_placement")
    private String principleOfPlacement;

    @Column (name = "comfort_level")
    private String comfortLevel;

    @Column (name = "number_room")
    private String numberRoom;

    @Column (name = "rating_average")
    private Long ratingAverage;

    @ManyToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("room")
    private Set<Comfort> comforts = Collections.emptySet();

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<Order> orders = Collections.emptySet();

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }


}
