package booking.hotel.repository.impl;

import booking.hotel.domain.Booking;
import booking.hotel.domain.User;
import booking.hotel.repository.column.BookingColumn;
import booking.hotel.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
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
@RequiredArgsConstructor
public class BookingRepositoryImpl implements BookingRepository {

    private  final  JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    // @Autowired
    //    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    //        this.jdbcTemplate = jdbcTemplate;
    //    }

    @Override
    public List<Booking> findAllOrdersUser(Long idUser) {
        final String findOrdersUser = "select * from booking where id_user = ? ";
        return jdbcTemplate.query(findOrdersUser,new Object[] {idUser},this::getBookingRowMapper);

    }

    @Override
    public List<Booking> findAll() {
        return jdbcTemplate.query("select * from  booking order by id desc", this::getBookingRowMapper);
    }

    @Override
    public Booking findOne(Long id) {
        final String findOneWithNameParam = "select * from booking where id = :idBooking ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idBooking", id);

        return namedParameterJdbcTemplate.queryForObject(findOneWithNameParam, params, this::getBookingRowMapper);

    }

    @Override
    public Booking save(Booking entity) {
        final String createQuery = "insert into booking (id_room, data_check_in, data_check_out, status, id_user, created, changed, general_price, rating_for_room,rating_for_client) " +
                "values (:idRoom, :dataCheckIn, :dataCheckOut, :status, :idUser, :created, :changed, :generalPrice, :ratingForRoom, :ratingForClient);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = generateBookingParamsMap(entity);

        namedParameterJdbcTemplate.update(createQuery, params, keyHolder, new String[]{"id"});

        long createdUserId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return findOne(createdUserId);
    }

    @Override
    public void batchInsert(List<Booking> entities) {
        final String createQuery = "insert into booking (id_room, data_check_in, data_check_out, status, id_user, created, changed, general_price, rating_for_room,rating_for_client) " +
                "values (:idRoom, :dataCheckIn, :dataCheckOut, :status, :idUser, :created, :changed, :generalPrice, :ratingForRoom, :ratingForClient);";

        List<MapSqlParameterSource> batchParams = new ArrayList<>();

        for (Booking booking : entities) {
            batchParams.add(generateBookingParamsMap(booking));
        }

        namedParameterJdbcTemplate.batchUpdate(createQuery, batchParams.toArray(new MapSqlParameterSource[0]));

    }

    @Override
    public Booking update(Booking entity) {
        final String createQuery = "update booking set id_room = :idRoom, data_check_in= :dataCheckIn, data_check_out = :dataCheckOut," +
                " status = :status, id_user = :idUser, created = :created, changed = :changed, general_price = :generalPrice," +
                "rating_for_room = :ratingForRoom, rating_for_client = :ratingForClient  where id= :id ";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = generateBookingParamsMap(entity);

        namedParameterJdbcTemplate.update(createQuery, params, keyHolder, new String[]{"id"});

        long createdUserId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return findOne(createdUserId);
    }

    @Override
    public void delete(Long id) {
        final String findOneWithNameParam = "delete from booking b where b.id = :id;" ;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        namedParameterJdbcTemplate.queryForObject(findOneWithNameParam, params, this::getBookingRowMapper);

    }

    private Booking getBookingRowMapper(ResultSet rs, int i) throws SQLException {
        Booking booking=new Booking();
        booking.setId(rs.getLong(BookingColumn.ID));
        booking.setIdRoom(rs.getLong(BookingColumn.ID_ROOM));
        booking.setDataCheckIn(rs.getDate(BookingColumn.DATA_CHECK_IN));
        booking.setDataCheckOut(rs.getDate(BookingColumn.DATA_CHECK_OUT));
        booking.setStatus(rs.getString(BookingColumn.STATUS));
        booking.setIdUser(rs.getLong(BookingColumn.ID_USER));
        booking.setCreated(rs.getDate(BookingColumn.CREATED));
        booking.setChanged(rs.getDate(BookingColumn.CHANGED));
        booking.setGeneralPrice(rs.getLong(BookingColumn.GENERAL_PRICE));
        booking.setRatingForClient(rs.getLong(BookingColumn.RATING_FOR_CLIENT));
        booking.setRatingForRoom(rs.getLong(BookingColumn.RATING_FOR_ROOM));

        return booking;
    }

    private MapSqlParameterSource generateBookingParamsMap(Booking entity) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idRoom", entity.getIdRoom());
        params.addValue("dataCheckIn", entity.getDataCheckIn());
        params.addValue("dataCheckOut", entity.getDataCheckOut());
        params.addValue("status", entity.getStatus());
        params.addValue("idUser", entity.getIdUser());
        params.addValue("created", new Date());
        params.addValue("changed", new Date());
        params.addValue("generalPrice", entity.getGeneralPrice());
        params.addValue("ratingForRoom", new Date());
        params.addValue("ratingForClient",new Date());

        return params;
    }
}
