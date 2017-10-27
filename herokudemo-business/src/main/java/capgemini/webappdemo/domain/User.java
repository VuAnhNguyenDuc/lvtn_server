package capgemini.webappdemo.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "app_user")
public class User{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private String username;
    private String password;
    private String email;
    private int status;
    private String fullname;
    @Transient
    private String userType;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public User() {
    }

    public User(String username, String password, String email, int status) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String uername) {
        this.username = uername;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
