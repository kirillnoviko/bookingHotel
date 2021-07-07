package booking.hotel.controller.rest;


import booking.hotel.domain.Role;
import booking.hotel.domain.User;
import booking.hotel.repository.RoleRepository;
import booking.hotel.repository.UserRepository;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/roles")
@RequiredArgsConstructor
public class RoleRestController {
    private final RoleRepository roleRepository;



    @ApiImplicitParams({
            @ApiImplicitParam(name = "idRole", dataType = "string", paramType = "query", value = "id role for output users"),

    })
    @GetMapping
    public Role findAll(@RequestParam Long idRole) {
        System.out.println("In rest controller");
        return roleRepository.findOne(idRole);
    }
}
