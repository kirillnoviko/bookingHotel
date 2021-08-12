package booking.hotel.service;


import booking.hotel.domain.Order;
import booking.hotel.domain.StatusName;
import booking.hotel.repository.dataspring.OrderRepositoryData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepositoryData orderRepositoryData;

    public Order createOrder(Order order){
        List<Long> bookedRooms = orderRepositoryData.findByIdUserAndData(order.getRoom().getId(),order.getDataCheckIn(),order.getDataCheckOut());
        for(Long idRoom : bookedRooms ){
            if(idRoom==order.getRoom().getId()){
               // break;
                return null;
                //TODO exception this room is already booked
            }
        }
        return orderRepositoryData.save(order);
    }

}
