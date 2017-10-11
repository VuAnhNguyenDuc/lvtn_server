package capgemini.webappdemo.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginUtil {
    public boolean isLogin(HttpServletRequest request){
        HttpSession session = request.getSession();
        return !(session.getAttribute("username") == null || session.getAttribute("username").equals(""));
    }

    public String checkLogin(HttpServletRequest request){
        if(!isLogin(request)){
            return "web/login";
        } else{
            return "";
        }
    }
}
