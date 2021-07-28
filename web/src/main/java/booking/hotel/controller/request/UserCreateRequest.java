package booking.hotel.controller.request;

import booking.hotel.domain.Role;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@ApiOperation("Class for creating user entity")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequest {

    private String name;

    private String surname;

    private String gmail;

    private String password;

    private List<String> roles;

}
