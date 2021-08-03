package booking.hotel.repository;


import booking.hotel.domain.Comfort;
import booking.hotel.domain.Room;
import booking.hotel.util.RoomSearchRequest;

import java.sql.Timestamp;
import java.util.List;

public interface RoomRepository {

    //<E,M> List<Room> findCriteriaRoom(Criteria<E> searchRoom, Criteria<M> searchData, List<String> additionalComfort);
    // List<Room> findCriteriaRoom(RoomSearchRequest request);

    List<Room> findByParamsRoom(Long minPriceRequest, Long maxPriceRequest,
                                Long minRatingRequest, Long maxRatingRequest, String principlePlacementRequest );

    List<Room> findByListComfortsRoom(List<Long> comforts);

    List<Room> findByData(List<Room> rooms, Timestamp dataIn, Timestamp dataOut);



}
