package booking.hotel.repository;

import booking.hotel.domain.AdditionalComfort;
import booking.hotel.domain.Role;
import booking.hotel.domain.Room;
import booking.hotel.domain.User;
import booking.hotel.domain.criteria.Criteria;

import java.util.Date;
import java.util.List;

public interface RoomRepository extends CrudOperations<Long,Room>{

    <E,M> List<Room> findCriteriaRoom(Criteria<E> searchRoom, Criteria<M> searchData);

    public void saveRoomAdditionalComfort(Room room, List<AdditionalComfort> userRoles);

    public List<Room> searchForFreeRoomByDate(Date DataIn, Date DateOut);

}
