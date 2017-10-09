package capgemini.webappdemo.domain;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "coordinate")
public class Coordinate {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
    private float longtitude;
    private float lattitude;
    private int detail_id;

    //private String jsonKey;

    public int getDetail_id() {
        return detail_id;
    }

    public void setDetail_id(int detail_id) {
        this.detail_id = detail_id;
    }

    public Coordinate() {
        this.id = 0;
    }

    public Coordinate(Integer id, Date time, float longtitude, float lattitude, int detail_id) {
        this.id = id;
        this.time = time;
        this.longtitude = longtitude;
        this.lattitude = lattitude;
        this.detail_id = detail_id;
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

    public float getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(float longtitude) {
        this.longtitude = longtitude;
    }

    public float getLattitude() {
        return lattitude;
    }

    public void setLattitude(float lattitude) {
        this.lattitude = lattitude;
    }

    /*public String getJsonKey() {
        return jsonKey;
    }

    public void setJsonKey(String jsonKey) {
        this.jsonKey = jsonKey;
    }*/
}
