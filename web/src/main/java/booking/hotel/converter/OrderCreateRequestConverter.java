package booking.hotel.converter;

import booking.hotel.domain.Order;
import booking.hotel.repository.dataspring.RoleRepositoryData;
import booking.hotel.repository.dataspring.RoomRepositoryData;
import booking.hotel.request.OrderCreateRequest;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;

import static booking.hotel.domain.StatusName.BOOKED;

@Component
public class OrderCreateRequestConverter extends  EntityConverter <OrderCreateRequest, Order>{

    private RoomRepositoryData roomRepository;


    public OrderCreateRequestConverter(RoomRepositoryData roomRepository) {
        this.roomRepository = roomRepository;

    }

    @Override
    public Order convert(OrderCreateRequest orderCreateRequest) {

        Order order =new Order();
        order.setRoom(roomRepository.findById(orderCreateRequest.getIdRoom()).get());
        order.setDataCheckIn(orderCreateRequest.getDataIn());
        order.setDataCheckOut(orderCreateRequest.getDataOut());
        order.setStatus(BOOKED);
        order.setCreated(new Timestamp(new Date().getTime()));

        return doConvert(orderCreateRequest,order);
    }
}
