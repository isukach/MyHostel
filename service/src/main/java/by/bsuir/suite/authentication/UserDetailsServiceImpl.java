package by.bsuir.suite.authentication;

import by.bsuir.suite.dao.UserDao;
import by.bsuir.suite.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

/**
 * Custom implementation of spring security UserDetailsService.
 * Provides access to user information for authentication.
 * @author i.sukach
 */
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserDetailsDisassembler userDetailsDisassembler;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException, DataAccessException {
        User user = userDao.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user by provided username");
        }
        return userDetailsDisassembler.disassemble(user);
    }
}
