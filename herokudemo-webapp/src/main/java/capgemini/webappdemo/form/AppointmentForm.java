package capgemini.webappdemo.form;

import capgemini.webappdemo.domain.User;

import java.util.List;
/*
    This is the form we use when creating new appointment from api
*/
public class AppointmentForm {
    private String json_token;
    private String name;
    private String destination;
    private String start_date;
    /*private List<User> users;*/
    private String users;
    private int status;
    private int appointment_id;

    public String getJson_token() {
        return json_token;
    }

    public void setJson_token(String json_token) {
        this.json_token = json_token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    /*public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }*/

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getAppointment_id() {
        return appointment_id;
    }

    public void setAppointment_id(int appointment_id) {
        this.appointment_id = appointment_id;
    }
}
