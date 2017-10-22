package capgemini.webappdemo.controllers.Rest;

import capgemini.webappdemo.domain.Appointment;
import capgemini.webappdemo.domain.Coordinate;
import capgemini.webappdemo.domain.Detail;
import capgemini.webappdemo.domain.Vehicle;
import capgemini.webappdemo.service.Appointment.AppointmentService;
import capgemini.webappdemo.service.Detail.DetailService;
import capgemini.webappdemo.service.Vehicle.VehicleService;
import capgemini.webappdemo.utils.CalculateDistance;
import capgemini.webappdemo.utils.CalculateMoney;
import capgemini.webappdemo.utils.CommonUtils;
import capgemini.webappdemo.utils.JsonTokenUtil;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
public class DetailApi {
    @Autowired
    private DetailService detailService;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private AppointmentService apmService;

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
            detail.setStart_time(commonUtils.convertStringToDate(startTime));
            //detail.setDescription(description);
            detail.setStart_location(startLocation);
            detail.setUser_created(id);
            detail.setWarning(false);
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
            result.put("description","please input the start time on start_date_string column");
        } else{
            Detail dt = detailService.get(id);
            if(dt == null){
                result.put("description","this detail does not exist");
            } else{
                try {
                    dt.setStart_time(commonUtils.convertStringToDate(startTime));
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
    public ResponseEntity<JSONObject> endDetail(@RequestBody JSONObject input){
        System.out.println("ending a detail - Detail API");
        String jsonToken = input.get("json_token").toString();
        int id = (int) input.get("id");
        String endTime = input.get("end_time").toString();
        String endLocation = input.get("end_location").toString();
        JSONObject result = new JSONObject();

        if(jsonToken.equals("") || !jsonTokenUtil.validateKey(jsonToken)){
            result.put("message",0);
            result.put("description","invalid json token");
            return new ResponseEntity<JSONObject>(result, HttpStatus.OK);
        }
        result.put("message",0);

        if(endTime.isEmpty()){
            result.put("description","please input end time");
        } else if(endLocation.isEmpty()){
            result.put("description","please input end location");
        } else{
            Detail dt = detailService.get(id);
            if(dt == null){
                result.put("description","this detail does not exist");
            } else{
                try {
                    dt.setEnd_time(commonUtils.convertStringToDate(endTime));
                    detailService.update(dt);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(dt.getCoordinates() == null || dt.getInput_cost() == 0){
                    result.put("description","please input the coordinates and cost of this detail before end it");
                } else{
                    calculate(id,dt.getCoordinates());
                    result.put("message",1);
                }
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
            result.put("message",1);
        }
        return new ResponseEntity<JSONObject>(result,HttpStatus.OK);
    }

    @RequestMapping(value = "/api/getVehicles", method = RequestMethod.GET)
    public ResponseEntity<List<Vehicle>> getVehicles(){
        return new ResponseEntity<List<Vehicle>>(vehicleService.getAll(),HttpStatus.OK);
    }

    private void calculate(int detail_id,List<Coordinate> coords){
        Detail dt = detailService.get(detail_id);
        // total_length in km
        double total_length = cd.getTotalDistance(coords);
        // time in seconds
        long total_time = commonUtils.getSeconds(dt.getStart_time(),dt.getEnd_time());
        // estimate cost
        double estimate_cost = cm.getEstimateCost(vehicleService.get(dt.getVehicle_id()).getName(),total_length,total_time);
        dt.setTotal_length(total_length);
        dt.setEstimate_cost(estimate_cost);
        // velocity km/h
        dt.setAverage_velocity((total_length*3600)/total_time);
        if(estimate_cost * 1.5 <= dt.getInput_cost()){
            dt.setWarning(true);
            Appointment apm = apmService.get(dt.getAppointment_id());
            apm.setStatus(-1);
            apmService.update(apm);
        }
        detailService.update(dt);
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