package booking.hotel.security.service;

import booking.hotel.domain.User;
import booking.hotel.exception.NoSuchEntityException;
import booking.hotel.repository.dataspring.UserRepositoryData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidationNewUser {

    private final UserRepositoryData userRepositoryData;

    public void checkUser(User user){

        if(user.getGmail().isEmpty() || user.getPassword().isEmpty() ){
            throw  new  NoSuchEntityException("заполните все поля");
        }
        if(!userRepositoryData.findByGmail(user.getGmail()).isEmpty()){
            throw  new  NoSuchEntityException("пользователь с таким gmail  уже зарегистрирован");
        }



    }
}
