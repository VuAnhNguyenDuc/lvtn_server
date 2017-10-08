package capgemini.webappdemo.controllers;

import static capgemini.webappdemo.controllers.NavigationRegistry.VIEW_WEB_OVERVIEW;
import static capgemini.webappdemo.controllers.NavigationRegistry.redirectTo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/")
public class RootController {

	@RequestMapping(method = RequestMethod.GET)
	public String startApplication() {
		return redirectTo(VIEW_WEB_OVERVIEW);
	}

}