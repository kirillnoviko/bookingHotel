package booking.hotel.controller.rest;

import booking.hotel.beans.SecurityConfig;
import booking.hotel.domain.Order;
import booking.hotel.domain.User;
import booking.hotel.repository.dataspring.UserRepositoryData;

import booking.hotel.request.UserChangeRequest;
import booking.hotel.request.UserCreateRequest;
import booking.hotel.service.UserService;
import booking.hotel.util.PrincipalUtils;
import io.swagger.annotations.*;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/rest/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserRepositoryData userRepositoryData;
    private final UserService userService;
    private final SecurityConfig securityConfig;
    private final PrincipalUtils principalUtils;
    private final SecurityConfig config;

    public final ConversionService conversionService;


    @ApiOperation(value = "show all users ")
    @GetMapping
    public List<User> findAll() {
        System.out.println("In rest controller");
        return userRepositoryData.findByIsBannedFalseAndIsDeletedFalse();
    }

    @ApiOperation(value = "find user by id")
    @GetMapping("/{idUser}")
    public User findOne(@PathVariable("idUser") Long id) {

        return userRepositoryData.findById(id).get();

    }

    @ApiOperation(value = "show all orders user ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idUser", dataType = "string", paramType = "query", value = "ID for deleted Room"),

    })
    @GetMapping("/orders")
    public List<Order> showOrders(@RequestParam("idUser") Long id) {

       User user = userRepositoryData.findById(id).get();
        return userRepositoryData.showAllOrderUser(user);

    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/search")
    public User findUser(@ApiIgnore Principal principal){
        String username = principalUtils.getUsername(principal);
        return userRepositoryData.findByGmail(username).get();
    }




    @ApiOperation(value = "deleted user by id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idUser", dataType = "string", paramType = "query", value = "ID for deleted Room"),

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
    @PostMapping("/update")
    public User updateUser( @RequestBody UserChangeRequest request) {

        User User = conversionService.convert(request, User.class);
        return userService.saveOrUpdateWithAddedRoles(User);

    }

    @ApiOperation(value = "added roles for  User ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idUser", dataType = "string", paramType = "query", value = "ID for deleted Room"),

    })
    @PostMapping("/admin/roles")
    public User addRoleForUser(@RequestParam("idUser") Long id, @RequestBody List<String> listRoles){
        return userService.addRolesForUser(id,listRoles);
    }

    @ApiOperation(value = "delete role for  User ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idUser", dataType = "string", paramType = "query", value = "ID for deleted Room"),

    })
    @DeleteMapping("/admin/role")
    public User deleteRoleForUser(@RequestParam("idUser") Long id, @RequestBody List<String> listRoles){
        return userService.deleteRoleForUser(id,listRoles);
    }






  /*  @ApiOperation(value = "Generate auto users in system")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "usersCount", dataType = "string", paramType = "path",
                    value = "Count of generated users", required = true, defaultValue = "100")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Users was successfully created!"),
            @ApiResponse(code = 500, message = "Internal server error! https://stackoverflow.com/questions/37405244/how-to-change-the-response-status-code-for-successful-operation-in-swagger")
    })
    @PostMapping("/generate/{usersCount}")
    public List<User> generateUsers(@PathVariable("usersCount") Integer count) {
        throw new RuntimeException("Haha!");
//        List<User> generateUsers = userGenerator.generate(count);
//        userRepository.batchInsert(generateUsers);
//
//        return userRepository.findAll();
    }*/

}
