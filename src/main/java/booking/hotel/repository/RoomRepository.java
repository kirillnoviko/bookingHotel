package booking.hotel.repository;

import booking.hotel.domain.Comfort;
import booking.hotel.domain.Room;
import booking.hotel.domain.criteria.Criteria;

import java.util.List;

public interface RoomRepository extends CrudOperations<Long,Room>{

    <E,M> List<Room> findCriteriaRoom(Criteria<E> searchRoom, Criteria<M> searchData, List<String> additionalComfort);

    public void saveRoomAdditionalComfort(Room room, List<Comfort> userRoles);



}
