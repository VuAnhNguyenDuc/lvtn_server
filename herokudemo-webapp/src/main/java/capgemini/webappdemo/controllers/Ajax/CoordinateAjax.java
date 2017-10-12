package capgemini.webappdemo.controllers.Ajax;

import capgemini.webappdemo.domain.Coordinate;
import capgemini.webappdemo.service.Coordinate.CoordinateService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class CoordinateAjax {

    @Autowired
    private CoordinateService service;

    @RequestMapping(value = "/ajax/getCoordinates", method = RequestMethod.GET, params = "detailid")
    public JSONArray getCoordinates(@PathVariable("detailid") int detail_id){
        JSONArray road = new JSONArray();
        List<Coordinate> coords = service.getCoordsOfDetail(detail_id);
        for(Coordinate coord : coords){
            JSONObject obj = new JSONObject();
            obj.put("longtitude",coord.getLongtitude());
            obj.put("latitude",coord.getLattitude());
            obj.put("time",convertDateToString(coord.getTime()));
            road.add(coord);
        }
        return road;
    }

    private String convertDateToString(Date date){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm dd-MM-yyyy");
        return dateFormat.format(date);
    }
}
