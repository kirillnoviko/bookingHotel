package booking.hotel.converter;


import org.springframework.stereotype.Component;

import booking.hotel.domain.Order;
import booking.hotel.exception.NoSuchEntityException;
import booking.hotel.repository.dataspring.OrderRepositoryData;
import booking.hotel.repository.dataspring.RoomRepositoryData;
import booking.hotel.request.OrderChangeRequest;
import static booking.hotel.domain.StatusName.*;


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
        if(request.getName()!=null ){
            if(order.getStatus()!=COMPLETED || order.getStatus()!=CANCELED){
                order.setStatus(request.getName());
            }else{
                throw new NoSuchEntityException("the order has a completed or canceled status, you cannot change the order status");
            }

        }



        return doConvert(request,order);
    }

}
