package capgemini.webappdemo.controllers.Web;

import capgemini.webappdemo.domain.Client;
import capgemini.webappdemo.service.Client.ClientService;
import capgemini.webappdemo.utils.LoginUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
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
public class ClientController {
    @Autowired
    private ClientService service;
    private LoginUtil loginUtil = new LoginUtil();

    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    public String getClients(HttpSession session, ModelMap model){
        if(!loginUtil.isLogin(session)){
            return "redirect:/login";
        } else{
            model.addAttribute("pageName","client");
            List<Client> cls = service.getAll();
            model.addAttribute("cls",cls);
            return "web/client/client";
        }
    }

    @RequestMapping(value = "/client/insert", method = RequestMethod.GET)
    public String insertClientGet(HttpSession session, ModelMap model){
        if(!loginUtil.isLogin(session)){
            return "redirect:/login";
        } else{
            Client client = new Client();
            model.addAttribute("client",client);
            return "web/client/client_insert";
        }
    }

    @RequestMapping(value = "/client/insert", method = RequestMethod.POST)
    public String insertClientPost(@ModelAttribute("client") @Valid Client client, BindingResult result, ModelMap model){
        if(result.hasErrors()){
            return "web/client/client_insert";
        } else{
            String name = client.getName();
            /*String phone = client.getPhone_number();
            String address = client.getAddress();
            String email = client.getEmail();
            int user_create = client.getUser_create_id();*/

            Client cl = service.checkClientByName(name);

            if(cl != null){
                model.addAttribute("error","this client was created in the database");
                return "web/client/client_insert";
            } else{
                if(!isValidEmailAddress(client.getEmail())){
                    model.addAttribute("error","Ivalid email address");
                    return "web/client/client_insert";
                } else if(!validatePhone(client.getPhone_number())){
                    model.addAttribute("error","Ivalid phone number");
                    return "web/client/client_insert";
                }
                service.add(client);
                return "redirect:/clients";
            }
        }
    }

    @RequestMapping(value = "/client/update", method = RequestMethod.GET,params = "client_id")
    public String updateClientGet(HttpSession session,@RequestParam("client_id") int id,ModelMap model){
        if(!loginUtil.isLogin(session)){
            return "redirect:/login";
        }
        Client client = service.get(id);
        model.addAttribute("client",client);
        return "web/client/client_update";
    }

    @RequestMapping(value = "/client/update", method = RequestMethod.POST, params = "client_id")
    public String updateClientPost(@ModelAttribute("client") Client client,BindingResult result,@RequestParam("client_id") int id, ModelMap model){
        if(result.hasErrors()){
            return "web/client/client_update";
        } else{
            if(!isValidEmailAddress(client.getEmail())){
                model.addAttribute("error","Ivalid email address");
                return "web/client/client_update";
            } else if(!validatePhone(client.getPhone_number())){
                model.addAttribute("error","Ivalid phone number");
                return "web/client/client_update";
            } else{
                Client old = service.get(id);
                old.setPhone_number(client.getPhone_number());
                old.setAddress(client.getAddress());
                old.setEmail(client.getEmail());
                old.setName(client.getName());
                service.update(old);
                return "redirect:/clients";
            }
        }
    }

    public boolean validatePhone(String input){
        //matches numbers only
        String regexStr = "^[0-9 ]*$";
        if(input.equals("")){
            return false;
        } else if(input.matches(regexStr)){
            int blanks = StringUtils.countOccurrencesOf(input," ");
            // less than 10 numbers
            if(input.length() - blanks < 10){
                return false;
            }
            return true;
        } else{
            return false;
        }
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
