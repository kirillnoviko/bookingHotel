package booking.hotel.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp created;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp changed;

    private boolean isBanned;

    private boolean isDeleted;
}
