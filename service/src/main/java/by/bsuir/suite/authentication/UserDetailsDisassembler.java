package by.bsuir.suite.authentication;

import by.bsuir.suite.domain.Role;
import by.bsuir.suite.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Used to convert User entity to UserDetails, which is required for spring security authentication.
 * @author i.sukach
 */
@Component
public class UserDetailsDisassembler {

    @Transactional(readOnly = true)
    public UserDetails disassemble(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        boolean enabled = user.getActive();
        boolean accountNonExpired = user.getActive();
        boolean credentialsNonExpired = user.getActive();
        boolean accountNonLocked = user.getActive();

        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (Role role : user.getRoles()) {
            authorities.add(new GrantedAuthorityImpl(role.getName()));
        }

        return new org.springframework.security.core.userdetails.User(username, password, enabled, accountNonExpired,
        credentialsNonExpired, accountNonLocked, authorities);
    }
}
