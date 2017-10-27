package capgemini.webappdemo.controllers.Ajax;

import capgemini.webappdemo.domain.Appointment;
import capgemini.webappdemo.domain.Client;
import capgemini.webappdemo.domain.User;
import capgemini.webappdemo.domain.UserAppointmentView;
import capgemini.webappdemo.service.Client.ClientService;
import capgemini.webappdemo.service.Manager.ManagerService;
import capgemini.webappdemo.service.User.UserService;
import capgemini.webappdemo.service.UserAppointmentView.UserAppointmentViewService;
import capgemini.webappdemo.utils.CommonUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserAjax {
    @Autowired
    private UserAppointmentViewService uavService;

    private CommonUtils utils = new CommonUtils();

    @RequestMapping(value = "/ajax/appointment/month/chart", method = RequestMethod.GET, params = {"year","isCreated","id"})
    public String compareAppointmentByMonthChart(@RequestParam("year")int year, @RequestParam("id") int id, @RequestParam("isCreated") boolean isCreated){
        String result = "";
        for(int i = 1; i <= 12; i++){
            try {
                List<Appointment> apms = uavService.getAppointmentsByMonth(i,year,id,isCreated);
                if(apms == null || apms.size() == 0){
                    result += "0 ";
                } else{
                    result += apms.size() + " ";
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @RequestMapping(value = "/ajax/appointment/month", method = RequestMethod.GET, params = {"year","isCreated","id"})
    public JSONArray getAppointmentsInOneYear(@RequestParam("year")int year,@RequestParam("id") int id,@RequestParam("isCreated") boolean isCreated){
        List<Appointment> total = new ArrayList<>();
        for(int i = 1; i <= 12; i++){
            try {
                List<Appointment> temp = uavService.getAppointmentsByMonth(i,year,id,isCreated);
                if(temp != null){
                    total.addAll(temp);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return convertToJSONArray(total);
    }

    @RequestMapping(value = "/ajax/appointment/year/chart", method = RequestMethod.GET, params = {"from","to","id","isCreated"})
    public JSONArray compareAppointmentsByYearChart(@RequestParam("from") int from, @RequestParam("to") int to, @RequestParam("id") int id,@RequestParam("isCreated") boolean isCreated){
        String background = "rgba(255, 99, 132, 0.2)";
        String border = "rgba(255,99,132,1)";
        JSONArray array = new JSONArray();
        JSONObject object;

        List<Appointment> results = new ArrayList<>();
        for(int i = from; i <= to; i++){
            try {
                results = uavService.getAppointmentsByYear(i,id,isCreated);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            object = new JSONObject();
            if(results == null){
                object.put("amount",0);
            } else{
                object.put("amount",results.size());
            }
            object.put("year",i);
            object.put("background",background);
            object.put("border",border);

            array.add(object);
        }
        return array;
    }

    @RequestMapping(value = "/ajax/appointment/year", method = RequestMethod.GET, params = {"from","to","id"})
    public JSONArray compareAppointmentsByYear(@RequestParam("from") int from, @RequestParam("to") int to, @RequestParam("id") int id,@RequestParam("isCreated") boolean isCreated){
        JSONArray result = new JSONArray();
        List<Appointment> total = new ArrayList<>();
        for(int i = from; i <= to; i++){
            try {
                List<Appointment> temp = uavService.getAppointmentsByYear(i,id,isCreated);
                if(temp != null){
                    total.addAll(temp);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return convertToJSONArray(total);
    }

    private JSONArray convertToJSONArray(List<Appointment> appointments){
        JSONArray results = new JSONArray();
        for (Appointment apm : appointments) {
            JSONObject object = new JSONObject();
            object.put("appointment_id", apm.getId());
            object.put("appointment_name", apm.getName());
            object.put("start_date", utils.convertDateToString(apm.getStart_date()));
            if(apm.getEnd_date() != null){
                object.put("end_date", utils.convertDateToString(apm.getEnd_date()));
            } else{
                object.put("end_date","");
            }

            if(apm.getStatus() == 1){
                object.put("status","Active");
            } else if(apm.getStatus() == 2){
                object.put("status","Warning");
            } else{
                object.put("status","Finished");
            }
            results.add(object);
        }
        return results;
    }
}
