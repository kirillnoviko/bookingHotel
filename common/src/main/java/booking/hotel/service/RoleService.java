package booking.hotel.service;


import booking.hotel.domain.Role;
import booking.hotel.domain.User;
import booking.hotel.repository.dataspring.RoleRepositoryData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepositoryData roleRepositoryData;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    public void deleteWithDependencies(Long id){
        roleRepositoryData.deleteDependenciesUser(id);
        roleRepositoryData.delete(roleRepositoryData.findById(id).get());
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    public Role saveOrUpdateRole(Role role) {

        role = roleRepositoryData.save(role);
        return  role;

    }

    }
