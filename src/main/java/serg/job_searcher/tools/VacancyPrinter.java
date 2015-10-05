package serg.job_searcher.tools;

import java.util.List;

import serg.job_searcher.entities.Vacancy;

public class VacancyPrinter {
	
	private static final String LINE = "--------------------------------------------";
	private static final String VAC_NUM = "Number of vacancy: ";
	
	private VacancyPrinter() {}
	
	private static void printLine() {
		System.out.println(LINE);
	}
	
	public static void printPositions(List<Vacancy> vacList) {
		int i = 0;
		printLine();
		for (Vacancy vac : vacList) {
			System.out.println(new StringBuilder(50)
					.append(++i)
					.append(". ")
					.append(vac.getPosition())
			);
		}
	}
	
	public static void printVacancy(int number, List<Vacancy> vacList) {
		printLine();
		System.out.println(VAC_NUM + number);
		printInfo(vacList.get(--number));
		printLine();
	}
	
	public static void printList(List<Vacancy> vacList) {
		printCorrectMessage(vacList.size());
		int i = 0;
		for (Vacancy vac : vacList) {
			printLine();
			System.out.println(new StringBuilder(50)
					.append(VAC_NUM)
					.append(++i)
			);
			printInfo(vac);
		}
	}
	
	private static void printCorrectMessage(int newVacCount) {
		System.out.println("I find " + newVacCount + " new " + (newVacCount == 1 ? "vacancy." : "vacancies."));
	}
	
	private static void printInfo(Vacancy vac) {
		printPosition(vac);
		printCompany(vac);
		printCity(vac);
		printText(vac);
		printTime(vac);
		printKeyWords(vac);
	}
	
	private static void printPosition(Vacancy vac) {
		System.out.println("Position: " + vac.getPosition());
	}

	private static void printCompany(Vacancy vac) {
		System.out.println("Company: " + vac.getCompany());
	}

	private static void printCity(Vacancy vac) {
		System.out.println("City: " + vac.getCity());
	}

	private static void printText(Vacancy vac) {
		System.out.println("Vacancy text: " + vac.getText());
	}

	private static void printTime(Vacancy vac) {
		System.out.println("Last refresh: " + vac.getTime());
	}

	private static void printKeyWords(Vacancy vac) {
		System.out.println("Key words:");
		for (String keyWord : vac.getKeyWordsList()) {
			System.out.println(keyWord);
		}
	}
}