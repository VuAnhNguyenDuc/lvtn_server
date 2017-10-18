package capgemini.webappdemo.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EmployeeForm {
    private String username;
    @NotNull
    @Size(min = 4, max = 32,message = "password must be from between 4 to 32 characters")
    private String password;
    @NotNull
    @Size(min = 4, max = 32,message = "full name must be from between 4 to 32 characters")
    private String full_name;
    @NotNull
    private int manager_id;
    @NotNull
    @Size(min = 4, max = 32,message = "email must be from between 4 to 32 characters")
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

    public int getManager_id() {
        return manager_id;
    }

    public void setManager_id(int manager_id) {
        this.manager_id = manager_id;
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
