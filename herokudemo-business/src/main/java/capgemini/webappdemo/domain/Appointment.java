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
    private String start_location;
    private String end_location;
    private Integer status;

    @Transient
    private List<Detail> details;
    @Transient
    private List<User> users;
    @Transient
    private List<Image> images;

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

    public Appointment(Integer id, String name, Date start_date, String start_location, String end_location, Integer status, List<Detail> details, List<User> users, List<Image> images) {
        this.id = id;
        this.name = name;
        this.start_date = start_date;
        this.start_location = start_location;
        this.end_location = end_location;
        this.status = status;
        this.details = details;
        this.users = users;
        this.images = images;
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

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    /*public String getJsonKey() {
        return jsonKey;
    }

    public void setJsonKey(String jsonKey) {
        this.jsonKey = jsonKey;
    }*/
}
