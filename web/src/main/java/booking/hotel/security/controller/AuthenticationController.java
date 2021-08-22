package booking.hotel.security.controller;

import booking.hotel.domain.Key;
import booking.hotel.domain.User;
import booking.hotel.repository.dataspring.UserRepositoryData;
import booking.hotel.repository.dataspring.UuidKeyRepository;
import booking.hotel.security.requests.AuthRequest;
import booking.hotel.security.requests.AuthResponse;
import booking.hotel.util.TokenUtils;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authentication")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final UserRepositoryData userRepositoryData;
    private final UuidKeyRepository uuidKeyRepository;

    private final TokenUtils tokenUtils;

    private final UserDetailsService userProvider;

    @ApiOperation(value = "Login user in system", notes = "Return Auth-Token with user login")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful authorization"),
            @ApiResponse(code = 400, message = "Request error"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @PostMapping
    public ResponseEntity<AuthResponse> loginUser(@RequestBody AuthRequest request) {

        /*Check login and password*/
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getGmail(),
                        request.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        /*Generate token with answer to user*/
        return ResponseEntity.ok(
                AuthResponse
                        .builder()
                        .login(request.getGmail())
                        .token(tokenUtils.generateToken(userProvider.loadUserByUsername(request.getGmail())))
                        .build()
        );
    }

    @ApiOperation(value = "Confirmation user in system")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful the confirmation"),
            @ApiResponse(code = 400, message = "Request error"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", dataType = "string", paramType = "query", value = "code for confirum Gmail"),
            @ApiImplicitParam(name = "gmail", dataType = "string", paramType = "query", value = "gmail user"),
    })
    @PostMapping("/confirmation")
    public boolean confirmationGmail(@RequestParam("code") String code,@RequestParam("gmail") String gmail){

        User user = userRepositoryData.findByGmail(gmail).get();
        List<Key> keys = uuidKeyRepository.findByIdUser(user.getId());

        for(Key key : keys){
            if(key.getUuid().equals(code)){
                userRepositoryData.updateIsBanned(user.getId());
                return true;
            }
        }
        return false;
    }

}