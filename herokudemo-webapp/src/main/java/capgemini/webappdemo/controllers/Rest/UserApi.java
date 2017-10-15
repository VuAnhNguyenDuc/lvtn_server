package capgemini.webappdemo.controllers.Rest;

import capgemini.webappdemo.domain.*;
import capgemini.webappdemo.form.ChangePasswordForm;
import capgemini.webappdemo.service.Appointment.AppointmentService;
import capgemini.webappdemo.service.User.UserService;
import capgemini.webappdemo.utils.JsonTokenUtil;
import capgemini.webappdemo.utils.TokenPayload;
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
    public ResponseEntity<Message> changePassword(@RequestBody ChangePasswordForm user, Errors errors){
        System.out.println("changing password - User API");
        if(errors.hasErrors()){
            for(ObjectError err:errors.getAllErrors()){
                System.out.println(err.toString());
            }
        }
        if(user.getJson_token().equals("") || !jsonTokenUtil.validateKey(user.getJson_token())){
            return new ResponseEntity<Message>(new Message("invalid json token key"), HttpStatus.BAD_REQUEST);
        } else{
            String decoded = jsonTokenUtil.getPayloadFromKey(user.getJson_token());
            TokenPayload token = jsonTokenUtil.parsePayload(decoded);
            if(token.getUser_id() != 0){
                int id = token.getUser_id();
                String pwd = user.getOld_pass();
                User usr = userService.get(id);
                if(!usr.getPassword().equals(pwd)){
                    return new ResponseEntity<Message>(new Message("old password does not match"),HttpStatus.OK);
                } else{
                    String userType = userService.getUserType(usr.getId());
                    TokenPayload new_token = new TokenPayload(usr.getId(),userType,user.getNew_pass());
                    String payload = jsonTokenUtil.createPayload(new_token);
                    String jsonKey = jsonTokenUtil.createJWT(payload);

                    userService.changePassword(id,user.getNew_pass());
                    return new ResponseEntity<Message>(new Message("success",jsonKey), HttpStatus.OK);
                }
            } else{
                return new ResponseEntity<Message>(new Message("invalid json token key"), HttpStatus.NO_CONTENT);
            }
        }
    }

    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    public ResponseEntity<Message> loginApi(@RequestBody User user){
        System.out.println("user login in - User API");
        if(user.getUsername() != null && user.getPassword() != null){
            User result = userService.checkLogin(user.getUsername(),user.getPassword());
            if(result != null){
                String userType = userService.getUserType(result.getId());
                TokenPayload token = new TokenPayload(result.getId(),userType,user.getPassword());
                String payload = jsonTokenUtil.createPayload(token);
                String jsonKey = jsonTokenUtil.createJWT(payload);
                return new ResponseEntity<Message>(new Message("success",jsonKey,userService.getUserType(result.getId()),result.getId()), HttpStatus.OK);
            } else{
                return new ResponseEntity<Message>(new Message("failed", ""), HttpStatus.OK);
            }
        } else if(user.getJson_token() != null){
            if(!jsonTokenUtil.validateKey(user.getJson_token())){
                return new ResponseEntity<Message>(new Message("This is not a correct json key or your json key is outdated, please login again to receive the new key"),HttpStatus.BAD_REQUEST);
            } else{
                TokenPayload token = jsonTokenUtil.parsePayload(jsonTokenUtil.getPayloadFromKey(user.getJson_token()));
                int id = token.getUser_id();
                User usr = userService.get(id);
                return new ResponseEntity<Message>(new Message("success",user.getJson_token(),userService.getUserType(usr.getId()),usr.getId()), HttpStatus.OK);
            }
        }
        return new ResponseEntity<Message>(new Message("Username and Password cannot be empty!!", ""), HttpStatus.NO_CONTENT);
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
