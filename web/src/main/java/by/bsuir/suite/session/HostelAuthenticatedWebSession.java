package by.bsuir.suite.session;

import by.bsuir.suite.dto.person.PersonInfoDto;
import by.bsuir.suite.password.PasswordEncryptor;
import by.bsuir.suite.service.UserService;
import by.bsuir.suite.service.lan.LanService;
import by.bsuir.suite.util.Permissions;
import org.apache.log4j.Logger;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

/**
 * Provides authentication and holds authenticated user information.
 * @author i.sukach
 */
public class HostelAuthenticatedWebSession extends AuthenticatedWebSession {
    private Map<String, String> map = new HashMap<String, String>();

    private SessionUser user;

    private String username;

    private String password;

    private static final Logger LOG = Logger.getLogger(HostelAuthenticatedWebSession.class);

    @SpringBean(name = "authenticationManager")
    private AuthenticationManager authenticationManager;

    @SpringBean
    private UserService userService;

    @SpringBean
    private LanService lanService;

    public void setAttributeToMap(String key, String attr) {
        map.put(key, attr);
    }
    
    public String getAttributeFromMap(String key){
        return map.get(key);
    }
    
    public void removeAttributeFromMap(String key){
        map.remove(key);
    }

    public HostelAuthenticatedWebSession(Request request) {
        super(request);
        injectDependency();
        ensureDependenciesNotNull();
    }

    private void injectDependency() {
        Injector.get().inject(this);
    }

    private void ensureDependenciesNotNull() {
        if (authenticationManager == null) {
            throw new IllegalStateException("Authentication manager required!");
        }
    }

    @Override
    public boolean authenticate(String username, String password) {
        boolean authenticated;
        try {
            this.username = username;
            this.password = password;
            Authentication authentication = getAuthentication();
            authenticated = authentication.isAuthenticated();
            if (authenticated) {
                user = new SessionUser(getUsernameFromContext(), createRolesFromContext());
                PersonInfoDto personInfoDto = userService.getUserByUsername(user.getUserName());
                user.setPersonId(personInfoDto.getId());
            }
        } catch (AuthenticationException e) {
            LOG.warn(format("User '%s' failed to login. Reason: %s", username, e.getMessage()));
            authenticated = false;
        }
        return authenticated;
    }

    @Override
    public Roles getRoles() {
        Roles roles = new Roles();
        getRolesIfSignedIn(roles);
        return roles;
    }

    private Authentication getAuthentication() {
        String encryptedPassword = PasswordEncryptor.encrypt(password);
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, encryptedPassword));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    private void getRolesIfSignedIn(Roles roles) {
        if (isSignedIn()) {
            addRolesFromAuthentication(roles, getAuthentication());
            if (lanService.hasContract(user.getPersonId())) {
                roles.add(Permissions.LAN_VIEWING_PERMISSION);
            }
        }
    }

    private void addRolesFromAuthentication(Roles roles, Authentication authentication) {
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            roles.add(authority.getAuthority());
        }
    }

    private Roles createRolesFromContext() {
        Roles roles = new Roles();
        for (GrantedAuthority authority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
            roles.add(authority.getAuthority());
        }
        return roles;
    }

    private String getUsernameFromContext() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public SessionUser getUser() {
        return user;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
