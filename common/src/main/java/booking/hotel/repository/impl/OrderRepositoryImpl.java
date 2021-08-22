package booking.hotel.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import booking.hotel.domain.Order;
import booking.hotel.domain.Order_;
import booking.hotel.domain.User;
import booking.hotel.domain.User_;
import booking.hotel.repository.OrderRepository;
import booking.hotel.domain.StatusName;


@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManager entityManager;


    @Override
    public List<Order> findByAllParams(Order order ) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> query = cb.createQuery(Order.class); //here select, where, orderBy, having
        Root<Order> root = query.from(Order.class); //here params  select * from m_users -> mapping
        Join<Order,User> ou = root.join(Order_.user);

        ParameterExpression<Timestamp> paramDataIn = cb.parameter(Timestamp.class);
        ParameterExpression<Timestamp> paramDataOut = cb.parameter(Timestamp.class);
        ParameterExpression<StatusName> paramStatus = cb.parameter(StatusName.class);
        ParameterExpression<Long> paramIdUser = cb.parameter(Long.class);

        /*Provide access to fields in class that mapped to columns*/

        Expression<Timestamp> dataCheckIn = root.get(Order_.dataCheckIn);
        Expression<Timestamp> dataCheckOut = root.get(Order_.dataCheckOut);
        Expression<StatusName> status = root.get(Order_.status);
        Expression<Long> idUser = ou.get(User_.id);


        List<Predicate> predicates = new ArrayList<>();
        if (order.getStatus() != null) {
            predicates.add(cb.equal(status, paramStatus));
        }
        if (order.getDataCheckOut() != null) {
            predicates.add(cb.between(dataCheckOut, paramDataIn,paramDataOut));
        }
        if (order.getDataCheckIn() != null) {
            predicates.add(cb.between(dataCheckIn, paramDataIn,paramDataOut));
        }



        query
                .select(root)
                .distinct(true)
                .where(
                        cb.and(
                                (cb.equal(idUser, paramIdUser)),
                                (cb.and(predicates.toArray(new Predicate[predicates.size()])))
                        )
                )
                ;


        TypedQuery<Order> resultQuery = entityManager.createQuery(query);

        resultQuery.setParameter(paramIdUser,order.getUser().getId());
        if (order.getStatus() != null) {
            resultQuery.setParameter(paramStatus,order.getStatus());
        }
        if (order.getDataCheckOut() != null) {
            resultQuery.setParameter(paramDataIn,order.getDataCheckIn());
        }
        if (order.getDataCheckIn() != null) {
            resultQuery.setParameter(paramDataOut,order.getDataCheckOut());
        }




        return resultQuery.getResultList();
    }

}
