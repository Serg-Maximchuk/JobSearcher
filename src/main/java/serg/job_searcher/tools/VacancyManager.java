package serg.job_searcher.tools;

import java.util.LinkedList;
import java.util.List;
import serg.job_searcher.browsers.WebBrowser;
import serg.job_searcher.entities.Vacancy;

public class VacancyManager {

	private static final String MESSAGE_CLASS = "mtmb";
	private static final String MESSAGE_TEXT = "text-center";
	private static final String ACTIVE_VAC_CLASS = "navNewVacCount";
	private static final String VAC_CLASS = "item";
	
	private List<Vacancy> vacancies;
	private int newVacCount;
	private int activeVacCount;
	private int actualVacCount;
	private LinkedList<Integer> vacCountMesList;
	private List<String> mesList;
	private HTML_Interpretator<?> driverInter;
	private WebBrowser browser;
	private boolean testRegime;
	private int pageCount = 1;
	
	public void setRegime(int regime) {
		if (regime == 1) testRegime = true;
	}

	public void controlBrowser(WebBrowser browser) {
		this.browser = browser;
		driverInter = new HTML_Interpretator<>(browser.getDriver());
	}

	public void getVacCountList() {
		intializeVacCountFromMessages();
		for (String message : mesList) {
			ConsoleSpeaker.printMessage(message);
		}
		newVacCount = getNewVacCount();
		activeVacCount = getActiveVacCount();
		actualVacCount = getActualVacCount();
		if (testRegime) {
			ConsoleSpeaker.askTestVacNum();
			int number = ConsoleSpeaker.getInt();
			while (number > 50 || number < 1) {
				ConsoleSpeaker.incorrect();
				number = ConsoleSpeaker.getInt();
			}
			newVacCount = number;
		} else if (newVacCount == actualVacCount) {
			// There is no new vacancies.
			newVacCount = 0;
		}
		vacancies = generateVacList(newVacCount);
	}

	public void intializeVacCountFromMessages() {
		vacCountMesList = new LinkedList<>();
		List<HTML_Interpretation> interList = driverInter.getListFromClass(MESSAGE_CLASS);
		mesList = new LinkedList<>();
		for (HTML_Interpretation interElement : interList) {
			mesList.add(interElement.getTextInClass(MESSAGE_TEXT));
		}
		for (String message : mesList) {
			vacCountMesList.add(Integer.valueOf(message.replaceAll("[^0-9]+", " ").trim()));
		}
	}

	public int getNewVacCount() {
		return vacCountMesList.getFirst();
	}

	public int getActiveVacCount() {
		try {
			String foundedNewVacStr = driverInter.getTextInClass(ACTIVE_VAC_CLASS);
			return Integer.valueOf(foundedNewVacStr);
		} catch (Exception e) {
			System.out.println("Can't get active vacancies. Is they exist?");
			return 0;
		}
	}

	public int getActualVacCount() {
		return vacCountMesList.getLast();
	}

	public void printStat() {
		ConsoleSpeaker.printVacancies(newVacCount, activeVacCount, actualVacCount);
	}

	public boolean isNewVacExist() {
		return (newVacCount > 0) ? true : false;
	}

	public List<Vacancy> generateVacList(int vacNumber) {
		if (vacNumber == 0) {
			return null;
		}
		if (vacNumber > 20) {
			List<Vacancy> vacList = VacancyExtractor.extract(driverInter.getListFromClass(VAC_CLASS), 20);
			int delta = vacNumber - 20;
			ConsoleSpeaker.needSee(delta);
			VacancyManager man = new VacancyManager();
			man.setPageCount(pageCount);
			man.increasePageCount();
			man.controlBrowser(goToNextPage());
			vacList.addAll(man.generateVacList(delta));
			return vacList;
		} else {
			List<Vacancy> vacList = VacancyExtractor.extract(driverInter.getListFromClass(VAC_CLASS), vacNumber);
			return vacList;
		}
	}
	
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	
	public void increasePageCount() {
		pageCount++;
	}

	public WebBrowser goToNextPage() {
		StringBuilder currentURL = new StringBuilder(100);
		currentURL.append(browser.getCurrURL());
		currentURL.append("?pg=");
		currentURL.append(pageCount);
		browser.open(currentURL.toString());
		return browser;
	}

	public void printNewVacPositions() {
		VacancyPrinter.printPositions(vacancies);
	}

	public int askPosition() {
		ConsoleSpeaker.askPosition();
		int number = ConsoleSpeaker.getInt();
		while (number > newVacCount || number < 0) {
			ConsoleSpeaker.incorrect();
			number = askPosition();
		}
		return number;
	}

	public void printVacancy(int number) {
		VacancyPrinter.printVacancy(number, vacancies);
	}

	public void printNewVacList() {
		VacancyPrinter.printList(vacancies);
	}
	
	public void closeBrowser() {
		browser.close();
	}
}