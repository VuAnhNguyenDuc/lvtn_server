package capgemini.webappdemo.controllers.Web;

import capgemini.webappdemo.domain.*;
import capgemini.webappdemo.service.Appointment.AppointmentService;
import capgemini.webappdemo.service.Coordinate.CoordinateService;
import capgemini.webappdemo.service.Detail.DetailService;
import capgemini.webappdemo.service.User.UserService;
import capgemini.webappdemo.service.UserAppointmentView.UserAppointmentViewService;
import capgemini.webappdemo.service.Vehicle.VehicleService;
import capgemini.webappdemo.utils.CalculateDistance;
import capgemini.webappdemo.utils.CalculateMoney;
import capgemini.webappdemo.utils.CommonUtils;
import capgemini.webappdemo.utils.LoginUtil;
import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AppointmentController {
    @Autowired
    private UserAppointmentViewService service;

    @Autowired
    private AppointmentService appService;

    @Autowired
    private UserService userService;

    @Autowired
    private DetailService detailService;

    @Autowired
    private CoordinateService coorService;

    @Autowired
    private VehicleService vhService;

    private LoginUtil loginUtil = new LoginUtil();

    private CommonUtils commonUtils = new CommonUtils();

    private CalculateDistance cd = new CalculateDistance();

    private CalculateMoney cm = new CalculateMoney();

    @RequestMapping(value = "/appointments", method = RequestMethod.GET)
    public String getAppointments(HttpSession session,ModelMap model){
        if(!loginUtil.isLogin(session)){
            return "redirect:/login";
        } else{
            List<Appointment> apps = appService.getAll();
            for(Appointment app : apps){
                User mng = userService.get(app.getManager_id());
                app.setManager_name(mng.getFullname());
                app.setStart_date_str(commonUtils.convertDateToString(app.getStart_date()));
                if(app.getEnd_date() != null){
                    app.setEnd_date_str(commonUtils.convertDateToString(app.getEnd_date()));
                } else{
                    app.setEnd_date_str("");
                }
            }
            model.addAttribute("pageName","appointment");
            model.addAttribute("apms",apps);
            return "web/appointment/appointment";
        }
    }

    @RequestMapping(value = "/appointment/details", method = RequestMethod.GET, params = "appointment_id")
    public String getAppointmentDetails(HttpSession session, @RequestParam("appointment_id") int id, ModelMap model){
        if(!loginUtil.isLogin(session)){
            return "redirect:/login";
        } else{
            Appointment app = appService.get(id);
            List<User> users = appService.getUsersOfAppointment(id);
            List<Detail> details = detailService.getDetailsOfAppointment(id);
            for(Detail dt : details){
                //calculateDetail(dt);
                dt.setVehicle_name(vhService.get(dt.getVehicle_id()).getName());
                if(dt.getStart_time() != null){
                    dt.setStart_time_str(commonUtils.convertDateToString(dt.getStart_time()));
                }
                if(dt.getEnd_time() != null){
                    dt.setEnd_time_str(commonUtils.convertDateToString(dt.getEnd_time()));
                }
            }
            app.setUsers(users);
            app.setStart_date_str(commonUtils.convertDateToString(app.getStart_date()));
            if(app.getEnd_date() != null){
                app.setEnd_date_str(commonUtils.convertDateToString(app.getEnd_date()));
            }
            app.setDetails(details);
            model.addAttribute("pageName","appointment");
            model.addAttribute("apm",app);
            model.addAttribute("dts",details);
            model.addAttribute("mng",userService.get(app.getManager_id()).getFullname());
            return "web/appointment/apm_detail";
        }
    }

    // Show the road of an appointment
    @RequestMapping(value = "/appointment/viewMap", method = RequestMethod.GET, params = {"id"})
    public String getDetail(HttpSession session,  @RequestParam("id") int id,ModelMap model){
        if(!loginUtil.isLogin(session)){
            return "redirect:/login";
        } else{
            List<Detail> dts = detailService.getDetailsOfAppointment(id);
            List<Coordinate> total_coords = new ArrayList<>();
            if(dts.size() > 0){
                for(Detail dt : dts){
                    List<Coordinate> coords = coorService.getCoordsOfDetail(dt.getId());
                    total_coords.addAll(coords);
                }
            }
            model.addAttribute("coords",parseCoords(total_coords));
            model.addAttribute("pageName","appointment");
            return "web/appointment/apm_map";
        }
    }

    private JSONArray parseCoords(List<Coordinate> coords){
        JSONArray result = new JSONArray();
        for(Coordinate coord : coords){
            JSONObject obj = new JSONObject();
            obj.put("lat",coord.getLatitude());
            obj.put("lng",coord.getLongitude());
            result.add(obj);
        }
        return result;
    }

    /*private void calculateDetail(Detail dt){
        List<Coordinate> coords = coorService.getCoordsOfDetail(dt.getId());
        if(coords.size() > 0){
            double length = cd.getTotalDistance(coords);
            long time = commonUtils.getSeconds(dt.getStart_time(),dt.getEnd_time());
            double total = cm.getEstimateCost(dt.getVehicle_name(),length,time);
            dt.setTotal_length(length);
            dt.setEstimate_cost(total);
        } else{
            dt.setTotal_length(0);
            dt.setAverage_velocity(0);
        }
    }*/
}
