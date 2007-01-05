package edu.duke.cabig.c3pr.web;

public class SearchRegisterCommand {
	private String searchTypeText;
	private String searchType;
	private String studyid;
	private String shortTitleText;
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getSearchTypeText() {
		return searchTypeText;
	}
	public void setSearchTypeText(String searchTypeText) {
		this.searchTypeText = searchTypeText;
	}
	public String getShortTitleText() {
		return shortTitleText;
	}
	public void setShortTitleText(String shortTitleText) {
		this.shortTitleText = shortTitleText;
	}
	public String getStudyid() {
		return studyid;
	}
	public void setStudyid(String studyid) {
		this.studyid = studyid;
	}

	
}
