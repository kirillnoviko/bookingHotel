package booking.hotel.repository;

import booking.hotel.domain.Role;
import booking.hotel.domain.User;

import java.util.List;

public interface RoleRepository extends CrudOperations<Long, Role> {

    List<Role> getUserRoles(User user);
    List<Role> findByName(String roleName);
}
