package booking.hotel.repository;


import booking.hotel.domain.Comfort;
import booking.hotel.domain.Room;
import booking.hotel.domain.RoomSearchRequest;

import java.util.List;
import java.util.Map;

public interface RoomRepository extends CrudOperations<Long,Room>{

    //<E,M> List<Room> findCriteriaRoom(Criteria<E> searchRoom, Criteria<M> searchData, List<String> additionalComfort);
    // List<Room> findCriteriaRoom(RoomSearchRequest request);

    List<Room> findByParamsRoom(RoomSearchRequest request);

    List<Room> findByListComfortsRoom(List<String> comforts);

    public void saveRoomAdditionalComfort(Room room, List<Comfort> userRoles);



}
