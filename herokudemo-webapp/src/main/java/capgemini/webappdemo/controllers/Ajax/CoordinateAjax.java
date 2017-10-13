package capgemini.webappdemo.controllers.Ajax;

import capgemini.webappdemo.domain.Coordinate;
import capgemini.webappdemo.domain.Detail;
import capgemini.webappdemo.service.Appointment.AppointmentService;
import capgemini.webappdemo.service.Coordinate.CoordinateService;
import capgemini.webappdemo.service.Detail.DetailService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class CoordinateAjax {

    @Autowired
    private AppointmentService apmService;

    @Autowired
    private DetailService dtService;

    @Autowired
    private CoordinateService service;

    @RequestMapping(value = "/ajax/getCoordinates", method = RequestMethod.GET, params = "appointmentid")
    public JSONArray getCoordinates(@PathVariable("appointmentid") int id){
        JSONArray road = new JSONArray();
        List<Detail> dts = dtService.getDetailsOfAppointment(id);
        for(Detail dt:dts){
            List<Coordinate> coords = service.getCoordsOfDetail(dt.getId());
            for(Coordinate coord : coords){
                JSONObject obj = new JSONObject();
                obj.put("longtitude",coord.getLongtitude());
                obj.put("latitude",coord.getLattitude());
                obj.put("time",convertDateToString(coord.getTime()));
                road.add(coord);
            }
        }
        return road;
    }

    private String convertDateToString(Date date){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm dd-MM-yyyy");
        return dateFormat.format(date);
    }
}
