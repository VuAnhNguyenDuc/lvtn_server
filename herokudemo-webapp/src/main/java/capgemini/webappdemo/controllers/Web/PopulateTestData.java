package capgemini.webappdemo.controllers.Web;

import capgemini.webappdemo.domain.*;
import capgemini.webappdemo.service.Admin.AdminService;
import capgemini.webappdemo.service.Appointment.AppointmentService;
import capgemini.webappdemo.service.Client.ClientService;
import capgemini.webappdemo.service.Coordinate.CoordinateService;
import capgemini.webappdemo.service.Detail.DetailService;
import capgemini.webappdemo.service.Employee.EmployeeService;
import capgemini.webappdemo.service.Manager.ManagerService;
import capgemini.webappdemo.service.SpecialPlace.SpecialPlaceService;
import capgemini.webappdemo.service.User.UserService;
import capgemini.webappdemo.service.Vehicle.VehicleService;
import capgemini.webappdemo.utils.CalculateMoney;
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

    @Autowired
    private VehicleService vhcService;

    @Autowired
    private DetailService dtService;

    @Autowired
    private ClientService clService;

    @Autowired
    private SpecialPlaceService spService;

    private DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
    private CommonUtils cu = new CommonUtils();

    @RequestMapping(value = "/createUsers", method = RequestMethod.GET)
    public void createUsers(){
        Admin admin = new Admin();
        admin.setUsername("admin");
        admin.setPassword("admin");
        adminService.add(admin);

        User mng = new User();
        mng.setUsername("mng");
        mng.setPassword("mng");
        mng.setFullname("Nguyễn Đức Vũ Anh");
        mng.setEmail("mng@gmail.com");
        mng.setStatus(1);
        userService.add(mng);

        Manager mngM = new Manager();
        mngM.setUser_id(mng.getId());
        mngM.setStatus(1);
        mngM.setNumber_of_employees(0);
        managerService.add(mngM);


        User dung = new User();
        dung.setUsername("dung");
        dung.setPassword("dung");
        dung.setFullname("Dũng Nguyễn");
        dung.setEmail("dung@gmail.com");
        dung.setStatus(1);
        userService.add(dung);

        Employee emp = new Employee();
        emp.setUser_id(dung.getId());
        emp.setStatus(1);
        emp.setManager_id(mng.getId());
        emp.setEmployee_type("Employee");
        employeeService.add(emp);

        User dam = new User();
        dam.setUsername("dam");
        dam.setPassword("dam");
        dam.setFullname("Đàm Trương");
        dam.setEmail("dam@gmail.com");
        dam.setStatus(1);
        userService.add(dam);

        emp = new Employee();
        emp.setUser_id(dam.getId());
        emp.setStatus(1);
        emp.setManager_id(mng.getId());
        emp.setEmployee_type("Employee");
        employeeService.add(emp);
    }

    @RequestMapping(value = "/createClient", method = RequestMethod.GET)
    public void createClient(){
        Client cl = new Client();
        cl.setName("Vũ Hoàng Hương");
        cl.setEmail("vuong@gmail.com");
        cl.setPhone_number("0976765446");
        cl.setAddress("Biên Hòa, Đồng Nai");
        clService.add(cl);
    }

    @RequestMapping(value = "/createVehicles", method = RequestMethod.GET)
    public void createVehicles(){
        Vehicle v1 = new Vehicle("Uber X");
        v1.setCalculatable(true);
        v1.setStatus(1);
        Vehicle v2 = new Vehicle("Uber Black");
        v2.setCalculatable(true);
        v2.setStatus(1);
        Vehicle v3 = new Vehicle("Uber SUV");
        v3.setCalculatable(true);
        v3.setStatus(1);
        Vehicle v4 = new Vehicle("Uber MOTO");
        v4.setCalculatable(true);
        v4.setStatus(1);
        Vehicle v5 = new Vehicle("Grab Bike");
        v5.setCalculatable(true);
        v5.setStatus(1);
        Vehicle v6 = new Vehicle("Grab Bike Premium");
        v6.setCalculatable(true);
        v6.setStatus(1);
        Vehicle v7 = new Vehicle("Grab Car 4 seats");
        v7.setCalculatable(true);
        v7.setStatus(1);
        Vehicle v8 = new Vehicle("Grab Car 7 seats");
        v8.setCalculatable(true);
        v8.setStatus(1);
        Vehicle v9 = new Vehicle("Air plane");
        v9.setCalculatable(true);
        v9.setStatus(1);
        Vehicle v10 = new Vehicle("Train");
        v10.setCalculatable(true);
        v10.setStatus(1);
        Vehicle v11 = new Vehicle("Boat");
        v11.setCalculatable(true);
        v11.setStatus(1);
        vhcService.add(v1);
        vhcService.add(v2);
        vhcService.add(v3);
        vhcService.add(v4);
        vhcService.add(v5);
        vhcService.add(v6);
        vhcService.add(v7);
        vhcService.add(v8);
        vhcService.add(v9);
        vhcService.add(v10);
        vhcService.add(v11);
    }

    @RequestMapping(value = "/createSpecialPlaces",method = RequestMethod.GET)
    public void createSpecialPlaces(){
        SpecialPlace sp = new SpecialPlace();
        sp.setName("Cat Lai Harbor");
        sp.setType("Harbor");
        sp.setRange(200.0);
        sp.setLatitude(10.755063);
        sp.setLongitude(106.7883273);
        sp.setStatus(1);
        spService.add(sp);

        sp.setName("Tan Son Nhat Airport");
        sp.setType("Airport");
        sp.setRange(1000.0);
        sp.setLatitude(10.8184631);
        sp.setLongitude(106.6566358);
        sp.setStatus(1);
        spService.add(sp);

        sp.setName("Saigon Train Station");
        sp.setType("Train Station");
        sp.setRange(500.0);
        sp.setLatitude(10.7823643);
        sp.setLongitude(106.6749899);
        sp.setStatus(1);
        spService.add(sp);
    }


    @RequestMapping(value = "/populateData", method = RequestMethod.GET)
    public void populateData() throws ParseException {
        coorService.deleteAll();
    }

    /*
    formulas
    Grab Car 7 seats
    if s<=2  24 + 0.3*t/60
    else 24 + 11*s + 0.3*t/60

    Grab Car 4 seats
    if s<=2 20 + 0.3*t/60
    else 20 + 9*s + 0.3*t/60

    Grab Bike Premium
    if s<=2 20
    else 20 + 7*(s-2)

    Grab Bike
    if s<=2 12
    else 12 + 3.8*(s-2)

    UberMoto
    10 + 3.7*s + 0.2*t/60

    Uber SUV
    5 + 9.597*s + 0.8*t/60

    Uber Black
    5 + 9.597*s + 0.8*t/60

    Uber X
    15 + 7.5*s + 0.3*t/60

    */
}
