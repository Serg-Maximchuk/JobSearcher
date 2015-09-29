package serg.job_searcher.entities;

import java.util.Iterator;
import java.util.LinkedList;

public class Vacancy {
	
	private String position;
	private String company;
	private String city;
	private String text;
	private String time;
	private LinkedList<String> keyWordsList;
	
	public void setInfo(LinkedList<String> infoList) {
		Iterator<String> iter = infoList.iterator();
		position = iter.next();
		company = iter.next();
		city = iter.next();
		text = iter.next();
		time = iter.next();
	}
	
	public void setKeyWords(LinkedList<String> keyWordsList) {
		this.keyWordsList = keyWordsList;
	}
	
	public String getPosition() {
		return position;
	}

	/**
	 * @return the company
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @return the keyWordsList
	 */
	public LinkedList<String> getKeyWordsList() {
		return keyWordsList;
	}
}