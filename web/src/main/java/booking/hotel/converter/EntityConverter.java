package booking.hotel.converter;

import booking.hotel.domain.Role;
import booking.hotel.domain.User;
import booking.hotel.repository.RoleRepository;
import booking.hotel.request.UserCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;


import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;


public abstract class EntityConverter<S, T> implements Converter<S, T> {

     RoleRepository roleRepository ;

    protected User doConvert(UserCreateRequest request,User user ) {

        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setBirthDate(request.getBirthDate());

        user.setGmail(request.getGmail());
        user.setPassword(request.getPassword());

        user.setChanged(new Timestamp(new Date().getTime()));

        Set<Role> roles = null;
        for(Long id : request.getRoles()){
            roles.add(roleRepository.findOne(id));
        }
        user.setRoles(roles);

        return user;
    }
}
