package serg.job_searcher.tools;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

import org.openqa.selenium.NoSuchElementException;
import serg.job_searcher.entities.Vacancy;

public class VacancyExtractor {

	private static final String MESSAGE_CLASS = "mtmb";
	private static final String POSITION_CLASS = "headline";
	private static final String COMPANY_CLASS = "rua-p-c-default";
	private static final String CITY_CLASS = "rua-p-c_16";
	private static final String SEPARATOR = "•";
	private static final String TEXT_CLASS = "rua-p-c-mid";
	private static final String TIME_CLASS = "pull-right";
	private static final String KEY_WORDS_CLASS = "inline";
	
	private VacancyExtractor() {}

	public static List<Vacancy> extract(List<HTML_Interpretation> interVacList) {
		Vector<Vacancy> vacancies = new Vector<>();
		vacancies.setSize(interVacList.size());
		System.out.print("Process: [");
		final CountDownLatch latch = new CountDownLatch(interVacList.size());
		for (HTML_Interpretation interpretatedVac : interVacList) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					vacancies.setElementAt(extract(interpretatedVac), interVacList.indexOf(interpretatedVac));
					System.out.print('#');
					latch.countDown();
				}
			}).start();
		}
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(']');
		return vacancies;
	}

	public static Vacancy extract(HTML_Interpretation HTML_Int) {
		return new Vacancy() {
			{
				setInfo(collectInfo(HTML_Int));
				setKeyWords(extractKeyWords(HTML_Int));
			}
		};
	}

	private static LinkedList<String> collectInfo(HTML_Interpretation HTML_Int) {
		return new LinkedList<String>() {
			private static final long serialVersionUID = -5019187361458691782L;
			{
				add(extractPosition(HTML_Int));
				add(extractCompany(HTML_Int));
				add(extractCity(HTML_Int));
				add(extractText(HTML_Int));
				add(extractTime(HTML_Int));
			}
		};
	}

	private static String extractPosition(HTML_Interpretation HTML_Int) {
		return HTML_Int.getTextInClass(POSITION_CLASS);
	}

	private static String extractCompany(HTML_Interpretation HTML_Int) {
		return HTML_Int.getTextInClass(COMPANY_CLASS);
	}

	private static String extractCity(HTML_Interpretation HTML_Int) {
		String[] elementText = HTML_Int.getTextInClass(CITY_CLASS).split(SEPARATOR);
		return elementText[elementText.length - 1].trim();
	}

	private static String extractText(HTML_Interpretation HTML_Int) {
		int i;
		try {
			HTML_Int.getTextInClass(MESSAGE_CLASS);
			i = 1;
		} catch (NoSuchElementException e) {
			i = 0;
		}
		return HTML_Int.getTextListFromClass(TEXT_CLASS).remove(i);
	}

	private static String extractTime(HTML_Interpretation HTML_Int) {
		return HTML_Int.getTextInClass(TIME_CLASS);
	}

	private static List<String> extractKeyWords(HTML_Interpretation HTML_Int) {
		return HTML_Int.getElByClass(KEY_WORDS_CLASS).getTextListFromClass(TEXT_CLASS);
	}
}