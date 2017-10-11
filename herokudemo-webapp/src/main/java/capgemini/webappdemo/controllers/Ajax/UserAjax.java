package capgemini.webappdemo.controllers.Ajax;

import capgemini.webappdemo.domain.User;
import capgemini.webappdemo.domain.UserAppointmentView;
import capgemini.webappdemo.service.User.UserService;
import capgemini.webappdemo.service.UserAppointmentView.UserAppointmentViewService;
import capgemini.webappdemo.utils.CommonUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserAjax {
    @Autowired
    private UserAppointmentViewService uavService;

    @Autowired
    private UserService userService;

    private CommonUtils utils = new CommonUtils();

    @RequestMapping(value = "/ajax/appointment/month", method = RequestMethod.GET, params = {"year","managerid"})
    public String compareAppointmentByMonth(@PathVariable("year")int year,@PathVariable("managerid") int id){
        String result = "";
        for(int i = 1; i <= 12; i++){
            try {
                List<UserAppointmentView> uavs = uavService.getAppointmentsByMonth(i,year,id);
                result += uavs.size() + " ";
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @RequestMapping(value = "/ajax/appointment/year", method = RequestMethod.GET, params = {"from","to","managerid"})
    public JSONArray compareAppointmentsByYear(@PathVariable("from") int from, @PathVariable("to") int to, @PathVariable("managerid") int id){

        return null;
    }

    public JSONArray convertToJSONArray(List<UserAppointmentView> appointments){
        JSONArray results = new JSONArray();
        for (UserAppointmentView appointment1 : appointments) {
            JSONObject object = new JSONObject();
            UserAppointmentView ap = appointment1;
            User mng = userService.get(ap.getCreated_by());
            object.put("appointment_id", ap.getAppointment_id());
            object.put("appointment_name", ap.getAppointment_name());
            object.put("start_date", utils.convertDateToString(ap.getStart_date()));
            object.put("created_by", mng.getUsername());

            if(ap.getStatus() == 1){
                object.put("status","working");
            } else{
                object.put("status","finished");
            }
            results.add(object);
        }
        return results;
    }

}
