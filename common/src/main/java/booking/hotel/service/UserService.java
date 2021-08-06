package booking.hotel.service;


import booking.hotel.domain.User;
import booking.hotel.repository.dataspring.UserRepositoryData;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepositoryData userRepositoryData;


    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    public void deleteWithDependencies(Long id){
        userRepositoryData.deleteDependenciesRoles(id);
        userRepositoryData.delete(id);
    }


    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    public User saveOrUpdateWithAddedRoles(User user){

        User usewr = new User();

       /* if(id!=null){
            user=userRepositoryData.findById(id).get();

            if(generalInfoUser.getName()!=null){
                user.getGeneralInfoUser().setName(generalInfoUser.getName());
            }
            if(generalInfoUser.getGmail()!=null){
                user.getGeneralInfoUser().setGmail(generalInfoUser.getGmail());
            }
            if(generalInfoUser.getBirthDate()!=null){
                user.getGeneralInfoUser().setBirthDate(generalInfoUser.getBirthDate());
            }
            if(generalInfoUser.getPassword()!=null){
                user.getGeneralInfoUser().setPassword(generalInfoUser.getPassword());
            }
            if(generalInfoUser.getSurname()!=null){
                user.getGeneralInfoUser().setSurname(generalInfoUser.getSurname());
            }

        }else{
            user.setGeneralInfoUser(generalInfoUser);
            user.setRatingAverage(5l);
            user.setCreated(new Timestamp(new Date().getTime()));

        }

        user.setChanged(new Timestamp(new Date().getTime()));
        user = userRepositoryData.save(user);

        if (roles != null) {
            for(Long role : roles){
                userRepositoryData.createSomeRow(user.getId(),role);
            }
        }*/

        return  user;
    }
}
