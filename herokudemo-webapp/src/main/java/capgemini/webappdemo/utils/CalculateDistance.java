package capgemini.webappdemo.utils;

import capgemini.webappdemo.domain.Coordinate;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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

    public double getAvarageVelocity(List<Coordinate> coords){
        double avg = 0;
        double length = 0;
        long time = 0;
        int size = coords.size() - 1;
        for(int i = 0; i < size; i++){
            Coordinate c1 = coords.get(i);
            Coordinate c2 = coords.get(i+1);
            length += getDistance(c1,c2);
            time += (c2.getTime().getTime() - c1.getTime().getTime())/(1000); // km/s
        }
        return (length*3600/time); //km/h
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
