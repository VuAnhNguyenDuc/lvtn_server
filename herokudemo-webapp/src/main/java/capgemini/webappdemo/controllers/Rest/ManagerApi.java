package capgemini.webappdemo.controllers.Rest;

import capgemini.webappdemo.domain.Employee;
import capgemini.webappdemo.domain.User;
import capgemini.webappdemo.service.Employee.EmployeeService;
import capgemini.webappdemo.service.Manager.ManagerService;
import capgemini.webappdemo.service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vu Anh Nguyen Duc on 10/10/2017.
 */
@RestController
public class ManagerApi {
    @Autowired
    private UserService userService;

    @Autowired
    private ManagerService managerService;

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(value = "/api/manager/{id}/getEmployees", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getManagedUsers(@PathVariable("id") int id){
        List<Employee> emps = employeeService.getEmployeesByManagerId(id);
        List<User> users = new ArrayList<>();
        for(Employee emp : emps){
            User user = userService.get(emp.getUser_id());
            user.setPassword("");
            users.add(user);
        }
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }


}
