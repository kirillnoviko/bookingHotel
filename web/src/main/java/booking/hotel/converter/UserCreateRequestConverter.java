package booking.hotel.converter;

import booking.hotel.domain.Role;
import booking.hotel.domain.User;
import booking.hotel.repository.RoleRepository;
import booking.hotel.repository.dataspring.RoleRepositoryData;
import booking.hotel.repository.dataspring.UserRepositoryData;
import booking.hotel.request.UserCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.*;

@Component
public class UserCreateRequestConverter extends EntityConverter<UserCreateRequest, User> {

    RoleRepositoryData roleRepositoryData;
    public UserCreateRequestConverter(RoleRepositoryData roleRepositoryData) {
        this.roleRepositoryData = roleRepositoryData;

    }
    @Override
    public User convert(UserCreateRequest request) {

        User user = new User();
        user.setCreated(new Timestamp(new Date().getTime()));
       // user.getRoles().add(roleRepositoryData.findById(1L).get());
        return doConvert(request, user);
    }
}