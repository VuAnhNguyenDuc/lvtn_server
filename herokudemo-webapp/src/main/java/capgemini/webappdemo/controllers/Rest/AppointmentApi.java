package capgemini.webappdemo.controllers.Rest;

import capgemini.webappdemo.domain.Appointment;
import capgemini.webappdemo.domain.Detail;
import capgemini.webappdemo.domain.User;
import capgemini.webappdemo.form.AppointmentForm;
import capgemini.webappdemo.service.Appointment.AppointmentService;
import capgemini.webappdemo.service.Coordinate.CoordinateService;
import capgemini.webappdemo.service.Detail.DetailService;
import capgemini.webappdemo.service.Manager.ManagerService;
import capgemini.webappdemo.service.User.UserService;
import capgemini.webappdemo.utils.CommonUtils;
import capgemini.webappdemo.utils.JsonTokenUtil;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RestController
public class AppointmentApi {

    @Autowired
    private AppointmentService apmService;

    @Autowired
    private ManagerService mngService;

    @Autowired
    private UserService userService;

    @Autowired
    private DetailService dtService;

    @Autowired
    private CoordinateService coorService;

    private JsonTokenUtil jsonTokenUtil = new JsonTokenUtil();

    private CommonUtils commonUtils = new CommonUtils();

    private Logger logger = LoggerFactory.getLogger(AppointmentApi.class);

    @RequestMapping(value = "/api/createAppointment", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> createAppointment(@RequestBody JSONObject input){
        JSONObject result = new JSONObject();
        String jsonToken = input.get("json_token").toString();
        String name = input.get("name").toString();
        String destination = input.get("destination").toString();
        String startDate = input.get("start_date").toString();
        //List<User> users = input.getUsers();
        String users = input.get("users").toString();
        //int status = input.getStatus();

        if(jsonToken.equals("") || !jsonTokenUtil.validateKey(jsonToken)){
            result.put("message",0);
            result.put("description","invalid json token");
            return new ResponseEntity<>(result,HttpStatus.OK);
        }

        int id = jsonTokenUtil.getUserIdFromJsonKey(jsonToken);
        if(!userService.getUserType(id).equals("Manager")){
            result.put("message",0);
            result.put("description","this user does not have enough priviledges");
            return new ResponseEntity<JSONObject>(result,HttpStatus.OK);
        }

        result.put("message",0);
        if(name == null || !within(name,8,32)){
            result.put("description","appointment name must not be empty and within 8 to 32 characters");
        } else if(destination == null){
            result.put("description","appointment destination must not be empty");
        } else if(users == null){
            result.put("description","please input at least one user who will take this appointment");
        } else if(startDate == null){
            result.put("description","please input the start date of this appointment");
        } else if(apmService.checkAppointmentExist(name)){
            result.put("description","an appointment with the same name already existed");
        } else{
            Appointment apm = new Appointment();
            apm.setName(name);
            apm.setDestination(destination);
            apm.setManager_id(id);
            apm.setStatus(1);
            apm.setTotal_cost(0);
            try {
                Date date = commonUtils.convertStringToDate(startDate);
                apm.setStart_date(date);
                apmService.add(apm);
            } catch (ParseException e) {
                logger.info(e.getMessage());
            }
            if(apm.getId() == 0){
                result.put("description","something went wrong when creating appointment");
            } else{
                int[] usrs = commonUtils.convertStringToArray(users);
                for (int usr : usrs) {
                    mngService.assignAppointmentToUser(apm.getId(), usr);
                }
                result.put("message",1);
                result.put("appointment_id",apm.getId());
            }
        }
        return new ResponseEntity<JSONObject>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/endAppointment", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> endAppointment(@RequestBody JSONObject input) throws ParseException {
        JSONObject result = new JSONObject();
        String jsonToken = input.get("json_token").toString();
        int id = (int) input.get("id");
        String endDate = input.get("end_date").toString();

        result.put("message",0);
        if(jsonToken.equals("") || !jsonTokenUtil.validateKey(jsonToken)){
            result.put("description","invalid json token");
            return new ResponseEntity<JSONObject>(result,HttpStatus.OK);
        }
        if(id == 0){
            result.put("description","please input proper id value");
            return new ResponseEntity<JSONObject>(result,HttpStatus.OK);
        } else{
            Appointment ap = apmService.get(id);
            if(ap == null){
                result.put("description","this appointment does not exist");
            } else {
                List<Detail> dts = dtService.getDetailsOfAppointment(id);
                if(dts.size() == 0){
                    result.put("description","this appointment has no schedules (details)");
                } else{
                    for(Detail dt : dts){
                        if(coorService.getCoordsOfDetail(dt.getId()).size() == 0){
                            result.put("description","a detail in this appointment has no coordinates");
                            return new ResponseEntity<JSONObject>(result,HttpStatus.OK);
                        }
                    }
                    // warning
                    if(ap.getStatus() != -1){
                        ap.setStatus(0);
                    }
                    ap.setEnd_date(commonUtils.convertStringToDate(endDate));
                    apmService.update(ap);
                    result.put("message",1);
                }
            }

            return new ResponseEntity<JSONObject>(result,HttpStatus.OK);
        }
    }

    private boolean within(String value, int min, int max){
        return (value.length() < min || value.length() > max)?false:true;
    }
}
