package serg.job_searcher.browsers;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeBrowser extends AbstractWebBrowser {
	
	private static final String WEB_DRIVER_PACKAGE = "webdriver.chrome.driver";
	private static final String WEB_DRIVER_PATH = "/home/serg/work/chromedriver/chromedriver";
	private static final String PATH_ATTRIBUTE = "user-data-dir=";
	private static final String PROFILE_PATH = "/home/serg/.config/google-chrome";
	private static final String MAXIMIZE = "--start-maximized";

	public ChromeBrowser() {
		System.setProperty(WEB_DRIVER_PACKAGE, WEB_DRIVER_PATH);
		ChromeOptions options = new ChromeOptions();
		options.addArguments(PATH_ATTRIBUTE + PROFILE_PATH);
		options.addArguments(MAXIMIZE);
		driver = new ChromeDriver(options);
	}
}