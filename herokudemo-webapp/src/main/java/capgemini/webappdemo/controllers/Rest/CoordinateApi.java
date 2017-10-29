package capgemini.webappdemo.controllers.Rest;

import capgemini.webappdemo.domain.Coordinate;
import capgemini.webappdemo.form.Coor;
import capgemini.webappdemo.form.CoorFormString;
import capgemini.webappdemo.form.CoordinateForm;
import capgemini.webappdemo.service.Coordinate.CoordinateService;
import capgemini.webappdemo.utils.JsonTokenUtil;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CoordinateApi {
    @Autowired
    private CoordinateService coordinateService;
    private JsonTokenUtil jsonTokenUtil = new JsonTokenUtil();

    @RequestMapping(value = "/api/detail/addCoordinate", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> addCoordinate(@RequestBody CoordinateForm input) throws ParseException {

        int detailId = input.getDetail_id();
        List<Coor> coordinates = input.getCoordinates();
        String jsonToken = input.getJson_token();
        List<Coordinate> coords = new ArrayList<>();
        JSONObject result = new JSONObject();

        result.put("message",0);
        if(jsonToken.equals("") || !jsonTokenUtil.validateKey(jsonToken)){
            result.put("description","invalid json token");
            return new ResponseEntity<JSONObject>(result,HttpStatus.OK);
        }
        boolean flag = true;
        for(Coor coor : coordinates){
            String time = coor.getTime();
            double latitude = coor.getLatitude();
            double longitude = coor.getLongitude();

            Coordinate co = new Coordinate();
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
            co.setTime(dateFormat.parse(time));
            co.setLatitude(latitude);
            co.setLongitude(longitude);
            co.setDetail_id(detailId);
            coordinateService.add(co);
            if(co.getId() == 0){
                result.put("message",0);
                result.put("description","something went wrong when creating new coordinate, please check logs for more information");
                flag = false;
                break;
            }
        }
        if(flag){
            result.put("message",1);
        }
        return new ResponseEntity<JSONObject>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/detail/addCoordinate/str", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> addCoorStr(@RequestBody CoorFormString input) throws org.json.simple.parser.ParseException, ParseException {
        System.out.println("adding coordinates");
        int detailid = input.getDetail_id();
        String jsonToken = input.getJson_token();
        ArrayList<String> coords = input.getCoordinates();
        JSONObject result = new JSONObject();
        System.out.println("detail id = " + detailid + "; coords = "+coords);
        result.put("message",0);
        boolean flag = true;

        for(String coor : coords){
            String temp = coor.replaceAll("\'","\\\"");
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(temp);
            double latitude = (double) json.get("latitude");
            double longitude = (double) json.get("longitude");
            String time = json.get("time").toString();

            Coordinate co = new Coordinate();
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
            co.setTime(dateFormat.parse(time));
            co.setLatitude(latitude);
            co.setLongitude(longitude);
            co.setDetail_id(detailid);
            coordinateService.add(co);
            if(co.getId() == 0){
                result.put("message",0);
                result.put("description","something went wrong when creating new coordinate, please check logs for more information");
                flag = false;
                break;
            }
            if(flag){
                result.put("message",1);
            }
        }
        return new ResponseEntity<JSONObject>(result,HttpStatus.OK);
    }

    @RequestMapping(value = "/api/detail/addCoordinate/strArray", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> addCoorStr2(@RequestBody JSONObject input) throws ParseException {
        String jsonToken = input.get("json_token").toString();
        int detailid = (int) input.get("detail_id");
        ArrayList<String> coords = (ArrayList<String>) input.get("coordinates");
        JSONObject result = new JSONObject();
        result.put("message",0);
        if(!jsonTokenUtil.validateKey(jsonToken)){
            result.put("description","invalid json token");
        } else{
            //int i = 1;
            Double latitude = 0.0;
            Double longitude = 0.0;
            String time = "";
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
            for(int i = 0; i < coords.size() - 2; i+=3){
                latitude = Double.parseDouble(coords.get(i));
                longitude = Double.parseDouble(coords.get(i+1));
                time = coords.get(i+2);
                Coordinate coor = new Coordinate();
                coor.setLatitude(latitude);
                coor.setLongitude(longitude);
                coor.setTime(dateFormat.parse(time));
                coor.setDetail_id(detailid);
                coordinateService.add(coor);
            }
            result.put("message",1);
        }
        return new ResponseEntity<JSONObject>(result,HttpStatus.OK);
    }
}
