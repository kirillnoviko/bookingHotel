package booking.hotel.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "booking")
@Data
@NoArgsConstructor
/*@EqualsAndHashCode(exclude = {
        "roles"
})*/
public class Booking {

    @Id
    private Long id;

    @Column(name = "id_room")
    private Long idRoom;

    @Column(name = "data_check_in")
    private Date dataCheckIn;

    @Column(name = "data_check_out")
    private Date dataCheckOut;

    @Column
    private String status;

    @Column(name = "id_user")
    private Long idUser;

    @Column
    private Date created;

    @Column
    private Date changed;

    @Column(name = "general_price")
    private Long generalPrice;

    @Column(name = "rating_for_client")
    private Long ratingForClient;

    @Column(name = "rating_for_room")
    private Long ratingForRoom;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }


}
