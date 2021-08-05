package booking.hotel.domain;


import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class GeneralInfoRoom implements Serializable {

    private String name;

    private Long price;

    private String principleOfPlacement;

    private String comfortLevel;

    private Long numberRoom;
}
