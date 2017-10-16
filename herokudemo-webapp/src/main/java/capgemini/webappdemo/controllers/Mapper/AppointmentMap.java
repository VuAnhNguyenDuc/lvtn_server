package capgemini.webappdemo.controllers.Mapper;

import capgemini.webappdemo.domain.UserAppointmentView;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

/**
 * Created by Vu Anh Nguyen Duc on 10/15/2017.
 */
public class AppointmentMap {
    public JSONArray AppointmentToJson(List<UserAppointmentView> input){
        // user_id, appointment_id, manager_name, appointment_name, destination, start_date, status
        JSONArray result = new JSONArray();
        for(UserAppointmentView uav : input){
            JSONObject obj = new JSONObject();
            obj.put("user_id",uav.getUser_id());
            obj.put("appointment_id",uav.getAppointment_id());
            obj.put("manager_name",uav.getManagerName());
            obj.put("appointment_name",uav.getAppointment_name());
            obj.put("destination",uav.getDestination());
            obj.put("start_date",uav.getStart_date_str());
            if(uav.getStatus() == 1){
                obj.put("status","active");
            } else{
                obj.put("status","inactive");
            }
            result.add(obj);
        }
        return result;
    }
}
