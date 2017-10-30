package capgemini.webappdemo.utils;

import capgemini.webappdemo.domain.Coordinate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Created by Vu Anh Nguyen Duc on 10/16/2017.
 */
public class CalculateDistance {
    public double degreeToRadians(double degree){
        return (degree * Math.PI / 180);
    }

    public double getDistance(Coordinate c1, Coordinate c2){
        double earthRadius = 6371;

        double dLat = degreeToRadians(c2.getLatitude() - c1.getLatitude());
        double dLon = degreeToRadians(c2.getLongitude() - c1.getLongitude());

        double lat1 = degreeToRadians(c1.getLatitude());
        double lat2 = degreeToRadians(c2.getLatitude());

        double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a),Math.sqrt(1-a));
        return earthRadius * c;
    }

    public double getTotalDistance(List<Coordinate> coords){
        double total = 0;
        for(int i = 0; i < coords.size() - 1; i++){
            Coordinate c1 = coords.get(i);
            Coordinate c2 = coords.get(i+1);
            total += getDistance(c1,c2);
        }
        return total;
    }
}
