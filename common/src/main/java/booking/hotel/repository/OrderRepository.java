package booking.hotel.repository;

import booking.hotel.domain.Order;

import java.util.List;

public interface OrderRepository extends  CrudOperations<Long, Order> {
    List<Order> findAllOrdersUser(String gmail);
}
