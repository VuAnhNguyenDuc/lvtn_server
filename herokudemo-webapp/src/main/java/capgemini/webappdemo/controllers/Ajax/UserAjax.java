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

    @RequestMapping(value = "/ajax/appointment/month/chart", method = RequestMethod.GET, params = {"year","isCreated","id"})
    public String compareAppointmentByMonthChart(@PathVariable("year")int year,@PathVariable("id") int id,@PathVariable("isCreated") boolean isCreated){
        String result = "";
        for(int i = 1; i <= 12; i++){
            try {
                List<UserAppointmentView> uavs = uavService.getAppointmentsByMonth(i,year,id,isCreated);
                result += uavs.size() + " ";
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @RequestMapping(value = "/ajax/appointment/month", method = RequestMethod.GET, params = {"year","isCreated","id"})
    public JSONArray getAppointmentsInOneYear(@PathVariable("year")int year,@PathVariable("id") int id,@PathVariable("isCreated") boolean isCreated){
        List<UserAppointmentView> total = new ArrayList<>();
        for(int i = 1; i <= 12; i++){
            try {
                total.addAll(uavService.getAppointmentsByMonth(i,year,id,isCreated));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return convertToJSONArray(total);
    }

    @RequestMapping(value = "/ajax/appointment/year/chart", method = RequestMethod.GET, params = {"from","to","id","isCreated"})
    public JSONArray compareAppointmentsByYearChart(@PathVariable("from") int from, @PathVariable("to") int to, @PathVariable("id") int id,@PathVariable("isCreated") boolean isCreated){
        String background = "rgba(255, 99, 132, 0.2)";
        String border = "rgba(255,99,132,1)";
        JSONArray array = new JSONArray();
        JSONObject object;

        List<UserAppointmentView> results = new ArrayList<>();
        for(int i = from; i <= to; i++){
            try {
                results = uavService.getAppointmentsByYear(i,id,isCreated);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            object = new JSONObject();
            object.put("amount",results.size());
            object.put("year",i);
            object.put("background",background);
            object.put("border",border);

            array.add(object);
        }
        return array;
    }

    @RequestMapping(value = "/ajax/appointment/year", method = RequestMethod.GET, params = {"from","to","id"})
    public JSONArray compareAppointmentsByYear(@PathVariable("from") int from, @PathVariable("to") int to, @PathVariable("id") int id,@PathVariable("isCreated") boolean isCreated){
        JSONArray result = new JSONArray();
        List<UserAppointmentView> total = new ArrayList<>();
        for(int i = from; i <= to; i++){
            try {
                total.addAll(uavService.getAppointmentsByYear(i,id,isCreated));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return convertToJSONArray(total);
    }

    public JSONArray convertToJSONArray(List<UserAppointmentView> appointments){
        JSONArray results = new JSONArray();
        for (UserAppointmentView appointment1 : appointments) {
            JSONObject object = new JSONObject();
            UserAppointmentView ap = appointment1;
            User mng = userService.get(ap.getCreate_by());
            object.put("appointment_id", ap.getAppointment_id());
            object.put("appointment_name", ap.getAppointment_name());
            object.put("start_date", utils.convertDateToString(ap.getStart_date()));
            object.put("end_date",utils.convertDateToString(ap.getEnd_date()));
            object.put("create_by", mng.getUsername());

            if(ap.getStatus() == 1){
                object.put("status","Active");
            } else{
                object.put("status","Finished");
            }
            results.add(object);
        }
        return results;
    }


}
