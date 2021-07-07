package booking.hotel.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
