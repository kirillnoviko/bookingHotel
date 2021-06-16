package booking.hotel.repository;

import booking.hotel.domain.Booking;
import booking.hotel.domain.User;

import java.util.List;

public interface BookingRepository extends  CrudOperations<Long, Booking> {
    List<Booking> findAllOrdersUser(Long idUser);
}
