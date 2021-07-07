package booking.hotel.repository.impl;

import booking.hotel.domain.Role;
import booking.hotel.domain.User;
import booking.hotel.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Repository
@Primary
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public static final String ROLE_ID = "id";
    public static final String ROLE_NAME = "role_name";

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;


    private Role getRoleRowMapper(ResultSet resultSet, int i) throws SQLException {
        Role role = new Role();

        role.setId(resultSet.getLong(ROLE_ID));
        role.setRoleName(resultSet.getString(ROLE_NAME));

        return role;
    }

    @Override
    public List<Role> findAll() {
        return jdbcTemplate.query("select * from roles order by id desc", this::getRoleRowMapper);
    }

    @Override
    public Role findOne(Long id) {
  /*      final String findOneWithNameParam = "select * from roles where id = :roleId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("roleId", id);

        return namedParameterJdbcTemplate.queryForObject(findOneWithNameParam, params, this::getRoleRowMapper);
   */

        try (Session session = sessionFactory.openSession()) {
            return session.find(Role.class, id);

        }
    }

    @Override
    public Role save(Role entity) {
        final String createQuery = "insert into roles (role_name) " +
                "values (:roleName);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("roleName", entity.getRoleName());

        namedParameterJdbcTemplate.update(createQuery, params, keyHolder, new String[]{"id"});

        long createdRole = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return findOne(createdRole);
    }

    @Override
    public void batchInsert(List<Role> entities) {

    }


    @Override
    public Role update(Role entity) {
        return null;
    }


    @Override
    public void delete(Long id) {

    }

    @Override
    public List<Role> getUserRoles(User user) {
        final String findOneWithNameParam = "select r.id as id, r.role_name as role_name from user_roles inner join roles r on r.id = user_roles.role_id where user_roles.user_id = :userId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", user.getId());

        return namedParameterJdbcTemplate.query(findOneWithNameParam, params, this::getRoleRowMapper);
    }
}

