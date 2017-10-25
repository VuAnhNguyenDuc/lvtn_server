package capgemini.webappdemo.domain;

import javax.persistence.*;

@Entity
@Table(name = "vehicle")
public class Vehicle {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String name;
    private boolean is_calculatable;

    public Vehicle() {
        this.id = 0;
    }

    public Vehicle(String name) {
        this.name = name;
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

    public boolean is_calculatable() {
        return is_calculatable;
    }

    public void setIs_calculatable(boolean is_calculatable) {
        this.is_calculatable = is_calculatable;
    }
}
