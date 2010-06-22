package edu.duke.cabig.c3pr.constants;

import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.register;
import static gov.nih.nci.cabig.ctms.domain.EnumHelper.sentenceCasedName;
import gov.nih.nci.cabig.ctms.domain.CodedEnum;

public enum UserPrivilegeType implements CodedEnum<String>{
	STUDY_CREATE("edu.duke.cabig.c3pr.domain.Study:CREATE"),
	STUDY_READ("edu.duke.cabig.c3pr.domain.Study:READ"),
	STUDY_UPDATE("edu.duke.cabig.c3pr.domain.Study:UPDATE"),
	STUDY_OVERRIDE("edu.duke.cabig.c3pr.domain.Study:OVERRIDE"),
	HEALTHCARESITE_CREATE("edu.duke.cabig.c3pr.domain.HealthcareSite:CREATE"),
	HEALTHCARESITE_READ("edu.duke.cabig.c3pr.domain.HealthcareSite:READ"),
	HEALTHCARESITE_UPDATE("edu.duke.cabig.c3pr.domain.HealthcareSite:UPDATE"),
	HEALTHCARESITE_OVERRIDE("edu.duke.cabig.c3pr.domain.HealthcareSite:OVERRIDE"),
	STUDYSUBJECT_CREATE("edu.duke.cabig.c3pr.domain.StudySubject:CREATE"),
	STUDYSUBJECT_READ("edu.duke.cabig.c3pr.domain.StudySubject:READ"),
	STUDYSUBJECT_UPDATE("edu.duke.cabig.c3pr.domain.StudySubject:UPDATE"),
	STUDYSUBJECT_OVERRIDE("edu.duke.cabig.c3pr.domain.StudySubject:OVERRIDE"),
	SUBJECT_CREATE("edu.duke.cabig.c3pr.domain.Participant:CREATE"),
	SUBJECT_READ("edu.duke.cabig.c3pr.domain.Participant:READ"),
	SUBJECT_UPDATE("edu.duke.cabig.c3pr.domain.Participant:UPDATE"),
	SUMMARY3REPORT_CREATE("edu.duke.cabig.c3pr.domain.Summary3Report:CREATE"),
	SUMMARY3REPORT_READ("edu.duke.cabig.c3pr.domain.Summary3Report:READ"),
	SUMMARY3REPORT_UPDATE("edu.duke.cabig.c3pr.domain.Summary3Report:UPDATE"),
	STUDYPERSONNEL_CREATE("edu.duke.cabig.c3pr.domain.StudyPersonnel:CREATE"),
	STUDYPERSONNEL_READ("edu.duke.cabig.c3pr.domain.StudyPersonnel:READ"),
	STUDYPERSONNEL_UPDATE("edu.duke.cabig.c3pr.domain.StudyPersonnel:UPDATE"),
	RESEARCHSTAFF_CREATE("edu.duke.cabig.c3pr.domain.ResearchStaff:CREATE"),
	RESEARCHSTAFF_READ("edu.duke.cabig.c3pr.domain.ResearchStaff:READ"),
	RESEARCHSTAFF_UPDATE("edu.duke.cabig.c3pr.domain.ResearchStaff:UPDATE"),
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
	USER_UPDATE("edu.duke.cabig.c3pr.domain.C3PRUser:UPDATE");

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
