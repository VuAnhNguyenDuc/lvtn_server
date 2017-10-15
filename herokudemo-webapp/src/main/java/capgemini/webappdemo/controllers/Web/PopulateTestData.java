package capgemini.webappdemo.controllers.Web;

import capgemini.webappdemo.domain.Admin;
import capgemini.webappdemo.domain.Employee;
import capgemini.webappdemo.domain.Manager;
import capgemini.webappdemo.domain.User;
import capgemini.webappdemo.service.Admin.AdminService;
import capgemini.webappdemo.service.Employee.EmployeeService;
import capgemini.webappdemo.service.Manager.ManagerService;
import capgemini.webappdemo.service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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


    @RequestMapping(value = "/populateData", method = RequestMethod.GET)
    public void populateData(){
        Admin admin = new Admin();
        admin.setUsername("admin");
        admin.setPassword("admin");
        adminService.add(admin);

        User mng = new User();
        mng.setUsername("mng");
        mng.setPassword("mng");
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
        emp.setEmail("emp@gmail.com");
        userService.add(emp);

        Employee empE = new Employee();
        empE.setUser_id(emp.getId());
        empE.setStatus(1);
        empE.setManager_id(mng.getId());
        empE.setEmployee_type("Employee");
    }
}
