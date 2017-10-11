package capgemini.webappdemo.controllers.Web;

import capgemini.webappdemo.domain.Admin;
import capgemini.webappdemo.form.LoginForm;
import capgemini.webappdemo.service.Admin.AdminService;
import capgemini.webappdemo.utils.LoginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class Home {
    @Autowired
    private AdminService adminService;

    private LoginUtil loginUtil = new LoginUtil();

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(Model model){
        LoginForm lgf = new LoginForm();
        model.addAttribute("lgf", lgf);
        return "web/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPostPage(HttpServletRequest request, @ModelAttribute LoginForm lgf, Errors errors, Model model){
        if(errors.hasErrors()){
            return "web/login";
        } else{
            String usn = lgf.getUsername();
            String pwd = lgf.getPassword();
            Admin admin = adminService.login(usn,pwd);
            if(admin == null){
                model.addAttribute("error","Invalid username or password");
                return "web/login";
            } else{
                HttpSession session = request.getSession();
                session.setAttribute("admin",usn);
                return "redirect:/home";
            }
        }
    }

    @RequestMapping(value = {"/","/home"}, method = RequestMethod.GET)
    public String homePage(HttpServletRequest request, ModelMap model){
        if(!loginUtil.isLogin(request)){
            return "redirect:/login";
        } else{
            return "web/home";
        }
    }
}
