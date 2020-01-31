package store.awesm.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

/**
 *
 * @author zhishangli
 * @time 20:48 2020-01-18
 */
@Entity
@Table(name = "privilege")
public class Privilege implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code")
    private Integer code;

    @Column(name = "title")
    private String title;

    @Column(name = "url")
    private String url;

    public Privilege() {
    }

    public Privilege(Integer code, String title, String url) {
        this.code = code;
        this.title = title;
        this.url = url;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @JsonIgnore
    @Override
    public String getAuthority() {
        return url;
    }


}
