package serg.job_searcher.browsers;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;

public class FirefoxBrowser extends AbstractWebBrowser {
	
	private static final String PROFILE_NAME = "default";
	
	public FirefoxBrowser() {
		ProfilesIni allProfiles = new ProfilesIni();
		FirefoxProfile profile = allProfiles.getProfile(PROFILE_NAME);
		driver = new FirefoxDriver(profile);
	}
}