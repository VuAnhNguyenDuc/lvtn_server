package capgemini.webappdemo.controllers.Rest;

import capgemini.webappdemo.domain.Coordinate;
import capgemini.webappdemo.domain.Message;
import capgemini.webappdemo.form.CoordinateForm;
import capgemini.webappdemo.service.Coordinate.CoordinateService;
import capgemini.webappdemo.utils.CommonUtils;
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
public class CoordinateApi {
    @Autowired
    private CoordinateService coordinateService;

    private CommonUtils commonUtils = new CommonUtils();

    @RequestMapping(value = "/api/detail/addCoordinate", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> addCoordinate(@RequestBody CoordinateForm input) throws ParseException {

        int detailId = input.getDetail_id();
        List<CoordinateForm.Coor> coordinates = input.getCoordinates();

        JSONObject result = new JSONObject();

        boolean flag = true;
        for(CoordinateForm.Coor coor : coordinates){
            String time = coor.getTime();
            double latitude = coor.getLatitude();
            double longitude = coor.getLongitude();

            Coordinate co = new Coordinate();
            co.setTime(commonUtils.convertStringToDate(time));
            co.setLatitude(latitude);
            co.setLongitude(longitude);
            co.setDetail_id(detailId);
            coordinateService.add(co);
            if(co.getId() == 0){
                result.put("message",0);
                result.put("description","something went wrong when creating new coordinate, please check logs for more information");
                flag = false;
                break;
            }
        }
        if(flag){
            result.put("message",1);
        }
        return new ResponseEntity<JSONObject>(result, HttpStatus.OK);
    }
}
