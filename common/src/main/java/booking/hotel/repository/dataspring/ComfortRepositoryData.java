package booking.hotel.repository.dataspring;

import booking.hotel.domain.Comfort;
import booking.hotel.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ComfortRepositoryData extends JpaRepository<Comfort, Long> {

    Optional<Comfort> findById(Long id);
}
