package capgemini.webappdemo.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "special_place")
public class SpecialPlace {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    @Size(min = 1, message = "name can not be empty")
    private String name;
    private String type;
   // @NotEmpty(message = "range can not be empty")
    private double range;
   // @NotEmpty(message = "latitude can not be empty")
    private double latitude;
    //@NotEmpty(message = "longitude can not be empty")
    private double longitude;
    private int status;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getRange() {
        return range;
    }

    public void setRange(double range) {
        this.range = range;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
