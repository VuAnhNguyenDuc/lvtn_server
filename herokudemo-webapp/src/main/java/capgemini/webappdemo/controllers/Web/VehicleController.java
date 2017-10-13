package capgemini.webappdemo.controllers.Web;

import capgemini.webappdemo.domain.Vehicle;
import capgemini.webappdemo.service.Vehicle.VehicleService;
import capgemini.webappdemo.utils.LoginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class VehicleController {
    @Autowired
    private VehicleService service;

    private LoginUtil loginUtil = new LoginUtil();

    @RequestMapping(value = "/vehicles", method = RequestMethod.GET)
    public String getVehicles(HttpSession session,ModelMap model){
        if(!loginUtil.isLogin(session)){
            return "redirect:/login";
        }
        List<Vehicle> vehicles = service.getAll();
        if(vehicles != null){
            model.addAttribute("vehicles",vehicles);
        } else{
            model.addAttribute("vehicles", new ArrayList<>());
        }
        return "web/vehicle/vehicle";
    }

    @RequestMapping(value = "/vehicle/insert", method = RequestMethod.GET)
    public String insertVehicleGet(HttpSession session,ModelMap model){
        if(!loginUtil.isLogin(session)){
            return "redirect:/login";
        } else{
            model.addAttribute("vehicle", new Vehicle());
            return "web/vehicle/insertVehicle";
        }
    }

    @RequestMapping(value = "/vehicle/insert", method = RequestMethod.POST)
    public String insertVehiclePost(@ModelAttribute("vehicle") @Valid  Vehicle vehicle, BindingResult result, ModelMap model){
        if(result.hasErrors()){
            return "web/vehicle/insertVehicle";
        } else{
            if(service.checkExist(vehicle.getName())){
                model.addAttribute("error","This vehicle existed in the database");
                return "web/vehicle/insertVehicle";
            }
            service.add(vehicle);
            return "redirect:/vehicles";
        }
    }
}
