package booking.hotel.repository.impl;

import booking.hotel.domain.Booking;
import booking.hotel.domain.Room;
import booking.hotel.domain.User;
import booking.hotel.repository.BookingColumn;
import booking.hotel.repository.BookingRepository;
import booking.hotel.repository.RoomColumn;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookingRepositoryImpl implements BookingRepository {

    private  final  JdbcTemplate jdbcTemplate;

    // @Autowired
    //    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    //        this.jdbcTemplate = jdbcTemplate;
    //    }

    @Override
    public List<Booking> findAllOrdersUser(Long idUSer) {
        final String findOrdersUser = "select * from booking where id_user = ? ";
        return jdbcTemplate.query(findOrdersUser,new Object[] {idUSer},this::getBookingRowMapper);

    }

    @Override
    public List<Booking> findAll() {
        return jdbcTemplate.query("select * from  booking order by id desc", this::getBookingRowMapper);
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

    @Override
    public Booking findOne(Long id) {
        return null;
    }

    @Override
    public void addOne(Booking entity) {

    }

    @Override
    public Booking save(Booking entity) {
        return null;
    }

    @Override
    public void save(List<Booking> entities) {

    }

    @Override
    public Booking update(Booking entity) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
