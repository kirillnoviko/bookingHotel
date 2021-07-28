package booking.hotel.repository.impl;

import booking.hotel.domain.Comfort;
import booking.hotel.domain.Room;
import booking.hotel.repository.ComfortRepository;
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
public class ComfortRepositoryImpl implements ComfortRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public static final String ID = "id";
    public static final String NAME_COMFORT = "name_comfort";

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;


    private Comfort getAdditionalComfortRowMapper(ResultSet resultSet, int i) throws SQLException {
        Comfort additional = new Comfort();

        additional.setId(resultSet.getLong(ID));
        additional.setNameComfort(resultSet.getString(NAME_COMFORT));

        return additional;
    }

    @Override
    public List<Comfort> findAll() {
        return jdbcTemplate.query("select * from comforts order by id desc", this::getAdditionalComfortRowMapper);
    }

    @Override
    public Comfort findOne(Long id) {
      /*  final String findOneWithNameParam = "select * from additional_comfort where id = :additionalId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("additionalId", id);

        return namedParameterJdbcTemplate.queryForObject(findOneWithNameParam, params, this::getAdditionalComfortRowMapper);
*/
        try (Session session = sessionFactory.openSession()) {
            return session.find(Comfort.class, id);

        }
    }



    @Override
    public Comfort save(Comfort entity) {
        final String createQuery = "insert into comforts (name_comfort) " +
                "values (:comfortName);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("comfortName", entity.getNameComfort());

        namedParameterJdbcTemplate.update(createQuery, params, keyHolder, new String[]{"id"});

        long createdRole = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return findOne(createdRole);
    }

    @Override
    public void batchInsert(List<Comfort> entities) {

    }

    @Override
    public Comfort update(Comfort entity) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<Comfort> getRoomAdditionalComfort(Room room) {
        final String findOneWithNameParam = "select ad.id as id, ad.name_comfort as name_additional from comforts_rooms inner join comforts ad on  comforts_rooms.id_comfort = ad.id where comforts_rooms.id_room = :roomId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("roomId", room.getId());

        return namedParameterJdbcTemplate.query(findOneWithNameParam, params, this::getAdditionalComfortRowMapper);

    }


}
