package capgemini.webappdemo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import static capgemini.webappdemo.controllers.NavigationRegistry.URL_HELLO;

/**
 * Created by Vu Anh Nguyen Duc on 10/10/2017.
 */
@Controller
public class TestController {
    @RequestMapping(value = URL_HELLO, method = RequestMethod.GET)
    public ResponseEntity<String> helloWorld(){
        return new ResponseEntity<String>("Hello World", HttpStatus.OK);
    }
}
