package capgemini.webappdemo.controllers.Rest;

import capgemini.webappdemo.domain.*;
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

    private static final Logger logger = LoggerFactory.getLogger(UserApi.class);

    private JsonTokenUtil jsonTokenUtil = new JsonTokenUtil();

    @RequestMapping(value = "/api/changepass", method = RequestMethod.POST)
    public ResponseEntity<Message> changePassword(@RequestBody User user, Errors errors){
        logger.info("changing password - User API");
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
                String pwd = user.getPassword();
                return new ResponseEntity<Message>(new Message(userService.changePassword(id,pwd),""), HttpStatus.OK);
            } else{
                return new ResponseEntity<Message>(new Message("invalid json token key"), HttpStatus.NO_CONTENT);
            }
        }
    }

    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    public ResponseEntity<Message> loginApi(@RequestBody User user, Errors errors, Model model){
        logger.info("user login in - User API");
        if(user.getUsername() != null && user.getPassword() != null){
            User result = userService.checkLogin(user.getUsername(),user.getPassword());
            if(result != null){
                String userType = userService.getUserType(result.getId());
                TokenPayload token = new TokenPayload(result.getId(),userType);
                String payload = jsonTokenUtil.createPayload(token);
                String jsonKey = jsonTokenUtil.createJWT(payload);
                return new ResponseEntity<Message>(new Message("success,"+userService.getUserType(result.getId())+","+result.getId(), jsonKey), HttpStatus.OK);
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
                return new ResponseEntity<Message>(new Message("success,"+userService.getUserType(usr.getId())+","+usr.getId(), ""), HttpStatus.OK);
            }
        }
        return new ResponseEntity<Message>(new Message("Username and Password cannot be empty!!", ""), HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/api/getActiveAppointments", method = RequestMethod.POST)
    public ResponseEntity<List<UserAppointmentView>> getActiveAps(@RequestBody Message msg,Errors errors){
        logger.info("getting active appointments - User API");
        if(msg.getJson_token().equals("") || !jsonTokenUtil.validateKey(msg.getJson_token())){
            return new ResponseEntity<List<UserAppointmentView>>(HttpStatus.BAD_REQUEST);
        }
        int id = jsonTokenUtil.getUserIdFromJsonKey(msg.getJson_token());
        List<UserAppointmentView> result = userService.getActiveAppointments(id);
        if(result != null && result.size() > 0){
            return new ResponseEntity<List<UserAppointmentView>>(userService.getActiveAppointments(id),HttpStatus.OK);
        } else{
            return new ResponseEntity<List<UserAppointmentView>>(new ArrayList<UserAppointmentView>(),HttpStatus.NO_CONTENT);
        }
    }

    @RequestMapping(value = "/api/getAllAppointments", method = RequestMethod.POST)
    public ResponseEntity<List<UserAppointmentView>> getAllAps(@RequestBody Message msg){
        logger.info("getting all appointments - User API");
        if(msg.getJson_token().equals("") || !jsonTokenUtil.validateKey(msg.getJson_token())){
            return new ResponseEntity<List<UserAppointmentView>>(HttpStatus.BAD_REQUEST);
        }
        int id = jsonTokenUtil.getUserIdFromJsonKey(msg.getJson_token());
        List<UserAppointmentView> result = userService.getAllAppointments(id);
        if(result != null && result.size() > 0){
            return new ResponseEntity<List<UserAppointmentView>>(userService.getAllAppointments(id),HttpStatus.OK);
        } else{
            return new ResponseEntity<List<UserAppointmentView>>(HttpStatus.NO_CONTENT);
        }
    }

    @RequestMapping(value = "/api/getAppointment", method = RequestMethod.POST)
    public ResponseEntity<UserAppointmentView> getAppointment(@RequestBody Message msg,Errors errors){
        if(msg.getJson_token().equals("") || !jsonTokenUtil.validateKey(msg.getJson_token())){
            return new ResponseEntity<UserAppointmentView>(HttpStatus.BAD_REQUEST);
        } else{
            int id = jsonTokenUtil.getUserIdFromJsonKey(msg.getJson_token());
            return new ResponseEntity<UserAppointmentView>(userService.getAppointment(id),HttpStatus.OK);
        }
    }

}
