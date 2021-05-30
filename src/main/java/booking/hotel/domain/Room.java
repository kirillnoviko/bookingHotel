package booking.hotel.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    private Long id;

    private String name;

    private Long price;

    private String principleOfPlacement;

    private String numberRoom;

    private Long ratingAverage;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }


}
