package booking.hotel.util;

import com.fasterxml.jackson.databind.ser.std.StdArraySerializers;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomSearchRequest {

    private List<Long> idComfort;

    private Long priceMin;

    private Long priceMax;

    private String principleOfPlacement;

    private Long ratingMin;

    private Long ratingMax;

    private Timestamp dataOut;

    private Timestamp dataIn;



}
