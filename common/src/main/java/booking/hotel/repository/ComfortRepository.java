package booking.hotel.repository;

import booking.hotel.domain.Comfort;
import booking.hotel.domain.Room;

import java.util.List;

public interface ComfortRepository extends CrudOperations<Long, Comfort>{
    List<Comfort> getRoomAdditionalComfort(Room room);
}
