package booking.hotel.repository;


import booking.hotel.domain.Room;
import booking.hotel.util.EntityForSearchRoom;

import java.util.List;

public interface RoomRepository {

    List<Room> findByListComfortsRoom(EntityForSearchRoom entity);

}
