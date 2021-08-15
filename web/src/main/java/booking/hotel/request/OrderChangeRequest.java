package booking.hotel.request;

import lombok.Data;

@Data
public class OrderChangeRequest extends OrderCreateRequest{

    Long idOrder;
}
