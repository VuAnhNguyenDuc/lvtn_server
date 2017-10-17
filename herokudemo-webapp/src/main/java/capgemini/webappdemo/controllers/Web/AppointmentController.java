package capgemini.webappdemo.controllers.Web;

import capgemini.webappdemo.domain.*;
import capgemini.webappdemo.service.Appointment.AppointmentService;
import capgemini.webappdemo.service.Coordinate.CoordinateService;
import capgemini.webappdemo.service.Detail.DetailService;
import capgemini.webappdemo.service.User.UserService;
import capgemini.webappdemo.service.UserAppointmentView.UserAppointmentViewService;
import capgemini.webappdemo.utils.CalculateDistance;
import capgemini.webappdemo.utils.CalculateMoney;
import capgemini.webappdemo.utils.CommonUtils;
import capgemini.webappdemo.utils.LoginUtil;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
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

    private LoginUtil loginUtil = new LoginUtil();

    private CommonUtils commonUtils = new CommonUtils();

    private CalculateDistance cd = new CalculateDistance();

    private CalculateMoney cm = new CalculateMoney();

    @RequestMapping(value = "/appointments", method = RequestMethod.GET)
    public String getAppointments(HttpSession session,ModelMap model){
        if(!loginUtil.isLogin(session)){
            return "redirect:/login";
        } else{
            List<UserAppointmentView> apps = service.getAll();
            for(UserAppointmentView app : apps){
                User mng = userService.get(app.getCreate_by());
                app.setManagerName(mng.getUsername());
                app.setStart_date_str(commonUtils.convertDateToString(app.getStart_date()));
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
                calculateDetail(dt);
            }
            app.setUsers(users);
            app.setDate_str(commonUtils.convertDateToString(app.getStart_date()));
            app.setDetails(details);
            model.addAttribute("pageName","appointment");
            model.addAttribute("apm",app);
            model.addAttribute("dts",details);
            model.addAttribute("mng",userService.get(app.getManager_id()).getUsername());
            return "web/appointment/apm_detail";
        }
    }

    // Show the road of an appointment
    @RequestMapping(value = "/appointment/viewMap", method = RequestMethod.GET, params = {"id"})
    public String getDetail(HttpSession session,  @RequestParam("id") int id,ModelMap model){
        if(!loginUtil.isLogin(session)){
            return "redirect:/login";
        } else{
            List<Coordinate> coords = coorService.getCoordsOfDetail(id);
            model.addAttribute("pageName","appointment");
            model.addAttribute("coords",coorService.parseCoords(coords));
            return "web/appointment/apm_map";
        }
    }

    private void calculateDetail(Detail dt){
        List<Coordinate> coords = coorService.getCoordsOfDetail(dt.getId());
        if(coords.size() > 0){
            double length = cd.getTotalDistance(coords);
            long time = commonUtils.getSeconds(dt.getStart_time(),dt.getEnd_time());
            double total = cm.getEstimateCost(dt.getVehicle_name(),length,time);
            dt.setTotal_length(length);
            dt.setEstimate_cost(total);
        }
    }
}
