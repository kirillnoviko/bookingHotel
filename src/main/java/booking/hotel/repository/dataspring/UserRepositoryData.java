package booking.hotel.repository.dataspring;

import booking.hotel.domain.User;
import org.springframework.cache.annotation.Cacheable;
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


@Cacheable("users")
public interface UserRepositoryData extends CrudRepository<User, Long>, PagingAndSortingRepository<User, Long> ,JpaRepository<User, Long> {


/*
    @Cacheable("users")
    List<User> findByIsBannedFalseAndIsDeletedFalse();
*/


    @Cacheable("users")
    Optional<User> findById(Long id);

    Optional<User> findByGmail(String gmail);

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    User save(User entity);


    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    @Modifying
    @Query(value = "insert into user_roles(user_id, role_id) values (:user_id, :role_id)", nativeQuery = true)
    int createSomeRow(@Param("user_id") Long userId, @Param("role_id") Long roleId);


}
