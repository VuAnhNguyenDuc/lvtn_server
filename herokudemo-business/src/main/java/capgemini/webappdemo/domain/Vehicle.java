package capgemini.webappdemo.domain;

import javax.persistence.*;

@Entity
@Table(name = "vehicle")
public class Vehicle {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String name;
    @Column(name = "status")
    private Integer status;
    @Column(name = "is_calculatable")
    private boolean calculatable;


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

    public boolean isCalculatable() {
        return calculatable;
    }

    public void setCalculatable(boolean calculatable) {
        this.calculatable = calculatable;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
