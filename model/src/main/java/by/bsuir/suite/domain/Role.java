package by.bsuir.suite.domain;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.HashSet;
import java.util.Set;

/**
 * @author i.sukach
 */
@Entity
@Table(name = "role")
public class Role extends BaseEntity {

    private String name;
    
    private String description;

    private Set<User> users = new HashSet<User>();

    public Role() {}

    public Role(String name, String description){
        this.name = name;
        this.description = description;
    }

    @Column(name = "name")
    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description")
    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
