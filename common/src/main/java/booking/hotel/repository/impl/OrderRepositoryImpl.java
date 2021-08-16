package booking.hotel.repository.impl;

import booking.hotel.domain.*;

import booking.hotel.domain.Order;
import booking.hotel.repository.OrderRepository;
import booking.hotel.repository.dataspring.OrderRepositoryData;
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

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
                );


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
