package booking.hotel.controller.request;

import com.fasterxml.jackson.databind.ser.std.StdArraySerializers;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomSearchRequest {

    private List<String> additionalComfort;

    private Long price;

    private String principleOfPlacement;

    private Long ratingAverage;

    private Date dataOut;

    private Date dataIn;



}
