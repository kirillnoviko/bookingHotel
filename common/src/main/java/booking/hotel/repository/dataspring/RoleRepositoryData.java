package booking.hotel.repository.dataspring;

import booking.hotel.domain.Comfort;
import booking.hotel.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepositoryData extends JpaRepository<Role, Long> {
    Optional<Role> findById(Long id);
    Optional<Role> findByRoleName(String name);
}
