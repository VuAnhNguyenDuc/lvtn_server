package capgemini.webappdemo.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "coordinate")
public class Coordinate {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
    private float longitude;
    private float latitude;
    private int detail_id;

    @Transient
    private String time_str;

    public int getDetail_id() {
        return detail_id;
    }

    public void setDetail_id(int detail_id) {
        this.detail_id = detail_id;
    }

    public Coordinate() {
        this.id = 0;
    }

    public Coordinate(Date time, float longitude, float latitude, int detail_id, String time_str) {
        this.time = time;
        this.longitude = longitude;
        this.latitude = latitude;
        this.detail_id = detail_id;
        this.time_str = time_str;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public String getTime_str() {
        return time_str;
    }

    public void setTime_str(String time_str) {
        this.time_str = time_str;
    }
}
