package capgemini.webappdemo.controllers.Ajax;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VehicleAjax {
    @RequestMapping(value = "/ajax/vehicle/price", method = RequestMethod.GET, params = "input")
    public String updatePrice(@RequestParam("input")JSONObject input){
        int id = (int) input.get("id");
        return "hello vehicle " + id;
    }
}
