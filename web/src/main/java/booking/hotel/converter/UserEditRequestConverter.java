package booking.hotel.converter;

import booking.hotel.domain.User;
import booking.hotel.repository.dataspring.UserRepositoryData;
import booking.hotel.request.UserChangeRequest;
import org.springframework.stereotype.Component;

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
