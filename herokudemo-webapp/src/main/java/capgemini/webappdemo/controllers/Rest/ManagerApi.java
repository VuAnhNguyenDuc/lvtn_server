package capgemini.webappdemo.controllers.Rest;

import capgemini.webappdemo.domain.Employee;
import capgemini.webappdemo.domain.User;
import capgemini.webappdemo.service.Employee.EmployeeService;
import capgemini.webappdemo.service.Manager.ManagerService;
import capgemini.webappdemo.service.User.UserService;
import capgemini.webappdemo.utils.JsonTokenUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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
    private EmployeeService employeeService;

    private JsonTokenUtil jsonTokenUtil = new JsonTokenUtil();

    @RequestMapping(value = "/api/manager/getEmployees", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> getManagedUsers(@RequestBody JSONObject input){
        String jsonToken = input.get("json_token").toString();
        JSONObject result = new JSONObject();

        if(jsonToken.equals("") || !jsonTokenUtil.validateKey(jsonToken)){
            result.put("message",0);
            result.put("description","invalid json token");
        } else{
            int id = jsonTokenUtil.getUserIdFromJsonKey(jsonToken);
            if(userService.getUserType(id).equals("Employee")){
                result.put("message",0);
                result.put("description","this user does not have enough priveledges");
            } else{
                List<Employee> emps = employeeService.getEmployeesByManagerId(id);
                List<User> users = new ArrayList<>();
                JSONArray empList = new JSONArray();
                result.put("message",1);
                for(Employee emp : emps){
                    User user = userService.get(emp.getUser_id());
                    JSONObject obj = new JSONObject();
                    obj.put("id",user.getId());
                    obj.put("username",user.getUsername());
                    obj.put("fullname",user.getFullname());
                    obj.put("email",user.getEmail());
                    obj.put("status",user.getStatus());
                    obj.put("type",userService.getUserType(user.getId()));
                    empList.add(obj);
                }
                result.put("listEmployees",empList);
            }
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
