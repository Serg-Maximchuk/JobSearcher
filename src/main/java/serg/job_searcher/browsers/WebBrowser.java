package serg.job_searcher.browsers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import serg.job_searcher.tools.DriverWrap;

public interface WebBrowser {
	public WebBrowser open(String url);
	public WebDriver getDriver();
	public WebElement waitThenExtract(By byMethod);
	public String getCurrURL();
	public void close();
	public DriverWrap getDriverWrap();
}