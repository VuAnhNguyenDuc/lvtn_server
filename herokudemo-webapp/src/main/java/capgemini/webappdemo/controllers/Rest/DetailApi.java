package capgemini.webappdemo.controllers.Rest;

import capgemini.webappdemo.domain.*;
import capgemini.webappdemo.service.Appointment.AppointmentService;
import capgemini.webappdemo.service.Coordinate.CoordinateService;
import capgemini.webappdemo.service.Detail.DetailService;
import capgemini.webappdemo.service.SpecialPlace.SpecialPlaceService;
import capgemini.webappdemo.service.Vehicle.VehicleService;
import capgemini.webappdemo.utils.CalculateDistance;
import capgemini.webappdemo.utils.CalculateMoney;
import capgemini.webappdemo.utils.CommonUtils;
import capgemini.webappdemo.utils.JsonTokenUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class DetailApi {
    @Autowired
    private DetailService detailService;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private AppointmentService apmService;

    @Autowired
    private CoordinateService coordService;

    @Autowired
    private SpecialPlaceService spService;

    private CommonUtils commonUtils = new CommonUtils();
    private JsonTokenUtil jsonTokenUtil = new JsonTokenUtil();
    private CalculateDistance cd = new CalculateDistance();
    private CalculateMoney cm = new CalculateMoney();

    @RequestMapping(value = "/api/createDetail", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> createDetail(@RequestBody JSONObject input) throws ParseException {
        System.out.println("creating detail - Detail API");
        String jsonToken = input.get("json_token").toString();
        int vehicleId = (int) input.get("vehicle_id");
        String startTime = input.get("start_time").toString();
        String startLocation = input.get("start_location").toString();
        //String description = input.get("description").toString();
        int appointmentId = (int) input.get("appointment_id");

        JSONObject result = new JSONObject();

        if(jsonToken.equals("") || !jsonTokenUtil.validateKey(jsonToken)){
            result.put("message",0);
            result.put("description","invalid json token");
            return new ResponseEntity<JSONObject>(result,HttpStatus.OK);
        }

        result.put("message",0);
        if(vehicleId == 0){
            result.put("description","please input the vehicle that you will use for this detail");
        } else if(appointmentId == 0){
            result.put("description","please input the appointment id of this detail");
        } else {
            int id = jsonTokenUtil.getUserIdFromJsonKey(jsonToken);
            Detail detail = new Detail();
            detail.setAppointment_id(appointmentId);
            detail.setStart_time(commonUtils.convertStringToDateSec(startTime));
            //detail.setDescription(description);
            detail.setStart_location(startLocation);
            detail.setUser_created(id);
            detail.setWarning(false);
            detail.setVehicle_id(vehicleId);
            detailService.add(detail);
            if(detail.getId() != 0){
                result.put("message",1);
                result.put("detail_id",detail.getId());
            } else{
                result.put("description","something went wrong while creating detail, please check logs for more information");
            }
        }
        return new ResponseEntity<JSONObject>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/detail/start", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> startDetail(@RequestBody JSONObject input){
        System.out.println("starting a detail - Detail API");
        String jsonToken = input.get("json_token").toString();
        int id = (int) input.get("id");
        String  startTime = input.get("start_time").toString();

        JSONObject result = new JSONObject();

        if(jsonToken.equals("") || !jsonTokenUtil.validateKey(jsonToken)){
            result.put("message",0);
            result.put("description","invalid json token");
            return new ResponseEntity<JSONObject>(result, HttpStatus.OK);
        }

        if(startTime.isEmpty()){
            result.put("description","please input the start time on start_date column");
        } else{
            Detail dt = detailService.get(id);
            if(dt == null){
                result.put("description","this detail does not exist");
            } else{
                try {
                    dt.setStart_time(commonUtils.convertStringToDateSec(startTime));
                    detailService.update(dt);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                result.put("message",1);
            }
        }
        return new ResponseEntity<JSONObject>(result,HttpStatus.OK);
    }

    @RequestMapping(value = "/api/detail/end", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> endDetail(@RequestBody JSONObject input) throws ParseException {
        System.out.println("ending a detail - Detail API");
        String jsonToken = input.get("json_token").toString();
        int id = (int) input.get("id");
        //String endTime = input.get("end_time").toString();
        //String endLocation = input.get("end_location").toString();
        String imageContent = input.get("image_content").toString();
        String description = input.get("description").toString();
        double inputCost = (double) input.get("input_cost");
        JSONObject result = new JSONObject();

        if(jsonToken.equals("") || !jsonTokenUtil.validateKey(jsonToken)){
            result.put("message",0);
            result.put("description","invalid json token");
            return new ResponseEntity<JSONObject>(result, HttpStatus.OK);
        }
        result.put("message",0);

        Detail dt = detailService.get(id);
        if(dt == null){
            result.put("description","this detail does not exist");
        } else{
            List<Coordinate> coords = coordService.getCoordsOfDetail(id);
            if(vehicleService.get(dt.getVehicle_id()).isCalculatable() && (coords.size() == 0 || inputCost == 0)){
                result.put("description","please input the coordinates and cost of this detail before end it");
            } else{
                dt.setInput_cost(inputCost);
                dt.setDescription(description);
                dt.setImage_content(imageContent);
                dt.setCoordinates(coords);
                /*dt.setEnd_time(commonUtils.convertStringToDateSec(endTime));*/
                detailService.update(dt);
                calculate(id,dt.getCoordinates());
                result.put("message",1);
            }
        }

        return new ResponseEntity<JSONObject>(result,HttpStatus.OK);
    }

    @RequestMapping(value = "/api/detail/addImage", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> addImage(@RequestBody JSONObject input){
        System.out.println("adding an image to detail - Detail API");
        String jsonToken = input.get("json_token").toString();
        String imageContent = input.get("image_content").toString();
        int id = (int) input.get("id");

        JSONObject result = new JSONObject();
        result.put("message",0);

        if(jsonToken.equals("") || !jsonTokenUtil.validateKey(jsonToken)){
            result.put("description","invalid json token");
            return new ResponseEntity<JSONObject>(result, HttpStatus.OK);
        }

        if(imageContent.isEmpty()){
            result.put("description","please input image content first");
        } else{
            Detail dt = detailService.get(id);
            if(result == null){
                result.put("description","this detail does not exist");
            } else{
                dt.setImage_content(imageContent);
                detailService.update(dt);
                result.put("message",1);
            }
        }
        return new ResponseEntity<JSONObject>(result,HttpStatus.OK);
    }

    @RequestMapping(value = "/api/detail/addCost", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> addCost(@RequestBody JSONObject input){
        System.out.println("adding a price cost to detail - Detail API");
        String jsonToken = input.get("json_token").toString();
        int id = (int) input.get("id");
        int inputCost = (int) input.get("input_cost");
        //double convertCost =  inputCost;
        JSONObject result = new JSONObject();

        result.put("message",0);

        if(jsonToken.equals("") || !jsonTokenUtil.validateKey(jsonToken)){
            result.put("description","invalid json token");
            return new ResponseEntity<JSONObject>(result, HttpStatus.OK);
        }

        if(inputCost == 0){
            result.put("description","please input a valid price");
        } else{
            Detail dt = detailService.get(id);
            if(dt == null){
                result.put("description","this detail does not exist");
            } else{
                dt.setInput_cost(inputCost);
                detailService.update(dt);
                Appointment ap = apmService.get(dt.getAppointment_id());
                ap.setTotal_cost(ap.getTotal_cost() + inputCost);
                apmService.update(ap);
                result.put("message",1);
            }
        }
        return new ResponseEntity<JSONObject>(result,HttpStatus.OK);
    }

    @RequestMapping(value = "/api/detail/addDescription", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> addDescription(@RequestBody JSONObject input){
        System.out.println("adding a price cost to detail - Detail API");
        String jsonToken = input.get("json_token").toString();
        int id = (int) input.get("id");
        String description = input.get("description").toString();

        JSONObject result = new JSONObject();

        result.put("message",0);

        if(jsonToken.equals("") || !jsonTokenUtil.validateKey(jsonToken)){
            result.put("description","invalid json token");
            return new ResponseEntity<JSONObject>(result, HttpStatus.OK);
        }

        if(id == 0){
            result.put("description","please input a valid detail id");
        } else if(description == null && description.equals("")){
            result.put("description","please input valid description");
        } else{
            Detail dt = detailService.get(id);
            dt.setDescription(description);
            detailService.update(dt);
            result.put("message",1);
        }
        return new ResponseEntity<JSONObject>(result,HttpStatus.OK);
    }

    @RequestMapping(value = "/api/getVehicles", method = RequestMethod.GET)
    public ResponseEntity<JSONArray> getVehicles(){
        List<Vehicle> total = vehicleService.getAll();
        JSONArray vehicles = new JSONArray();
        for(Vehicle temp : total){
            JSONObject obj = new JSONObject();
            obj.put("id",temp.getId());
            obj.put("name",temp.getName());
            vehicles.add(obj);
        }
        return new ResponseEntity<JSONArray>(vehicles,HttpStatus.OK);
    }

    private void calculate(int detail_id,List<Coordinate> coords){
        Detail dt = detailService.get(detail_id);
        // total_length in km
        double total_length = cd.getTotalDistance(coords);
        System.out.println("Total length is " + total_length);
        // time in seconds
        long total_time = commonUtils.getSeconds(dt.getStart_time(),dt.getEnd_time());
        System.out.println("Total time is " + total_time);

        // estimate cost
        double estimate_cost = cm.getEstimateCost(vehicleService.get(dt.getVehicle_id()).getCalculate_formula(),total_length,total_time);
        System.out.println("Estimate cost is "+estimate_cost);

        dt.setTotal_length(total_length);
        dt.setEstimate_cost(estimate_cost);
        // velocity km/h
        double avgVelocity = cd.getAvarageVelocity(coords);
        System.out.println("Average velocity is "+avgVelocity);
        dt.setAverage_velocity(avgVelocity);

        String predictedVehicle = predictVehicle(coords,avgVelocity);
        dt.setPredicted_vehicle(predictedVehicle);
        System.out.println("Predicted vehicle is "+predictedVehicle);

        if(estimate_cost * 1.5 <= dt.getInput_cost()){
            dt.setWarning(true);

            /*Appointment apm = apmService.get(dt.getAppointment_id());
            apm.setStatus(-1);
            apmService.update(apm);*/
        }
        detailService.update(dt);
    }

    private String checkSpecialPlace(Coordinate coord){
        List<SpecialPlace> total = spService.getAll();
        for(int i = 0; i < total.size(); i++){
            SpecialPlace temp = total.get(i);
            double range = temp.getRange()/1000; //(m to km)
            Coordinate place = new Coordinate();
            place.setLatitude(temp.getLatitude());
            place.setLatitude(temp.getLongitude());
            double distance = cd.getDistance(coord,place);
            if(distance <= range){
                return temp.getType();
            }
        }
        return "no";
    }

    public String predictVehicle(List<Coordinate> coords, double averageVelocity){
        Coordinate start_coord = coords.get(0);
        String specialPlace = checkSpecialPlace(start_coord);
        if(specialPlace.equals("no")){
            if(averageVelocity <= 10){
                return "On foot";
            } else{
                return "Road vehicles";
            }
        } else if(specialPlace.equals("airport")){
            return "Aerial Vehicles";
        } else if(specialPlace.equals("train station")){
            return "Train";
        } else if(specialPlace.equals("dock harbor")){
            return "Maritine Vehicles";
        }
        return "";
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}


/*@RequestMapping(value = "/api/detail/addEndLocation", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> addEndLocation(@RequestBody JSONObject input){
        System.out.println("adding an end location to detail - Detail API");

        if(detail.getJson_token().equals("") || !jsonTokenUtil.validateKey(detail.getJson_token())){
            return new ResponseEntity<Message>(new Message("invalid json token"), HttpStatus.BAD_REQUEST);
        }
        Message msg = new Message("");
        if(detail.getId() == 0){
            msg.setMessage("please input a valid detail id");
        } else if(detail.getEnd_location() == null && detail.getEnd_location().equals("")){
            msg.setMessage("please input valid end location");
        } else{
            Detail dt = detailService.get(detail.getId());
            dt.setEnd_location(detail.getEnd_location());
            msg.setMessage("success");
        }
        return new ResponseEntity<Message>(msg,HttpStatus.OK);
    }*/