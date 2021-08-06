package booking.hotel.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
@Data
public class UserChangeRequest extends  UserCreateRequest{

    private Long id;


}
