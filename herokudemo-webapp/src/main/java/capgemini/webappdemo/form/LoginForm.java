package capgemini.webappdemo.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginForm {
    @NotNull
    @Size(min = 4, max = 32, message = "username cannot be empty and must be within 4 to 32 characters")
    private String username;
    @NotNull
    @Size(min = 4, max = 32, message = "password cannot be empty and must be within 4 to 32 characters")
    private String password;

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
}
