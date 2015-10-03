package serg.job_searcher.browsers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import serg.job_searcher.tools.HTML_Interpretation;

public interface WebBrowser {
	public void open(String url);
	public WebDriver getDriver();
	public WebElement waitThenExtract(By byMethod);
	public String getCurrURL();
	public void close();
	public HTML_Interpretation getInterDriver();
}