package booking.hotel.repository;

import booking.hotel.domain.Role;
import booking.hotel.domain.User;

import java.util.List;

public interface UserRepository {


    List<User> findUsersByQuery(Integer limit, String query);

    void saveUserRoles(User user, List<Role> userRoles);

    User findByLoginAndPassword(String login, String password);

    User findByLogin(String login);


}
