package booking.hotel.repository.dataspring;

import booking.hotel.domain.Comfort;
import booking.hotel.domain.Role;
import booking.hotel.domain.User;
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

public interface RoleRepositoryData extends JpaRepository<Role, Long> {

    Optional<Role> findById(Long id);

    Optional<Role> findByRoleName(String name);

    List<Role> findAll();

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    @Modifying
    void deleteById(Long id);

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    @Modifying
    @Query(value = "delete  from user_roles where role_id= :idRole",nativeQuery = true)
    int deleteDependenciesUser(@Param("idRole") Long idRole);

    @Query(value = "select *  from roles inner join user_roles ur on roles.id = ur.role_id where user_id= :idUser",nativeQuery = true)
    List<Role> getUserRoles(@Param ("idUser") Long id);
}
