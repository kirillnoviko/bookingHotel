package booking.hotel.controller.request;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomSearchRequest {

    private Long price;

    private String principleOfPlacement;

    private Long ratingAverage;


}
