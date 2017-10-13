package capgemini.webappdemo.controllers.Rest;

import capgemini.webappdemo.domain.Employee;
import capgemini.webappdemo.domain.ID;
import capgemini.webappdemo.domain.User;
import capgemini.webappdemo.service.Employee.EmployeeService;
import capgemini.webappdemo.service.Manager.ManagerService;
import capgemini.webappdemo.service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/api/manager/getEmployees", method = RequestMethod.POST)
    public ResponseEntity<List<User>> getManagedUsers(@RequestBody ID id){
        if(id.getId() == 0){
            return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
        }
        List<Employee> emps = employeeService.getEmployeesByManagerId(id.getId());
        List<User> users = new ArrayList<>();
        for(Employee emp : emps){
            User user = userService.get(emp.getUser_id());
            user.setPassword("");
            users.add(user);
        }
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }


}
