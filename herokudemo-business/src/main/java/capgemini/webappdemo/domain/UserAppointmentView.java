package capgemini.webappdemo.domain;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "user_appointment_view")
@Immutable
public class UserAppointmentView implements Serializable {
    @Id
    private int user_id;
    @Id
    private int appointment_id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date start_date;
    private int create_by;
    private int status;
    private String appointment_name;
    private String start_location;
    private String end_location;
    @Transient
    private String managerName;
    @Transient
    private String start_date_str;

    public String getAppointment_name() {
        return appointment_name;
    }

    public void setAppointment_name(String appointment_name) {
        this.appointment_name = appointment_name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCreate_by() {
        return create_by;
    }

    public void setCreate_by(int create_by) {
        this.create_by = create_by;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public int getAppointment_id() {
        return appointment_id;
    }

    public void setAppointment_id(int appointment_id) {
        this.appointment_id = appointment_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getStart_date_str() {
        return start_date_str;
    }

    public void setStart_date_str(String start_date_str) {
        this.start_date_str = start_date_str;
    }

    public String getStart_location() {
        return start_location;
    }

    public void setStart_location(String start_location) {
        this.start_location = start_location;
    }

    public String getEnd_location() {
        return end_location;
    }

    public void setEnd_location(String end_location) {
        this.end_location = end_location;
    }
}
