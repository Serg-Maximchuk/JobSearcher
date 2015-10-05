package serg.job_searcher.tools;

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

	private VacancyExtractor() {
	}

	public static List<Vacancy> extract(List<HTML_Interpretation> interVacList) {
		int size = interVacList.size();
		Vector<Vacancy> vacancies = new Vector<>();
		vacancies.setSize(size);
		System.out.print("Process: [");
		final CountDownLatch latch = new CountDownLatch(size);
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

	public static Vacancy extract(HTML_Interpretation interpretatedVac) {
		return new Vacancy() {
			{
				final CountDownLatch latch = new CountDownLatch(6);

				new Thread(new Runnable() {
					@Override
					public void run() {
						setPosition(interpretatedVac.getTextInClass(POSITION_CLASS));
						latch.countDown();
					}
				}).start();

				new Thread(new Runnable() {
					@Override
					public void run() {
						setCompany(interpretatedVac.getTextInClass(COMPANY_CLASS));
						latch.countDown();
					}
				}).start();

				new Thread(new Runnable() {
					@Override
					public void run() {
						String[] elementText = interpretatedVac.getTextInClass(CITY_CLASS).split(SEPARATOR);
						setCity(elementText[elementText.length - 1].trim());
						latch.countDown();
					}
				}).start();

				new Thread(new Runnable() {
					@Override
					public void run() {
						int i;
						try {
							interpretatedVac.getTextInClass(MESSAGE_CLASS);
							i = 1;
						} catch (NoSuchElementException e) {
							i = 0;
						}
						setText(interpretatedVac.getTextListFromClass(TEXT_CLASS).remove(i));
						latch.countDown();
					}
				}).start();

				new Thread(new Runnable() {
					@Override
					public void run() {
						setTime(interpretatedVac.getTextInClass(TIME_CLASS));
						latch.countDown();
					}
				}).start();
				
				new Thread(new Runnable() {
					@Override
					public void run() {
						setKeyWords(interpretatedVac
								.getElByClass(KEY_WORDS_CLASS)
								.getTextListFromClass(TEXT_CLASS));
						latch.countDown();
					}
				}).start();

				try {
					latch.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
	}
}