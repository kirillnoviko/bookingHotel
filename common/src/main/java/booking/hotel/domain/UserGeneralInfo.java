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
public class UserGeneralInfo {

    String name;

    String surname;

    Timestamp birthDate;

    String gmail;

    String password;



}
