package serg.job_searcher;

import serg.job_searcher.tools.ConsoleSpeaker;
import serg.job_searcher.tools.VacancyManager;

/**
 * Class JobSearcher. Goes to the rabota.ua, searching for a new junior job,
 * send cover letter and CV, say me about that and make some statistics.
 * 
 * @author Serhii Maksymchuk
 */
public class JobSearcher {
	
	public static void main(String[] args) {
		VacancyManager manager = new VacancyManager();
		
		ConsoleSpeaker.hello();
		manager.initialize();
		manager.getVacCountList();
		manager.printStat();
		
		if (manager.isNewVacExist()) {
			System.out.println("Founded next positions:");
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
		
		manager.askCloseBrowser();
		if (manager.isNeededCloseBrowser()) {
			manager.closeBrowser();
		}
		ConsoleSpeaker.bye();
		
		/*
		 * print message about key words (if exist); find junior java developer;
		 * ask what to do; post cover letter; send CV; report
		 * 
		 */
	}
}