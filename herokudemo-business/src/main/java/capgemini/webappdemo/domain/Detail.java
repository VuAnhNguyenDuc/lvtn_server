package capgemini.webappdemo.domain;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "detail")
public class Detail {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    //private Integer estimate_cost;
    private Integer input_cost;
    @Type(type = "text")
    private String image_content;
    @Transient
    private List<Coordinate> coordinates;
    private int appointment_id;
    private int vehicle_id;

    private String start_location;
    private String end_location;
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date start_time;
    @Temporal(TemporalType.TIMESTAMP)
    private Date end_time;

    @Transient
    private String start_time_string;
    @Transient
    private String end_time_string;
    @Transient
    private String vehicle_name;

    private int user_created;
    @Transient
    private String user_created_name;
    @Transient
    private String json_token;

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public int getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(int vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public int getAppointment_id() {
        return appointment_id;
    }

    public void setAppointment_id(int appointment_id) {
        this.appointment_id = appointment_id;
    }

    public Detail() {
        this.id = 0;
    }

    public Detail(Integer id, Integer input_cost, List<Coordinate> coordinates, int appointment_id, int vehicle_id, Date start_time, Date end_time) {
        this.id = id;
        this.input_cost = input_cost;
        this.coordinates = coordinates;
        this.appointment_id = appointment_id;
        this.vehicle_id = vehicle_id;
        this.start_time = start_time;
        this.end_time = end_time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /*public Integer getEstimate_cost() {
        return estimate_cost;
    }

    public void setEstimate_cost(Integer estimate_cost) {
        this.estimate_cost = estimate_cost;
    }*/

    public Integer getInput_cost() {
        return input_cost;
    }

    public void setInput_cost(Integer input_cost) {
        this.input_cost = input_cost;
    }

    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

    public String getStart_time_string() {
        return start_time_string;
    }

    public void setStart_time_string(String start_time_string) {
        this.start_time_string = start_time_string;
    }

    public String getEnd_time_string() {
        return end_time_string;
    }

    public void setEnd_time_string(String end_time_string) {
        this.end_time_string = end_time_string;
    }

    public String getImage_content() {
        return image_content;
    }

    public void setImage_content(String image_content) {
        this.image_content = image_content;
    }

    public int getUser_created() {
        return user_created;
    }

    public void setUser_created(int user_created) {
        this.user_created = user_created;
    }

    public String getVehicle_name() {
        return vehicle_name;
    }

    public void setVehicle_name(String vehicle_name) {
        this.vehicle_name = vehicle_name;
    }

    public String getUser_created_name() {
        return user_created_name;
    }

    public void setUser_created_name(String user_created_name) {
        this.user_created_name = user_created_name;
    }

    public String getJson_token() {
        return json_token;
    }

    public void setJson_token(String json_token) {
        this.json_token = json_token;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
