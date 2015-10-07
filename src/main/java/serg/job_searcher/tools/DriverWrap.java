package serg.job_searcher.tools;

import java.util.List;

public interface DriverWrap {
	DriverWrap getElByClass(String className);
	String getTextInClass(String className);
	List<DriverWrap> getListFromClass(String className);
	List<String> getTextListFromClass(String className);
}