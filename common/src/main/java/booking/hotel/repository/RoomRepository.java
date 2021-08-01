package booking.hotel.repository;


import booking.hotel.domain.Comfort;
import booking.hotel.domain.Room;
import booking.hotel.util.RoomSearchRequest;

import java.util.List;

public interface RoomRepository {

    //<E,M> List<Room> findCriteriaRoom(Criteria<E> searchRoom, Criteria<M> searchData, List<String> additionalComfort);
    // List<Room> findCriteriaRoom(RoomSearchRequest request);

    List<Room> findByParamsRoom(RoomSearchRequest request);

    List<Room> findByListComfortsRoom(List<Long> comforts);

    public void saveRoomAdditionalComfort(Room room, List<Comfort> userRoles);



}
