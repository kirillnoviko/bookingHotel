package booking.hotel.controller.rest;

import booking.hotel.beans.SecurityConfig;
import booking.hotel.domain.User;
import booking.hotel.repository.dataspring.UserRepositoryData;

import booking.hotel.util.PrincipalUtils;
import io.swagger.annotations.*;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/rest/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserRepositoryData userRepositoryData;
    private final SecurityConfig securityConfig;
    private final PrincipalUtils principalUtils;
    private final SecurityConfig config;




/*
    @GetMapping
    public List<User> findAll() {
        System.out.println("In rest controller");
        return userRepositoryData.findByIsBannedFalseAndIsDeletedFalse();
    }
*/


   @ApiImplicitParams({
            @ApiImplicitParam(name = "idUser", dataType = "string", paramType = "query", value = "ID for search user"),

    })
    @GetMapping("/findOne")
    public User findOne(@RequestParam Long idUser) {

        return userRepositoryData.findById(idUser).get();

    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/search")
    public User findUser(@ApiIgnore Principal principal){
        String username = principalUtils.getUsername(principal);
        return userRepositoryData.findByGmail(username).get();
    }








/*    @ApiImplicitParams({
            @ApiImplicitParam(name = "Secret-Key", dataType = "string", paramType = "header",
                    value = "Secret header for secret functionality!! Hoho")
    })
    @GetMapping("/secret")
    public List<User> securedFindAll(HttpServletRequest request) {
        String secretKey = request.getHeader("Secret-Key");
        if (StringUtils.isNotBlank(secretKey) && secretKey.equals(securityConfig.getSecretKey())) {
            return userRepositoryData.findAll();
        } else {
            //throw new UnauthorizedException();
            return Collections.emptyList();
        }
    }*/


/*
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Secret-Key", dataType = "string", paramType = "header", value = "Secret header for secret functionality!! Hoho"),
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true, dataType = "string", paramType = "header")
    })

    @GetMapping("/hello")
    public List<User> securedFindAll(HttpServletRequest request,
                                     @ApiIgnore Principal principal) {

        String username = principalUtils.getUsername(principal);
        String secretKey = request.getHeader("Secret-Key");

        if (StringUtils.isNotBlank(secretKey) && secretKey.equals(config.getSecretKey())) {
            return Collections.singletonList(userRepository.findByLogin(username));
        } else {
            //throw new UnauthorizedException();
            return Collections.emptyList();
        }
    }*/




/*    @ApiOperation(value = "Creating one user")
    @PostMapping
    public User createUser(@RequestBody UserCreateRequest createRequest) {
        User generatedUser = userGenerator.generate();
        generatedUser.setWeight(createRequest.getWeight());
        generatedUser.setLogin(createRequest.getLogin());
        generatedUser.setName(createRequest.getName());
        generatedUser.setSurname(createRequest.getSurname());
        return userRepository.save(generatedUser);
    }*/



    @ApiOperation(value = "Generate auto users in system")
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
    }

}
