package capgemini.webappdemo.controllers.Rest;

import capgemini.webappdemo.domain.*;
import capgemini.webappdemo.form.AppointmentForm;
import capgemini.webappdemo.service.Appointment.AppointmentService;
import capgemini.webappdemo.service.Client.ClientService;
import capgemini.webappdemo.service.User.UserService;
import capgemini.webappdemo.service.Vehicle.VehicleService;
import capgemini.webappdemo.utils.CalculateDistance;
import capgemini.webappdemo.utils.CommonUtils;
import capgemini.webappdemo.utils.JsonTokenUtil;
import capgemini.webappdemo.utils.TokenPayload;
import org.eclipse.jetty.util.ajax.JSON;
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
import java.util.ArrayList;
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

    @Autowired
    private ClientService clService;

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
            obj.put("name",uav.getAppointment_name());
            obj.put("destination",uav.getDestination());
            obj.put("start_date",commonUtils.convertDateToString(uav.getStart_date()));

            int clientId = uav.getClient_id();
            Client cl = clService.get(clientId);
            JSONObject client = new JSONObject();
            client.put("id",cl.getId());
            client.put("name",cl.getName());
            client.put("phone_number",cl.getPhone_number());
            client.put("address",cl.getAddress());
            client.put("email",cl.getEmail());

            obj.put("client",client);
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
            obj.put("name",uav.getAppointment_name());
            obj.put("destination",uav.getDestination());
            obj.put("start_date",commonUtils.convertDateToString(uav.getStart_date()));
            obj.put("end_date",commonUtils.convertDateToString(uav.getEnd_date()));
            obj.put("total_cost",uav.getTotal_cost());

            int clientId = uav.getClient_id();
            Client cl = clService.get(clientId);
            JSONObject client = new JSONObject();
            client.put("id",cl.getId());
            client.put("name",cl.getName());
            client.put("phone_number",cl.getPhone_number());
            client.put("address",cl.getAddress());
            client.put("email",cl.getEmail());

            obj.put("client",client);
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
            if(uav == null){
                result.put("message",0);
                result.put("description","this appointment does not exist");
                return new ResponseEntity<JSONObject>(result,HttpStatus.OK);
            }
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
                obj.put("end_date",commonUtils.convertDateToString(uav.getEnd_date()));
                obj.put("distance",length);
                obj.put("vehicles",vehicles);
            }

            int clientId = uav.getClient_id();
            Client cl = clService.get(clientId);
            JSONObject client = new JSONObject();
            client.put("id",cl.getId());
            client.put("name",cl.getName());
            client.put("phone_number",cl.getPhone_number());
            client.put("address",cl.getAddress());
            client.put("email",cl.getEmail());
            obj.put("client",client);

            result.put("message",1);
            result.put("appointment",obj);
            return new ResponseEntity<JSONObject>(result,HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/api/updateAppointment", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> updateAppointment(@RequestBody JSONObject input) throws ParseException {
        JSONObject result = new JSONObject();
        String jsonToken = input.get("json_token").toString();
        String name = input.get("name").toString();
        String destination = input.get("destination").toString();
        String startDate = input.get("start_date").toString();
        int appointment_id = (int) input.get("appointment_id");
        int clientId = (int) input.get("client_id");
        String users = input.get("users").toString();

        if(jsonToken.equals("") || !jsonTokenUtil.validateKey(jsonToken)){
            result.put("message",0);
            result.put("description","invalid json token");
            return new ResponseEntity<JSONObject>(result,HttpStatus.OK);
        } else{
            result.put("message",0);
            int id = jsonTokenUtil.getUserIdFromJsonKey(jsonToken);
            if(!userService.getUserType(id).equals("Manager")){
                result.put("description","This user does not have enough priviledge to use this funciton");
            } else{
                Appointment apm = apmService.get(appointment_id);
                if(apm == null){
                    result.put("description","this appointment does not exist");
                } else if(apm.getManager_id() != id){
                  result.put("description","this manager did not created this appointment");
                } else{
                    apm.setName(name);
                    apm.setDestination(destination);
                    apm.setStart_date(commonUtils.convertStringToDate(startDate));
                    apm.setClient_id(clientId);
                    List<User> usrs = apmService.getUsersOfAppointment(apm.getId());
                    List<User> userArray = new ArrayList<>();
                    int[] temp = commonUtils.convertStringToArray(users);
                    for(int usr : temp){
                        User user = new User();
                        user.setId(usr);
                        userArray.add(user);
                    }

                    if(compareUsers(usrs,userArray)){
                        apmService.updateAppointment(apm,false);
                    } else{
                        apm.setUsers(userArray);
                        apmService.updateAppointment(apm,true);
                    }
                    result.put("message",1);
                }
            }
            return new ResponseEntity<JSONObject>(result,HttpStatus.OK);
        }
    }

    private boolean compareUsers(List<User> u1,List<User> u2){
        int s1 = u1.size();
        int s2 = u2.size();
        if(s1 != s2){
            return false;
        } else{
            for(int i = 0; i < s1; i++){
                if(u1.get(i).getId() != u2.get(i).getId()){
                    return false;
                }
            }
        }
        return true;
    }

}
