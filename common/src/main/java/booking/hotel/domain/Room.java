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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "name")),
            @AttributeOverride(name = "price", column = @Column(name = "price")),
            @AttributeOverride(name = "principleOfPlacement", column = @Column(name = "principle_of_placement")),
            @AttributeOverride(name = "comfortLevel", column = @Column(name = "comfort_level")),
                    @AttributeOverride(name = "numberRoom", column = @Column(name = "number_room"))
    })
    private GeneralInfoRoom generalInfoRoom;

    @Column (name = "rating_average")
    private Long ratingAverage;

    @Column (name = "is_deleted")
    private boolean deleted;

    @ManyToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("room")
    private Set<Comfort> comforts = Collections.emptySet();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<Order> orders = Collections.emptySet();

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }


}
