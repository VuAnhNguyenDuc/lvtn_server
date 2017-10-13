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
    @Temporal(TemporalType.TIMESTAMP)
    private Date end_date;
    private int created_by;
    private int status;
    private String appointment_name;
    @Transient
    private String managerName;
    @Transient
    private String start_date_str;
    @Transient
    private String end_date_str;

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

    public int getCreated_by() {
        return created_by;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
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

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public String getEnd_date_str() {
        return end_date_str;
    }

    public void setEnd_date_str(String end_date_str) {
        this.end_date_str = end_date_str;
    }
}
