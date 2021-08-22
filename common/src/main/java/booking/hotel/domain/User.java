package booking.hotel.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
        "roles","orders"
})
@NamedQuery(name = "User_findByLogin",
        query = "select u from User  u where u.gmail= :gmail")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String surname;


    @Column
    private String gmail;

    @Column
    private String password;


    @Column(name = "birth_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp birthDate;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp created;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp changed;

    @Column (name = "is_banned")
    private boolean isBanned;

    @Column (name = "is_deleted")
    private boolean isDeleted;

    @Column (name = "rating_average")
    private Long ratingAverage;

    @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("users")
    private Set<Role> roles = Collections.emptySet();


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<Order> orders = Collections.emptySet();


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
