package serg.job_searcher.tools;

import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public class DriverWrapper<T extends SearchContext> implements DriverWrap {

	private T webElement;

	public DriverWrapper(T webElement) {
		this.webElement = webElement;
	}
	
	@Override
	public DriverWrap getElByClass(String className) {
		return new DriverWrapper<>(getWebEByClass(className));
	}

	@Override
	public String getTextInClass(String className) {
		return getWebEByClass(className).getText();
	}

	@Override
	public List<DriverWrap> getListFromClass(String className) {
		List<DriverWrap> interList = new LinkedList<>();
		for (WebElement webE : getListEFromClass(className)) {
			interList.add(new DriverWrapper<>(webE));
		}
		return interList;
	}

	@Override
	public List<String> getTextListFromClass(String className) {
		List<String> textList = new LinkedList<>();
		for (WebElement webE : getListEFromClass(className)) {
			textList.add(webE.getText());
		}
		return textList;
	}
	
	private WebElement getWebEByClass(String className) {
		return webElement.findElement(By.className(className));
	}
	
	private List<WebElement> getListEFromClass(String className) {
		return webElement.findElements(By.className(className));
	}
}