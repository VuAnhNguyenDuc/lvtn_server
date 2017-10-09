package capgemini.webappdemo.domain;

import java.io.Serializable;

public class TokenPayload implements Serializable {
    private int user_id;
    private String username;
    private String email;
    private String type;    // manager or employee

    // If a user is taking an appointment
    private int appointment_id;
    // If a user is taking a detail
    private int detail_id;

    public TokenPayload() {
    }

    public TokenPayload(int user_id, String username, String email, String type) {
        this.user_id = user_id;
        this.username = username;
        this.email = email;
        this.type = type;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAppointment_id() {
        return appointment_id;
    }

    public void setAppointment_id(int appointment_id) {
        this.appointment_id = appointment_id;
    }

    public int getDetail_id() {
        return detail_id;
    }

    public void setDetail_id(int detail_id) {
        this.detail_id = detail_id;
    }
}
