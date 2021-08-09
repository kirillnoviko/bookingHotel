package booking.hotel.converter;

import booking.hotel.domain.Role;
import booking.hotel.domain.User;
import booking.hotel.request.RoleCreateRequest;
import booking.hotel.request.UserCreateRequest;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;

@Component
public class RoleCreateRequestConverter extends EntityConverter<RoleCreateRequest, Role> {

    @Override
    public Role convert(RoleCreateRequest request) {

        Role role = new Role();

        return doConvert(request, role);
    }
}