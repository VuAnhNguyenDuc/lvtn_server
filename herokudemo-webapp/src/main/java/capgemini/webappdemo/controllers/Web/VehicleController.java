package capgemini.webappdemo.controllers.Web;

import capgemini.webappdemo.domain.Vehicle;
import capgemini.webappdemo.service.Vehicle.VehicleService;
import capgemini.webappdemo.utils.LoginUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
        model.addAttribute("pageName","vehicle");
        return "web/vehicle/vehicle";
    }

    @RequestMapping(value = "/vehicle/insert", method = RequestMethod.GET)
    public String insertVehicleGet(HttpSession session,ModelMap model){
        if(!loginUtil.isLogin(session)){
            return "redirect:/login";
        } else{
            model.addAttribute("vehicle", new Vehicle());
            model.addAttribute("pageName","vehicle");
            model.addAttribute("pageName","vehicle");
            return "web/vehicle/vehicle_insert";
        }
    }

    @RequestMapping(value = "/vehicle/insert", method = RequestMethod.POST)
    public String insertVehiclePost(@ModelAttribute("vehicle") @Valid Vehicle vehicle, BindingResult result, ModelMap model){
        if(result.hasErrors()){
            return "web/vehicle/vehicle_insert";
        } else{
            if(service.checkExist(vehicle.getName(),0)){
                model.addAttribute("error","This vehicle existed in the database");
                return "web/vehicle/vehicle_insert";
            }
            service.add(vehicle);
            return "redirect:/vehicles";
        }
    }

    @RequestMapping(value = "/vehicle/update", method = RequestMethod.GET, params = "id")
    public String updateVehicleGet(@RequestParam("id") int id, HttpSession session, ModelMap model){
        if(!loginUtil.isLogin(session)){
            return "redirect:/login";
        } else{
            Vehicle vehicle = service.get(id);
            model.addAttribute("vehicle",vehicle);
            model.addAttribute("pageName","vehicle");
            return "web/vehicle/vehicle_update";
        }
    }

    @RequestMapping(value = "/vehicle/update", method = RequestMethod.POST, params = "id")
    public String updateVehiclePost(@ModelAttribute("vehicle") @Valid Vehicle vehicle,BindingResult result,@RequestParam("id") int id, ModelMap model){
        if(result.hasErrors()){
            return "web/vehicle/vehicle_update";
        } else{
            if(service.checkExist(vehicle.getName(),id)){
                model.addAttribute("error","This name is already taken by another vehicle");
                return "web/vehicle/vehicle_update";
            } else{
                Vehicle old = service.get(id);
                old.setStatus(vehicle.getStatus());
                old.setWarning_rate(vehicle.getWarning_rate());
                service.update(old);
                return "redirect:/vehicles";
            }
        }
    }

    @RequestMapping(value = "vehicle/updatePrice", method = RequestMethod.GET, params = "id")
    public String updatePriceGet(@RequestParam("id") int id, HttpSession session, ModelMap model) throws ParseException {
        if(!loginUtil.isLogin(session)){
            return "redirect:/login";
        } else{
            Vehicle vehicle = service.get(id);
            JSONArray formulas = new JSONArray();
            JSONArray vars = new JSONArray();

            String vehicle_formula = vehicle.getCalculate_formula();

            if(vehicle_formula != null && !vehicle_formula.equals("")){
                JSONParser parser = new JSONParser();
                JSONObject obj = (JSONObject) parser.parse(vehicle.getCalculate_formula());
                formulas = (JSONArray) obj.get("formulas");
                vars = (JSONArray) obj.get("vars");
            }
            model.addAttribute("id",id);
            model.addAttribute("formulas", formulas);
            model.addAttribute("variables", vars);
            model.addAttribute("pageName","vehicle");
            model.addAttribute("vehicle_name",vehicle.getName());
            return "web/vehicle/vehicle_price";
        }
    }
}
