package store.awesm.domain;

import javax.persistence.*;
import java.util.List;

/**
 * TODO
 *
 * @author zhishangli
 * @time 20:45 2020-01-18
 */
@Entity
@Table(name = "role")
public class Role {
    public static final Integer ROLE_ADMIN = 1;
    public static final Integer ROLE_USER = 2;
    public static final Integer ROLE_BUSINESS = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "rolename")
    private String rolename;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_privilege",
            joinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "PRIVILEGE_ID", referencedColumnName = "id")}
    )
    private List<Privilege> privileges;

    public Role() {
    }

    public Role(String rolename) {
        this.rolename = rolename;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public List<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<Privilege> privileges) {
        this.privileges = privileges;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", rolename='" + rolename + '\'' +
                ", privileges=" + privileges +
                '}';
    }
}
