package booking.hotel.request;


import booking.hotel.domain.StatusName;
import lombok.Data;

@Data
public class OrderSearchRequest extends OrderCreateRequest{
    StatusName statusName;
}
