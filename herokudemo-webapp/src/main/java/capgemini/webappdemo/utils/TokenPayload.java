package capgemini.webappdemo.utils;

import java.io.Serializable;

public class TokenPayload implements Serializable {
    private int user_id;
    private String type;    // manager or employee
    private String password;

    public TokenPayload() {
    }

    public TokenPayload(int user_id,String type,String password) {
        this.user_id = user_id;
        this.type = type;
        this.password = password;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
