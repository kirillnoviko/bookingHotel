package booking.hotel.service;


import booking.hotel.domain.Order;
import booking.hotel.domain.StatusName;
import booking.hotel.exception.BookingRoomException;
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
                throw  new BookingRoomException("The room with id = "+ idRoom +" has already been booked for these dates");
            }
        }
        return orderRepositoryData.save(order);
    }

    public List<Order> findAllParams(Order orderSearch){

        if(orderSearch.getDataCheckIn()==null && orderSearch.getDataCheckOut()==null && orderSearch.getStatus()!=null){
           return orderRepositoryData.findByUserGmailAndStatus(orderSearch.getUser().getGmail(),orderSearch.getStatus());
        }
        if(orderSearch.getDataCheckIn()==null && orderSearch.getDataCheckOut()==null && orderSearch.getStatus()==null){
            return orderRepositoryData.findByUserGmail(orderSearch.getUser().getGmail());
        }
        return orderRepositoryData.findByAllParams(orderSearch);

    }

}
