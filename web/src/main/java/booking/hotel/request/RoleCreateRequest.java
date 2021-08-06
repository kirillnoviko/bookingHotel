package booking.hotel.request;


import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiOperation("Class for creating role entity")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleCreateRequest {
    private String roleName;
}