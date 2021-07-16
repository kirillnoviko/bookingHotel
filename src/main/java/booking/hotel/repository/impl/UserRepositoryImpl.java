package booking.hotel.repository.impl;

import booking.hotel.domain.Role;
import booking.hotel.domain.User;
import booking.hotel.repository.column.UserColumn;
import booking.hotel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
//@Primary
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {


    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;


    @Override
    public List<User> findAll() {
        //return namedParameterJdbcTemplate.query("select * from users order by id desc", this::getUserRowMapper);

        try (Session session = sessionFactory.openSession()) {
            return  session.createQuery("FROM User where isDeleted = true and isBanned = true").list();
        }
    }



    @Override
    public User findOne(Long id) {

      /*  final String findOneWithNameParam = "select * from users where id = :idUser ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idUser", id);

        return namedParameterJdbcTemplate.queryForObject(findOneWithNameParam, params, this::getUserRowMapper);*/

        try (Session session = sessionFactory.openSession()) {
            return session.find(User.class,id);
        }
    }

    @Override
    public User save(User entity) {
        final String createQuery = "insert into users (name, surname, birth_date, gmail, password, is_deleted, is_banned, created, changed, rating_average) " +
                "values (:name, :surname, :birthDate, :gmail, :password, :isDeleted, :isBanned, :created, :changed, :ratingAverage);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = generateUserParamsMap(entity);

        namedParameterJdbcTemplate.update(createQuery, params, keyHolder, new String[]{"id"});

        long createdUserId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return findOne(createdUserId);
    }

    @Override
    public User update(User entity) {
        final String createQuery = "update users set name = :name, surname= :price, gmail = :gmail," +
                " password = :password, rating_average = :ratingAverage = :idRole, birth_date = :birthDate  where id= :id ";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = generateUserParamsMap(entity);

        namedParameterJdbcTemplate.update(createQuery, params, keyHolder, new String[]{"id"});

        long createdUserId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return findOne(createdUserId);
    }

    @Override
    public void delete(Long id) {

        final String findOneWithNameParam = "delete from user_roles ur where ur.user_id = :idUser;" +
                "delete from users u where u.id = :idUser";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idUser", id);

        namedParameterJdbcTemplate.queryForObject(findOneWithNameParam, params, this::getUserRowMapper);

    }

    @Override
    public List<User> findUsersByQuery(Integer limit, String query) {
        return null;
    }

    @Override
    public void batchInsert(List<User> users) {
        final String createQuery = "insert into users (name, surname, birth_date, gmail, password, is_deleted, is_banned, created, changed, rating_average) " +
                "values (:name, :surname, :birthDate, :gmail, :password, :isDeleted, :isBanned, :created, :changed, :ratingAverage, 1);";

        List<MapSqlParameterSource> batchParams = new ArrayList<>();

        for (User user : users) {
            batchParams.add(generateUserParamsMap(user));
        }

        namedParameterJdbcTemplate.batchUpdate(createQuery, batchParams.toArray(new MapSqlParameterSource[0]));

    }


    @Override
    public void saveUserRoles(User user, List<Role> userRoles) {
        final String createQuery = "insert into user_roles (role_id, user_id) " +
                "values (:roleId, :userId);";

        List<MapSqlParameterSource> batchParams = new ArrayList<>();

        for (Role role : userRoles) {

            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("roleId", role.getId());
            params.addValue("userId", user.getId());

            batchParams.add(params);
        }

        namedParameterJdbcTemplate.batchUpdate(createQuery, batchParams.toArray(new MapSqlParameterSource[0]));
    }

    @Override
    public User findByLoginAndPassword(String gmail, String password) {
        final String searchQuery = "select * from users where gmail = :gmail and password = :password";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("password", password);
        params.addValue("gmail", gmail);

        return namedParameterJdbcTemplate.queryForObject(searchQuery, params, this::getUserRowMapper);
    }


    @Override
    public User findByLogin(String gmail) {
        final String searchQuery = "select * from users where gmail = :gmail";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("gmail", gmail);

        return Optional.of(namedParameterJdbcTemplate.queryForObject(searchQuery, params, this::getUserRowMapper)).get();
    }

    private User getUserRowMapper(ResultSet rs, int i) throws SQLException {
        User user = new User();
        user.setId(rs.getLong(UserColumn.ID));
        user.setName(rs.getString(UserColumn.NAME));
        user.setSurname(rs.getString(UserColumn.SURNAME));
        user.setBirthDate(rs.getTimestamp(UserColumn.BIRTH_DATE));
        user.setGmail(rs.getString(UserColumn.GMAIL));
        user.setPassword(rs.getString(UserColumn.PASSWORD));
        user.setRatingAverage(rs.getLong(UserColumn.RATING_AVERAGE));
        return user;
    }

    private MapSqlParameterSource generateUserParamsMap(User entity) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", entity.getName());
        params.addValue("surname", entity.getSurname());
        params.addValue("birthDate", new Date());
        params.addValue("gmail", entity.getGmail());
        params.addValue("password", entity.getPassword());
        params.addValue("isDeleted", false);
        params.addValue("isBanned", false);
        params.addValue("created", new Date());
        params.addValue("changed", new Date());
        params.addValue("ratingAverage", 1l);

        return params;
    }
}
