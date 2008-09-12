package edu.duke.cabig.c3pr.domain;

public class Companion {
	
	private String companionStudyShortTitle ;
	private String companionStudyPrimaryIdentifier;
	private String registrationStatus ;
	private Boolean mandatoryIndicator ;
	private int registrationId ;
	private int studySiteId ;
	
	public String getCompanionStudyPrimaryIdentifier() {
		return companionStudyPrimaryIdentifier;
	}
	public void setCompanionStudyPrimaryIdentifier(
			String companionStudyPrimaryIdentifier) {
		this.companionStudyPrimaryIdentifier = companionStudyPrimaryIdentifier;
	}
	public void setRegistrationId(int registrationId) {
		this.registrationId = registrationId;
	}
	public void setStudySiteId(int studySiteId) {
		this.studySiteId = studySiteId;
	}
	
	public String getCompanionStudyShortTitle() {
		return companionStudyShortTitle;
	}
	public void setCompanionStudyShortTitle(String companionStudyShortTitle) {
		this.companionStudyShortTitle = companionStudyShortTitle;
	}
	public String getRegistrationStatus() {
		return registrationStatus;
	}
	public void setRegistrationStatus(String registrationStatus) {
		this.registrationStatus = registrationStatus;
	}
	
	public int getRegistrationId() {
		return registrationId;
	}
	public int getStudySiteId() {
		return studySiteId;
	}
	public void setMandatoryIndicator(Boolean mandatoryIndicator) {
		this.mandatoryIndicator = mandatoryIndicator;
	}
	public Boolean getMandatoryIndicator() {
		return mandatoryIndicator;
	}

}
