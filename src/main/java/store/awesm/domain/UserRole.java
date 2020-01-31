package store.awesm.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author zhishangli
 * @time 14:40 2020-01-23
 */
@Entity
@Table(name = "user_role")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "user_id")
    private Integer uid;
    @Column(name = "role_id")
    private Integer rid;

    public UserRole(Integer uid, Integer rid) {
        this.uid = uid;
        this.rid = rid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }
}
