package capgemini.webappdemo.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "coordinate")
public class Coordinate {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
    private double longitude;
    private double latitude;
    private int detail_id;

    public int getDetail_id() {
        return detail_id;
    }

    public void setDetail_id(int detail_id) {
        this.detail_id = detail_id;
    }

    public Coordinate() {
        this.id = 0;
    }

    public Coordinate(Date time, float longitude, float latitude, int detail_id) {
        this.time = time;
        this.longitude = longitude;
        this.latitude = latitude;
        this.detail_id = detail_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
