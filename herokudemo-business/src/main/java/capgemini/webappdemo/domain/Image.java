package capgemini.webappdemo.domain;

import javax.persistence.*;

@Entity
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String base64_content;
    private int appointment_id;
    //private String jsonToken;

    public int getAppointment_id() {
        return appointment_id;
    }

    public void setAppointment_id(int appointment_id) {
        this.appointment_id = appointment_id;
    }

    public String getBase64_content() {
        return base64_content;
    }

    public void setBase64_content(String base64_content) {
        this.base64_content = base64_content;
    }

    public Image() {
        this.id = 0;
    }

    public Image(Integer id, String base64_content, int appointment_id) {
        this.id = id;
        this.base64_content = base64_content;
        this.appointment_id = appointment_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /*public String getJsonToken() {
        return jsonToken;
    }

    public void setJsonToken(String jsonToken) {
        this.jsonToken = jsonToken;
    }*/
}
