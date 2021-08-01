package booking.hotel.repository.impl;

import booking.hotel.domain.Order;
import booking.hotel.repository.column.BookingColumn;
import booking.hotel.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class OrderRepositoryImpl implements OrderRepository {

    private  final  JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    // @Autowired
    //    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    //        this.jdbcTemplate = jdbcTemplate;
    //    }

    @Override
    public List<Order> findAllOrdersUser(String gmail) {
        final String findOrdersUser = "select * from orders join users u on u.id = orders.id_user where u.gmail = ? ";
        return jdbcTemplate.query(findOrdersUser, new String[]{gmail},this::getBookingRowMapper);

    }

    @Override
    public List<Order> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return  session.createQuery("FROM Order ").list();
        }
    }

    @Override
    public Order findOne(Long id) {
        final String findOneWithNameParam = "select * from orders where id = :idBooking ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idBooking", id);

        return namedParameterJdbcTemplate.queryForObject(findOneWithNameParam, params, this::getBookingRowMapper);

    }

    @Override
    public Order save(Order entity) {
        final String createQuery = "insert into orders (id_room, data_check_in, data_check_out, status, id_user, created, changed, general_price, rating_for_room,rating_for_client) " +
                "values (:idRoom, :dataCheckIn, :dataCheckOut, :status, :idUser, :created, :changed, :generalPrice, :ratingForRoom, :ratingForClient);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = generateBookingParamsMap(entity);

        namedParameterJdbcTemplate.update(createQuery, params, keyHolder, new String[]{"id"});

        long createdUserId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return findOne(createdUserId);
    }

    @Override
    public void batchInsert(List<Order> entities) {
        final String createQuery = "insert into orders (id_room, data_check_in, data_check_out, status, id_user, created, changed, general_price, rating_for_room,rating_for_client) " +
                "values (:idRoom, :dataCheckIn, :dataCheckOut, :status, :idUser, :created, :changed, :generalPrice, :ratingForRoom, :ratingForClient);";

        List<MapSqlParameterSource> batchParams = new ArrayList<>();

        for (Order order : entities) {
            batchParams.add(generateBookingParamsMap(order));
        }

        namedParameterJdbcTemplate.batchUpdate(createQuery, batchParams.toArray(new MapSqlParameterSource[0]));

    }

    @Override
    public Order update(Order entity) {
        final String createQuery = "update orders set id_room = :idRoom, data_check_in= :dataCheckIn, data_check_out = :dataCheckOut," +
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
        final String findOneWithNameParam = "delete from orders b where b.id = :id;" ;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        namedParameterJdbcTemplate.queryForObject(findOneWithNameParam, params, this::getBookingRowMapper);

    }

    private Order getBookingRowMapper(ResultSet rs, int i) throws SQLException {
        Order order =new Order();
        order.setId(rs.getLong(BookingColumn.ID));

        order.setDataCheckIn(rs.getTimestamp(BookingColumn.DATA_CHECK_IN));
        order.setDataCheckOut(rs.getTimestamp(BookingColumn.DATA_CHECK_OUT));
        order.setStatus(rs.getString(BookingColumn.STATUS));
        order.setIdUser(rs.getLong(BookingColumn.ID_USER));
        order.setCreated(rs.getTimestamp(BookingColumn.CREATED));
        order.setChanged(rs.getTimestamp(BookingColumn.CHANGED));
        order.setGeneralPrice(rs.getLong(BookingColumn.GENERAL_PRICE));
        order.setRatingForClient(rs.getLong(BookingColumn.RATING_FOR_CLIENT));
        order.setRatingForRoom(rs.getLong(BookingColumn.RATING_FOR_ROOM));

        return order;
    }

    private MapSqlParameterSource generateBookingParamsMap(Order entity) {

        MapSqlParameterSource params = new MapSqlParameterSource();

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
