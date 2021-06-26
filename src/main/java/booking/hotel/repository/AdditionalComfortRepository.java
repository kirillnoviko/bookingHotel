package booking.hotel.repository;

import booking.hotel.domain.AdditionalComfort;
import booking.hotel.domain.Role;
import booking.hotel.domain.Room;
import booking.hotel.domain.User;

import java.util.List;

public interface AdditionalComfortRepository extends CrudOperations<Long, AdditionalComfort>{
    List<AdditionalComfort> getRoomAdditionalComfort(Room room);
}
