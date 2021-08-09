package booking.hotel.util;

import booking.hotel.repository.dataspring.UserRepositoryData;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class PrincipalUtils {

    private final UserRepositoryData userRepository;

    public String getUsername(Principal principal) {
        Object castedPrincipal = ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        return ((User) castedPrincipal).getUsername();
    }

    public String getPassword(Principal principal) {
        Object castedPrincipal = ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        return ((User) castedPrincipal).getPassword();
    }

    public Collection<GrantedAuthority> getAuthorities(Principal principal) {
        Object castedPrincipal = ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        return ((User) castedPrincipal).getAuthorities();
    }
}
