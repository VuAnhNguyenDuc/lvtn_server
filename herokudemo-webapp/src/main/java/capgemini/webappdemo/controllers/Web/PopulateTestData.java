package capgemini.webappdemo.controllers.Web;

import capgemini.webappdemo.domain.*;
import capgemini.webappdemo.service.Admin.AdminService;
import capgemini.webappdemo.service.Appointment.AppointmentService;
import capgemini.webappdemo.service.Coordinate.CoordinateService;
import capgemini.webappdemo.service.Employee.EmployeeService;
import capgemini.webappdemo.service.Manager.ManagerService;
import capgemini.webappdemo.service.User.UserService;
import capgemini.webappdemo.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Vu Anh Nguyen Duc on 10/15/2017.
 */
@Controller
public class PopulateTestData {
    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @Autowired
    private ManagerService managerService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private AppointmentService apmService;

    @Autowired
    private CoordinateService coorService;

    private DateFormat dateFormat = new SimpleDateFormat("HH:mm dd-MM-yyyy");
    private CommonUtils cu = new CommonUtils();

    @RequestMapping(value = "/populateData", method = RequestMethod.GET)
    public void populateData() throws ParseException {
        /*Coordinate coor = new Coordinate();
        coor.setDetail_id(1);
        coor.setLongitude(123);
        coor.setLatitude(123);
        coor.setTime(cu.convertStringToDate("11:00 18-10-2017"));
        coorService.add(coor);*/

       /* User mng = userService.get(3);
        mng.setFullname("Tran Van A");
        userService.update(mng);

        User dung = userService.get(4);
        dung.setFullname("Tran Van B");
        userService.update(dung);

        User dam = userService.get(5);
        dam.setFullname("Trang Van C");
        userService.update(dam);*/

        Admin admin = new Admin();
        admin.setUsername("admin");
        admin.setPassword("admin");
        adminService.add(admin);

        User mng = new User();
        mng.setUsername("mng");
        mng.setPassword("mng");
        mng.setFullname("Vu Anh");
        mng.setEmail("mng@mgial.com");
        userService.add(mng);

        Manager mngM = new Manager();
        mngM.setUser_id(mng.getId());
        mngM.setStatus(1);
        mngM.setNumber_of_employees(0);
        managerService.add(mngM);

        User emp = new User();
        emp.setUsername("emp");
        emp.setPassword("emp");
        emp.setFullname("Tran Van A");
        emp.setEmail("emp@gmail.com");
        userService.add(emp);

        Employee empE = new Employee();
        empE.setUser_id(emp.getId());
        empE.setStatus(1);
        empE.setManager_id(mng.getId());
        empE.setEmployee_type("Employee");
        employeeService.add(empE);

        User dung = new User();
        dung.setUsername("dung");
        dung.setPassword("dung");
        dung.setFullname("Tran Van B");
        dung.setEmail("dung@gmail.com");
        userService.add(dung);

        empE = new Employee();
        empE.setUser_id(dung.getId());
        empE.setStatus(1);
        empE.setManager_id(dung.getId());
        empE.setEmployee_type("Employee");
        employeeService.add(empE);

        User dam = new User();
        dam.setUsername("dam");
        dam.setPassword("dam");
        dam.setFullname("Tran Van C");
        dam.setEmail("dam@gmail.com");
        userService.add(dam);

        empE = new Employee();
        empE.setUser_id(dam.getId());
        empE.setStatus(1);
        empE.setManager_id(dam.getId());
        empE.setEmployee_type("Employee");
        employeeService.add(empE);


        Appointment apm = new Appointment();
        apm.setName("Appointment 1");
        apm.setManager_id(mng.getId());
        apm.setDestination("destination 1");
        try {
            apm.setStart_date(dateFormat.parse("15:00 15-10-2017"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        apm.setStatus(1);
        List<User> usrs = new ArrayList<>();
        usrs.add(emp);
        apm.setUsers(usrs);
        apmService.add(apm);
        if(apm.getId() != 0){
            List<User> users = apm.getUsers();
            for(User user:users){
                managerService.assignAppointmentToUser(apm.getId(),user.getId());
            }
        }

        Appointment apm2 = new Appointment();
        apm2.setName("Appointment 2");
        apm2.setManager_id(mng.getId());
        apm2.setDestination("destination 2");
        try {
            apm2.setStart_date(dateFormat.parse("18:00 15-10-2017"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        apm2.setStatus(1);
        usrs = new ArrayList<>();
        usrs.add(dam);
        usrs.add(dung);
        apm2.setUsers(usrs);
        apmService.add(apm2);
        if(apm2.getId() != 0){
            List<User> users = apm2.getUsers();
            for(User user:users){
                managerService.assignAppointmentToUser(apm2.getId(),user.getId());
            }
        }
    }
}
