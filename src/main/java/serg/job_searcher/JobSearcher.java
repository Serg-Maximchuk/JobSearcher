package serg.job_searcher;

import serg.job_searcher.browsers.ChromeBrowser;
import serg.job_searcher.browsers.FirefoxBrowser;
import serg.job_searcher.browsers.WebBrowser;
import serg.job_searcher.tools.ConsoleSpeaker;
import serg.job_searcher.tools.VacancyManager;

/**
 * Class JobSearcher. Goes to the rabota.ua, searching for a new junior job,
 * send cover letter and CV, say me about that and make some statistics.
 * 
 * @author Serhii Maksymchuk
 */
public class JobSearcher {

	private static final String VACANCIES_URL = "http://rabota.ua/jobsearch/notepad/vacancies_profile";

	private static boolean hadToBeClosed;

	public static void main(String[] args) {
		VacancyManager manager = new VacancyManager();

		ConsoleSpeaker.hello();
		WebBrowser browser = (ConsoleSpeaker.askBrowser() == 1) ? new ChromeBrowser() : new FirefoxBrowser();
		browser.open(VACANCIES_URL);
		manager.initialize(browser);
		manager.getVacCountList();
		manager.printStat();

		if (manager.isNewVacExist()) {
			ConsoleSpeaker.positionsMess();
			manager.printNewVacPositions();
			int number = manager.askPosition();
			while (number > 0) {
				manager.printVacancy(number);
				number = manager.askPosition();
			}
			// manager.printNewVacList();
		} else {
			ConsoleSpeaker.closeMessage();
		}
		askCloseBrowser();
		if (hadToBeClosed) {
			browser.close();
		}
		ConsoleSpeaker.bye();

		/*
		 * print message about key words (if exist); find junior java developer;
		 * ask what to do; post cover letter; send CV; report
		 * 
		 */
	}

	public static void askCloseBrowser() {
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
}
