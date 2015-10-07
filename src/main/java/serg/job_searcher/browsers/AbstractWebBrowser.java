package serg.job_searcher.browsers;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import serg.job_searcher.tools.DriverWrap;
import serg.job_searcher.tools.DriverWrapper;

abstract class AbstractWebBrowser implements WebBrowser {
	
	protected WebDriver driver;
	protected DriverWrap driverWrap;

	@Override
	public WebBrowser open(String url) {
		driver.get(url);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	@Override
	public DriverWrap getDriverWrap() {
		if (driverWrap == null) {
			driverWrap = new DriverWrapper<>(driver);
		}
		return driverWrap;
	}
	
	@Override
	public WebDriver getDriver() {
		return driver;
	}
	
	@Override
	public WebElement waitThenExtract(By extractMethod) throws TimeoutException {
		WebDriverWait wait = new WebDriverWait(driver, 3);
		return wait.until(ExpectedConditions.elementToBeClickable(extractMethod));
	}
	
	@Override
	public String getCurrURL() {
		return driver.getCurrentUrl();
	}
	
	@Override
	public void close() {
		driver.close();
	}
}