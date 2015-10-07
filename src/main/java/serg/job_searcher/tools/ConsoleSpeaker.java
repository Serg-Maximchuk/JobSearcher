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
	private static final String BUILD_MESSAGE = "Launching " + "%s" + "%s" + NL;
	private static final String POSITIONS_MESSAGE = "Founded next positions:";
	private static final String[] REGIME_WORDS = {" mode", "Test", "Search"};
	private static final String[] BROWSER_WORDS = {" browser", "Chrome", "Firefox"};
	private static final String ERR = "ERROR.";
	private static final String ASK_CLOSE_BROWSER =
			"What to do with browser?" + NL
			+ "1. Close - type '1'" + NL
			+ "2. Nothing - type '2'" + NL
			+ "Your choise is:";
	private static final String BROWSER_CLOSING_MESSAGE = "Closing browser...";
	private static final String CLOSE_MESSAGE = "There is no new vacancies.";
	private static final String BYE_MESSAGE = "Good bye.";
	private static final String ASK_TEST_VAC = "As I work in test mode, how much vacancies needs to be founded?";
	private static final String NEED_SEE = "I need see " + "%s" + " vacancies on next page." + NL;
	private static final String ASK_POSITION =
			"To print more information about interesting vacancy just type it's number." + NL
			+"To go out - type '0'.";
	private static final String VACANCIES = "New vacancies: " + "%s" + NL + "Active vacancies: " + "%s" + NL
			 + "Actual vacancies: " + "%s" + NL;
	private static final String MESSAGE = "Message: " + "%s" + NL;
	
	
	private ConsoleSpeaker() {}

	public static void hello() {
		System.out.println(HELLO_MESSAGE);
	}

	public static int askRegime() {
		System.out.println(CHOOSE_MODE_MESSAGE);
		return getAnswer(REGIME_WORDS);
	}
	
	public static int askBrowser() {
		System.out.println(CHOOSE_BROWSER_MESSAGE);
		return getAnswer(BROWSER_WORDS);
	}
	
	private static int getAnswer(String[] words) {
		int answer = get_2_Or_1();
		buildMessage(answer, words);
		return answer;
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
	
	public static void buildMessage(int pointer, String[] words) {
		System.out.format(BUILD_MESSAGE, words[pointer], words[0]);
	}
	
	public static void positionsMess() {
		System.out.println(POSITIONS_MESSAGE);
	}
	
	public static void error() {
		System.out.println(ERR);
	}
	
	public static void askTestVacNum() {
		System.out.println(ASK_TEST_VAC);		
	}

	public static void needSee(int delta) {
		System.out.format(NEED_SEE, delta);
	}

	public static void askPosition() {
		System.out.println(ASK_POSITION);
	}

	public static void printVacancies(int newVacCount, int activeVacCount, int actualVacCount) {
		System.out.format(VACANCIES, newVacCount, activeVacCount, actualVacCount);
	}

	public static void printMessage(String text) {
		System.out.format(MESSAGE, text);
	}
	
	public static void askCloseBrowser() {
		System.out.println(ASK_CLOSE_BROWSER);
	}
	
	public static void printCloseBrowserMessage() {
		System.out.println(BROWSER_CLOSING_MESSAGE);
	}
	
	public static void closeMessage() {
		System.out.println(CLOSE_MESSAGE);
	}

	public static void bye() {
		System.out.println(BYE_MESSAGE);
	}
}