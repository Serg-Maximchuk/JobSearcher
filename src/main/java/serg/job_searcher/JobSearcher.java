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
	
	public static void main(String[] args) {
		JobSearcher searcher = new JobSearcher();
		VacancyManager manager = new VacancyManager();
		
		ConsoleSpeaker.hello();
		
		int regime = searcher.askWorkRegime();
		WebBrowser browser = searcher.askAndOpenBrowser();
		browser.open(VACANCIES_URL);
		manager.setRegime(regime);
		manager.controlBrowser(browser);
		manager.getVacCountList();
		manager.printStat();
		
		if (manager.isNewVacExist()) {
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
		ConsoleSpeaker.bye();
		manager.closeBrowser();
		
		/*
		 * print message about key words (if exist); find junior java developer;
		 * ask what to do; post cover letter; send CV; report
		 * 
		 */
	}
	
	public int askWorkRegime() {
		ConsoleSpeaker.askRegime();
		int regime = ConsoleSpeaker.get_2_Or_1();
		ConsoleSpeaker.printRegimeMessage(regime);
		return regime;
	}

	public WebBrowser askAndOpenBrowser() {
		ConsoleSpeaker.askBrowser();
		int answer = ConsoleSpeaker.get_2_Or_1();
		ConsoleSpeaker.printBrowserMessage(answer);
		WebBrowser browser = null;
		if (answer == 1) {
			browser = new ChromeBrowser();
		} else if (answer == 2) {
			browser = new FirefoxBrowser();
		} else {
			ConsoleSpeaker.error();
		}
		return browser;
	}
}