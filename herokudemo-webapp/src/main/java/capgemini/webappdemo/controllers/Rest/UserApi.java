package capgemini.webappdemo.controllers.Rest;

import capgemini.webappdemo.domain.*;
import capgemini.webappdemo.form.AppointmentForm;
import capgemini.webappdemo.service.Appointment.AppointmentService;
import capgemini.webappdemo.service.User.UserService;
import capgemini.webappdemo.service.Vehicle.VehicleService;
import capgemini.webappdemo.utils.CalculateDistance;
import capgemini.webappdemo.utils.CommonUtils;
import capgemini.webappdemo.utils.JsonTokenUtil;
import capgemini.webappdemo.utils.TokenPayload;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;


/**
 * Created by Vu Anh Nguyen Duc on 10/10/2017.
 */

@Controller
public class UserApi {
    @Autowired
    private UserService userService;

    @Autowired
    private AppointmentService apmService;

    @Autowired
    private VehicleService vehicleService;

    private static final Logger logger = LoggerFactory.getLogger(UserApi.class);

    private JsonTokenUtil jsonTokenUtil = new JsonTokenUtil();

    private CommonUtils commonUtils = new CommonUtils();

    @RequestMapping(value = "/api/changepass", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> changePassword(@RequestBody JSONObject input){
        System.out.println("changing password - User API");
        String oldpass = input.get("old_pass").toString();
        String newpass = input.get("new_pass").toString();
        String jsonkey = input.get("json_token").toString();

        JSONObject result = new JSONObject();

        if(jsonkey.equals("") || !jsonTokenUtil.validateKey(jsonkey)){
            result.put("message",0);
            result.put("description","invalid json key");
        } else{
            String decoded = jsonTokenUtil.getPayloadFromKey(jsonkey);
            TokenPayload token = jsonTokenUtil.parsePayload(decoded);
            if(token.getUser_id() != 0){
                int id = token.getUser_id();
                String pwd = oldpass;
                User usr = userService.get(id);
                if(!usr.getPassword().equals(pwd)){
                    result.put("message",0);
                    result.put("description","old password does not match");
                } else{
                    String userType = userService.getUserType(usr.getId());
                    TokenPayload new_token = new TokenPayload(usr.getId(),userType,newpass);
                    String payload = jsonTokenUtil.createPayload(new_token);
                    String jsonKey = jsonTokenUtil.createJWT(payload);

                    userService.changePassword(id,newpass);
                    result.put("message",1);
                    result.put("new_json_token",jsonKey);

                }
            }
        }
        return new ResponseEntity<JSONObject>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> loginApi(@RequestBody JSONObject input){
        System.out.println("user login in - User API");
        String username = (input.get("username")!=null)? input.get("username").toString() : null;
        String password = (input.get("password")!=null)? input.get("password").toString() :null;

        JSONObject result = new JSONObject();

        if(username != null && password != null){
            User usr = userService.checkLogin(username,password);
            if(usr != null){
                String userType = userService.getUserType(usr.getId());
                TokenPayload token = new TokenPayload(usr.getId(),userType,password);
                String payload = jsonTokenUtil.createPayload(token);
                String jsonKey = jsonTokenUtil.createJWT(payload);
                result.put("message",1);
                result.put("id",usr.getId());
                result.put("type",userType);
                result.put("json_token",jsonKey);
            } else{
                result.put("message",0);
                result.put("description","incorrect username or password");
            }
        } else{
            String jsonToken = input.get("json_token").toString();
            if(jsonToken == null){
                result.put("message",0);
            }
            if(!jsonTokenUtil.validateKey(jsonToken)){
                result.put("message",0);
                result.put("description","this is a valid json key, please try to login again with username and password");
            } else{
                TokenPayload token = jsonTokenUtil.parsePayload(jsonTokenUtil.getPayloadFromKey(jsonToken));
                int id = token.getUser_id();
                User usr = userService.get(id);
                result.put("message",1);
                result.put("id",usr.getId());
                result.put("type",userService.getUserType(usr.getId()));
            }
        }
        return new ResponseEntity<JSONObject>(result,HttpStatus.OK);
    }

    @RequestMapping(value = "/api/getActiveAppointments", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> getActiveAps(@RequestBody JSONObject input){
        System.out.println("getting active appointments - User API");
        String jsonToken = input.get("json_token").toString();
        JSONObject result = new JSONObject();

        if(jsonToken.equals("") || !jsonTokenUtil.validateKey(jsonToken)){
            result.put("message",0);
            result.put("description","invalid json key");
            return new ResponseEntity<JSONObject>(result,HttpStatus.OK);
        }
        int id = jsonTokenUtil.getUserIdFromJsonKey(jsonToken);
        List<UserAppointmentView> uavs = userService.getActiveAppointments(id);
        JSONArray uavList = new JSONArray();
        for(UserAppointmentView uav : uavs){
            JSONObject obj = new JSONObject();
            obj.put("id",uav.getAppointment_id());
            obj.put("destination",uav.getDestination());
            obj.put("start_date",commonUtils.convertDateToString(uav.getStart_date()));
            uavList.add(obj);
        }
        result.put("message",1);
        result.put("listActiveAppointments",uavList);
        return new ResponseEntity<JSONObject>(result,HttpStatus.OK);
    }

    @RequestMapping(value = "/api/getCompletedAppointments", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> getAllAps(@RequestBody JSONObject input){
        System.out.println("getting all appointments - User API");
        String jsonToken = input.get("json_token").toString();
        JSONObject result = new JSONObject();
        if(jsonToken.equals("") || !jsonTokenUtil.validateKey(jsonToken)){
            result.put("message",0);
            result.put("description","invalid json token");
            return new ResponseEntity<JSONObject>(result,HttpStatus.OK);
        }
        int id = jsonTokenUtil.getUserIdFromJsonKey(jsonToken);
        List<UserAppointmentView> uavs = userService.getCompletedAppointments(id);
        JSONArray uavList = new JSONArray();
        for(UserAppointmentView uav : uavs){
            JSONObject obj = new JSONObject();
            obj.put("id",uav.getAppointment_id());
            obj.put("destination",uav.getDestination());
            obj.put("start_date",commonUtils.convertDateToString(uav.getStart_date()));
            obj.put("end_date",commonUtils.convertDateToString(uav.getEnd_date()));
            obj.put("total_cost",100);
            uavList.add(obj);
        }
        result.put("message",1);
        result.put("completedAppoiments",uavList);
        return new ResponseEntity<JSONObject>(result,HttpStatus.OK);
    }

    @RequestMapping(value = "/api/getAppointment", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> getAppointment(@RequestBody JSONObject input){
        String jsonToken = input.get("json_token").toString();
        int id = (int) input.get("appointment_id");
        JSONObject result = new JSONObject();

        if(jsonToken.equals("") || !jsonTokenUtil.validateKey(jsonToken)){
            result.put("message",0);
            result.put("description","invalid json key");
            return new ResponseEntity<JSONObject>(result,HttpStatus.OK);
        } else{
            UserAppointmentView uav = userService.getAppointment(id);
            JSONObject obj = new JSONObject();
            obj.put("id",uav.getAppointment_id());
            obj.put("status",uav.getStatus());
            obj.put("destination",uav.getDestination());
            obj.put("start_date",commonUtils.convertDateToString(uav.getStart_date()));
            if(uav.getStatus() == 0){
                Appointment ap = apmService.get(uav.getAppointment_id());
                List<Detail> dts = ap.getDetails();
                CalculateDistance cd = new CalculateDistance();
                double length = 0;
                JSONArray vehicles = new JSONArray();
                for(Detail dt:dts){
                    length+= cd.getTotalDistance(dt.getCoordinates());
                    JSONObject temp = new JSONObject();
                    temp.put("name",vehicleService.get(dt.getVehicle_id()));
                    vehicles.add(temp);
                }
                obj.put("distance",length);
                obj.put("vehicles",vehicles);
            }
            //result.put("end_date",commonUtils.convertDateToString())
            result.put("message",1);
            result.put("appointment",obj);
            return new ResponseEntity<JSONObject>(result,HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/api/updateAppointment", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> updateAppointment(@RequestBody AppointmentForm input) throws ParseException {
        JSONObject result = new JSONObject();
        String jsonToken = input.getJson_token();
        String name = input.getName();
        String destination = input.getDestination();
        String startDate = input.getStart_date();
        int status = input.getStatus();
        List<User> users = input.getUsers();

        if(jsonToken.equals("") || !jsonTokenUtil.validateKey(jsonToken)){
            result.put("message",0);
            result.put("description","invalid json token");
            return new ResponseEntity<JSONObject>(result,HttpStatus.OK);
        } else{
            result.put("message",0);
            int id = jsonTokenUtil.getUserIdFromJsonKey(input.getJson_token());
            if(!userService.getUserType(id).equals("Manager")){
                result.put("description","This user does not have enough priviledge to use this funciton");
            } else{
                Appointment apm = apmService.getApmByName(name);
                if(apm == null){
                    result.put("description","this appointment does not exist");
                } else{
                    apm.setDestination(destination);
                    apm.setStart_date(commonUtils.convertStringToDate(startDate));
                    apm.setStatus(status);
                    List<User> usrs = apmService.getUsersOfAppointment(apm.getId());
                    if(usrs.equals(users)){
                        apmService.updateAppointment(apm,false);
                    } else{
                        apmService.updateAppointment(apm,true);
                    }
                    result.put("message",1);
                }
            }
            return new ResponseEntity<JSONObject>(result,HttpStatus.OK);
        }
    }

}
