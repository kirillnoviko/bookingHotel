package booking.hotel.repository.dataspring;

import booking.hotel.domain.Order;
import booking.hotel.domain.StatusName;
import booking.hotel.repository.OrderRepository;
import booking.hotel.repository.RoomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface OrderRepositoryData extends JpaRepository<Order, Long>, RoomRepository, OrderRepository {

    List<Order> findAll();

    Optional<Order> findById(Long idOrder);

    List<Order> findByUserGmail(String gmail);

    List<Order> findByUserGmailAndStatus(String gmail, StatusName status);

    @Query(value = " select o.room.id from Order o " +
            "where o.room.id = :idRoom and " +
            "(o.status <> 'CANCELED' or o.status <> 'COMPLETED') and " +
            "( " +
            "(:dataIn between o.dataCheckIn and o.dataCheckOut) or " +
            "(:dataOut between o.dataCheckIn and o.dataCheckOut) or" +
            "(:dataIn < o.dataCheckIn and :dataOut > o.dataCheckOut) " +
            ")"
    )
    List<Long> findByIdUserAndData(Long idRoom, Timestamp dataIn, Timestamp dataOut);

}
