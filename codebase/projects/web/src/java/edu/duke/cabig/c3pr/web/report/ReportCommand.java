package edu.duke.cabig.c3pr.web.report;

/**
 * @author Vinay Gangoli
 */

public class ReportCommand {
	
	private String studyCoordinatingSite;
	private String studyShortTitle;
	private String siteName;
	private String siteNciId;
	private String regStartDate;
	private String regEndDate;	
	private String birthDate;
	private String raceCode;
	
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getSiteNciId() {
		return siteNciId;
	}
	public void setSiteNciId(String siteNciId) {
		this.siteNciId = siteNciId;
	}

	public String getStudyShortTitle() {
		return studyShortTitle;
	}
	public void setStudyShortTitle(String studyShortTitle) {
		this.studyShortTitle = studyShortTitle;
	}
	public String getStudyCoordinatingSite() {
		return studyCoordinatingSite;
	}
	public void setStudyCoordinatingSite(String studyCoordinatingSite) {
		this.studyCoordinatingSite = studyCoordinatingSite;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getRaceCode() {
		return raceCode;
	}
	public void setRaceCode(String raceCode) {
		this.raceCode = raceCode;
	}
	public String getRegEndDate() {
		return regEndDate;
	}
	public void setRegEndDate(String regEndDate) {
		this.regEndDate = regEndDate;
	}
	public String getRegStartDate() {
		return regStartDate;
	}
	public void setRegStartDate(String regStartDate) {
		this.regStartDate = regStartDate;
	}


}
