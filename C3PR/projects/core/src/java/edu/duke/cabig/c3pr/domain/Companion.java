package edu.duke.cabig.c3pr.domain;

public class Companion {
	
	private String companionStudyShortTitle ;
	private String companionStudyPrimaryIdentifier;
	private String registrationStatus ;
	private Boolean mandatoryIndicator ;
	private int registrationId ;
	private int companionStudyId;
	private String companionRegistrationUrl;
	private String registrationDataEntryStatus ;;
	
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

	public void setMandatoryIndicator(Boolean mandatoryIndicator) {
		this.mandatoryIndicator = mandatoryIndicator;
	}
	public Boolean getMandatoryIndicator() {
		return mandatoryIndicator;
	}
	public void setCompanionStudyId(int companionStudyId) {
		this.companionStudyId = companionStudyId;
	}
	public int getCompanionStudyId() {
		return companionStudyId;
	}
	public void setCompanionRegistrationUrl(String companionRegistrationUrl) {
		this.companionRegistrationUrl = companionRegistrationUrl;
	}
	public String getCompanionRegistrationUrl() {
		return companionRegistrationUrl;
	}
	public String getRegistrationDataEntryStatus() {
		return registrationDataEntryStatus;
	}
	public void setRegistrationDataEntryStatus(String registrationDataEntryStatus) {
		this.registrationDataEntryStatus = registrationDataEntryStatus;
	}

}
