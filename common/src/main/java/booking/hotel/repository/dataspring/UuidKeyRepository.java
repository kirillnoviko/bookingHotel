package booking.hotel.repository.dataspring;

import booking.hotel.domain.Key;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UuidKeyRepository  extends JpaRepository<Key, Long> {
    List<Key> findByIdUser(Long idUser);


}
