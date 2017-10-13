package capgemini.webappdemo.controllers.Web;

import capgemini.webappdemo.domain.*;
import capgemini.webappdemo.service.Appointment.AppointmentService;
import capgemini.webappdemo.service.Coordinate.CoordinateService;
import capgemini.webappdemo.service.Detail.DetailService;
import capgemini.webappdemo.service.User.UserService;
import capgemini.webappdemo.service.UserAppointmentView.UserAppointmentViewService;
import capgemini.webappdemo.utils.LoginUtil;
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

    @RequestMapping(value = "/appointments", method = RequestMethod.GET)
    public String getAppointments(HttpSession session,ModelMap model){
        if(!loginUtil.isLogin(session)){
            return "redirect:/login";
        } else{
            List<UserAppointmentView> apps = service.getAll();
            for(UserAppointmentView app : apps){
                User mng = userService.get(app.getCreated_by());
                app.setManagerName(mng.getUsername());
            }
            model.addAttribute("pageName","appointment");
            model.addAttribute("apps",apps);
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
            app.setUsers(users);
            app.setDetails(details);
            model.addAttribute("pageName","appointment");
            model.addAttribute("app",app);
            return "web/appointment/apm_detail";
        }
    }

    // Show the road of a detail
    @RequestMapping(value = "/details", method = RequestMethod.GET, params = {"detail_id"})
    public String getDetail(HttpSession session,  @RequestParam("detail_id") int detail_id,ModelMap model){
        if(!loginUtil.isLogin(session)){
            return "redirect:/login";
        } else{
            List<Coordinate> coords = coorService.getCoordsOfDetail(detail_id);
            model.addAttribute("pageName","appointment");
            model.addAttribute("coords",coorService.parseCoords(coords));
            return "web/detail/detail";
        }
    }
}
