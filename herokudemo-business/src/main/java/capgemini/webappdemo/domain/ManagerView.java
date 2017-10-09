package capgemini.webappdemo.domain;

import java.util.List;

public class ManagerView {
    private int id;
    private String username;
    private String email;
    private List<User> employees;
    private int status;

    public ManagerView() {
    }

    public ManagerView(int id, String username, String email, List<User> employees, int status) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.employees = employees;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<User> getEmployees() {
        return employees;
    }

    public void setEmployees(List<User> employees) {
        this.employees = employees;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
