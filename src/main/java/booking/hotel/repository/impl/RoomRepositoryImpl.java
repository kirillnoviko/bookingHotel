package booking.hotel.repository.impl;

import booking.hotel.domain.AdditionalComfort;
import booking.hotel.domain.Role;
import booking.hotel.domain.Room;
import booking.hotel.domain.User;
import booking.hotel.domain.criteria.Criteria;
import booking.hotel.repository.AdditionalComfortRepository;
import booking.hotel.repository.column.RoomColumn;
import booking.hotel.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class RoomRepositoryImpl implements RoomRepository {

    private final AdditionalComfortRepository additionalComfortRepository;
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
    public void batchInsert(List<Room> entities) {
        final String createQuery = "insert into room (name, price, principle_of_placement, comfort_level, number_room, rating_average) " +
                "values (:name, :price, :principleOfPlacement,:comfortLevel, :numberRoom, :ratingAverage);";

        List<MapSqlParameterSource> batchParams = new ArrayList<>();

        for (Room room : entities) {
            batchParams.add(generateRoomParamsMap(room));
        }

        namedParameterJdbcTemplate.batchUpdate(createQuery, batchParams.toArray(new MapSqlParameterSource[0]));

    }


    @Override
    public Room update(Room entity) {
        final String createQuery = "update room set name = :name, price= :price, principle_of_placement = :principleOfPlacement," +
                " number_room = :numberRoom, rating_average = :ratingAverage  where id= :id ";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = generateRoomParamsMap(entity);

        namedParameterJdbcTemplate.update(createQuery, params, keyHolder, new String[]{"id"});

        long createdRoomId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return findOne(createdRoomId);
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
    public <E,M> List<Room> findCriteriaRoom( Criteria<E> searchRoom,Criteria<M> searchData, List<String> additionalComfort){

        List<String> result = new ArrayList<String>();
        MapSqlParameterSource params = new MapSqlParameterSource();

        for (Map.Entry<E, Object> entry : searchRoom.getCriteria()) {
            if(entry.getValue()!=null ){
                String temp=entry.getKey().toString().toLowerCase() + " = :" + entry.getKey().toString().toLowerCase();
                result.add(temp);
                params.addValue(entry.getKey().toString().toLowerCase(),entry.getValue());
            }
        }

        String queryPartOne="select rm.id,rm.name,rm.price,rm.principle_of_placement,rm.number_room,rm.rating_average " +
                "from room rm where ";

        for(int i=0;i<result.size();i++){

            queryPartOne += result.get(i);
            if( i!=result.size()-1){
                queryPartOne+=" and ";
            }
            if(i==result.size()-1){
                queryPartOne+=" except ";
            }

        }

        String queryPartTwo=" select r.id,r.name,r.price,r.principle_of_placement,r.number_room,r.rating_average " +
                "from room r inner join booking b on (r.id=b.id_room) " +
                "where  (:data_in between data_check_in and data_check_out) or (:data_out between data_check_in and data_check_out) ;";


        for (Map.Entry<M, Object> entry : searchData.getCriteria()) {
            if(entry.getValue()!=null ){
                params.addValue(entry.getKey().toString().toLowerCase(),entry.getValue());
            }
        }

        String query= queryPartOne + queryPartTwo;
        return searchByAdditionalComfortForRoom(namedParameterJdbcTemplate.query(query, params, this::getRoomRowMapper),additionalComfort);


    }

    private <T> List<T> searchByAdditionalComfortForRoom(List<T> rooms,List<String> additionalComforts) {
        int fl=0;
        List<T> resultRooms= new ArrayList<>();
        for( T room : rooms){
            fl=0;
            for(AdditionalComfort additional : additionalComfortRepository.getRoomAdditionalComfort((Room)room)){
                boolean result =additionalComforts.stream().anyMatch(additionalComfort->additionalComfort.equals(additional.getNameAdditional()));
                if(!result){
                    fl=1;
                    break;
                }
            }
            if(fl==0){
                resultRooms.add(room);
            }
        }
        return resultRooms;
    }

    @Override
    public void saveRoomAdditionalComfort(Room room, List<AdditionalComfort> roomAdditionalComfort) {
        final String createQuery = "insert into additional_in_section (id,id_section_hotel_room, id_additional_comfort) " +
                "values (:additionalComfortId, :roomId);";

        List<MapSqlParameterSource> batchParams = new ArrayList<>();

        for (AdditionalComfort additional : roomAdditionalComfort) {

            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("additionalComfortId", additional.getId());
            params.addValue("roomId", room.getId());
            batchParams.add(params);

        }

        namedParameterJdbcTemplate.batchUpdate(createQuery, batchParams.toArray(new MapSqlParameterSource[0]));

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
