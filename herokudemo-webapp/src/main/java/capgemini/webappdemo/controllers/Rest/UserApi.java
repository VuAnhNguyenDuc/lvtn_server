package capgemini.webappdemo.controllers.Rest;

import capgemini.webappdemo.domain.Message;
import capgemini.webappdemo.domain.User;
import capgemini.webappdemo.service.User.UserService;
import capgemini.webappdemo.utils.JsonTokenUtil;
import capgemini.webappdemo.utils.TokenPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Vu Anh Nguyen Duc on 10/10/2017.
 */

@Controller
public class UserApi {
    @Autowired
    private UserService userService;

    private JsonTokenUtil jsonTokenUtil = new JsonTokenUtil();

    @RequestMapping(value = "/api/login", method = RequestMethod.GET)
    public ResponseEntity<Message> hello(){
        return new ResponseEntity<Message>(new Message("hello","123456"), HttpStatus.OK);
    }

    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    public ResponseEntity<String> loginApi(@RequestBody User user, Errors errors, Model model){
        /*if(user.getUsername() != null && user.getPassword() != null){

            User result = userService.checkLogin(user.getUsername(),user.getPassword());
            if(result != null){
                TokenPayload token = new TokenPayload(result.getId(),"manager");
                String payload = jsonTokenUtil.createPayload(token);
                String jsonKey = jsonTokenUtil.createJWT(payload);
                return new ResponseEntity<String>("Login successful", HttpStatus.OK);
                *//*return new ResponseEntity<Message>(new Message("Login successful", jsonKey), HttpStatus.OK);*//*
            } else{
                return new ResponseEntity<String>("Incorrect username or password", HttpStatus.OK);
                *//*return new ResponseEntity<Message>(new Message("Incorrect username or password", ""), HttpStatus.OK);*//*
            }
        }
        *//*return new ResponseEntity<Message>(new Message("Username and Password cannot be empty!!", ""), HttpStatus.NO_CONTENT);*//*
        return new ResponseEntity<String>("username and password cannot be empty", HttpStatus.NO_CONTENT);*/
        String username = user.getUsername();
        String password = user.getPassword();
        return new ResponseEntity<String>(username + password, HttpStatus.OK);

    }
}
