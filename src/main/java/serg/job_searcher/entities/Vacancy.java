package serg.job_searcher.entities;

import java.util.List;

public class Vacancy {
	
	private String position;
	private String company;
	private String city;
	private String text;
	private String time;
	private List<String> keyWords;
	
	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * @param company the company to set
	 */
	public void setCompany(String company) {
		this.company = company;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @param keyWordsList the keyWordsList to set
	 */
	public void setKeyWords(List<String> keyWordsList) {
		this.keyWords = keyWordsList;
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
	public List<String> getKeyWordsList() {
		return keyWords;
	}
}