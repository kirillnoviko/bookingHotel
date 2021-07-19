package booking.hotel.controller.rest;


import booking.hotel.controller.request.RoleCreateRequest;
import booking.hotel.domain.Role;
import booking.hotel.domain.User;
import booking.hotel.repository.RoleRepository;
import booking.hotel.repository.UserRepository;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/roles")
@RequiredArgsConstructor
public class RoleRestController {
    private final RoleRepository roleRepository;



    @GetMapping
    public Role findAll(@RequestParam Long idRole) {
        System.out.println("In rest controller");
        return roleRepository.findOne(idRole);
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "idRole", dataType = "string", paramType = "query", value = "id role for output users"),

    })
    @GetMapping("/{roleId}")
    public Role findOne(@PathVariable("roleId") Long id) {
        return roleRepository.findOne(id);
    }

    @PostMapping("/save")
    public Role save(@RequestBody RoleCreateRequest request) {

        Role role = new Role();
        role.setRoleName(request.getRoleName());
        return roleRepository.save(role);

    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "idRole", dataType = "string", paramType = "query", value = "id role for update"),

    })
    @PostMapping("/update/{roleId}")
    public Role update(@RequestBody RoleCreateRequest request, @PathVariable Long roleId) {

        Role role = new Role();
        role.setId(roleId);
        role.setRoleName(request.getRoleName());
        return roleRepository.update(role);

    }
}
