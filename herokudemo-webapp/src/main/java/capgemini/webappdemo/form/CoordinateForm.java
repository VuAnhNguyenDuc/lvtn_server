package capgemini.webappdemo.form;

import java.util.List;

public class CoordinateForm {
    public class Coor {
        private double latitude;
        private double longitude;
        private String time;

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

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
    private int detail_id;
    private List<Coor> coordinates;

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
}
