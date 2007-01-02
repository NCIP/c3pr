package edu.duke.cabig.c3pr.web;

/**
 * 
 * @author Priyatam
 *
 */
public class SearchStudyCommand {
	
	private String searchTypeText;
	private String searchType;
	private String studyid;
	private String shortTitleText;
	
	/**
	 * @return the searchType
	 */
	public String getSearchType() {
		return searchType;
	}
	/**
	 * @param searchType the searchType to set
	 */
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	/**
	 * @return the searchTypeText
	 */
	public String getSearchTypeText() {
		return searchTypeText;
	}
	/**
	 * @param searchTypeText the searchTypeText to set
	 */
	public void setSearchTypeText(String searchTypeText) {
		this.searchTypeText = searchTypeText;
	}
	/**
	 * @return the studyid
	 */
	public String getStudyid() {
		return studyid;
	}
	/**
	 * @param studyid the studyid to set
	 */
	public void setStudyid(String studyid) {
		this.studyid = studyid;
	}
	/**
	 * @return the shortTitleText
	 */
	public String getShortTitleText() {
		return shortTitleText;
	}
	/**
	 * @param shortTitleText the shortTitleText to set
	 */
	public void setShortTitleText(String shortTitleText) {
		this.shortTitleText = shortTitleText;
	}
	
	

}
