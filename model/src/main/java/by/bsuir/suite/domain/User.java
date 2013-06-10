package by.bsuir.suite.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author i.sukach
 */
@NamedQueries({
        @NamedQuery(
                name = User.GET_USER_BY_USERNAME,
                query = "SELECT user FROM User user WHERE user.username = :username"
        ),
        @NamedQuery(
                name = User.GET_USER_COUNT,
                query = "SELECT COUNT(user) FROM User user WHERE user.username = :username"
        )
})
@javax.persistence.Entity
@Table(name = "user")
public class User extends BaseEntity {

    public static final String GET_USER_BY_USERNAME = "user.getByUsername";
    public static final String GET_USER_COUNT = "user.getCount";

    private String username;

    private String password;

    private boolean active = false;

    private Set<Role> roles = new HashSet<Role>();
    
    public User(){}
    
    public User(String username, String password, Boolean active){
        this.username = username;
        this.password = password;
        this.active = active;
    }

    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(columnDefinition = "tinyint", name = "active")
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name="user_role",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id")
    )
    public Set<Role> getRoles() {

        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Transient
    public void addRole(Role role){
        if (roles == null) {
            roles = new HashSet<Role>();
        }
        roles.add(role);
    }
}
