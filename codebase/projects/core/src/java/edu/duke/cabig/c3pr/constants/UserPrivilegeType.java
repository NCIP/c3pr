/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.constants;

import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.register;
import static gov.nih.nci.cabig.ctms.domain.EnumHelper.sentenceCasedName;
import gov.nih.nci.cabig.ctms.domain.CodedEnum;

public enum UserPrivilegeType implements CodedEnum<String>{
	STUDY_CREATE("edu.duke.cabig.c3pr.domain.Study:CREATE"),
	STUDY_READ("edu.duke.cabig.c3pr.domain.Study:READ"),
	STUDY_UPDATE("edu.duke.cabig.c3pr.domain.Study:UPDATE"),
	STUDY_DEFINITION_UPDATE("edu.duke.cabig.c3pr.domain.Study.Definition:UPDATE"),
	STUDY_DEFINITION_DETAILS_CREATE("edu.duke.cabig.c3pr.domain.Study.Definition.Details:CREATE"),
	STUDY_DEFINITION_DETAILS_READ("edu.duke.cabig.c3pr.domain.Study.Definition.Details:READ"),
	STUDY_DEFINITION_DETAILS_UPDATE("edu.duke.cabig.c3pr.domain.Study.Definition.Details:UPDATE"),
	STUDY_DEFINITION_SUPPLEMENTAL_CREATE("edu.duke.cabig.c3pr.domain.Study.Definition.Supplemental:CREATE"),
	STUDY_DEFINITION_SUPPLEMENTAL_READ("edu.duke.cabig.c3pr.domain.Study.Definition.Supplemental:READ"),
	STUDY_DEFINITION_SUPPLEMENTAL_UPDATE("edu.duke.cabig.c3pr.domain.Study.Definition.Supplemental:UPDATE"),
	STUDY_OVERRIDE("edu.duke.cabig.c3pr.domain.Study:OVERRIDE"),
	HEALTHCARESITE_CREATE("edu.duke.cabig.c3pr.domain.HealthcareSite:CREATE"),
	HEALTHCARESITE_READ("edu.duke.cabig.c3pr.domain.HealthcareSite:READ"),
	HEALTHCARESITE_UPDATE("edu.duke.cabig.c3pr.domain.HealthcareSite:UPDATE"),
	HEALTHCARESITE_OVERRIDE("edu.duke.cabig.c3pr.domain.HealthcareSite:OVERRIDE"),
	STUDYSUBJECT_CREATE("edu.duke.cabig.c3pr.domain.StudySubject:CREATE"),
	STUDYSUBJECT_READ("edu.duke.cabig.c3pr.domain.StudySubject:READ"),
	STUDYSUBJECT_UPDATE("edu.duke.cabig.c3pr.domain.StudySubject:UPDATE"),
	STUDYSUBJECT_OVERRIDE("edu.duke.cabig.c3pr.domain.StudySubject:OVERRIDE"),
	SUBJECT_CREATE("edu.duke.cabig.c3pr.domain.Subject:CREATE"),
	SUBJECT_READ("edu.duke.cabig.c3pr.domain.Subject:READ"),
	SUBJECT_UPDATE("edu.duke.cabig.c3pr.domain.Subject:UPDATE"),
	SUMMARY3REPORT_CREATE("edu.duke.cabig.c3pr.domain.Summary3Report:CREATE"),
	SUMMARY3REPORT_READ("edu.duke.cabig.c3pr.domain.Summary3Report:READ"),
	SUMMARY3REPORT_UPDATE("edu.duke.cabig.c3pr.domain.Summary3Report:UPDATE"),
	STUDYPERSONNEL_CREATE("edu.duke.cabig.c3pr.domain.StudyPersonnel:CREATE"),
	STUDYPERSONNEL_READ("edu.duke.cabig.c3pr.domain.StudyPersonnel:READ"),
	STUDYPERSONNEL_UPDATE("edu.duke.cabig.c3pr.domain.StudyPersonnel:UPDATE"),
	PERSONUSER_CREATE("edu.duke.cabig.c3pr.domain.PersonUser:CREATE"),
	PERSONUSER_READ("edu.duke.cabig.c3pr.domain.PersonUser:READ"),
	RPERSONUSER_UPDATE("edu.duke.cabig.c3pr.domain.PersonUser:UPDATE"),
	INVESTIGATOR_CREATE("edu.duke.cabig.c3pr.domain.Investigator:CREATE"),
	INVESTIGATOR_READ("edu.duke.cabig.c3pr.domain.Investigator:READ"),
	INVESTIGATOR_UPDATE("edu.duke.cabig.c3pr.domain.Investigator:UPDATE"),
	STUDYSITE_CREATE("edu.duke.cabig.c3pr.domain.StudySite:CREATE"),
	STUDYSITE_READ("edu.duke.cabig.c3pr.domain.StudySite:READ"),
	STUDYSITE_UPDATE("edu.duke.cabig.c3pr.domain.StudySite:UPDATE"),
	CONFIGURATION_CREATE("edu.duke.cabig.c3pr.utils.web.navigation.Task.Configuration:CREATE"),
	CONFIGURATION_READ("edu.duke.cabig.c3pr.utils.web.navigation.Task.Configuration:READ"),
	CONFIGURATION_UPDATE("edu.duke.cabig.c3pr.utils.web.navigation.Task.Configuration:UPDATE"),
	NOTIFICATION_CREATE("edu.duke.cabig.c3pr.utils.web.navigation.Task.Notification:CREATE"),
	NOTIFICATION_READ("edu.duke.cabig.c3pr.utils.web.navigation.Task.Notification:READ"),
	NOTIFICATION_UPDATE("edu.duke.cabig.c3pr.utils.web.navigation.Task.Notification:UPDATE"),
	IMPORT_CREATE("edu.duke.cabig.c3pr.utils.web.navigation.Task.Import:CREATE"),
	IMPORT_READ("edu.duke.cabig.c3pr.utils.web.navigation.Task.Import:READ"),
	IMPORT_UPDATE("edu.duke.cabig.c3pr.utils.web.navigation.Task.Import:UPDATE"),
	USER_CREATE("edu.duke.cabig.c3pr.domain.C3PRUser:CREATE"),
	USER_READ("edu.duke.cabig.c3pr.domain.C3PRUser:READ"),
	USER_UPDATE("edu.duke.cabig.c3pr.domain.C3PRUser:UPDATE"),
	INBOX_READ("edu.duke.cabig.c3pr.domain.Inbox:READ"),
	UI_RESEARCHSTAFF_SEARCH("edu.duke.cabig.c3pr.utils.web.navigation.Task.ResearchStaff:READ"),
	UI_RESEARCHSTAFF_CREATE("edu.duke.cabig.c3pr.utils.web.navigation.Task.ResearchStaff:CREATE"),
	UI_RESEARCHSTAFF_UPDATE("edu.duke.cabig.c3pr.utils.web.navigation.Task.ResearchStaff:UPDATE"),
	UI_INVESTIGATOR_SEARCH("edu.duke.cabig.c3pr.utils.web.navigation.Task.Investigator:READ"),
	UI_INVESTIGATOR_CREATE("edu.duke.cabig.c3pr.utils.web.navigation.Task.Investigator:CREATE"),
	UI_INVESTIGATOR_UPDATE("edu.duke.cabig.c3pr.utils.web.navigation.Task.Investigator:UPDATE"),
	UI_HEALTHCARE_SITE_SEARCH("edu.duke.cabig.c3pr.utils.web.navigation.Task.HealthcareSite:READ"),
	UI_HEALTHCARE_SITE_CREATE("edu.duke.cabig.c3pr.utils.web.navigation.Task.HealthcareSite:CREATE"),
	UI_HEALTHCARE_SITE_UPDATE("edu.duke.cabig.c3pr.utils.web.navigation.Task.HealthcareSite:UPDATE"),
	UI_SUBJECT_SEARCH("edu.duke.cabig.c3pr.utils.web.navigation.Task.Subject:READ"),
	UI_SUBJECT_CREATE("edu.duke.cabig.c3pr.utils.web.navigation.Task.Subject:CREATE"),
	UI_SUBJECT_UPDATE("edu.duke.cabig.c3pr.utils.web.navigation.Task.Subject:UPDATE"),
	UI_STUDY_SEARCH("edu.duke.cabig.c3pr.utils.web.navigation.Task.Study:READ"),
	UI_STUDY_CREATE("edu.duke.cabig.c3pr.utils.web.navigation.Task.Study:CREATE"),
	UI_STUDY_UPDATE("edu.duke.cabig.c3pr.utils.web.navigation.Task.Study:UPDATE"),
	UI_STUDYSUBJECT_SEARCH("edu.duke.cabig.c3pr.utils.web.navigation.Task.StudySubject:READ"),
	UI_STUDYSUBJECT_CREATE("edu.duke.cabig.c3pr.utils.web.navigation.Task.StudySubject:CREATE"),
	UI_STUDYSUBJECT_UPDATE("edu.duke.cabig.c3pr.utils.web.navigation.Task.StudySubject:UPDATE"),
	UI_PERSONUSER_CREATE("edu.duke.cabig.c3pr.utils.web.navigation.Task.PersonOrUser:CREATE"),
	UI_PERSONUSER_READ("edu.duke.cabig.c3pr.utils.web.navigation.Task.PersonOrUser:READ"),
	UI_PERSONUSER_UPDATE("edu.duke.cabig.c3pr.utils.web.navigation.Task.PersonOrUser:UPDATE"),
	;

	private String code;

	private UserPrivilegeType(String code) {
		this.code = code;
		register(this);
	}

	public String getCode() {
		return code;
	}

	public static UserPrivilegeType getByCode(String code) {
		return getByClassAndCode(UserPrivilegeType.class, code);
	}

	public String getDisplayName() {
		return sentenceCasedName(this);
	}
}
