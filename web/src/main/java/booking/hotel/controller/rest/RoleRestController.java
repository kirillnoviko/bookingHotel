package booking.hotel.controller.rest;


import booking.hotel.domain.User;
import booking.hotel.repository.dataspring.RoleRepositoryData;
import booking.hotel.domain.Role;
import booking.hotel.request.RoleChangeRequest;
import booking.hotel.request.RoleCreateRequest;
import booking.hotel.request.UserChangeRequest;
import booking.hotel.request.UserCreateRequest;
import booking.hotel.service.RoleService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/roles")
@RequiredArgsConstructor
public class RoleRestController {
    private final RoleRepositoryData roleRepositoryData;
    private final RoleService roleService;
    public final ConversionService conversionService;


    @ApiOperation(value = "show all roles")
    @GetMapping
    public List<Role> findAll() {
        return roleRepositoryData.findAll();
    }

    @ApiOperation(value = "find for one role by ID")
    @GetMapping("/{roleId}")
    public Role findOne(@PathVariable("roleId") Long id) {
        return roleRepositoryData.findById(id).get();
    }


    @ApiOperation(value = "created User ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successfully!"),
            @ApiResponse(code = 500, message = "Internal server error ")
    })
    @PostMapping("/save")
    public Role saveUser(@RequestBody RoleCreateRequest request) {

        Role role = conversionService.convert(request, Role.class);
        return roleService.saveOrUpdateRole(role);

    }

    @ApiOperation(value = "update User ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successfully!"),
            @ApiResponse(code = 500, message = "Internal server error ")
    })
    @PostMapping("/update")
    public Role updateUser( @RequestBody RoleChangeRequest request) {

        Role role = conversionService.convert(request, Role.class);
        return roleService.saveOrUpdateRole(role);

    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "idRole", dataType = "string", paramType = "query", value = "idRole for search entity Role "),

    })
    @DeleteMapping("/delete")
    public void delete(@RequestParam Long idRole ){
        roleService.deleteWithDependencies(idRole);    }
}
