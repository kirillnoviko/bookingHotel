package booking.hotel.repository.dataspring;

import booking.hotel.domain.Key;
import booking.hotel.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UuidKeyRepository  extends JpaRepository<Key, Long> {
    List<Key> findByIdUser(Long idUser);


}
