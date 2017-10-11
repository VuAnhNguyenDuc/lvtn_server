package capgemini.webappdemo.controllers.Web;

import capgemini.webappdemo.domain.Employee;
import capgemini.webappdemo.domain.Manager;
import capgemini.webappdemo.domain.User;
import capgemini.webappdemo.service.Employee.EmployeeService;
import capgemini.webappdemo.service.Manager.ManagerService;
import capgemini.webappdemo.service.User.UserService;
import capgemini.webappdemo.utils.LoginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private EmployeeService empService;

    @Autowired
    private ManagerService mngService;

    private LoginUtil loginUtil = new LoginUtil();

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String getUsers(HttpSession session, ModelMap model){
        if(!loginUtil.isLogin(session)){
            return "redirect:/login";
        } else{
            List<User> users = service.getAll();
            for(User user : users){
                user.setUserType(service.getUserType(user.getId()));
            }
            model.addAttribute("users",users);
            return "web/user/user";
        }
    }

    @RequestMapping(value = "/managers", method = RequestMethod.GET)
    public String getManagers(HttpSession session, ModelMap model){
        if(!loginUtil.isLogin(session)){
            return "redirect:/login";
        } else{
            List<Manager> mngs = mngService.getAll();
            for(Manager mng : mngs){
                mng = getManagerInfo(mng.getUser_id());
            }
            model.addAttribute("mngs",mngs);
            return "web/user/manager";
        }
    }

    @RequestMapping(value = "/employees", method = RequestMethod.GET)
    public String getEmployees(HttpSession session, ModelMap model){
        if(!loginUtil.isLogin(session)){
            return "redirect:/login";
        } else{
            List<Employee> emps = empService.getAll();
            for(Employee emp : emps){
                emp = getEmployeeInfo(emp.getUser_id());
            }
            model.addAttribute("emps",emps);
            return "web/user/employee";
        }
    }

    @RequestMapping(value = "/users/details", method = RequestMethod.GET, params = {"type","id"})
    public String getUserDetails(HttpSession session, @RequestParam("type") String type, @RequestParam("id") int id,ModelMap model){
        if(!loginUtil.isLogin(session)){
            return "redirect:/login";
        } else{
            User user = service.get(id);
            if(type.equals("manager")){
                List<Employee> emps = empService.getEmployeesByManagerId(id);
                Manager mng = getManagerInfo(id);
                model.addAttribute("id",id);
                model.addAttribute("mng",mng);
                return "web/user/manager_detail";
            } else{
                Employee emp = getEmployeeInfo(id);
                model.addAttribute("id",id);
                model.addAttribute("emp",emp);
                return "web/user/employee_detail";
            }
        }
    }

    private Manager getManagerInfo(int id){
        User user = service.get(id);
        Manager mng = mngService.get(id);
        List<Employee> emps = empService.getEmployeesByManagerId(id);
        mng.setUsername(user.getUsername());
        mng.setEmail(user.getEmail());
        mng.setEmployees(emps);
        mng.setStatus(user.getStatus());
        return mng;
    }

    private Employee getEmployeeInfo(int id){
        User user = service.get(id);
        Employee emp = empService.get(id);
        User mng = service.get(emp.getManager_id());
        emp.setManager_name(mng.getUsername());
        emp.setUsername(user.getUsername());
        emp.setEmail(user.getEmail());
        emp.setStatus(user.getStatus());
        return emp;
    }
}
