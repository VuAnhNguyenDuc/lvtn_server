package capgemini.webappdemo.controllers.Web;

import capgemini.webappdemo.domain.SpecialPlace;
import capgemini.webappdemo.service.SpecialPlace.SpecialPlaceService;
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
import java.util.List;

@Controller
public class SpecialPlaceController {
    @Autowired
    private SpecialPlaceService service;

    private LoginUtil loginUtil = new LoginUtil();

    @RequestMapping(value = "/specialPlaces", method = RequestMethod.GET)
    public String getSpecialPlaces(HttpSession session, ModelMap model){
        if(!loginUtil.isLogin(session)){
            return "redirect:/login";
        } else{
            List<SpecialPlace> sps = service.getAll();
            model.addAttribute("sps",sps);
            model.addAttribute("pageName","specialPlace");
            return "web/special_place/special_places";
        }
    }

    @RequestMapping(value = "/specialPlace/insert", method = RequestMethod.GET)
    public String insertSpecialPlaceGet(HttpSession session, ModelMap model){
        if(!loginUtil.isLogin(session)){
            return "redirect:/login";
        } else{
            SpecialPlace specialPlace = new SpecialPlace();
            model.addAttribute("specialPlace",specialPlace);
            model.addAttribute("pageName","specialPlace");
            return "web/special_place/special_place_insert";
        }
    }

    @RequestMapping(value = "/specialPlace/insert", method = RequestMethod.POST)
    public String insertSpecialPlacePost(@ModelAttribute("specialPlace") SpecialPlace specialPlace, BindingResult result, ModelMap model){
        if(result.hasErrors()){
            return "web/special_place/special_place_insert";
        } else{
            String name = specialPlace.getName();
            SpecialPlace checkExist = service.getPlaceByName(name);
            System.out.println(checkExist);
            if(checkExist == null){
                model.addAttribute("error","A place with the same name already existed");
                return "web/special_place/special_place_insert";
            } else{
                specialPlace.setStatus(1);
                service.add(specialPlace);
                return "redirect:/specialPlaces";
            }
        }
    }

    @RequestMapping(value = "/specialPlace/update", method = RequestMethod.GET, params = "id")
    public String updateSpecialPlaceGet(HttpSession session, ModelMap model,@RequestParam("id") int id){
        if(!loginUtil.isLogin(session)){
            return "redirect:/login";
        } else{
            SpecialPlace sp = service.get(id);
            model.addAttribute("specialPlace",sp);
            model.addAttribute("pageName","specialPlace");
            return "web/special_place/special_place_update";
        }
    }

    @RequestMapping(value = "/specialPlace/update", method = RequestMethod.POST, params = "id")
    public String updateSpecialPlacePost(@ModelAttribute("specialPlace") SpecialPlace specialPlace, BindingResult result, @RequestParam("id") int id, ModelMap model){
        if(result.hasErrors()){
            return "web/special_place/special_place_update";
        } else{
            String name = specialPlace.getName();
            SpecialPlace checkExist = service.getPlaceByName(name);
            System.out.println(checkExist);
            if(checkExist == null){
                model.addAttribute("error","A place with the same name already existed");
                return "web/special_place/special_place_update";
            } else{
                SpecialPlace sp = service.get(id);
                sp.setLatitude(specialPlace.getLatitude());
                sp.setLongitude(specialPlace.getLongitude());
                sp.setName(specialPlace.getName());
                sp.setRange(specialPlace.getRange());
                sp.setType(specialPlace.getType());
                sp.setStatus(specialPlace.getStatus());
                service.update(sp);
                return "redirect:/specialPlaces";
            }
        }
    }
}
