package capgemini.webappdemo.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "user_takes_appointment")
public class UserTakesAppointment implements Serializable {
    @Id
    private int appointment_id;
    @Id
    private int user_id;
    private String user_response;
    public String getUser_response() {
        return user_response;
    }

    public void setUser_response(String user_response) {
        this.user_response = user_response;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public UserTakesAppointment(int appointment_id, int user_id, String user_response) {
        this.appointment_id = appointment_id;
        this.user_id = user_id;
        this.user_response = user_response;
    }

    public UserTakesAppointment() {
    }

    public int getAppointment_id() {
        return appointment_id;
    }

    public void setAppointment_id(int appointment_id) {
        this.appointment_id = appointment_id;
    }

    public int getEmployee_id() {
        return user_id;
    }

    public void setEmployee_id(int user_id) {
        this.user_id = user_id;
    }
}
