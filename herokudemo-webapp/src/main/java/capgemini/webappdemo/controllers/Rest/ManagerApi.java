package capgemini.webappdemo.controllers.Rest;

import capgemini.webappdemo.domain.Employee;
import capgemini.webappdemo.domain.Message;
import capgemini.webappdemo.domain.User;
import capgemini.webappdemo.service.Employee.EmployeeService;
import capgemini.webappdemo.service.Manager.ManagerService;
import capgemini.webappdemo.service.User.UserService;
import capgemini.webappdemo.utils.JsonTokenUtil;
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

    private JsonTokenUtil jsonTokenUtil = new JsonTokenUtil();

    @RequestMapping(value = "/api/manager/getEmployees", method = RequestMethod.POST)
    public ResponseEntity<List<User>> getManagedUsers(@RequestBody Message msg){
        if(msg.getJson_token().equals("") || !jsonTokenUtil.validateKey(msg.getJson_token())){
            return new ResponseEntity<List<User>>(HttpStatus.BAD_REQUEST);
        }
        int id = jsonTokenUtil.getUserIdFromJsonKey(msg.getJson_token());
        List<Employee> emps = employeeService.getEmployeesByManagerId(id);
        List<User> users = new ArrayList<>();
        for(Employee emp : emps){
            User user = userService.get(emp.getUser_id());
            user.setPassword("");
            user.setUserType("Employee");
            users.add(user);
        }
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }


}
