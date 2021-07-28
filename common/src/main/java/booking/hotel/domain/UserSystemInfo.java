package booking.hotel.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.sql.Timestamp;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserSystemInfo {

    private Timestamp created;

    private Timestamp changed;

    private boolean isBanned;

    private boolean isDeleted;
}
