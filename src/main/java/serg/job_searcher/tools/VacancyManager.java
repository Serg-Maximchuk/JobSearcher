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
	private static final String PAGE_ARG = "?pg=";

	private WebBrowser browser;
	private List<Vacancy> vacancies;
	private int newVacCount;
	private int activeVacCount;
	private int actualVacCount;
	private LinkedList<Integer> vacCountMesList;
	private List<String> messages;
	private DriverWrap driver;
	private boolean testRegime;
	private int pageCount = 1;

	public void initialize(WebBrowser browser) {
		this.browser = browser;
		setDriverWrap(browser.getDriverWrap());
		testRegime = (ConsoleSpeaker.askRegime() == 1) ? true : false;
		intializeVacCountFromMessages();
	}

	public void setDriverWrap(DriverWrap driver) {
		this.driver = driver;
	}

	public void intializeVacCountFromMessages() {
		messages = getMessages();
		vacCountMesList = new LinkedList<Integer>() {
			private static final long serialVersionUID = 3131358699775535338L;

			{
				for (String message : messages) {
					add(Integer.valueOf(message.replaceAll("[^0-9]+", " ").trim()));
				}
			}
		};
	}

	public List<String> getMessages() {
		return new LinkedList<String>() {
			private static final long serialVersionUID = 55726793690860279L;

			{
				for (DriverWrap wrappedMessage : driver.getListFromClass(MESSAGE_CLASS)) {
					add(wrappedMessage.getTextInClass(MESSAGE_TEXT));
				}
			}
		};
	}

	public void getVacCountList() {
		for (String message : messages) {
			ConsoleSpeaker.printMessage(message);
		}
		newVacCount = getNewVacCount();
		activeVacCount = getActiveVacCount();
		actualVacCount = getActualVacCount();
		if (testRegime) {
			newVacCount = getTestVacCount();
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
			String foundedNewVacStr = driver.getTextInClass(ACTIVE_VAC_CLASS);
			return Integer.valueOf(foundedNewVacStr);
		} catch (Exception e) {
			System.out.println("Can't get active vacancies. Is they exist?");
			return 0;
		}
	}

	public int getActualVacCount() {
		return vacCountMesList.getLast();
	}

	public int getTestVacCount() {
		ConsoleSpeaker.askTestVacNum();
		int number = ConsoleSpeaker.getInt();
		while (number > 80 || number < 1) {
			if (number > 80) {
				System.out.println("Too much. Limit is 80.");
			} else if (number < 1) {
				ConsoleSpeaker.incorrect();
			}
			number = ConsoleSpeaker.getInt();
		}
		return number;
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
		List<DriverWrap> allVacOnPage = driver.getListFromClass(VAC_CLASS).subList(0, 20);
		int vacOnPageCount = allVacOnPage.size();
		if (vacCount > vacOnPageCount) {
			List<Vacancy> vacList = VacancyExtractor.extract(allVacOnPage);
			int delta = vacCount - vacOnPageCount;
			ConsoleSpeaker.needSee(delta);
			VacancyManager man = new VacancyManager();
			man.setPageCount(++pageCount);
			man.setDriverWrap(goToNextPage().getDriverWrap());
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
		return browser.open(buildNewURL(browser.getCurrURL()));
	}

	private String buildNewURL(String currentURL) {
		StringBuilder newURL = new StringBuilder(100);
		if (pageCount <= 2) {
			newURL.append(currentURL);
			newURL.append(PAGE_ARG);
		} else {
			newURL.append(currentURL.substring(0, currentURL.length() - 1));
		}
		newURL.append(pageCount);
		return newURL.toString();
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
}
