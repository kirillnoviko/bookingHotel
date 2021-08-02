package booking.hotel.repository.impl;


import booking.hotel.domain.*;

import booking.hotel.domain.Order;
import booking.hotel.exception.NoParamsException;
import booking.hotel.repository.ComfortRepository;
import booking.hotel.repository.column.RoomColumn;
import booking.hotel.repository.RoomRepository;

import booking.hotel.util.RoomSearchRequest;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class RoomRepositoryImpl implements RoomRepository {


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
    public List<Room> findByParamsRoom(Long minPriceRequest, Long maxPriceRequest,
                                       Long minRatingRequest, Long maxRatingRequest, String principlePlacementRequest ) {


 /*       if(minPriceRequest == null && maxPriceRequest== null
                && minRatingRequest == null && maxRatingRequest == null
                && principlePlacementRequest ==null)
        {
            throw new NoParamsException("All params is null");
        }*/

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Room> query = cb.createQuery(Room.class); //here select, where, orderBy, having
        Root<Room> root = query.from(Room.class); //here params  select * from m_users -> mapping

        ParameterExpression<Long> paramPriceMin = cb.parameter(Long.class);
        ParameterExpression<Long> paramPriceMax = cb.parameter(Long.class);
        ParameterExpression<Long> paramRatingMin = cb.parameter(Long.class);
        ParameterExpression<Long> paramRatingMax = cb.parameter(Long.class);
        ParameterExpression<String> paramPrincipleOfPlacement = cb.parameter(String.class);
        /*Provide access to fields in class that mapped to columns*/

        Expression<Long> priceMin = root.get(Room_.price);
        Expression<Long> priceMax = root.get(Room_.price);
        Expression<Long> ratingMin = root.get(Room_.ratingAverage);
        Expression<Long> ratingMax = root.get(Room_.ratingAverage);
        Expression<String> principleOfPlacement = root.get(Room_.principleOfPlacement);


        List<Predicate> predicates = new ArrayList<>();
        if (minPriceRequest != null) {
            predicates.add(cb.ge(priceMin, paramPriceMin));
        }
        if (maxPriceRequest != null) {
            predicates.add(cb.le(priceMax, paramPriceMax));
        }

        if (minRatingRequest != null) {
            predicates.add(cb.ge(ratingMin, paramRatingMin));
        }
        if (maxRatingRequest != null) {
            predicates.add(cb.le(ratingMax, paramRatingMax));
        }

        if (principlePlacementRequest != null) {
            predicates.add(cb.equal(principleOfPlacement, paramPrincipleOfPlacement));
        }


        query
                .select(root)
                .distinct(true)
                .where(
                        cb.and(cb.and(predicates.toArray(new Predicate[predicates.size()])))
                );



        TypedQuery<Room> resultQuery = entityManager.createQuery(query);

        if (minPriceRequest != null) {
            resultQuery.setParameter(paramPriceMin, minPriceRequest);
        }
        if (maxPriceRequest!= null) {
            resultQuery.setParameter(paramPriceMax, maxPriceRequest);
        }

        if (minRatingRequest!= null) {
            resultQuery.setParameter(paramRatingMin, minRatingRequest);
        }
        if (maxRatingRequest!= null) {
            resultQuery.setParameter(paramRatingMax, maxRatingRequest);
        }

        if (principlePlacementRequest != null) {
            resultQuery.setParameter(paramPrincipleOfPlacement, principlePlacementRequest);
        }


        return resultQuery.getResultList();
    }

    @Override
    public List<Room> findByListComfortsRoom(List<Long> comforts){




        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Room> query = cb.createQuery(Room.class); //here select, where, orderBy, having
        Root<Room> root = query.from(Room.class); //here params  select * from m_users -> mapping
        Join<Room,Comfort> rc = root.join(Room_.comforts);

        List<ParameterExpression<Long>> paramNameComforts = new ArrayList<>();
        List<Expression<Long>> nameComforts = new ArrayList<>();


        for(int i=0;i<comforts.size();i++){
            paramNameComforts.add(cb.parameter(Long.class));
            nameComforts.add(rc.get(Comfort_.id));
        }

        List<Predicate> predicates = new ArrayList<>();
        for(int i=0;i<comforts.size();i++){
            predicates.add(cb.equal(nameComforts.get(i),paramNameComforts.get(i)));
        }

        query
                .select(root)
                .where(
                        cb.and(cb.or(predicates.toArray(new Predicate[predicates.size()])))
                )
                .groupBy(root.get("id"))
                .having(cb.ge(cb.count(root.get("id")),comforts.size()));


        TypedQuery<Room> resultQuery = entityManager.createQuery(query);
        for(int i=0;i<comforts.size();i++){
            resultQuery.setParameter(paramNameComforts.get(i), comforts.get(i));
        }


        return resultQuery.getResultList();
    }


    public List<Room> findByData(List<Room> rooms, Timestamp dataIn,Timestamp dataOut){

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Room> query = cb.createQuery(Room.class); //here select, where, orderBy, having
        Root<Room> root = query.from(Room.class); //here params  select * from m_users -> mapping
        Join<Room, Order> rc = root.join(Room_.orders);

        ParameterExpression<Timestamp> parameterDataIn = cb.parameter(Timestamp.class);
        ParameterExpression<Timestamp> parameterDataOut = cb.parameter(Timestamp.class);
        Expression<Timestamp> exprDataIn =rc.get(Order_.dataCheckIn);
        Expression<Timestamp> exprDataOut =rc.get(Order_.dataCheckOut);


        List<ParameterExpression<Long>> paramRoomId = new ArrayList<>();
        List<Expression<Long>> roomId = new ArrayList<>();

        for(int i=0;i<rooms.size();i++){
            paramRoomId.add(cb.parameter(Long.class));
            roomId.add(root.get(Room_.id));
        }

        List<Predicate> predicates = new ArrayList<>();
        for(int i=0;i<rooms.size();i++){
            predicates.add(cb.equal(roomId.get(i),paramRoomId.get(i)));
        }

        query
                .select(root)
                .where(
                        cb.and(
                                (cb.between(parameterDataIn,exprDataIn,exprDataOut)),
                                (cb.between(parameterDataOut,exprDataIn,exprDataOut)),
                                (cb.or(predicates.toArray(new Predicate[predicates.size()])))
                                )
                        );



        TypedQuery<Room> resultQuery = entityManager.createQuery(query);
        for(int i=0;i<rooms.size();i++){
            resultQuery.setParameter(paramRoomId.get(i), rooms.get(i).getId());
        }

        resultQuery.setParameter(parameterDataIn,dataIn);
        resultQuery.setParameter(parameterDataOut,dataOut);

        return resultQuery.getResultList();
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
