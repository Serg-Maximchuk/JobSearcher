package serg.job_searcher.tools;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ConsoleSpeaker {
	
	private static final String HELLO_MESSAGE = "Job Searcher welcomes you!";
	private static final String NL = "\n";
	private static final String CHOOSE_MODE_MESSAGE =
			"Choose in which regime I should work:" + NL
			+ "1. TEST_MODE - type '1'" + NL
			+ "2. Search mode - type '2'" + NL
			+ "Set work regime:";
	private static final String CHOOSE_BROWSER_MESSAGE =
			"Choose in which browser I should work:" + NL
			+ "1. Chrome - type '1'" + NL
			+ "2. Firefox - type '2'" + NL
			+ "Choose browser:";
	private static final String INCORRECT = "Incorrect. Try again.";
	private static final String ERR = "REGIME ERROR.";
	private static final String CLOSE_MESSAGE = "There is no new vacancies. Closing browser.";
	private static final String BYE_MESSAGE = "Good bye.";
	private static final String ASK_TEST_VAC = "As I work in test mode, how much vacancies needs to be founded?";
	private static final String ASK_POSITION = 
			"To print more information about interesting vacancy just type it's number." + NL
			+"To go out - type '0'.";
	
	
	private ConsoleSpeaker() {}

	public static void hello() {
		System.out.println(HELLO_MESSAGE);
	}

	public static void askRegime() {
		System.out.println(CHOOSE_MODE_MESSAGE);
	}
	
	public static int get_2_Or_1() {
		int answer = 0;
		answer = getInt();
		while (answer > 2 || answer < 1) {
			incorrect();
			answer = get_2_Or_1();
		}
		return answer;
	}

	public static int getInt() {
		int receivededInt = 0;
		Scanner input = new Scanner(System.in);
		try {
			receivededInt = input.nextInt();
		} catch (InputMismatchException e) {
			System.out.println(INCORRECT);
			receivededInt = getInt();
		} catch (NoSuchElementException e) {
			System.out.println("Try 'hasNextInt() before accessing var");
		} catch (IllegalStateException e) {
			System.out.println("Scanner is closed. Do smth.");
		}
		return receivededInt;
	}
	
	public static void incorrect() {
		System.out.println(INCORRECT);
	}
	
	public static void error() {
		System.out.println(ERR);
	}
	
	public static void askBrowser() {
		System.out.println(CHOOSE_BROWSER_MESSAGE);
	}

	public static void closeMessage() {
		System.out.println(CLOSE_MESSAGE);
	}

	public static void bye() {
		System.out.println(BYE_MESSAGE);
	}

	public static void askTestVacNum() {
		System.out.println(ASK_TEST_VAC);		
	}

	public static void needSee(int delta) {
		System.out.println(new StringBuilder(50)
				.append("I need see ")
				.append(delta)
				.append(" vacancies on next page.")
		);
	}

	public static void askPosition() {
		System.out.println(ASK_POSITION);
	}

	public static void printVacancies(int newVacCount, int activeVacCount, int actualVacCount) {
		System.out.println(new StringBuilder(200)
				.append("New vacancies: ")
				.append(newVacCount)
				.append(NL)
				.append("Active vacancies: ")
				.append(activeVacCount)
				.append(NL)
				.append("Actual vacancies: ")
				.append(actualVacCount)
		);
	}

	public static void printMessage(String message) {
		System.out.println(new StringBuilder(50)
				.append("Message: ")
				.append(message)
		);
	}
}