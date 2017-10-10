package capgemini.webappdemo.controllers.Rest;

import capgemini.webappdemo.domain.Coordinate;
import capgemini.webappdemo.domain.Message;
import capgemini.webappdemo.service.Coordinate.CoordinateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class CoordinateApi {
    @Autowired
    private CoordinateService coordinateService;

    private Logger logger = LoggerFactory.getLogger(CoordinateApi.class);

    @RequestMapping(value = "/api/detail/{detailid}/addCoordinate", method = RequestMethod.POST)
    public ResponseEntity<Message> addCoordinate(@PathVariable("detailid")int id,@RequestBody List<Coordinate> coordinates){
        logger.info("creating coordinates - Coordinate API");
        Message msg = new Message("");
        boolean flag = true;
        for(Coordinate coor : coordinates){
            if(coor.getLongtitude() == 0 || coor.getLattitude() == 0){
                msg.setMessage("please input lattitude and longtitude for every coordinate you sent");
                flag = false;
                break;
            } else{
                coor.setDetail_id(id);
                coor.setTime(new Date());
                coordinateService.add(coor);
                if(coor.getId() == 0){
                    msg.setMessage("something went wrong when creating new coordinate, please check logs for more information");
                    flag = false;
                    break;
                }
            }
        }
        if(flag){
            msg.setMessage("coordinates created successfully for this detail");
        }
        return new ResponseEntity<Message>(msg, HttpStatus.OK);
    }
}
