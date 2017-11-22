package capgemini.webappdemo.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ManagerForm {
    @Size(min = 4, max = 32,message = "username must be from between 4 to 32 characters")
    private String username;
    @Size(min = 4, max = 32,message = "password must be from between 4 to 32 characters")
    private String password;
    @Size(min = 4, max = 32,message = "full name must be from between 4 to 32 characters")
    private String full_name;

    private String email;
    private int status;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
