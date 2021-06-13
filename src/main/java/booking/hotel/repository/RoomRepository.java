package booking.hotel.repository;

import booking.hotel.domain.Room;

import java.util.List;

public interface RoomRepository extends CrudOperations<Long,Room>{

    List<Room> findCriteriaRoom(Room entity);

}
