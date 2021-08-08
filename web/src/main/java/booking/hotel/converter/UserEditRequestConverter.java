package booking.hotel.converter;

import booking.hotel.domain.Role;
import booking.hotel.domain.User;
import booking.hotel.repository.RoleRepository;
import booking.hotel.repository.dataspring.UserRepositoryData;
import booking.hotel.request.UserChangeRequest;
import booking.hotel.request.UserCreateRequest;
import ch.qos.logback.core.joran.spi.NoAutoStart;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

import static java.util.Optional.ofNullable;

@Component
public class UserEditRequestConverter extends EntityConverter<UserChangeRequest, User> {

    private  UserRepositoryData userRepository;


    public UserEditRequestConverter(UserRepositoryData userRepository) {
        this.userRepository = userRepository;

    }

    @Override
    public User convert(UserChangeRequest request) {

        User user = userRepository.findById(request.getId()).get();

        return doConvert(request, user);
    }
}
