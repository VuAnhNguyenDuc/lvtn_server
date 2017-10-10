package capgemini.webappdemo.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Entity
@Table(name = "manager")
public class Manager {
    @Id
    private int user_id;
    private int number_of_employees;
    @Transient
    private String username;
    private List<Employee> employees;
    private int status;

    public Manager(int user_id, int number_of_employees) {
        this.user_id = user_id;
        this.number_of_employees = number_of_employees;
    }

    public Manager() {
    }

    public int getNumber_of_employees() {
        return number_of_employees;
    }

    public void setNumber_of_employees(int number_of_employees) {
        this.number_of_employees = number_of_employees;
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

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
