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
@NamedQuery(name = "User_findByLogin",
        query = "select u from User  u where u.gmail = :gmail")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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


    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "isBanned", column = @Column(name = "is_banned")),
            @AttributeOverride(name = "isDeleted", column = @Column(name = "is_deleted")),
            @AttributeOverride(name = "created", column = @Column(name = "created")),
            @AttributeOverride(name = "changed", column = @Column(name = "changed"))
    })
    private UserSystemInfo userSystemInfo;

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
