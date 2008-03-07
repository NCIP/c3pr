package edu.duke.cabig.c3pr.web.report;

/**
 * @author Vinay Gangoli
 */

public class ReportCommand {

    // The params array contains the following attributes in the following order,
    private String studyCoordinatingSite; // param[0]

    private String studyShortTitle; // param[1]

    private String siteName; // param[2]

    private String siteNciId; // param[3]

    private String regStartDate; // param[4]

    private String regEndDate; // param[5]

    private String birthDate; // param[6]

    private String raceCode; // param[7]

    private String[] params;

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

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }
}
