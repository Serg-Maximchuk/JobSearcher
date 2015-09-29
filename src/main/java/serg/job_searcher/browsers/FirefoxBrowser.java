package serg.job_searcher.browsers;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;

public class FirefoxBrowser extends AbstractWebBrowser {
	
	private static final String PROFILE_PATH = "/home/serg/.mozilla/firefox/i4s1fdtb.default";
	
	public FirefoxBrowser() {
		FirefoxProfile profile = new ProfilesIni().getProfile(PROFILE_PATH);
		driver = new FirefoxDriver(profile);
	}
}