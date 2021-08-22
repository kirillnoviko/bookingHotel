package booking.hotel.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import booking.hotel.domain.User;
import booking.hotel.repository.dataspring.RoleRepositoryData;
import booking.hotel.repository.dataspring.UserRepositoryData;

import java.sql.SQLException;
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


    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    public User addRolesForUser(Long id, List<String> listRoles){
        for(String nameRole : listRoles){

            if(userRepositoryData.checkRole(id,roleRepositoryData.findByRoleName(nameRole).get().getId()).isEmpty()){

                userRepositoryData.createSomeRow(id,roleRepositoryData.findByRoleName(nameRole).get().getId());

            }

        }
        return userRepositoryData.findById(id).get();
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    public User deleteRoleForUser(Long id, List<String> listRoles){
        for(String nameRole : listRoles){

                userRepositoryData.deleteSomeRow(id,roleRepositoryData.findByRoleName(nameRole).get().getId());


        }
        return userRepositoryData.findById(id).get();
    }

}
