package capgemini.webappdemo.domain;

import javax.persistence.*;

/**
 * Created by Vu Anh Nguyen Duc on 10/21/2017.
 */
@Entity
@Table(name = "vehicle_price")
public class VehiclePrice {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private String key;
    private double value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
