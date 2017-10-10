package capgemini.webappdemo.controllers.Rest;

import capgemini.webappdemo.domain.Appointment;
import capgemini.webappdemo.domain.Message;
import capgemini.webappdemo.domain.User;
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

    @RequestMapping(value = "/api/changepass/{userid}/{password}", method = RequestMethod.GET)
    public ResponseEntity<Message> changePassword(@PathVariable("userid") int id, @PathVariable("password") String pwd){
        logger.info("changing password - User API");
        return new ResponseEntity<Message>(new Message(userService.changePassword(id,pwd),""), HttpStatus.OK);
    }

    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    public ResponseEntity<Message> loginApi(@RequestBody User user, Errors errors, Model model){
        logger.info("user login in - User API");
        if(user.getUsername() != null && user.getPassword() != null){
            User result = userService.checkLogin(user.getUsername(),user.getPassword());
            String userType = userService.getUserType(result.getId());
            if(result != null){
                TokenPayload token = new TokenPayload(result.getId(),userType);
                String payload = jsonTokenUtil.createPayload(token);
                String jsonKey = jsonTokenUtil.createJWT(payload);
                return new ResponseEntity<Message>(new Message("success,"+userService.getUserType(result.getId())+","+result.getId(), jsonKey), HttpStatus.OK);
            } else{
                return new ResponseEntity<Message>(new Message("Incorrect username or password", ""), HttpStatus.OK);
            }
        }
        return new ResponseEntity<Message>(new Message("Username and Password cannot be empty!!", ""), HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/api/getActiveAppointments/{userid}", method = RequestMethod.GET)
    public ResponseEntity<List<Appointment>> getActiveAps(@PathVariable("userid") int id){
        logger.info("getting active appointments - User API");
        List<Appointment> result = userService.getActiveAppointments(id);
        if(result != null && result.size() > 0){
            return new ResponseEntity<List<Appointment>>(userService.getActiveAppointments(id),HttpStatus.OK);
        } else{
            return new ResponseEntity<List<Appointment>>(new ArrayList<Appointment>(),HttpStatus.NO_CONTENT);
        }
    }

    @RequestMapping(value = "/api/getAllAppointments/{userid}", method = RequestMethod.GET)
    public ResponseEntity<List<Appointment>> getAllAps(@PathVariable("userid") int id){
        logger.info("getting all appointments - User API");
        List<Appointment> result = userService.getAllAppointments(id);
        if(result != null && result.size() > 0){
            return new ResponseEntity<List<Appointment>>(userService.getAllAppointments(id),HttpStatus.OK);
        } else{
            return new ResponseEntity<List<Appointment>>(new ArrayList<Appointment>(),HttpStatus.NO_CONTENT);
        }
    }

}
