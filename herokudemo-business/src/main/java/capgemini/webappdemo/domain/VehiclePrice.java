package capgemini.webappdemo.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Vu Anh Nguyen Duc on 10/21/2017.
 */
@Entity
@Table(name = "vehicle_price")
public class VehiclePrice {
    private String key;
    private double value;

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
