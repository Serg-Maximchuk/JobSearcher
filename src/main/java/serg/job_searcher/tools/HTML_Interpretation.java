package serg.job_searcher.tools;

import java.util.List;

public interface HTML_Interpretation {
	HTML_Interpretation getElByClass(String className);
	String getTextInClass(String className);
	List<HTML_Interpretation> getListFromClass(String className);
	List<String> getTextListFromClass(String className);
}