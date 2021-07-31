package booking.hotel.repository.impl;


import booking.hotel.domain.*;

import booking.hotel.repository.ComfortRepository;
import booking.hotel.repository.column.RoomColumn;
import booking.hotel.repository.RoomRepository;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class RoomRepositoryImpl implements RoomRepository {

    private final UtilsForQuery utilsForQuery;
    private final ComfortRepository comfortRepository;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    //    @Autowired
    //    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    //        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    //    }


    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManager entityManager;


    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    @Override
    public List<Room> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return  session.createQuery("FROM Room ").list();
        }
    }



    @Override
    public Room findOne(Long id) {

        final String findOneWithNameParam = "select * from rooms where id = :idRoom ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idRoom", id);

        return namedParameterJdbcTemplate.queryForObject(findOneWithNameParam, params, this::getRoomRowMapper);

    }


    @Override
    public Room save(Room entity) {
        final String createQuery = "insert into rooms (name, price, principle_of_placement, number_room, rating_average) " +
                "values (:name, :price, :principleOfPlacement, :numberRoom, :ratingAverage);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = generateRoomParamsMap(entity);

        namedParameterJdbcTemplate.update(createQuery, params, keyHolder, new String[]{"id"});

        long createdUserId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return findOne(createdUserId);
    }

    @Override
    public void batchInsert(List<Room> entities) {
        final String createQuery = "insert into rooms (name, price, principle_of_placement, comfort_level, number_room, rating_average) " +
                "values (:name, :price, :principleOfPlacement,:comfortLevel, :numberRoom, :ratingAverage);";

        List<MapSqlParameterSource> batchParams = new ArrayList<>();

        for (Room room : entities) {
            batchParams.add(generateRoomParamsMap(room));
        }

        namedParameterJdbcTemplate.batchUpdate(createQuery, batchParams.toArray(new MapSqlParameterSource[0]));

    }


    @Override
    public Room update(Room entity) {
        final String createQuery = "update rooms set name = :name, price= :price, principle_of_placement = :principleOfPlacement," +
                " number_room = :numberRoom, rating_average = :ratingAverage  where id= :id ";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = generateRoomParamsMap(entity);

        namedParameterJdbcTemplate.update(createQuery, params, keyHolder, new String[]{"id"});

        long createdRoomId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return findOne(createdRoomId);
    }

    @Override
    public void delete(Long id) {

        final String findOneWithNameParam = "delete from comforts_rooms where comforts_rooms.id = :idRoom;" +
                "delete from rooms where rooms.id = :idRoom";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idRoom", id);

        namedParameterJdbcTemplate.queryForObject(findOneWithNameParam, params, this::getRoomRowMapper);


    }



  /*  @Override
    public <E,M> List<Room> findCriteriaRoom( Criteria<E> searchRoom,Criteria<M> searchData, List<String> additionalComfort){
    *//*    MapSqlParameterSource params = new MapSqlParameterSource();

        for (Map.Entry<E, Object> entry : searchRoom.getCriteria()) {
            if(entry.getValue()!=null ){

                params.addValue(entry.getKey().toString().toLowerCase(),entry.getValue());
            }
        }
        for (Map.Entry<M, Object> entry : searchData.getCriteria()) {
            if(entry.getValue()!=null ){
                params.addValue(entry.getKey().toString().toLowerCase(),entry.getValue());
            }
        }
        String query= utilsForQuery.createStringForSearchByParams(searchRoom)+ utilsForQuery.createStringForSearchByDate(searchData);

        return searchByAdditionalComfortForRoom(namedParameterJdbcTemplate.query(query, params, this::getRoomRowMapper),additionalComfort);
*//*

    }*/

    public <T> List<T> searchByAdditionalComfortForRoom(List<T> rooms,List<String> additionalComforts) {
        int fl=0;
        List<T> RoomsWithFilterAdditional= new ArrayList<>();
        if(additionalComforts==null || additionalComforts.isEmpty()){
            return rooms;
        }
        for( T room : rooms){
            fl=0;
            List<Comfort> roomWithComforts= comfortRepository.getRoomAdditionalComfort((Room)room);
            for(String additional : additionalComforts){
                boolean result =roomWithComforts.stream().anyMatch(roomWithAdditional->roomWithAdditional.getNameComfort().equals(additional));
                if(!result){
                    fl=1;
                    break;
                }
            }
            if(fl==0){
                RoomsWithFilterAdditional.add(room);
            }
        }

        return RoomsWithFilterAdditional;
    }

    @Override
    public List<Room> findByParamsRoom(RoomSearchRequest request) {
        //1. Get Builder for Criteria object

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Room> query = cb.createQuery(Room.class); //here select, where, orderBy, having
        Root<Room> root = query.from(Room.class); //here params  select * from m_users -> mapping
        //Join<Room,Comfort>   rc = root.join(Room_.comforts);

        /*type of future params in prepared statement*/


        ParameterExpression<Long> paramPriceMin= cb.parameter(Long.class);
        ParameterExpression<Long> paramPriceMax= cb.parameter(Long.class);
        ParameterExpression<Long> paramRatingMin= cb.parameter(Long.class);
        ParameterExpression<Long> paramRatingMax= cb.parameter(Long.class);
        ParameterExpression<String> paramPrincipleOfPlacement= cb.parameter(String.class);
        /*Provide access to fields in class that mapped to columns*/


        Expression<Long> priceMin =root.get(Room_.price);
        Expression<Long> priceMax = root.get(Room_.price);
        Expression<Long> ratingMin= root.get(Room_.ratingAverage);
        Expression<Long> ratingMax = root.get(Room_.ratingAverage);
        Expression<String> principleOfPlacement = root.get(Room_.principleOfPlacement);


      /*  List<ParameterExpression<String>> paramNameComforts = new ArrayList<>();
        List<Expression<String>> nameComforts = new ArrayList<>();
        for(int i=0;i<request.getAdditionalComfort().size();i++){
            paramNameComforts.add(cb.parameter(String.class));
            nameComforts.add(rc.get(Comfort_.nameComfort));
        }*/




        /*SQL Query customizing*/

        List<Predicate> predicates = new ArrayList<>();
        if(request.getPriceMin() != null)
        {
            predicates.add(cb.ge(priceMin,paramPriceMin));
        }
        if(request.getPriceMax() != null)
        {
            predicates.add(cb.le(priceMax,paramPriceMax));
        }

        if(request.getRatingMin() != null)
        {
            predicates.add(cb.ge(ratingMin, paramRatingMin));
        }
        if(request.getRatingMax() != null)
        {
            predicates.add(cb.le(ratingMax,paramRatingMax));
        }

        if(request.getPrincipleOfPlacement() != null)
        {
            predicates.add(cb.equal(principleOfPlacement,paramPrincipleOfPlacement));
        }

     /*   if(request.getAdditionalComfort() != null)
        {
            for(int i=0;i<request.getAdditionalComfort().size();i++){
                predicates.add(cb.equal(nameComforts.get(i),paramNameComforts.get(i)));
            }
        }*/

        query
                .select(root) //select * = select method, (root) = from users
                .distinct(true)
                .where(                         //where
                        cb.and(cb.and(predicates.toArray(new Predicate[predicates.size()])))
                );

        TypedQuery<Room> resultQuery = entityManager.createQuery(query); //prepared statement on hql


        if(request.getPriceMin() != null)
        {
            resultQuery.setParameter(paramPriceMin, request.getPriceMin());
        }
        if(request.getPriceMax() != null)
        {
            resultQuery.setParameter(paramPriceMax, request.getPriceMax());
        }

        if(request.getRatingMin() != null)
        {
            resultQuery.setParameter(paramRatingMin, request.getRatingMin());
        }
        if(request.getRatingMax() != null)
        {
            resultQuery.setParameter(paramRatingMax, request.getRatingMax());
        }

        if(request.getPrincipleOfPlacement() != null)
        {
            resultQuery.setParameter(paramPrincipleOfPlacement, request.getPrincipleOfPlacement());
        }
      /*  if(request.getAdditionalComfort() != null)
        {
            for(int i=0;i<request.getAdditionalComfort().size();i++){
                resultQuery.setParameter(paramNameComforts.get(i), request.getAdditionalComfort().get(i));
            }
        }*/

        return  resultQuery.getResultList();
    }

    @Override
    public List<Room> findByListComfortsRoom(List<String> comforts){

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Room> query = cb.createQuery(Room.class); //here select, where, orderBy, having
        Root<Room> root = query.from(Room.class); //here params  select * from m_users -> mapping
        Join<Room,Comfort>   rc = root.join(Room_.comforts);
        query.groupBy(root.get("name"));
        query.orderBy(cb.desc(cb.count(root.get("name"))));
        query.multiselect(root.get("name"),cb.count(root.get("name")));

       // ParameterExpression<Long> count = cb.parameter(Long.class);

        List<ParameterExpression<String>> paramNameComforts = new ArrayList<>();
        List<Expression<String>> nameComforts = new ArrayList<>();
        Expression<Long> id = root.get(Room_.id);
        for(int i=0;i<comforts.size();i++){
            paramNameComforts.add(cb.parameter(String.class));
            nameComforts.add(rc.get(Comfort_.nameComfort));
        }

        List<Predicate> predicates = new ArrayList<>();
        for(int i=0;i<comforts.size();i++){
            predicates.add(cb.equal(nameComforts.get(i),paramNameComforts.get(i)));
        }

        query
                .select(root)//select * = select method, (root) = from users
                //.distinct(true)
                .where(                         //where
                        cb.and(cb.or(predicates.toArray(new Predicate[predicates.size()])))
                )
                .groupBy(root.get("id"))
                .having(cb.ge(cb.count(root.get("id")),comforts.size()));


        TypedQuery<Room> resultQuery = entityManager.createQuery(query);
        //prepared statement on hql

        for(int i=0;i<comforts.size();i++){
            resultQuery.setParameter(paramNameComforts.get(i), comforts.get(i));
        }


        return resultQuery.getResultList();
    }

    public List<Room> findByData(Timestamp dataIn,Timestamp dataOut){
        return null;
    }

    @Override
    public void saveRoomAdditionalComfort(Room room, List<Comfort> roomComfort) {
        final String createQuery = "insert into comforts_rooms (id,id_room, id_comfort) " +
                "values (:additionalComfortId, :roomId);";

        List<MapSqlParameterSource> batchParams = new ArrayList<>();

        for (Comfort additional : roomComfort) {

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
