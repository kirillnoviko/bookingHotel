package booking.hotel.repository.dataspring;

import booking.hotel.domain.Order;
import booking.hotel.domain.User;
import booking.hotel.repository.RoomRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


@Cacheable("users")
//CrudRepository<User, Long>, PagingAndSortingRepository<User, Long> ,JpaRepository<User, Long> == JpaRepository<User, Long>
public interface UserRepositoryData extends JpaRepository<User, Long>  {



    @Cacheable("users")
    List<User> findByIsBannedFalseAndIsDeletedFalse();

    Optional<User> findById(Long id);

    @Query(value = "select * from  users  where gmail = :gmail ", nativeQuery = true)
    Optional<User>  findByGmail(String gmail);

    void deleteById(Long id);

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    @Modifying
    @Query(value = "update User set isDeleted = true where id = :idUser")
    int delete(@Param("idUser") Long idUser);

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    @Modifying
    @Query(value = "delete  from user_roles where user_id= :idUser",nativeQuery = true)
    int deleteDependenciesRoles(@Param("idUser") Long idUser);

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    @Modifying
    @Query(value = "insert into user_roles(user_id, role_id) values (:user_id, :role_id)", nativeQuery = true)
    int createSomeRow(@Param("user_id") Long userId, @Param("role_id") Long roleId);


    @Query(value = "select o from Order o   join  o.user where o.user = :idUser")
    List<Order> showAllOrderUser(User idUser);

}
