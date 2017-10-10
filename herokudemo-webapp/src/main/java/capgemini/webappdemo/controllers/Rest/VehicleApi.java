package capgemini.webappdemo.controllers.Rest;

import capgemini.webappdemo.domain.Vehicle;
import capgemini.webappdemo.service.Vehicle.VehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Vu Anh Nguyen Duc on 10/10/2017.
 */
@RestController
public class VehicleApi {
    @Autowired
    private VehicleService vehicleService;

    private Logger logger = LoggerFactory.getLogger(VehicleApi.class);

    @RequestMapping(value = "/api/vehicles", method = RequestMethod.GET)
    public ResponseEntity<List<Vehicle>> getVehicles(){
        return new ResponseEntity<List<Vehicle>>(vehicleService.getAll(), HttpStatus.OK);
    }
}
