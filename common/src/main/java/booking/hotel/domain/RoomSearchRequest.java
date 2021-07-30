package booking.hotel.domain;

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

    private Long priceMin;

    private Long priceMax;

    private String principleOfPlacement;

    private Long ratingMin;

    private Long ratingMax;

    private Date dataOut;

    private Date dataIn;



}
