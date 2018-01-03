package capgemini.webappdemo.controllers.Web;

import capgemini.webappdemo.domain.Employee;
import capgemini.webappdemo.domain.Manager;
import capgemini.webappdemo.domain.User;
import capgemini.webappdemo.domain.UserAppointmentView;
import capgemini.webappdemo.form.EmployeeForm;
import capgemini.webappdemo.form.ManagerForm;
import capgemini.webappdemo.service.Employee.EmployeeService;
import capgemini.webappdemo.service.Manager.ManagerService;
import capgemini.webappdemo.service.User.UserService;
import capgemini.webappdemo.service.UserAppointmentView.UserAppointmentViewService;
import capgemini.webappdemo.utils.LoginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
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

    @Autowired
    private UserAppointmentViewService uavService;

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
                mng.setFull_name(info.getFull_name());
                mng.setUsername(info.getUsername());
                mng.setEmail(info.getEmail());
                mng.setEmployees(info.getEmployees());
                mng.setStatus(info.getStatus());
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
            return "web/user/manager_insert";
        } else{
            if(service.checkUserExist(managerForm.getUsername())){
                model.addAttribute("error","This username was used by another user");
                return "web/user/manager_insert";
            }
            String email = managerForm.getEmail();
            if(!email.isEmpty() && !isValidEmailAddress(email)){
                model.addAttribute("error","Ivalid email address");
                return "web/user/manager_insert";
            }
            User usr = new User();
            Manager mng = new Manager();
            usr.setUsername(managerForm.getUsername());
            usr.setPassword(managerForm.getPassword());
            usr.setEmail(email);
            usr.setFullname(managerForm.getFull_name());
            usr.setUserType("Manager");
            usr.setStatus(1);
            service.add(usr);
            mng.setStatus(1);
            mng.setUser_id(usr.getId());
            mngService.add(mng);
            return "redirect:/managers";
        }
    }

    @RequestMapping(value = "/manager/update", method = RequestMethod.GET,params = "id")
    public String updateManagerGet(HttpSession session, ModelMap model,@RequestParam("id") int id){
        if(!loginUtil.isLogin(session)){
            return "redirect:/login";
        } else{
            User usr = service.get(id);
            ManagerForm managerForm = new ManagerForm();
            managerForm.setEmail(usr.getEmail());
            managerForm.setPassword(usr.getPassword());
            managerForm.setUsername(usr.getUsername());
            managerForm.setFull_name(usr.getFullname());
            managerForm.setStatus(usr.getStatus());
            model.addAttribute("username",usr.getUsername());
            model.addAttribute("pageName","manager");
            model.addAttribute("managerForm",managerForm);
            return "web/user/manager_update";
        }
    }

    @RequestMapping(value = "/manager/update", method = RequestMethod.POST,params = "id")
    public String updateManagerPost(@ModelAttribute("managerForm") @Valid ManagerForm managerForm, BindingResult result, ModelMap model,@RequestParam("id") int id){
        User usr = service.get(id);
        model.addAttribute("username",usr.getUsername());
        if(result.hasErrors()){
            return "web/user/manager_update";
        }
        usr.setUserType("Manager");
        String email = managerForm.getEmail();
        if(!email.isEmpty() && !isValidEmailAddress(email)){
            model.addAttribute("error","Ivalid email address");
            return "web/user/manager_update";
        } else{
            usr.setEmail(email);
            usr.setStatus(managerForm.getStatus());
            usr.setFullname(managerForm.getFull_name());
            service.update(usr);
            Manager mng = mngService.get(id);
            mng.setStatus(managerForm.getStatus());
            mng.setUser_id(usr.getId());
            mngService.update(mng);
            return "redirect:/managers";
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
                emp.setFull_name(info.getFull_name());
                emp.setUsername(info.getUsername());
                emp.setEmail(info.getEmail());
                emp.setManager_name(info.getManager_name());
                emp.setStatus(info.getStatus());
            }
            model.addAttribute("pageName","employee");
            model.addAttribute("emps",emps);
            return "web/user/employee";
        }
    }

    @RequestMapping(value = "/employee/insert", method = RequestMethod.GET)
    public String insertEmployeeGet(HttpSession session, ModelMap model){
        if(!loginUtil.isLogin(session)){
            return "redirect:/login";
        } else{
            /*List<Manager> mngs = mngService.getAll();
            for(Manager mng : mngs){
                User usr = service.get(mng.getUser_id());
                mng.setUsername(usr.getUsername());
            }
            model.addAttribute("mngs",mngs);*/
            model.addAttribute("pageName","employee");
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
        String email = employeeForm.getEmail();
        if(!email.isEmpty() && !isValidEmailAddress(email)){
            model.addAttribute("error","Ivalid email address");
            return "web/user/manager_insert";
        } else{
            User usr = new User();
            usr.setUserType("Employee");
            usr.setUsername(employeeForm.getUsername());
            usr.setPassword(employeeForm.getPassword());
            usr.setEmail(employeeForm.getEmail());
            usr.setStatus(1);
            usr.setFullname(employeeForm.getFull_name());
            service.add(usr);
            Employee emp = new Employee();
            emp.setStatus(1);
            emp.setUser_id(usr.getId());
            emp.setManager_id(employeeForm.getManager_id());
            empService.add(emp);
            return "redirect:/employees";
        }

    }

    @RequestMapping(value = "/employee/update", method = RequestMethod.GET,params = "id")
    public String updateEmployeeGet(HttpSession session, ModelMap model,@RequestParam("id") int id){
        if(!loginUtil.isLogin(session)){
            return "redirect:/login";
        } else{
            User usr = service.get(id);
            Employee emp = empService.get(id);
            EmployeeForm employeeForm = new EmployeeForm();
            employeeForm.setEmail(usr.getEmail());
            employeeForm.setUsername(usr.getUsername());
            employeeForm.setFull_name(usr.getFullname());
            employeeForm.setManager_id(emp.getManager_id());
            employeeForm.setStatus(usr.getStatus());
            model.addAttribute("username",usr.getUsername());
            model.addAttribute("pageName","employee");
            model.addAttribute("employeeForm",employeeForm);
            return "web/user/employee_update";
        }
    }

    @RequestMapping(value = "/employee/update", method = RequestMethod.POST,params = "id")
    public String updateEmployeePost(@ModelAttribute("employeeForm") @Valid EmployeeForm employeeForm, BindingResult result, ModelMap model,@RequestParam("id") int id){
        User usr = service.get(id);
        model.addAttribute("username",usr.getUsername());
        if(result.hasErrors()){
            return "web/user/employee_update";
        }
        String email = employeeForm.getEmail();
        if(!email.isEmpty() && !isValidEmailAddress(email)){
            model.addAttribute("error","Ivalid email address");
            return "web/user/manager_update";
        } else{
            usr.setUserType("Employee");
            usr.setEmail(employeeForm.getEmail());
            usr.setStatus(employeeForm.getStatus());
            usr.setFullname(employeeForm.getFull_name());
            service.update(usr);
            Employee emp = empService.get(id);
            emp.setStatus(employeeForm.getStatus());
            emp.setUser_id(usr.getId());
            emp.setManager_id(employeeForm.getManager_id());
            empService.update(emp);
            return "redirect:/employees";
        }
    }

    @ModelAttribute("mngSelectList")
    public List<Manager> getManagerList(){
        List<Manager> mngs = mngService.getAll();
        for(Manager mng : mngs){
            User usr = service.get(mng.getUser_id());
            mng.setUsername(usr.getFullname());
        }
        return mngs;
    }

    @RequestMapping(value = "/user/details", method = RequestMethod.GET, params = {"type","id"})
    public String getUserDetails(HttpSession session, @RequestParam("type") String type, @RequestParam("id") int id,ModelMap model){
        if(!loginUtil.isLogin(session)){
            return "redirect:/login";
        } else{
            List<UserAppointmentView> uavs = service.getAllAppointments(id);
            if(type.equals("manager")){
                Manager mng = getManagerInfo(id);
                model.addAttribute("id",id);
                model.addAttribute("mng",mng);
                model.addAttribute("total",uavs.size());
                model.addAttribute("created",mngService.getCreatedAppointments(id));
                model.addAttribute("pageName","manager");
                return "web/user/manager_detail";
            } else{
                Employee emp = getEmployeeInfo(id);
                model.addAttribute("id",id);
                model.addAttribute("emp",emp);
                model.addAttribute("total",uavs.size());
                model.addAttribute("pageName","employee");
                return "web/user/employee_detail";
            }
        }
    }

    @RequestMapping(value = "/user/chart/month", method = RequestMethod.GET, params = {"year","id","isCreated"})
    public String drawApmChartMonth(HttpSession session, @RequestParam("year") int year, @RequestParam("id") int id,@RequestParam("isCreated") Boolean isCreated,  Model model){
        if(!loginUtil.isLogin(session)){
            return "redirect:/login";
        } else{
            model.addAttribute("id",id);
            model.addAttribute("year",year);
            model.addAttribute("isCreated",isCreated);
            return "web/appointment/apms_by_month_chart";
        }
    }

    @RequestMapping(value = "/user/chart/year", method = RequestMethod.GET, params = {"from","to","id","isCreated"})
    public String drawApmChartYear(HttpSession session, @RequestParam("from") int from, @RequestParam("to") int to, @RequestParam("id") int id,@RequestParam("isCreated") Boolean isCreated,  Model model){
        if(!loginUtil.isLogin(session)){
            return "redirect:/login";
        } else{
            model.addAttribute("id",id);
            model.addAttribute("from",from);
            model.addAttribute("to",to);
            model.addAttribute("isCreated",isCreated);
            return "web/appointment/apms_by_year_chart";
        }
    }

    @RequestMapping(value = "/user/infos/month", method = RequestMethod.GET, params = {"year","id"})
    public String drawCostChartMonth(HttpSession session, @RequestParam("year") int year, @RequestParam("id") int id, ModelMap model){
        if(!loginUtil.isLogin(session)){
            return "redirect:/login";
        } else{
            model.addAttribute("id",id);
            model.addAttribute("year",year);
            return "web/detail/cost_by_month_chart";
        }
    }

    @RequestMapping(value = "/user/infos/year", method = RequestMethod.GET, params = {"year","id"})
    public String drawCostChartYear(HttpSession session, @RequestParam("from") int from, @RequestParam("to") int to, @RequestParam("id") int id, ModelMap model){
        if(!loginUtil.isLogin(session)){
            return "redirect:/login";
        } else{
            model.addAttribute("id",id);
            model.addAttribute("from",from);
            model.addAttribute("to",to);
            return "web/detail/cost_by_year_chart";
        }
    }

    @RequestMapping(value = "/user/infos", method = RequestMethod.GET, params = {"id"})
    public String getUserInfo(@RequestParam("id") int id,ModelMap model){
        model.addAttribute("id",id);
        return "web/user/user_infos";
    }

    private Manager getManagerInfo(int id){
        User user = service.get(id);
        Manager mng = mngService.get(id);
        List<Employee> emps = empService.getEmployeesByManagerId(id);
        for(Employee emp : emps){
            User usr = service.get(emp.getUser_id());
            emp.setFull_name(usr.getFullname());
        }
        mng.setFull_name(user.getFullname());
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
        emp.setFull_name(user.getFullname());
        emp.setManager_name(mng.getFullname());
        emp.setUsername(user.getUsername());
        emp.setEmail(user.getEmail());
        emp.setStatus(user.getStatus());
        return emp;
    }

    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }
}
