package booking.hotel.converter;


import booking.hotel.domain.Role;
import booking.hotel.domain.User;
import booking.hotel.repository.dataspring.RoleRepositoryData;
import booking.hotel.repository.dataspring.UserRepositoryData;
import booking.hotel.request.RoleChangeRequest;
import booking.hotel.request.UserChangeRequest;
import org.springframework.stereotype.Component;

@Component
public class RoleEditRequestConverter extends EntityConverter<RoleChangeRequest, Role> {

    private RoleRepositoryData roleRepository;


    public RoleEditRequestConverter(RoleRepositoryData roleRepository) {
        this.roleRepository = roleRepository;

    }

    @Override
    public Role convert(RoleChangeRequest request) {

        Role role = roleRepository.findById(request.getId()).get();

        return doConvert(request, role);
    }
}