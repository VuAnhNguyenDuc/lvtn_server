package capgemini.webappdemo.domain;

import javax.persistence.*;

@Entity
@Table(name = "vehicle")
public class Vehicle {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private String name;
    @Column(name = "status")
    private Integer status;
    @Column(name = "is_calculatable")
    private boolean calculatable;
    @Column
    private String calculate_formula;


    public Vehicle() {
        this.id = 0;
    }

    public Vehicle(String name) {
        this.name = name;
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

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCalculate_formula() {
        return calculate_formula;
    }

    public void setCalculate_formula(String calculate_formula) {
        this.calculate_formula = calculate_formula;
    }
}
