package edu.duke.cabig.c3pr.web.report;

/*
 * Command object for the study report flow.
 * @author Vinay Gangoli
 */
public class StudyReportCommand {
		
//	  The params array contains the following attributes in the following order,	
		private String studyIdentifier;       	//param[0]
		private String studyShortTitle;		//param[1]
		private String participantIdentifier;			//param[2]
		private String firstName;				//param[3]
		private String lastName;				//param[4]
		
		private String[] params;
		

		public String getFirstName() {
			return firstName;
		}
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		public String getLastName() {
			return lastName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		public String getParticipantIdentifier() {
			return participantIdentifier;
		}
		public void setParticipantIdentifier(String participantIdentifier) {
			this.participantIdentifier = participantIdentifier;
		}
		public String getStudyIdentifier() {
			return studyIdentifier;
		}
		public void setStudyIdentifier(String studyIdentifier) {
			this.studyIdentifier = studyIdentifier;
		}
		public String getStudyShortTitle() {
			return studyShortTitle;
		}
		public void setStudyShortTitle(String studyShortTitle) {
			this.studyShortTitle = studyShortTitle;
		}
		public String[] getParams() {
			return params;
		}
		public void setParams(String[] params) {
			this.params = params;
		}

}
