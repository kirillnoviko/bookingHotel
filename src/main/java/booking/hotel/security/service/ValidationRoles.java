package booking.hotel.security.service;

import booking.hotel.domain.Role;
import booking.hotel.exception.NoSuchEntityException;
import booking.hotel.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ValidationRoles {

    private final RoleRepository roleRepository;

    public List<Role> checkRoles(List<String> roles){
        if(roles.isEmpty()){
            new NoSuchEntityException("список ролей пуст");
        }
        List<Role> resultListRole = new ArrayList<>();
        List<Role> rolesAll=roleRepository.findAll();
        for( String role: roles){


            boolean result =rolesAll.stream().anyMatch(roleAll->roleAll.getRoleName().equals(role));
            if(result){
                resultListRole.add(roleRepository.findByName(role));
            }else{
                throw  new NoSuchEntityException("некоректный ввод роли пользователя");
            }
        }
        return resultListRole;

    }
}
