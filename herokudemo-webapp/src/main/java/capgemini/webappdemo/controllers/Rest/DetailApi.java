package capgemini.webappdemo.controllers.Rest;

import capgemini.webappdemo.domain.Detail;
import capgemini.webappdemo.domain.Message;
import capgemini.webappdemo.domain.Vehicle;
import capgemini.webappdemo.service.Detail.DetailService;
import capgemini.webappdemo.service.Vehicle.VehicleService;
import capgemini.webappdemo.utils.CommonUtils;
import capgemini.webappdemo.utils.JsonTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
public class DetailApi {
    @Autowired
    private DetailService detailService;

    @Autowired
    private VehicleService vehicleService;

    private CommonUtils commonUtils = new CommonUtils();
    private JsonTokenUtil jsonTokenUtil = new JsonTokenUtil();

    @RequestMapping(value = "/api/createDetail", method = RequestMethod.POST)
    public ResponseEntity<Message> createDetail(@RequestBody Detail detail){
        System.out.println("creating detail - Detail API");
        if(detail.getJson_token().equals("") || !jsonTokenUtil.validateKey(detail.getJson_token())){
            return new ResponseEntity<Message>(new Message("invalid json token"), HttpStatus.BAD_REQUEST);
        }
        Message msg = new Message("");
        if(detail.getVehicle_id() == 0){
            msg.setMessage("please input the vehicle that you will use for this detail");
        } else if(detail.getAppointment_id() == 0){
            msg.setMessage("please input the appointment of this detail");
        } else {
            //detail.setUser_created(detail.getUser_created());
            int id = jsonTokenUtil.getUserIdFromJsonKey(detail.getJson_token());
            detail.setUser_created(id);
            detailService.add(detail);
            if(detail.getId() != 0){
                msg.setMessage("success,"+detail.getId());
            } else{
                msg.setMessage("something went wrong while creating detail, please check logs for more information");
            }
        }
        return new ResponseEntity<Message>(msg, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/detail/start", method = RequestMethod.POST)
    public ResponseEntity<Message> startDetail(@RequestBody Detail detail){
        System.out.println("starting a detail - Detail API");
        if(detail.getJson_token().equals("") || !jsonTokenUtil.validateKey(detail.getJson_token())){
            return new ResponseEntity<Message>(new Message("invalid json token"), HttpStatus.BAD_REQUEST);
        }
        Message msg = new Message("");
        if(detail.getStart_time_string().isEmpty()){
            msg.setMessage("please input the start time on start_date_string column");
        } else{
            int id = detail.getId();
            Detail result = detailService.get(id);
            if(result == null){
                msg.setMessage("this detail does not exist");
            } else{
                result.setStart_time_string(detail.getStart_time_string());
                try {
                    result.setStart_time(commonUtils.convertStringToDate(detail.getStart_time_string()));
                    detailService.update(result);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                msg.setMessage("success");
            }
        }
        return new ResponseEntity<Message>(msg,HttpStatus.OK);
    }

    @RequestMapping(value = "/api/detail/end", method = RequestMethod.POST)
    public ResponseEntity<Message> endDetail(@RequestBody Detail detail){
        System.out.println("ending a detail - Detail API");
        if(detail.getJson_token().equals("") || !jsonTokenUtil.validateKey(detail.getJson_token())){
            return new ResponseEntity<Message>(new Message("invalid json token"), HttpStatus.BAD_REQUEST);
        }
        Message msg = new Message("");
        if(detail.getEnd_time_string().isEmpty()){
            msg.setMessage("please input the end time on end_date_string column");
        } else{
            int id = detail.getId();
            Detail result = detailService.get(id);
            if(result == null){
                msg.setMessage("this detail does not exist");
            } else{
                result.setEnd_time_string(detail.getEnd_time_string());
                try {
                    result.setEnd_time(commonUtils.convertStringToDate(detail.getEnd_time_string()));
                    detailService.update(result);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                msg.setMessage("success");
            }
        }
        return new ResponseEntity<Message>(msg,HttpStatus.OK);
    }

    @RequestMapping(value = "/api/detail/addImage", method = RequestMethod.POST)
    public ResponseEntity<Message> addImage(@RequestBody Detail detail){
        System.out.println("adding an image to detail - Detail API");
        if(detail.getJson_token().equals("") || !jsonTokenUtil.validateKey(detail.getJson_token())){
            return new ResponseEntity<Message>(new Message("invalid json token"), HttpStatus.BAD_REQUEST);
        }
        Message msg = new Message("");
        if(detail.getImage_content().isEmpty()){
            msg.setMessage("please input image content first");
        } else{
            int id = detail.getId();
            Detail result = detailService.get(id);
            if(result == null){
                msg.setMessage("this detail does not exist");
            } else{
                result.setImage_content(detail.getImage_content());
                detailService.update(result);
                msg.setMessage("success");
            }
        }
        return new ResponseEntity<Message>(msg,HttpStatus.OK);
    }

    @RequestMapping(value = "/api/detail/addCost", method = RequestMethod.POST)
    public ResponseEntity<Message> addCost(@RequestBody Detail detail){
        System.out.println("adding a price cost to detail - Detail API");
        if(detail.getJson_token().equals("") || !jsonTokenUtil.validateKey(detail.getJson_token())){
            return new ResponseEntity<Message>(new Message("invalid json token"), HttpStatus.BAD_REQUEST);
        }
        Message msg = new Message("");
        if(detail.getInput_cost() == 0){
            msg.setMessage("please input a valid integer price");
        } else{
            int id = detail.getId();
            Detail result = detailService.get(id);
            if(result == null){
                msg.setMessage("this detail does not exist");
            } else{
                result.setInput_cost(detail.getInput_cost());
                detailService.update(result);
                msg.setMessage("success");
            }
        }
        return new ResponseEntity<Message>(msg,HttpStatus.OK);
    }

    @RequestMapping(value = "/api/detail/addDescription", method = RequestMethod.POST)
    public ResponseEntity<Message> addDescription(@RequestBody Detail detail){
        System.out.println("adding a price cost to detail - Detail API");
        if(detail.getJson_token().equals("") || !jsonTokenUtil.validateKey(detail.getJson_token())){
            return new ResponseEntity<Message>(new Message("invalid json token"), HttpStatus.BAD_REQUEST);
        }
        Message msg = new Message("");
        if(detail.getId() == 0){
            msg.setMessage("please input a valid detail id");
        } else if(detail.getDescription() == null && detail.getDescription().equals("")){
            msg.setMessage("please input valid description");
        } else{
            Detail dt = detailService.get(detail.getId());
            dt.setDescription(detail.getDescription());
            msg.setMessage("success");
        }
        return new ResponseEntity<Message>(msg,HttpStatus.OK);
    }

    @RequestMapping(value = "/api/detail/addEndLocation", method = RequestMethod.POST)
    public ResponseEntity<Message> addEndLocation(@RequestBody Detail detail){
        System.out.println("adding a price cost to detail - Detail API");
        if(detail.getJson_token().equals("") || !jsonTokenUtil.validateKey(detail.getJson_token())){
            return new ResponseEntity<Message>(new Message("invalid json token"), HttpStatus.BAD_REQUEST);
        }
        Message msg = new Message("");
        if(detail.getId() == 0){
            msg.setMessage("please input a valid detail id");
        } else if(detail.getEnd_location() == null && detail.getEnd_location().equals("")){
            msg.setMessage("please input valid end location");
        } else{
            Detail dt = detailService.get(detail.getId());
            dt.setEnd_location(detail.getEnd_location());
            msg.setMessage("success");
        }
        return new ResponseEntity<Message>(msg,HttpStatus.OK);
    }

    @RequestMapping(value = "/api/getVehicles", method = RequestMethod.GET)
    public ResponseEntity<List<Vehicle>> getVehicles(){
        return new ResponseEntity<List<Vehicle>>(vehicleService.getAll(),HttpStatus.OK);
    }
}
