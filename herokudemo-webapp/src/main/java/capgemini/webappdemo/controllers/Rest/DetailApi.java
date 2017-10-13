package capgemini.webappdemo.controllers.Rest;

import capgemini.webappdemo.domain.Detail;
import capgemini.webappdemo.domain.Message;
import capgemini.webappdemo.domain.Vehicle;
import capgemini.webappdemo.service.Detail.DetailService;
import capgemini.webappdemo.service.Vehicle.VehicleService;
import capgemini.webappdemo.utils.CommonUtils;
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

    private Logger logger = LoggerFactory.getLogger(DetailApi.class);
    private CommonUtils commonUtils = new CommonUtils();

    @RequestMapping(value = "/api/createDetail", method = RequestMethod.POST)
    public ResponseEntity<Message> createDetail(@RequestBody Detail detail){
        logger.info("creating detail - Detail API");
        Message msg = new Message("");
        if(detail.getVehicle_id() == 0){
            msg.setMessage("please input the vehicle that you will use for this detail");
        } else if(detail.getAppointment_id() == 0){
            msg.setMessage("please input the appointment of this detail");
        } else {
            detail.setUser_created(detail.getUser_created());
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
    public ResponseEntity<Message> startDetail(@PathVariable("detailid") int id, @RequestBody Detail detail){
        logger.info("starting a detail - Detail API");
        Message msg = new Message("");
        if(detail.getStart_time_string().isEmpty()){
            msg.setMessage("please input the start time on start_date_string column");
        } else{
            Detail result = detailService.get(id);
            if(result == null){
                msg.setMessage("this detail does not exist");
            } else{
                result.setStart_time_string(detail.getStart_time_string());
                try {
                    result.setStart_time(commonUtils.convertStringToDate(detail.getStart_time_string()));
                    detailService.update(result);
                } catch (ParseException e) {
                    logger.info(e.getMessage());
                }
                msg.setMessage("detail successfully updated");
            }
        }
        return new ResponseEntity<Message>(msg,HttpStatus.OK);
    }

    @RequestMapping(value = "/api/detail/end", method = RequestMethod.POST)
    public ResponseEntity<Message> endDetail(@PathVariable("detailid") int id, @RequestBody Detail detail){
        logger.info("ending a detail - Detail API");
        Message msg = new Message("");
        if(detail.getEnd_time_string().isEmpty()){
            msg.setMessage("please input the end time on end_date_string column");
        } else{
            Detail result = detailService.get(id);
            if(result == null){
                msg.setMessage("this detail does not exist");
            } else{
                result.setEnd_time_string(detail.getEnd_time_string());
                try {
                    result.setEnd_time(commonUtils.convertStringToDate(detail.getEnd_time_string()));
                    detailService.update(result);
                } catch (ParseException e) {
                    logger.info(e.getMessage());
                }
                msg.setMessage("detail successfully updated");
            }
        }
        return new ResponseEntity<Message>(msg,HttpStatus.OK);
    }

    @RequestMapping(value = "/api/detail/addImage", method = RequestMethod.POST)
    public ResponseEntity<Message> addImage(@PathVariable("detailid") int id, @RequestBody Detail detail){
        logger.info("adding an image to detail - Detail API");
        Message msg = new Message("");
        if(detail.getImage_content().isEmpty()){
            msg.setMessage("please input image content first");
        } else{
            Detail result = detailService.get(id);
            if(result == null){
                msg.setMessage("this detail does not exist");
            } else{
                result.setImage_content(detail.getImage_content());
                detailService.update(result);
                msg.setMessage("datail updated successfully");
            }
        }
        return new ResponseEntity<Message>(msg,HttpStatus.OK);
    }

    @RequestMapping(value = "/api/detail/addCost", method = RequestMethod.POST)
    public ResponseEntity<Message> addCost(@PathVariable("detailid") int id, @RequestBody Detail detail){
        logger.info("adding an price cost to detail - Detail API");
        Message msg = new Message("");
        if(detail.getInput_cost() == 0){
            msg.setMessage("please input a valid integer price");
        } else{
            Detail result = detailService.get(id);
            if(result == null){
                msg.setMessage("this detail does not exist");
            } else{
                result.setInput_cost(detail.getInput_cost());
                detailService.update(result);
                msg.setMessage("detail updated successfully");
            }
        }
        return new ResponseEntity<Message>(msg,HttpStatus.OK);
    }

    @RequestMapping(value = "/api/getVehicles", method = RequestMethod.GET)
    public ResponseEntity<List<Vehicle>> getVehicles(){
        return new ResponseEntity<List<Vehicle>>(vehicleService.getAll(),HttpStatus.OK);
    }
}
