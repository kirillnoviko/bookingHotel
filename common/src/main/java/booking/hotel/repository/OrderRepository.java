package booking.hotel.repository;

import booking.hotel.domain.Order;

import java.util.List;

public interface OrderRepository {
    List<Order> findAllOrdersUser(String gmail);
}
