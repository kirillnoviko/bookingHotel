package booking.hotel.repository.dataspring;

import booking.hotel.domain.Room;
import booking.hotel.domain.User;
import booking.hotel.repository.RoomRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;


public interface RoomRepositoryData extends JpaRepository<Room, Long>, RoomRepository {

    List<Room> findAll();




}
