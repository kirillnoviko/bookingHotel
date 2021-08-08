package booking.hotel.converter;

import booking.hotel.domain.Role;
import booking.hotel.domain.User;
import booking.hotel.repository.RoleRepository;
import booking.hotel.request.UserCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
}
