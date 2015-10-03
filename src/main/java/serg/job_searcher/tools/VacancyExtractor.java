package serg.job_searcher.tools;

import java.util.LinkedList;
import java.util.List;

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

	private static HTML_Interpretation interpretation;

	public static List<Vacancy> extract(List<HTML_Interpretation> webElemVacList) {
		List<Vacancy> vacancies = new LinkedList<>();
		System.out.print("Process: [");
		for (HTML_Interpretation webElementVac : webElemVacList) {
			System.out.print('#');
			Vacancy vac = VacancyExtractor.extract(webElementVac);
			vacancies.add(vac);
		}
		System.out.println(']');
		return vacancies;
	}

	public static Vacancy extract(HTML_Interpretation HTML_Int) {
		interpretation = HTML_Int;
		LinkedList<String> infoList = collectInfo();
		LinkedList<String> keyWordsList = extractKeyWords();
		Vacancy vac = new Vacancy();
		vac.setInfo(infoList);
		vac.setKeyWords(keyWordsList);
		return vac;
	}

	public static LinkedList<String> collectInfo() {
		LinkedList<String> infoList = new LinkedList<>();
		infoList.add(extractPosition());
		infoList.add(extractCompany());
		infoList.add(extractCity());
		infoList.add(extractText());
		infoList.add(extractTime());
		return infoList;
	}

	public static String extractPosition() {
		return interpretation.getTextInClass(POSITION_CLASS);
	}

	public static String extractCompany() {
		return interpretation.getTextInClass(COMPANY_CLASS);
	}

	public static String extractCity() {
		String[] elementText = interpretation.getTextInClass(CITY_CLASS).split(SEPARATOR);
		return elementText[elementText.length - 1].trim();
	}

	public static String extractText() {
		int i;
		try {
			interpretation.getTextInClass(MESSAGE_CLASS);
			i = 1;
		} catch (NoSuchElementException e) {
			i = 0;
		}
		List<String> textList = interpretation.getTextListFromClass(TEXT_CLASS);
		return textList.remove(i);
	}

	public static String extractTime() {
		return interpretation.getTextInClass(TIME_CLASS);
	}

	public static LinkedList<String> extractKeyWords() {
		HTML_Interpretation keyWordsElement = interpretation.getElByClass(KEY_WORDS_CLASS);
		List<String> keyWords = keyWordsElement.getTextListFromClass(TEXT_CLASS);
		LinkedList<String> keyWordsList = new LinkedList<>();
		for (String keyWord : keyWords) {
			keyWordsList.add(keyWord);
		}
		return keyWordsList;
	}
}