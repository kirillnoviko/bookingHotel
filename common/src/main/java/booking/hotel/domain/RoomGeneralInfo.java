package booking.hotel.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoomGeneralInfo {
    private String name;

    private Long price;

    private String principleOfPlacement;

    private String comfortLevel;

    private Long numberRoom;
}
