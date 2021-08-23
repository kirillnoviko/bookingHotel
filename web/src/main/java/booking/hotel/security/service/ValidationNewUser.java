package booking.hotel.security.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import booking.hotel.domain.User;
import booking.hotel.exception.NoSuchEntityException;
import booking.hotel.repository.dataspring.UserRepositoryData;

@Service
@RequiredArgsConstructor
public class ValidationNewUser {

    private final UserRepositoryData userRepositoryData;

    public User checkUser(User user){

        if(user.getGmail().isEmpty() || user.getPassword().isEmpty() ){
            throw  new  NoSuchEntityException("заполните все поля");
        }
        if(!userRepositoryData.findByGmail(user.getGmail()).isEmpty()){
            throw  new  NoSuchEntityException("пользователь с таким gmail  уже зарегистрирован");
        }

        return user;


    }
}
