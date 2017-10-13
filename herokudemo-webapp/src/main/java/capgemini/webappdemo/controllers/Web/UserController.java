package capgemini.webappdemo.controllers.Web;

import capgemini.webappdemo.domain.Employee;
import capgemini.webappdemo.domain.Manager;
import capgemini.webappdemo.domain.User;
import capgemini.webappdemo.form.EmployeeForm;
import capgemini.webappdemo.form.ManagerForm;
import capgemini.webappdemo.service.Employee.EmployeeService;
import capgemini.webappdemo.service.Manager.ManagerService;
import capgemini.webappdemo.service.User.UserService;
import capgemini.webappdemo.utils.LoginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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
                Manager info = getManagerInfo(mng.getUser_id());
                mng.setUsername(info.getUsername());
                mng.setEmail(info.getEmail());
                mng.setEmployees(info.getEmployees());
            }
            model.addAttribute("pageName","manager");
            model.addAttribute("mngs",mngs);
            return "web/user/manager";
        }
    }

    @RequestMapping(value = "/manager/insert", method = RequestMethod.GET)
    public String insertManagerGet(HttpSession session, ModelMap model){
        if(!loginUtil.isLogin(session)){
            return "redirect:/login";
        } else{
            model.addAttribute("pageName","manager");
            model.addAttribute("managerForm",new ManagerForm());
            return "web/user/manager_insert";
        }
    }

    @RequestMapping(value = "/manager/insert", method = RequestMethod.POST)
    public String insertManagerPost(@ModelAttribute("managerForm") @Valid ManagerForm managerForm, BindingResult result, ModelMap model){
        if(result.hasErrors()){
            return "redirect:web/user/manager_insert";
        } else{
            if(service.checkUserExist(managerForm.getUsername())){
                model.addAttribute("error","This username was used by another user");
                return "web/user/manager_insert";
            }
            User usr = new User();
            Manager mng = new Manager();
            usr.setUsername(managerForm.getUsername());
            usr.setPassword(managerForm.getPassword());
            usr.setEmail(managerForm.getEmail());
            usr.setUserType("Manager");
            usr.setStatus(1);
            service.add(usr);
            mng.setStatus(1);
            mng.setUser_id(usr.getId());
            mngService.add(mng);
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
                Employee info = getEmployeeInfo(emp.getUser_id());
                emp.setUsername(info.getUsername());
                emp.setEmail(info.getEmail());
                emp.setManager_name(info.getManager_name());
            }
            model.addAttribute("emps",emps);
            return "web/user/employee";
        }
    }

    @RequestMapping(value = "/employee/insert", method = RequestMethod.GET)
    public String insertEmployeeGet(HttpSession session, ModelMap model){
        if(!loginUtil.isLogin(session)){
            return "redirect:/login";
        } else{
            List<Manager> mngs = mngService.getAll();
            model.addAttribute("mngs",mngs);
            model.addAttribute("employeeForm",new EmployeeForm());
            return "web/user/employee_insert";
        }
    }

    @RequestMapping(value = "/employee/insert", method = RequestMethod.POST)
    public String insertEmployeePost(@ModelAttribute("employeeForm") @Valid EmployeeForm employeeForm, BindingResult result, ModelMap model){
        if(result.hasErrors()){
            return "web/user/employee_insert";
        }
        if(service.checkUserExist(employeeForm.getUsername())){
            model.addAttribute("error","This username was used by another user");
            return "web/user/employee_insert";
        }
        User usr = new User();
        usr.setUserType("Employee");
        usr.setUsername(employeeForm.getUsername());
        usr.setPassword(employeeForm.getPassword());
        usr.setEmail(employeeForm.getEmail());
        usr.setStatus(1);
        service.add(usr);
        Employee emp = new Employee();
        emp.setStatus(1);
        emp.setUser_id(usr.getId());
        emp.setManager_id(employeeForm.getManager_id());
        empService.add(emp);
        return "web/user/employee";
    }

    @RequestMapping(value = "/user/details", method = RequestMethod.GET, params = {"type","id"})
    public String getUserDetails(HttpSession session, @RequestParam("type") String type, @RequestParam("id") int id,ModelMap model){
        if(!loginUtil.isLogin(session)){
            return "redirect:/login";
        } else{
            if(type.equals("manager")){
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
        for(Employee emp : emps){
            User usr = service.get(emp.getUser_id());
            emp.setUsername(usr.getUsername());
        }
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
