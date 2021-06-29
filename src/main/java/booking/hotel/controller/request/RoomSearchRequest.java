package booking.hotel.controller.request;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomSearchRequest {

    private Long price;

    private String principleOfPlacement;

    private Long ratingAverage;

    private Date dataOut;

    private Date dataIn;



}
