package capgemini.webappdemo.domain;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "vehicle")
public class Vehicle {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    @Size(min = 1, message = "Cannot input empty value into vehicle's name")
    private String name;
    @Column(name = "status")
    private Integer status;
    @Column(name = "is_calculatable")
    private boolean calculatable = true;
    @Column
    @Type(type = "text")
    private String calculate_formula;
    @Column(name = "warning_rate")
    private double warning_rate;

    public Vehicle() {
        this.id = 0;
        this.warning_rate = 1;
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

    public boolean getCalculatable(){return calculatable;}

    public void setCalculatable(boolean calculatable) {
        this.calculatable = calculatable;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCalculate_formula() {
        return calculate_formula;
    }

    public void setCalculate_formula(String calculate_formula) {
        this.calculate_formula = calculate_formula;
    }

    public double getWarning_rate() {
        return warning_rate;
    }

    public void setWarning_rate(double warning_rate) {
        this.warning_rate = warning_rate;
    }
}
