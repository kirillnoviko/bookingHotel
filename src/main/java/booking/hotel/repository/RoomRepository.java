package booking.hotel.repository;

import booking.hotel.domain.AdditionalComfort;
import booking.hotel.domain.Role;
import booking.hotel.domain.Room;
import booking.hotel.domain.User;
import booking.hotel.domain.criteria.Criteria;

import java.util.List;

public interface RoomRepository extends CrudOperations<Long,Room>{

    <E> List<Room> findCriteriaRoom(Criteria<E> searchRoom);

    void saveRoomAdditionalComfort(Room room, List<AdditionalComfort> userRoles);

}
