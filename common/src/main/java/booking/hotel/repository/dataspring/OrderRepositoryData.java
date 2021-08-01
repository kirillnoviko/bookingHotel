package booking.hotel.repository.dataspring;

import booking.hotel.domain.Order;
import booking.hotel.domain.Room;
import booking.hotel.repository.RoomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepositoryData extends JpaRepository<Order, Long>, RoomRepository {


}
