package serg.job_searcher.tools;

import java.util.LinkedList;
import java.util.List;

import serg.job_searcher.browsers.ChromeBrowser;
import serg.job_searcher.browsers.FirefoxBrowser;
import serg.job_searcher.browsers.WebBrowser;
import serg.job_searcher.entities.Vacancy;

public class VacancyManager {

	private static final String VACANCIES_URL = "http://rabota.ua/jobsearch/notepad/vacancies_profile";

	private static final String MESSAGE_CLASS = "mtmb";
	private static final String MESSAGE_TEXT = "text-center";
	private static final String ACTIVE_VAC_CLASS = "navNewVacCount";
	private static final String VAC_CLASS = "item";
	private static final String PAGE_ARG = "?pg=";

	private List<Vacancy> vacancies;
	private int newVacCount;
	private int activeVacCount;
	private int actualVacCount;
	private LinkedList<Integer> vacCountMesList;
	private List<String> messages;
	private HTML_Interpretation driverInter;
	private WebBrowser browser;
	private boolean testRegime;
	private int pageCount = 1;
	private boolean hadToBeClosed;

	private static class ManagerHelper {

		public static boolean findOutRegime() {
			return (ConsoleSpeaker.askRegime() == 1) ? true : false;
		}

		public static WebBrowser findOutBrowser() {
			return (ConsoleSpeaker.askBrowser() == 1) ? new ChromeBrowser() : new FirefoxBrowser();
		}
	}

	public void initialize() {
		testRegime = ManagerHelper.findOutRegime();
		browser = ManagerHelper.findOutBrowser();
		browser.open(VACANCIES_URL);
		this.driverInter = browser.getInterDriver();
		intializeVacCountFromMessages();
	}

	public void setDriverInter(HTML_Interpretation driverInter) {
		this.driverInter = driverInter;
	}

	public void intializeVacCountFromMessages() {
		vacCountMesList = new LinkedList<>();
		for (String message : getMessages()) {
			vacCountMesList.add(Integer.valueOf(message.replaceAll("[^0-9]+", " ").trim()));
		}
	}

	public List<String> getMessages() {
		List<String> messages = new LinkedList<>();
		List<HTML_Interpretation> messagesInterList = driverInter.getListFromClass(MESSAGE_CLASS);
		for (HTML_Interpretation interElement : messagesInterList) {
			messages.add(interElement.getTextInClass(MESSAGE_TEXT));
		}
		return messages;
	}

	public void getVacCountList() {
		for (String message : messages) {
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

	public List<Vacancy> generateVacList(int vacCount) {
		if (vacCount == 0) {
			return null;
		}
		// the last element (21-st) is page switcher, so we need only 20
		List<HTML_Interpretation> allVacOnPage = driverInter.getListFromClass(VAC_CLASS).subList(0, 20);
		int vacOnPageCount = allVacOnPage.size();
		if (vacCount > vacOnPageCount) {
			List<Vacancy> vacList = VacancyExtractor.extract(allVacOnPage);
			int delta = vacCount - vacOnPageCount;
			ConsoleSpeaker.needSee(delta);
			VacancyManager man = new VacancyManager();
			man.setPageCount(++pageCount);
			man.setDriverInter(goToNextPage().getInterDriver());
			vacList.addAll(man.generateVacList(delta));
			return vacList;
		} else {
			return VacancyExtractor.extract(allVacOnPage.subList(0, vacCount));
		}
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public WebBrowser goToNextPage() {
		StringBuilder newURL = new StringBuilder(100);
		String currentURL = browser.getCurrURL();
		if (pageCount <= 2) {
			newURL.append(currentURL);
			newURL.append(PAGE_ARG);
		} else {
			newURL.append(currentURL.substring(0, currentURL.length() - 1));
		}
		newURL.append(pageCount);
		browser.open(newURL.toString());
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

	public void askCloseBrowser() {
		ConsoleSpeaker.askCloseBrowser();
		int answer = ConsoleSpeaker.get_2_Or_1();
		if (answer == 1) {
			hadToBeClosed = true;
			ConsoleSpeaker.printCloseBrowserMessage();
		} else if (answer == 2) {
			hadToBeClosed = false;
		} else {
			ConsoleSpeaker.error();
		}
	}

	public boolean isNeededCloseBrowser() {
		return hadToBeClosed;
	}

	public void closeBrowser() {
		browser.close();
	}
}