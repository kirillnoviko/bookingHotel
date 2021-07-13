package booking.hotel.security.service;

import booking.hotel.domain.Role;
import booking.hotel.domain.User;
import booking.hotel.exception.NoSuchEntityException;
import booking.hotel.repository.RoleRepository;
import booking.hotel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ValidationNewUser {

    private final UserRepository userRepository;

    public void checkUser(User user){

        if(user.getGmail().isEmpty() || user.getPassword().isEmpty() ){
            throw  new  NoSuchEntityException("заполните все поля");
        }
        if(!userRepository.findByLogin(user.getGmail()).equals(null)){
            throw  new  NoSuchEntityException("пользователь с таким gmail  уже зарегистрирован");
        }


    }
}
