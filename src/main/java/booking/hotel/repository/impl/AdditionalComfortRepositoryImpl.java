package booking.hotel.repository.impl;

import booking.hotel.domain.AdditionalComfort;
import booking.hotel.domain.Role;
import booking.hotel.domain.Room;
import booking.hotel.repository.AdditionalComfortRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Primary
@RequiredArgsConstructor
public class AdditionalComfortRepositoryImpl implements AdditionalComfortRepository {

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public static final String ID = "id";
    public static final String NAME_ADDITIONAL = "name_additional";

    @Override
    public List<AdditionalComfort> findAll() {
        return null;
    }

    @Override
    public AdditionalComfort findOne(Long id) {
        return null;
    }

    @Override
    public void addOne(AdditionalComfort entity) {

    }

    @Override
    public AdditionalComfort save(AdditionalComfort entity) {
        return null;
    }

    @Override
    public void save(List<AdditionalComfort> entities) {

    }

    @Override
    public AdditionalComfort update(AdditionalComfort entity) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<Role> getRoomAdditionalComfort(Room room) {
        return null;
    }
}
