package booking.hotel.domain;


import lombok.Getter;
import lombok.Setter;

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
