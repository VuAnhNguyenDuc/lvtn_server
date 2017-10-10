package capgemini.webappdemo.domain;

import javax.persistence.*;

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    private int user_id;
    private String employee_type;
    private int manager_id;

    public Employee(int user_id, String employee_type, int manager_id) {
        this.user_id = user_id;
        this.employee_type = employee_type;
        this.manager_id = manager_id;
    }

    public Employee() {
    }

    public int getManager_id() {
        return manager_id;
    }

    public void setManager_id(int manager_id) {
        this.manager_id = manager_id;
    }

    public String getEmployee_type() {
        return employee_type;
    }

    public void setEmployee_type(String employee_type) {
        this.employee_type = employee_type;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
