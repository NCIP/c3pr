package edu.duke.cabig.c3pr.web;

/**
 * @author Priyatam
 */

public class SearchRegistrationCommand extends SearchCommand{
	
	private String select;
	private String subjectOption;
	private String studyOption;
	public String getSelect() {
		return select;
	}
	public void setSelect(String select) {
		this.select = select;
	}
	public String getStudyOption() {
		return studyOption;
	}
	public void setStudyOption(String studyOption) {
		this.studyOption = studyOption;
	}
	public String getSubjectOption() {
		return subjectOption;
	}
	public void setSubjectOption(String subjectOption) {
		this.subjectOption = subjectOption;
	}
	
}
