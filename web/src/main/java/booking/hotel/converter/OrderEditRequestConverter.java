package booking.hotel.converter;

import booking.hotel.domain.Order;
import booking.hotel.domain.Role;
import booking.hotel.repository.dataspring.OrderRepositoryData;
import booking.hotel.repository.dataspring.RoleRepositoryData;
import booking.hotel.repository.dataspring.RoomRepositoryData;
import booking.hotel.request.OrderChangeRequest;
import booking.hotel.request.OrderCreateRequest;
import booking.hotel.request.RoleChangeRequest;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;

import static booking.hotel.domain.StatusName.BOOKED;


@Component
public class OrderEditRequestConverter  extends  EntityConverter <OrderChangeRequest, Order>{

    private OrderRepositoryData orderRepositoryData;
    private RoomRepositoryData roomRepository;


    public OrderEditRequestConverter(OrderRepositoryData orderRepositoryData, RoomRepositoryData roomRepositoryData ) {
        this.orderRepositoryData = orderRepositoryData;
        this.roomRepository = roomRepositoryData;
    }

    @Override
    public Order convert(OrderChangeRequest request) {

        Order order =orderRepositoryData.findById(request.getIdOrder()).get();

        if(request.getDataIn()!=null){
            order.setDataCheckIn(request.getDataIn());
        }
        if(request.getDataOut()!=null){
            order.setDataCheckOut(request.getDataOut());
        }
        if(request.getIdRoom()!=null){
            order.setRoom(roomRepository.findById(request.getIdRoom()).get());
        }

        return doConvert(request,order);
    }

}