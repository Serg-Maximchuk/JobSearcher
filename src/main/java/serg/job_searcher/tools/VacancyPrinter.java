package serg.job_searcher.tools;

import java.util.List;

import serg.job_searcher.entities.Vacancy;

public class VacancyPrinter {
	
	private static final String LINE = "--------------------------------------------";
	private static final String VAC_NUM = "Number of vacancy: ";
	
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
					.append(vac.getPosition()
			));
		}
	}
	
	public static void printVacancy(int number, List<Vacancy> vacList) {
		printLine();
		System.out.println(new StringBuilder(50)
				.append(VAC_NUM)
				.append(number)
		);
		--number;
		printInfo(vacList.get(number));
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
		StringBuilder message = new StringBuilder(50)
				.append("I find ")
				.append(newVacCount)
				.append(" new ");
		message.append( (newVacCount == 1) ? "vacancy." : "vacancies." );
		System.out.println(message);
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
		System.out.println(new StringBuilder(50)
				.append("Position: ")
				.append(vac.getPosition()));
	}

	private static void printCompany(Vacancy vac) {
		System.out.println(new StringBuilder(50)
				.append("Company: ")
				.append(vac.getCompany()));
	}

	private static void printCity(Vacancy vac) {
		System.out.println(new StringBuilder(50)
				.append("City: ")
				.append(vac.getCity()));
	}

	private static void printText(Vacancy vac) {
		System.out.println(new StringBuilder(50)
				.append("Vacancy text: ")
				.append(vac.getText()));
	}

	private static void printTime(Vacancy vac) {
		System.out.println(new StringBuilder(50)
				.append("Last refresh: ")
				.append(vac.getTime()));
	}

	private static void printKeyWords(Vacancy vac) {
		System.out.println("Key words:");
		for (String keyWord : vac.getKeyWordsList()) {
			System.out.println(keyWord);
		}
	}
}