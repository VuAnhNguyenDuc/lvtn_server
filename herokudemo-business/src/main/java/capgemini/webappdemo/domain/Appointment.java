package capgemini.webappdemo.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String name;
    @Temporal(TemporalType.TIMESTAMP)
    private Date start_date;
    private String destination;
    private Integer status;

    @Transient
    private String date_str;
    @Transient
    private List<Detail> details;
    @Transient
    private List<User> users;
    @Transient
    private String json_token;

    private int manager_id;

    //private String jsonKey;


    public int getManager_id() {
        return manager_id;
    }

    public void setManager_id(int manager_id) {
        this.manager_id = manager_id;
    }

    public Appointment() {
        this.id = 0;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Detail> getDetails() {
        return details;
    }

    public void setDetails(List<Detail> details) {
        this.details = details;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getDate_str() {
        return date_str;
    }

    public void setDate_str(String date_str) {
        this.date_str = date_str;
    }

    public String getJson_token() {
        return json_token;
    }

    public void setJson_token(String json_token) {
        this.json_token = json_token;
    }
}
