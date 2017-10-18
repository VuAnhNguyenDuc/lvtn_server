package capgemini.webappdemo.form;

import java.util.List;

public class CoordinateForm {
    private int detail_id;
    private List<Coor> coordinates;
    private String json_token;

    public int getDetail_id() {
        return detail_id;
    }

    public void setDetail_id(int detail_id) {
        this.detail_id = detail_id;
    }

    public List<Coor> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Coor> coordinates) {
        this.coordinates = coordinates;
    }

    public String getJson_token() {
        return json_token;
    }

    public void setJson_token(String json_token) {
        this.json_token = json_token;
    }
}
