package capgemini.webappdemo.controllers.Rest;

import capgemini.webappdemo.domain.Appointment;
import capgemini.webappdemo.domain.Message;
import capgemini.webappdemo.domain.User;
import capgemini.webappdemo.service.Appointment.AppointmentService;
import capgemini.webappdemo.service.Manager.ManagerService;
import capgemini.webappdemo.service.User.UserService;
import capgemini.webappdemo.utils.CommonUtils;
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

    private CommonUtils commonUtils = new CommonUtils();

    private Logger logger = LoggerFactory.getLogger(AppointmentApi.class);

    @RequestMapping(value = "/api/{managerid}/createAppointment", method = RequestMethod.POST)
    public ResponseEntity<Message> createAppointment(@PathVariable("managerid") int id, @RequestBody Appointment apm){
        logger.info("creating appointment - Appointment API");
        Message msg = new Message("");
        if(!userService.getUserType(id).equals("Manager")){
            return new ResponseEntity<Message>(new Message("This user does not have enough privileges",""),HttpStatus.OK);
        }

        if(apm.getName() == null || !within(apm.getName(),8,32)){
            msg = new Message("appointment name must not be empty and within 8 to 32 characters");
        } else if(apm.getStart_location() == null){
            msg.setMessage("appointment start location must not be empty");
        } else if(apm.getEnd_location() == null){
            msg.setMessage("appointment end location must not be empty");
        } else if(apm.getUsers() == null){
            msg.setMessage("please input at least one user who will take this appointment");
        } else if(apm.getDate_str() == null){
            msg.setMessage("please input the start date of this appointment with column date_str");
        } else{
            apm.setManager_id(id);
            apm.setStatus(1);
            try {
                Date date = commonUtils.convertStringToDate(apm.getDate_str());
                apm.setStart_date(date);
                apmService.add(apm);
            } catch (ParseException e) {
                logger.info(e.getMessage());
            }
            if(apm.getId() == 0){
                msg.setMessage("something went wrong when creating appointment");
            } else{
                List<User> users = apm.getUsers();
                for(User user:users){
                    mngService.assignAppointmentToUser(apm.getId(),user.getId());
                }
                msg.setMessage("success,"+apm.getId());
            }
        }
        return new ResponseEntity<Message>(msg, HttpStatus.OK);
    }

    private boolean within(String value, int min, int max){
        return (value.length() < min || value.length() > max)?false:true;
    }
}
