package booking.hotel.repository.impl;

import booking.hotel.domain.Room;
import booking.hotel.domain.User;
import booking.hotel.domain.criteria.Criteria;
import booking.hotel.repository.RoomColumn;
import booking.hotel.repository.RoomRepository;
import booking.hotel.repository.UserColumn;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import javax.naming.Name;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository

@RequiredArgsConstructor
public class RoomRepositoryImpl implements RoomRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    //    @Autowired
    //    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    //        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    //    }

    @Override
    public List<Room> findAll() {
        return namedParameterJdbcTemplate.query("select * from room order by id desc",this::getRoomRowMapper);
    }



    @Override
    public Room findOne(Long id) {

        final String findOneWithNameParam = "select * from users where id = :idUser ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idUser", id);

        return namedParameterJdbcTemplate.queryForObject(findOneWithNameParam, params, this::getRoomRowMapper);

    }

    @Override
    public void addOne(Room entity) {

    }

    @Override
    public Room save(Room entity) {
        final String createQuery = "insert into room (name, price, principle_of_placement, number_room, rating_average) " +
                "values (:name, :price, :principleOfPlacement, :numberRoom, :ratingAverage);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = generateRoomParamsMap(entity);

        namedParameterJdbcTemplate.update(createQuery, params, keyHolder, new String[]{"id"});

        long createdUserId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return findOne(createdUserId);
    }


    @Override
    public void save(List<Room> entities) {
        for (Room entity : entities) {
            addOne(entity);
        }
    }

    @Override
    public Room update(Room entity) {
        final String createQuery = "update room set name = :name, price= :price, principle_of_placement = :principleOfPlacement," +
                " number_room = :numberRoom, rating_average = :ratingAverage  where id= :id ";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = generateRoomParamsMap(entity);

        namedParameterJdbcTemplate.update(createQuery, params, keyHolder, new String[]{"id"});

        long createdUserId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return findOne(createdUserId);
    }

    @Override
    public void delete(Long id) {

        final String findOneWithNameParam = "delete from additional_in_section where additional_in_section.id = :idRoom;" +
                "delete from room where room.id = :idRoom";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idRoom", id);

        namedParameterJdbcTemplate.queryForObject(findOneWithNameParam, params, this::getRoomRowMapper);


    }

    @Override
    public <E> List<Room> findCriteriaRoom( Criteria<E> searchRoom){

        List<String> result = new ArrayList<String>();
        MapSqlParameterSource params = new MapSqlParameterSource();

        for (Map.Entry<E, Object> entry : searchRoom.getCriteria()) {
            if(entry.getValue()!=null ){
                String temp=entry.getKey().toString().toLowerCase() + " = :" + entry.getKey().toString().toLowerCase();
                result.add(temp);
                params.addValue(entry.getKey().toString().toLowerCase(),entry.getValue());
            }
        }

        String query="select * from room where ";
        for(int i=0;i<result.size();i++){

            query += result.get(i);
            if( i!=result.size()-1){
                query+=" and ";
            }
            if(i==result.size()-1){
                query+=" ; ";
            }

        }

        return namedParameterJdbcTemplate.query(query, params, this::getRoomRowMapper);

    }
    private Room getRoomRowMapper(ResultSet rs, int i) throws SQLException {
        Room room = new Room();
        room.setId(rs.getLong(RoomColumn.ID));
        room.setName(rs.getString(RoomColumn.NAME));
        room.setPrice(rs.getLong(RoomColumn.PRICE));
        room.setPrincipleOfPlacement(rs.getString(RoomColumn.PRINCIPLE_OF_PLACEMENT));
        room.setNumberRoom(rs.getString(RoomColumn.NUMBER_ROOM));
        room.setRatingAverage(rs.getLong(RoomColumn.RATING_AVERAGE));
        return room;
    }


    private MapSqlParameterSource generateRoomParamsMap(Room entity) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id",entity.getId());
        params.addValue("name", entity.getName());
        params.addValue("price", entity.getPrice());
        params.addValue("principleOfPlacement", entity.getPrincipleOfPlacement());
        params.addValue("numberRoom", entity.getNumberRoom());
        params.addValue("ratingAverage", entity.getRatingAverage());
        return params;
    }
}
