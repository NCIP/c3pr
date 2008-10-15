package edu.duke.cabig.c3pr.domain.factory;

import java.util.List;

import javax.persistence.Transient;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.transaction.annotation.Transactional;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.HealthcareSiteInvestigatorDao;
import edu.duke.cabig.c3pr.dao.InvestigatorDao;
import edu.duke.cabig.c3pr.domain.CompanionStudyAssociation;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.service.impl.StudyXMLImporterServiceImpl;

public class StudyFactory {
	
	private HealthcareSiteDao healthcareSiteDao;

	private InvestigatorDao investigatorDao;

	private HealthcareSiteInvestigatorDao healthcareSiteInvestigatorDao;

	private Logger log = Logger.getLogger(StudyFactory.class
			.getName());

	private C3PRExceptionHelper c3PRExceptionHelper;

	private MessageSource c3prErrorMessages;
	 
	
	@Transactional(readOnly = false)
	public Study buildStudy(Study study) throws C3PRCodedException {

		// load study orgs from db Not to be imported
		for (StudyOrganization organization : study.getStudyOrganizations()) {
			HealthcareSite loadedSite = healthcareSiteDao
					.getByNciInstituteCode(organization.getHealthcareSite()
							.getNciInstituteCode());
			if (loadedSite == null) {
				throw getC3PRExceptionHelper()
						.getException(
								getCode("C3PR.EXCEPTION.STUDY.INVALID.HEALTHCARESITE_IDENTIFIER.CODE"),
								new String[] { organization.getHealthcareSite()
										.getNciInstituteCode() });
			}
			organization.setHealthcareSite(loadedSite);

			// load Investigators from DB
			for (StudyInvestigator sInv : organization.getStudyInvestigators()) {
				Investigator inv = sInv.getHealthcareSiteInvestigator()
						.getInvestigator();
				List<Investigator> loadedInvestigators = investigatorDao
						.getInvestigatorsByNciInstituteCode(inv
								.getNciIdentifier());
				Investigator loadedInv = null;
				if (loadedInvestigators.size() > 0) {
					loadedInv = loadedInvestigators.get(0);
				}
				if (loadedInv == null) {
					throw getC3PRExceptionHelper()
							.getException(
									getCode("C3PR.EXCEPTION.STUDY.INVALID.NCI_IDENTIFIER.CODE"),
									new String[] { inv.getNciIdentifier() });
				}
				HealthcareSiteInvestigator loadedSiteInv = healthcareSiteInvestigatorDao
						.getSiteInvestigator(loadedSite, loadedInv);

				if (loadedSiteInv == null) {
					throw getC3PRExceptionHelper()
							.getException(
									getCode("C3PR.EXCEPTION.REGISTRATION.INVALID.HEALTHCARESITE_IDENTIFIER.CODE"),
									new String[] { loadedSite.getName(),
											loadedInv.getFullName() });
				}
				sInv.setHealthcareSiteInvestigator(loadedSiteInv);
				sInv.setSiteInvestigator(loadedSiteInv);

			}

		}

		for (OrganizationAssignedIdentifier identifier : study
				.getOrganizationAssignedIdentifiers()) {
			HealthcareSite loadedSite = healthcareSiteDao
					.getByNciInstituteCode(identifier.getHealthcareSite()
							.getNciInstituteCode());
			identifier.setHealthcareSite(loadedSite);
		}
		
		// build all the companion studies of the study.
		for (CompanionStudyAssociation companionStudyAssociation : study.getCompanionStudyAssociations()){
			buildStudy(companionStudyAssociation.getCompanionStudy());
		}
		
		return study;
		
	}
	
	public C3PRExceptionHelper getC3PRExceptionHelper() {
		return c3PRExceptionHelper;
	}


	public void setC3PRExceptionHelper(C3PRExceptionHelper exceptionHelper) {
		c3PRExceptionHelper = exceptionHelper;
	}


	public MessageSource getC3prErrorMessages() {
		return c3prErrorMessages;
	}


	public void setC3prErrorMessages(MessageSource errorMessages) {
		c3prErrorMessages = errorMessages;
	}


	public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
		this.healthcareSiteDao = healthcareSiteDao;
	}


	public void setInvestigatorDao(InvestigatorDao investigatorDao) {
		this.investigatorDao = investigatorDao;
	}


	public void setHealthcareSiteInvestigatorDao(
			HealthcareSiteInvestigatorDao healthcareSiteInvestigatorDao) {
		this.healthcareSiteInvestigatorDao = healthcareSiteInvestigatorDao;
	}

	@Transient
	public int getCode(String errortypeString) {
		return Integer.parseInt(this.c3prErrorMessages.getMessage(
				errortypeString, null, null));
	}


}
