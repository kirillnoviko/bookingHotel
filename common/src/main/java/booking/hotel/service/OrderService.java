package booking.hotel.service;


import booking.hotel.domain.Order;
import booking.hotel.domain.StatusName;
import booking.hotel.exception.BookingRoomException;
import booking.hotel.exception.NoSuchEntityException;
import booking.hotel.repository.dataspring.OrderRepositoryData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import static booking.hotel.domain.StatusName.CANCELED;


@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepositoryData orderRepositoryData;

    public Order createOrder(Order order){

        if(order.getStatus()==CANCELED){
            return orderRepositoryData.save(order);
        }

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

    public Order updateStatusName(Long idOrder, StatusName name){


        if(!orderRepositoryData.findById(idOrder).isEmpty()){

            Order order=orderRepositoryData.findById(idOrder).get();
            order.setChanged(new Timestamp(new Date().getTime()));
            order.setStatus(name);
            return orderRepositoryData.save(order);

        }
        else{
            throw new NoSuchEntityException("entity Order with id = " + idOrder + " does not exist");
        }

    }

}
