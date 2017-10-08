package capgemini.webappdemo.controllers;

public class NavigationRegistry {

	/*
	 * URL's
	 */
	public static final String URL_WEB = "web";
	public static final String URL_OVERVIEW = "overview";
	
	/*
	 * Views
	 */
	public static final String VIEW_WEB_OVERVIEW = "web/overview";

	/*
	 * Convenience methods
	 */
	public static final String redirectTo(String url) {
		return "redirect:" + url;
	}

	public static final String url(String... parts) {
		String result = "";
		for (String string : parts) {
			result += "/" + string;
		}
		return result;
	}


}
