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
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


public interface RoomRepositoryData extends JpaRepository<Room, Long>,CrudRepository<Room,Long>, RoomRepository {

    List<Room> findByDeletedIsFalse();

    List<Room> findAll();

    Optional<Room> findByIdAndDeletedIsFalse(Long id);

    Optional<Room> findById(Long id);

    void deleteById(Long id);

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    @Modifying
    @Query(value = "update Room set deleted = true where id = :idRoom")
    int delete(@Param("idRoom") Long idRoom);


    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    @Modifying
    @Query(value = "delete  from comforts_rooms where id_room= :idRoom",nativeQuery = true)
    int deleteDependenciesComforts(@Param("idRoom") Long idRoom);





}
