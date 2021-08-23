package booking.hotel.converter;

import org.springframework.stereotype.Component;

import booking.hotel.domain.Role;
import booking.hotel.request.RoleCreateRequest;

@Component
public class RoleCreateRequestConverter extends EntityConverter<RoleCreateRequest, Role> {

    @Override
    public Role convert(RoleCreateRequest request) {

        Role role = new Role();

        return doConvert(request, role);
    }
}