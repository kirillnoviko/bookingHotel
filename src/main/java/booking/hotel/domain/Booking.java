package booking.hotel.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    private Long id;

    private Long idRoom;

    private Date dataCheckIn;

    private Date dataCheckOut;

    private String status;

    private Long idUser;

    private Date created;

    private Date changed;

    private Long generalPrice;

    private Long ratingForClient;

    private Long ratingForRoom;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }


}
