package by.bsuir.suite.session;

import org.apache.wicket.IClusterable;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;

/**
 * @author i.sukach
 */
public class SessionUser implements IClusterable {
    private Long personId;
    private String userName;
    private final Roles roles;

    public SessionUser(String userName, Roles roles) {
        if (userName == null) {
            throw new IllegalArgumentException("name must not be null");
        }
        if (roles == null || roles.size() < 1) {
            throw new IllegalArgumentException("roles must not be null");
        }
        this.userName = userName;
        this.roles = roles;
    }

    public boolean hasRole(String role) {
        return this.roles.hasRole(role);
    }

    public boolean hasAnyRole(Roles roles) {
        return this.roles.hasAnyRole(roles);
    }

    public String getUserName() {
        return this.userName;
    }

    public Roles getRoles() {
        return this.roles;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
