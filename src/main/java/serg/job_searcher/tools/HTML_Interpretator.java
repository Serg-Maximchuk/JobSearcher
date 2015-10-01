package serg.job_searcher.tools;

import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public class HTML_Interpretator<T extends SearchContext> implements HTML_Interpretation {

	private T webElement;

	public HTML_Interpretator(T webElement) {
		this.webElement = webElement;
	}
	
	@Override
	public HTML_Interpretation getElByClass(String className) {
		return new HTML_Interpretator<>(getWebEByClass(className));
	}

	@Override
	public String getTextInClass(String className) {
		return getWebEByClass(className).getText();
	}

	@Override
	public List<HTML_Interpretation> getListFromClass(String className) {
		List<HTML_Interpretation> interList = new LinkedList<>();
		for (WebElement webE : getListEFromClass(className)) {
			interList.add(new HTML_Interpretator<>(webE));
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