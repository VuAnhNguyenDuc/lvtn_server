package capgemini.webappdemo.domain;

import javax.persistence.*;
import java.util.List;

@Entity(name = "detail")
public class Detail {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    //private Integer estimate_cost;
    private Integer input_cost;
    @Transient
    private List<Coordinate> coordinates;
    private int appointment_id;
    private int vehicle_id;
    private String start_time;
    private String end_time;
    //private String jsonKey;

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
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

    public Detail(Integer id, Integer input_cost, List<Coordinate> coordinates, int appointment_id, int vehicle_id, String start_time, String end_time) {
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

    /*public String getJsonKey() {
        return jsonKey;
    }

    public void setJsonKey(String jsonKey) {
        this.jsonKey = jsonKey;
    }*/
}
