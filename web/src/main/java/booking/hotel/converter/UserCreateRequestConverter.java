package booking.hotel.converter;

import booking.hotel.domain.User;
import booking.hotel.request.UserCreateRequest;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.*;

@Component
public class UserCreateRequestConverter extends EntityConverter<UserCreateRequest, User> {

    @Override
    public User convert(UserCreateRequest request) {

        User user = new User();
        user.setCreated(new Timestamp(new Date().getTime()));
        return doConvert(request, user);
    }
}