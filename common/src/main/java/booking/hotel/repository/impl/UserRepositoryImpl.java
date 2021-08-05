package booking.hotel.repository.impl;

import booking.hotel.domain.Role;
import booking.hotel.domain.User;
import booking.hotel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;


import java.util.*;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {


    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    @Override
    public List<User> findUsersByQuery(Integer limit, String query) {
        return null;
    }

    @Override
    public void saveUserRoles(User user, List<Role> userRoles) {

    }

    @Override
    public User findByLoginAndPassword(String login, String password) {
        return null;
    }

    @Override
    public User findByLogin(String login) {
        return null;
    }


}