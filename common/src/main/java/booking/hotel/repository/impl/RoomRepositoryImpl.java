package booking.hotel.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Subquery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.JoinType;
import java.sql.Timestamp;

import java.util.List;
import java.util.ArrayList;

import booking.hotel.domain.Order;
import booking.hotel.domain.Order_;
import booking.hotel.domain.Room;
import booking.hotel.domain.Room_;
import booking.hotel.domain.Comfort;
import booking.hotel.domain.Comfort_;
import booking.hotel.domain.GeneralInfoRoom_;
import booking.hotel.repository.RoomRepository;
import booking.hotel.util.EntityForSearchRoom;

@Repository
@RequiredArgsConstructor
public class RoomRepositoryImpl implements RoomRepository {

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManager entityManager;


    @Override
    public List<Room> findByListComfortsRoom(EntityForSearchRoom entity){

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Room> query = cb.createQuery(Room.class); //here select, where, orderBy, having
        Root<Room> root = query.from(Room.class); //here params  select * from m_users -> mapping
        Join<Room,Comfort> rc = root.join(Room_.comforts,JoinType.LEFT);


        Subquery<Room> subQuery = query.subquery(Room.class);
        Root<Room> subRoot = subQuery.from(Room.class);
        Join<Room,Order> roomOrderJoin = root.join(Room_.orders, JoinType.LEFT);

        // Parameters for criteria builder
        ParameterExpression<Long> paramPriceMin = cb.parameter(Long.class);
        ParameterExpression<Long> paramPriceMax = cb.parameter(Long.class);
        ParameterExpression<Long> paramRatingMin = cb.parameter(Long.class);
        ParameterExpression<Long> paramRatingMax = cb.parameter(Long.class);
        ParameterExpression<String> paramPrincipleOfPlacement = cb.parameter(String.class);
        ParameterExpression<Timestamp> parameterDataIn = cb.parameter(Timestamp.class);
        ParameterExpression<Timestamp> parameterDataOut = cb.parameter(Timestamp.class);
        List<ParameterExpression<Long>> paramNameComforts = new ArrayList<>();



        //Expression for criteria builder
        Expression<Long> priceMin = root.get(Room_.generalInfoRoom).get(GeneralInfoRoom_.price);
        Expression<Long> priceMax = root.get(Room_.generalInfoRoom).get(GeneralInfoRoom_.price);
        Expression<Long> ratingMin = root.get(Room_.ratingAverage);
        Expression<Long> ratingMax = root.get(Room_.ratingAverage);
        Expression<String> principleOfPlacement = root.get(Room_.generalInfoRoom).get(GeneralInfoRoom_.principleOfPlacement);
        Expression<Timestamp> exprDataIn =roomOrderJoin.get(Order_.dataCheckIn);
        Expression<Timestamp> exprDataOut =roomOrderJoin.get(Order_.dataCheckOut);
        List<Expression<Long>> nameComforts = new ArrayList<>();

        // create predicates
        List<Predicate> predicatesParams = new ArrayList<>();
        if (entity.getPriceMin() != null) {
            predicatesParams.add(cb.ge(priceMin, paramPriceMin));
        }
        if (entity.getPriceMax() != null) {
            predicatesParams.add(cb.le(priceMax, paramPriceMax));
        }
        if (entity.getRatingMin() != null) {
            predicatesParams.add(cb.ge(ratingMin, paramRatingMin));
        }
        if (entity.getRatingMax() != null) {
            predicatesParams.add(cb.le(ratingMax, paramRatingMax));
        }
        if (entity.getPrincipleOfPlacement() != null) {
            predicatesParams.add(cb.equal(principleOfPlacement, paramPrincipleOfPlacement));
        }

        List<Predicate> predicatesNameComforts = new ArrayList<>();
            for(int i=0;i<entity.getIdComfort().size();i++){
                paramNameComforts.add(cb.parameter(Long.class));
                nameComforts.add(rc.get(Comfort_.id));
                predicatesNameComforts.add(cb.equal(nameComforts.get(i),paramNameComforts.get(i)));
        }

        List<Predicate> predicatesData = new ArrayList<>();
        if (entity.getDataIn() != null) {
            predicatesData.add(cb.between(parameterDataIn,exprDataIn,exprDataOut));
        }
        if (entity.getDataOut() != null) {
            predicatesData.add(cb.between(parameterDataOut,exprDataIn,exprDataOut));
        }





        //create SubQuery
        subQuery.select(subRoot)
                .where(cb.and(predicatesData.toArray(new Predicate[predicatesData.size()]))
                        );


        //create mainQuery
        query
                .select(root)
                .where(

                        cb.and(
                                cb.or(predicatesNameComforts.toArray(new Predicate[predicatesNameComforts.size()])),
                                cb.and(predicatesParams.toArray(new Predicate[predicatesParams.size()])),
                                cb.not(cb.exists(subQuery))
                        )
                )
                .groupBy(root.get("id"))
                .having(cb.ge(cb.count(root.get("id")),entity.getIdComfort().size()));





        //set parameters
        TypedQuery<Room> resultQuery = entityManager.createQuery(query);

            for(int i=0;i<entity.getIdComfort().size();i++){
                resultQuery.setParameter(paramNameComforts.get(i), entity.getIdComfort().get(i));
            }


        if (entity.getDataIn() != null) {
            resultQuery.setParameter(parameterDataIn,entity.getDataIn());
        }
        if (entity.getDataOut() != null) {
            resultQuery.setParameter(parameterDataOut,entity.getDataOut());
        }
        if (entity.getPriceMin() != null) {
            resultQuery.setParameter(paramPriceMin, entity.getPriceMin());
        }
        if (entity.getPriceMax()!= null) {
            resultQuery.setParameter(paramPriceMax, entity.getPriceMax());
        }
        if (entity.getRatingMin()!= null) {
            resultQuery.setParameter(paramRatingMin, entity.getRatingMin());
        }
        if (entity.getRatingMax()!= null) {
            resultQuery.setParameter(paramRatingMax, entity.getRatingMin());
        }
        if (entity.getPrincipleOfPlacement() != null) {
            resultQuery.setParameter(paramPrincipleOfPlacement, entity.getPrincipleOfPlacement());
        }


        return resultQuery.getResultList();
    }


}
