package capgemini.webappdemo.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginUtil {
    public boolean isLogin(HttpSession session){
        return !(session.getAttribute("admin") == null || session.getAttribute("admin").equals(""));
    }
}
