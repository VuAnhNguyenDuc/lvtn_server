package capgemini.webappdemo.controllers;

import static capgemini.webappdemo.controllers.NavigationRegistry.URL_OVERVIEW;
import static capgemini.webappdemo.controllers.NavigationRegistry.redirectTo;
import static capgemini.webappdemo.controllers.NavigationRegistry.URL_WEB;
import static capgemini.webappdemo.controllers.NavigationRegistry.VIEW_WEB_OVERVIEW;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import capgemini.webappdemo.domain.Name;
import capgemini.webappdemo.form.AddNameForm;
import capgemini.webappdemo.service.NameService;



/**
 * This is the controller serving requests from /web<br>
 * Here we will map views for entry level pages
 *
 */
@Controller
@RequestMapping(value = "/" + URL_WEB)
public class WebController {
	
	@Autowired
	private NameService nameService;

	/**
	 * Serves GET requests to /web/overview<br>
	 * This is where our {@link RootController} redirects requests for our
	 * webapp root page
	 * 
	 * @param model
	 *            object passed to the view with data we can retrieve from .jsp
	 * @return path to the JSP to use
	 */
	@RequestMapping(value = URL_OVERVIEW, method = RequestMethod.GET)
	public String showFrontpage(Model model) {
		
		List<Name> names = nameService.getAll();
		model.addAttribute("addNameForm", new AddNameForm());
		model.addAttribute("names", names);
		
		return VIEW_WEB_OVERVIEW;
	}
	
	/**
	 * Controller that handles the "addName" button click.
	 * Read the form, and add name to database if not null or empty.
	 * 
	 * @param addNameForm
	 * @param errors
	 * @param model
	 * @return
	 */
	@RequestMapping(value = URL_OVERVIEW, method = RequestMethod.POST, params = "addName")
	public String addName(@ModelAttribute AddNameForm addNameForm,
			Errors errors, Model model) {
		
		// Add name if not null or empty
		String name = addNameForm.getName();
		if(name != null && name.length() != 0){
			nameService.add(new Name(name));
		}
		
		// Go back to web/overview
		return redirectTo(URL_OVERVIEW);
	}
	
	/**
	 * Controller that handles the "deleteAll" button click event
	 * 
	 * @param addNameForm
	 * @param errors
	 * @param model
	 * @return
	 */
	@RequestMapping(value = URL_OVERVIEW, method = RequestMethod.POST, params = "deleteAll")
	public String deleteAll(@ModelAttribute AddNameForm addNameForm,
			Errors errors, Model model) {
		
		// Delete them all
		nameService.deleteAll();
		
		// Go back to web/overview
		return redirectTo(URL_OVERVIEW);
	}
	
	/**
	 * Simple controller taking a name from GET request. We want this to run
	 * our load testing. so that /web/overview/?name=addMe will add a name
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping(value = URL_OVERVIEW, method = RequestMethod.GET, params = "name")
	public String addName(@RequestParam(value="name") String name) {
		
		// Add name if not null or empty
		if(name != null && name.length() != 0){
			nameService.add(new Name(name));
		}
		
		// Go back to web/overview
		return redirectTo("/web/overview");
	}
}