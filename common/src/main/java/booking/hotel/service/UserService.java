package booking.hotel.service;


import booking.hotel.domain.Role;
import booking.hotel.domain.User;
import booking.hotel.repository.RoleRepository;
import booking.hotel.repository.dataspring.RoleRepositoryData;
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
    private final RoleRepositoryData roleRepositoryData;


    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    public void deleteWithDependencies(Long id){
        userRepositoryData.deleteDependenciesRoles(id);
        userRepositoryData.delete(id);
    }


    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    public User saveOrUpdateWithAddedRoles(User user){

        user = userRepositoryData.save(user);

        if(user.getRoles().isEmpty()){
                userRepositoryData.createSomeRow(user.getId(),roleRepositoryData.findByRoleName("ROLE_USER").get().getId());
        }

        return  user;
    }
}
