package capgemini.webappdemo.controllers.Web;

import capgemini.webappdemo.domain.Appointment;
import capgemini.webappdemo.service.Appointment.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserAppointmentController {
    @Autowired
    private AppointmentService apmService;

    /*@RequestMapping(value = "/user/appointmentsByMonths", method = RequestMethod.GET)*/

}
