package booking.hotel.repository;

import booking.hotel.domain.Room;
import booking.hotel.domain.criteria.Criteria;

import java.util.List;

public interface RoomRepository extends CrudOperations<Long,Room>{

    <E> List<Room> findCriteriaRoom(Criteria<E> searchRoom);

}
