package booking.hotel.converter;

import booking.hotel.domain.Order;
import booking.hotel.domain.Role;
import booking.hotel.domain.User;
import booking.hotel.request.OrderChangeRequest;
import booking.hotel.request.OrderCreateRequest;
import booking.hotel.request.RoleCreateRequest;
import booking.hotel.request.UserCreateRequest;
import org.springframework.core.convert.converter.Converter;


import java.sql.Timestamp;
import java.util.*;


public abstract class EntityConverter<S, T> implements Converter<S, T> {

    protected User doConvert(UserCreateRequest request,User user ) {

        if(request.getName()!=null){
            user.setName(request.getName());
        }
        if(request.getSurname()!=null){
            user.setSurname(request.getSurname());
        }
        if(request.getBirthDate()!=null)
        {
            user.setBirthDate(request.getBirthDate());
        }
        if(request.getGmail()!=null){
           user.setGmail(request.getGmail());
        }
        if (request.getPassword() != null) {
            user.setPassword(request.getPassword());
        }

        user.setChanged(new Timestamp(new Date().getTime()));

        return user;
    }
    protected Role doConvert(RoleCreateRequest request,Role role){
        if(request.getRoleName()!=null){
            role.setRoleName(request.getRoleName());
        }
        return role;
    }

    protected  Order doConvert(OrderCreateRequest request, Order order){
        order.setChanged(new Timestamp(new Date().getTime()));
        return order;
    }

}
