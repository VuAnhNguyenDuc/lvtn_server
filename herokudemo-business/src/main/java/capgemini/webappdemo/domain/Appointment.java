package capgemini.webappdemo.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private String name;
    @Temporal(TemporalType.TIMESTAMP)
    private Date start_date;
    @Temporal(TemporalType.TIMESTAMP)
    private Date end_date;
    private String destination;
    private Integer status;
    private Integer total_cost;
    private Integer client_id;

    @Transient
    private List<Detail> details;
    @Transient
    private List<User> users;

    private int manager_id;

    @Transient
    private String manager_name;
    @Transient
    private String start_date_str;
    @Transient
    private String end_date_str;


    public int getManager_id() {
        return manager_id;
    }

    public void setManager_id(int manager_id) {
        this.manager_id = manager_id;
    }

    public Appointment() {
        this.id = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
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

    public String getStart_date_str() {
        return start_date_str;
    }

    public void setStart_date_str(String start_date_str) {
        this.start_date_str = start_date_str;
    }

    public String getEnd_date_str() {
        return end_date_str;
    }

    public void setEnd_date_str(String end_date_str) {
        this.end_date_str = end_date_str;
    }

    public String getManager_name() {
        return manager_name;
    }

    public void setManager_name(String manager_name) {
        this.manager_name = manager_name;
    }

    public Integer getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(Integer total_cost) {
        this.total_cost = total_cost;
    }

    public Integer getClient_id() {
        return client_id;
    }

    public void setClient_id(Integer client_id) {
        this.client_id = client_id;
    }
}
