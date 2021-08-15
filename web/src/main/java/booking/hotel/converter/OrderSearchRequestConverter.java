package booking.hotel.converter;

import booking.hotel.domain.Order;
import booking.hotel.repository.dataspring.OrderRepositoryData;
import booking.hotel.repository.dataspring.RoomRepositoryData;
import booking.hotel.request.OrderChangeRequest;
import booking.hotel.request.OrderSearchRequest;
import org.springframework.stereotype.Component;

@Component
public class OrderSearchRequestConverter extends  EntityConverter <OrderSearchRequest, Order>{

    private OrderRepositoryData orderRepositoryData;
    private RoomRepositoryData roomRepository;


    public OrderSearchRequestConverter(OrderRepositoryData orderRepositoryData, RoomRepositoryData roomRepositoryData ) {
        this.orderRepositoryData = orderRepositoryData;
        this.roomRepository = roomRepositoryData;
    }

    @Override
    public Order convert(OrderSearchRequest request) {


        Order order =new Order();

        if(request.getStatusName()!=null){
            order.setStatus(request.getStatusName());
        }
        if(request.getDataIn()!=null){
            order.setDataCheckIn(request.getDataIn());
        }
        if(request.getDataOut()!=null){
            order.setDataCheckOut(request.getDataOut());
        }

        return doConvert(request,order);
    }

}