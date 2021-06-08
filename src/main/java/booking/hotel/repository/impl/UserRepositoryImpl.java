package booking.hotel.repository.impl;

import booking.hotel.domain.User;
import booking.hotel.repository.UserColumn;
import booking.hotel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Repository
//@Primary
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {


    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<User> findAll() {
        return namedParameterJdbcTemplate.query("select * from users order by id desc", this::getUserRowMapper);
    }



    @Override
    public User findOne(Long id) {

        final String findOneWithNameParam = "select * from users where id = :idUser ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idUser", id);

        return namedParameterJdbcTemplate.queryForObject(findOneWithNameParam, params, this::getUserRowMapper);

    }

    @Override
    public void addOne(User entity) {

    }

    @Override
    public void save(List<User> entities) {

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
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<User> findUsersByQuery(Integer limit, String query) {
        return null;
    }

    @Override
    public Double getUserExpensiveCarPrice(Integer userId) {
        return null;
    }

    @Override
    public void batchInsert(List<User> users) {
        final String createQuery = "insert into users (name, surname, birth_date, gmail, password, is_deleted, is_banned, created, changed, rating_average) " +
                "values (:name, :surname, :birthDate, :gmail, :password, :isDeleted, :isBanned, :created, :changed, :ratingAverage);";

        List<MapSqlParameterSource> batchParams = new ArrayList<>();

        for (User user : users) {
            batchParams.add(generateUserParamsMap(user));
        }

        namedParameterJdbcTemplate.batchUpdate(createQuery, batchParams.toArray(new MapSqlParameterSource[0]));

    }


    private User getUserRowMapper(ResultSet rs, int i) throws SQLException {
        User user = new User();
        user.setId(rs.getLong(UserColumn.ID));
        user.setName(rs.getString(UserColumn.NAME));
        user.setSurname(rs.getString(UserColumn.SURNAME));
        user.setBirthDate(rs.getDate(UserColumn.BIRTH_DATE));
        user.setGmail(rs.getString(UserColumn.GMAIL));
        user.setRatingAverage(rs.getLong(UserColumn.RATING_AVERAGE));
        return user;
    }
    private MapSqlParameterSource generateUserParamsMap(User entity) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", entity.getName());
        params.addValue("surname", entity.getSurname());
        params.addValue("birthDate", entity.getBirthDate());
        params.addValue("gmail", entity.getGmail());
        params.addValue("password", entity.getPassword());
        params.addValue("isDeleted", false);
        params.addValue("isBanned", false);
        params.addValue("created", new Date());
        params.addValue("changed", new Date());
        params.addValue("ratingAverage", entity.getRatingAverage());

        return params;
    }
}
