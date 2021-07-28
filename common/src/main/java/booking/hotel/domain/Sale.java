package booking.hotel.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "sales")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {
        "orders"
})
public class Sale {
    @Id
    private Long id;

    @Column(name = "name_sale")
    private String saleName;

    @Column(name = "percent_sale")
    private Long percentSale;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "sales_orders",
            joinColumns = @JoinColumn(name = "id_sale"),
            inverseJoinColumns = @JoinColumn(name = "id_order")
    )
    @JsonIgnoreProperties("roles")
    private Set<User> users = Collections.emptySet();

}