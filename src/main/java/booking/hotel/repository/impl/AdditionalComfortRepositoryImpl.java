package booking.hotel.repository.impl;

import booking.hotel.domain.AdditionalComfort;
import booking.hotel.domain.Role;
import booking.hotel.domain.Room;
import booking.hotel.repository.AdditionalComfortRepository;
import lombok.RequiredArgsConstructor;
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
public class AdditionalComfortRepositoryImpl implements AdditionalComfortRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public static final String ID = "id";
    public static final String NAME_ADDITIONAL = "name_additional";


    private AdditionalComfort getAdditionalComfortRowMapper(ResultSet resultSet, int i) throws SQLException {
        AdditionalComfort additional = new AdditionalComfort();

        additional.setId(resultSet.getLong(ID));
        additional.setNameAdditional(resultSet.getString(NAME_ADDITIONAL));

        return additional;
    }

    @Override
    public List<AdditionalComfort> findAll() {
        return jdbcTemplate.query("select * from additional_comfort order by id desc", this::getAdditionalComfortRowMapper);
    }

    @Override
    public AdditionalComfort findOne(Long id) {
        final String findOneWithNameParam = "select * from additional_comfort where id = :additionalId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("additionalId", id);

        return namedParameterJdbcTemplate.queryForObject(findOneWithNameParam, params, this::getAdditionalComfortRowMapper);

    }



    @Override
    public AdditionalComfort save(AdditionalComfort entity) {
        final String createQuery = "insert into additional_comfort (name_additional) " +
                "values (:additionalName);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("additionalName", entity.getNameAdditional());

        namedParameterJdbcTemplate.update(createQuery, params, keyHolder, new String[]{"id"});

        long createdRole = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return findOne(createdRole);
    }

    @Override
    public void batchInsert(List<AdditionalComfort> entities) {

    }

    @Override
    public AdditionalComfort update(AdditionalComfort entity) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<AdditionalComfort> getRoomAdditionalComfort(Room room) {
        final String findOneWithNameParam = "select ad.id as id, ad.name_additional as name_additional from additional_in_section inner join additional_comfort ad on ad.id = additional_in_section.id_additional_comfort where additional_in_section.id_section_hotel_room = :roomId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("roomId", room.getId());

        return namedParameterJdbcTemplate.query(findOneWithNameParam, params, this::getAdditionalComfortRowMapper);

    }


}
