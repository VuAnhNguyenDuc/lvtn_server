package capgemini.webappdemo.controllers.Rest;

import capgemini.webappdemo.domain.*;
import capgemini.webappdemo.form.ChangePasswordForm;
import capgemini.webappdemo.service.Appointment.AppointmentService;
import capgemini.webappdemo.service.User.UserService;
import capgemini.webappdemo.utils.JsonTokenUtil;
import capgemini.webappdemo.utils.TokenPayload;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

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

    private static final Logger logger = LoggerFactory.getLogger(UserApi.class);

    private JsonTokenUtil jsonTokenUtil = new JsonTokenUtil();

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
            result.put("message",0);
            result.put("description","invalid json key");
        }
        return new ResponseEntity<JSONObject>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> loginApi(@RequestBody JSONObject input){
        System.out.println("user login in - User API");
        String username = input.get("username").toString();
        String password = input.get("password").toString();

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
    public ResponseEntity<List<UserAppointmentView>> getActiveAps(@RequestBody User usr){
        System.out.println("getting active appointments - User API");
        System.out.println(usr.getJson_token());
        if(usr.getJson_token().equals("") || !jsonTokenUtil.validateKey(usr.getJson_token())){
            return new ResponseEntity<List<UserAppointmentView>>(HttpStatus.BAD_REQUEST);
        }
        int id = jsonTokenUtil.getUserIdFromJsonKey(usr.getJson_token());
        List<UserAppointmentView> result = userService.getActiveAppointments(id);
        if(result != null && result.size() > 0){
            return new ResponseEntity<List<UserAppointmentView>>(userService.getActiveAppointments(id),HttpStatus.OK);
        } else{
            return new ResponseEntity<List<UserAppointmentView>>(HttpStatus.NO_CONTENT);
        }
    }

    @RequestMapping(value = "/api/getAllAppointments", method = RequestMethod.POST)
    public ResponseEntity<List<UserAppointmentView>> getAllAps(@RequestBody User usr){
        System.out.println("getting all appointments - User API");
        if(usr.getJson_token().equals("") || !jsonTokenUtil.validateKey(usr.getJson_token())){
            return new ResponseEntity<List<UserAppointmentView>>(HttpStatus.BAD_REQUEST);
        }
        int id = jsonTokenUtil.getUserIdFromJsonKey(usr.getJson_token());
        List<UserAppointmentView> result = userService.getAllAppointments(id);
        if(result != null && result.size() > 0){
            return new ResponseEntity<List<UserAppointmentView>>(userService.getAllAppointments(id),HttpStatus.OK);
        } else{
            return new ResponseEntity<List<UserAppointmentView>>(HttpStatus.NO_CONTENT);
        }
    }

    @RequestMapping(value = "/api/getAppointment", method = RequestMethod.POST)
    public ResponseEntity<UserAppointmentView> getAppointment(@RequestBody Appointment apm){
        if(apm.getJson_token().equals("") || !jsonTokenUtil.validateKey(apm.getJson_token())){
            return new ResponseEntity<UserAppointmentView>(HttpStatus.BAD_REQUEST);
        } else{
            //int id = jsonTokenUtil.getUserIdFromJsonKey(usr.getJson_token());
            return new ResponseEntity<UserAppointmentView>(userService.getAppointment(apm.getId()),HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/api/updateAppointment", method = RequestMethod.POST)
    public ResponseEntity<Message> updateAppointment(@RequestBody Appointment apm){
        if(apm.getJson_token().equals("") || !jsonTokenUtil.validateKey(apm.getJson_token())){
            return new ResponseEntity<Message>(HttpStatus.BAD_REQUEST);
        } else{
            Message msg = new Message("");
            int id = jsonTokenUtil.getUserIdFromJsonKey(apm.getJson_token());
            if(!userService.getUserType(id).equals("Manager")){
                msg.setMessage("This user does not have enough priviledge to use this funciton");
            } else{
                Appointment curr = apmService.get(apm.getId());
                if(curr != null){
                    msg.setMessage("this appointment does not exist");
                } else{
                    List<User> usrs = apmService.getUsersOfAppointment(apm.getId());
                    if(usrs.equals(apm.getUsers())){
                        apmService.updateAppointment(apm,false);
                    } else{
                        apmService.updateAppointment(apm,true);
                    }
                    msg.setMessage("success");
                }
            }
            return new ResponseEntity<Message>(msg,HttpStatus.OK);
        }
    }

}
