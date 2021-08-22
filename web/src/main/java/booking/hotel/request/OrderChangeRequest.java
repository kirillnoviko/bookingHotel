package booking.hotel.request;

import booking.hotel.domain.StatusName;
import lombok.Data;
import lombok.Value;

import static booking.hotel.domain.StatusName.CANCELED;

@Data
public class OrderChangeRequest extends OrderCreateRequest{

    Long idOrder;

    StatusName name=CANCELED;

}
