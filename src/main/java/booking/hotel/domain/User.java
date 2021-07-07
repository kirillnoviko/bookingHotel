package booking.hotel.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {
        "roles"
})
public class User {

    @Id
    private Long id;

    @Column
    private String name;

    @Column
    private String surname;

    @Column (name = "birth_date")
    private Timestamp birthDate;

    @Column
    private String gmail;

    @Column
    private String password;

    @Column (name = "is_deleted")
    private boolean isDeleted;

    @Column (name = "is_banned")
    private boolean isBanned;

    @Column
    private Timestamp created;

    @Column
    private Timestamp changed;

    @Column(name = "rating_average")
    private Long ratingAverage;

    @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("users")
    private Set<Role> roles = Collections.emptySet();

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
