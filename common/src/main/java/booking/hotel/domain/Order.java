package booking.hotel.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
/*@EqualsAndHashCode(exclude = {
        "roles"
})*/
@EqualsAndHashCode(exclude = {
        "room","user"
})

public class Order {

    @Id
    private Long id;

    @Column(name = "data_check_in")
    private Timestamp dataCheckIn;

    @Column(name = "data_check_out")
    private Timestamp dataCheckOut;

    @Column
    private String status;

    @Column
    private Timestamp created;

    @Column
    private Timestamp changed;

    @Column(name = "general_price")
    private Long generalPrice;

    @Column(name = "rating_for_client")
    private Long ratingForClient;

    @Column(name = "rating_for_room")
    private Long ratingForRoom;

    @ManyToOne
    @JoinColumn(name = "id_room")
    @JsonBackReference
    private Room room;

    @ManyToOne
    @JoinColumn(name = "id_user")
    @JsonBackReference
    private User user;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }


}
