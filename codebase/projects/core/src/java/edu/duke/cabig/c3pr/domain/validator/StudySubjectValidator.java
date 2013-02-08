/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain.validator;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;

public class StudySubjectValidator implements Validator {
    private StudySubjectDao studySubjectDao ;
    public StudySubjectDao getStudySubjectDao() {
		return studySubjectDao;
	}

	public void setStudySubjectDao(StudySubjectDao studySubjectDao) {
		this.studySubjectDao = studySubjectDao;
	}

	private MessageSource c3prErrorMessages;
	public MessageSource getC3prErrorMessages() {
		return c3prErrorMessages;
	}

	public void setC3prErrorMessages(MessageSource errorMessages) {
		c3prErrorMessages = errorMessages;
	}
	
	public int getCode(String errortypeString) {
		return Integer.parseInt(this.c3prErrorMessages.getMessage(
				errortypeString, null, null));
	}
	
	public String getMessageFromCode(int code, Object[] params, Locale locale) {
		String msg = "";
		try {
			msg = c3prErrorMessages.getMessage(code + "", params, locale);
		} catch (NoSuchMessageException e) {
			try {
				msg = c3prErrorMessages.getMessage(-1 + "", null, null);
			} catch (NoSuchMessageException e1) {
				msg = "Exception Code property file missing";
			}
		}
		return msg;
	}

	private Logger log = Logger.getLogger(StudySubjectValidator.class);
    
    public boolean supports(Class clazz) {
        return StudySubject.class.isAssignableFrom(clazz);
    }

	public void validateIdentifiers(Object target, Errors errors) {
		StudySubject studySubject = (StudySubject) target;

		List<OrganizationAssignedIdentifier> allOrganizationAssigedIdentitiers = studySubject
				.getOrganizationAssignedIdentifiers();
		try {
			for (int orgIdentifierIndex = 0; orgIdentifierIndex < allOrganizationAssigedIdentitiers
					.size(); orgIdentifierIndex++) {
				errors.pushNestedPath("studySubject.organizationAssignedIdentifiers["
						+ orgIdentifierIndex + "]");
				errors.popNestedPath();
			}

			Set<OrganizationAssignedIdentifier> uniqueOrgIdentifiers = new HashSet<OrganizationAssignedIdentifier>();
			uniqueOrgIdentifiers.addAll(allOrganizationAssigedIdentitiers);
			if (allOrganizationAssigedIdentitiers.size() > uniqueOrgIdentifiers
					.size()) {
				errors.rejectValue(
						"studySubject.organizationAssignedIdentifiers",
						new Integer(
								getCode("C3PR.STUDY.DUPLICATE.ORGANIZATION_ASSIGNED_IDENTIFIER.ERROR"))
								.toString(),
						getMessageFromCode(
								getCode("C3PR.STUDY.DUPLICATE.ORGANIZATION_ASSIGNED_IDENTIFIER.ERROR"),
								null, null));
			}
		} catch (Exception ex) {
			log.debug(ex.getMessage());
		}

		List<SystemAssignedIdentifier> allSystemAssigedIdentitiers = studySubject
				.getSystemAssignedIdentifiers();
		try {
			for (int sysIdentifierIndex = 0; sysIdentifierIndex < allSystemAssigedIdentitiers
					.size(); sysIdentifierIndex++) {
				errors.pushNestedPath("studySubject.systemAssignedIdentifiers["
						+ sysIdentifierIndex + "]");
				errors.popNestedPath();
			}
			Set<SystemAssignedIdentifier> uniqueSysIdentifiers = new HashSet<SystemAssignedIdentifier>();
			uniqueSysIdentifiers.addAll(allSystemAssigedIdentitiers);
			if (allSystemAssigedIdentitiers.size() > uniqueSysIdentifiers
					.size()) {
				errors.rejectValue(
						"study.systemAssignedIdentifiers",
						new Integer(
								getCode("C3PR.STUDY.DUPLICATE.SYSTEM_ASSIGNED_IDENTIFIER.ERROR"))
								.toString(),
						getMessageFromCode(
								getCode("C3PR.STUDY.DUPLICATE.SYSTEM_ASSIGNED_IDENTIFIER.ERROR"),
								null, null));
			}

		} catch (Exception ex) {
			log.debug(ex.getMessage());
		}
	}

	public void validate(Object target, Errors errors) {
		validateIdentifiers(target, errors);
	}

}
