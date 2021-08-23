package booking.hotel.controller.rest;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;
import java.util.List;

import booking.hotel.domain.Order;
import booking.hotel.domain.User;
import booking.hotel.repository.dataspring.UserRepositoryData;
import booking.hotel.request.UserChangeRequest;
import booking.hotel.request.UserCreateRequest;
import booking.hotel.service.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserRepositoryData userRepositoryData;
    private final UserService userService;

    public final ConversionService conversionService;


    @ApiOperation(value = "show all users ")
    @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true, dataType = "string", paramType = "header")
    @GetMapping
    public List<User> findAll() {
        System.out.println("In rest controller");
        return userRepositoryData.findByIsBannedFalseAndIsDeletedFalse();
    }

    @ApiOperation(value = "find user by id")
    @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true, dataType = "string", paramType = "header")
    @GetMapping("/{idUser}")
    public User findOne(@PathVariable("idUser") Long id) {

        return userRepositoryData.findById(id).get();

    }

    @ApiOperation(value = "show all orders user ")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/orders")
    public List<Order> showOrders(@ApiIgnore Principal principal) {

       User user = userRepositoryData.findByGmail(principal.getName()).get();
        return userRepositoryData.showAllOrderUser(user);

    }

    @ApiOperation(value = "deleted user by id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idUser", dataType = "string", paramType = "query", value = "ID for deleted Room"),
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true, dataType = "string", paramType = "header")

    })
    @DeleteMapping()
    public void delete(@RequestParam("idUser") Long id ) {
        userService.deleteWithDependencies(id);
    }


    @ApiOperation(value = "created User ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successfully!"),
            @ApiResponse(code = 500, message = "Internal server error ")
    })
    @PostMapping("/save")
    public User saveUser( @RequestBody UserCreateRequest request) {

        User User = conversionService.convert(request, User.class);
        return userService.saveOrUpdateWithAddedRoles(User);

    }


    @ApiOperation(value = "update User ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successfully!"),
            @ApiResponse(code = 500, message = "Internal server error ")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping("/update")
    public User updateUser( @RequestBody UserChangeRequest request) {

        User User = conversionService.convert(request, User.class);
        return userService.saveOrUpdateWithAddedRoles(User);

    }

    @ApiOperation(value = "added roles for  User ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idUser", dataType = "string", paramType = "query", value = "ID for deleted Room"),
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true, dataType = "string", paramType = "header")

    })
    @PostMapping("/admin/roles")
    public User addRoleForUser(@RequestParam("idUser") Long id, @RequestBody List<String> listRoles){
        return userService.addRolesForUser(id,listRoles);
    }

    @ApiOperation(value = "delete role for  User ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idUser", dataType = "string", paramType = "query", value = "ID for deleted Room"),
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true, dataType = "string", paramType = "header")

    })
    @DeleteMapping("/admin/role")
    public User deleteRoleForUser(@RequestParam("idUser") Long id, @RequestBody List<String> listRoles){
        return userService.deleteRoleForUser(id,listRoles);
    }

    @ApiOperation(value = "find all Users by Role ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/admin/users/{idRole}")
    public List<User> findAllUsersByRole(@PathVariable Long idRole){
        return  userRepositoryData.findAllUsersByRole(idRole);
    }


}
