package edu.duke.cabig.c3pr.domain.factory;

import java.util.List;

import javax.persistence.Transient;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.transaction.annotation.Transactional;

import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;
import edu.duke.cabig.c3pr.dao.DiseaseTermDao;
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.HealthcareSiteInvestigatorDao;
import edu.duke.cabig.c3pr.dao.InvestigatorDao;
import edu.duke.cabig.c3pr.domain.CompanionStudyAssociation;
import edu.duke.cabig.c3pr.domain.DiseaseTerm;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyDisease;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;

public class StudyFactory {
	
	private HealthcareSiteDao healthcareSiteDao;

	private InvestigatorDao investigatorDao;
	
	private DiseaseTermDao diseaseTermDao;

	public void setDiseaseTermDao(DiseaseTermDao diseaseTermDao) {
		this.diseaseTermDao = diseaseTermDao;
	}

	private HealthcareSiteInvestigatorDao healthcareSiteInvestigatorDao;

	private Logger log = Logger.getLogger(StudyFactory.class
			.getName());

	private C3PRExceptionHelper exceptionHelper;

	private MessageSource c3prErrorMessages;
	 
	
	@Transactional(readOnly = false)
	public Study buildStudy(Study study) throws C3PRCodedException {

		// load study orgs from db Not to be imported
		for (StudyOrganization organization : study.getStudyOrganizations()) {
			HealthcareSite loadedSite = healthcareSiteDao
					.getByPrimaryIdentifier(organization.getHealthcareSite()
							.getPrimaryIdentifier());
			if (loadedSite == null) {
				throw exceptionHelper
						.getException(
								getCode("C3PR.EXCEPTION.STUDY.INVALID.HEALTHCARESITE_IDENTIFIER.CODE"),
								new String[] { organization.getHealthcareSite()
										.getPrimaryIdentifier() });
			}
			organization.setHealthcareSite(loadedSite);

			// load Investigators from DB
			for (StudyInvestigator sInv : organization.getStudyInvestigators()) {
				Investigator inv = sInv.getHealthcareSiteInvestigator()
						.getInvestigator();
				Investigator loadedInvestigator = investigatorDao
											.getByAssignedIdentifier(inv.getAssignedIdentifier());
				if (loadedInvestigator == null) {
					throw exceptionHelper
							.getException(
									getCode("C3PR.EXCEPTION.STUDY.INVALID.NCI_IDENTIFIER.CODE"),
									new String[] { inv.getAssignedIdentifier() });
				}
				HealthcareSiteInvestigator loadedSiteInv = healthcareSiteInvestigatorDao
						.getSiteInvestigator(loadedSite, loadedInvestigator);

				if (loadedSiteInv == null) {
					throw exceptionHelper
							.getException(
									getCode("C3PR.EXCEPTION.STUDY.INVALID.HEALTHCARESITE_INVESTIGATOR.CODE"),
									new String[] {loadedInvestigator.getFullName(),loadedSite.getName()});
				}
				sInv.setHealthcareSiteInvestigator(loadedSiteInv);
				sInv.setSiteInvestigator(loadedSiteInv);

			}

		}

		for (OrganizationAssignedIdentifier identifier : study
				.getOrganizationAssignedIdentifiers()) {
			HealthcareSite loadedSite = healthcareSiteDao
					.getByPrimaryIdentifier(identifier.getHealthcareSite()
							.getPrimaryIdentifier());
			identifier.setHealthcareSite(loadedSite);
			if(identifier.getType()==OrganizationIdentifierTypeEnum.COORDINATING_CENTER_IDENTIFIER){
				identifier.setPrimaryIndicator(true);
			}
		}
		
		//build and save all study diseases
		
		for(StudyDisease studyDisease :study.getStudyDiseases()){
			List<DiseaseTerm> loadedDiseaseTerms = diseaseTermDao.getByCtepTerm(studyDisease.getDiseaseTerm().getCtepTerm());
			if (loadedDiseaseTerms == null) {
				throw exceptionHelper
						.getException(
								getCode("C3PR.EXCEPTION.STUDY.INVALID.CTEP_TERM.CODE"),
								new String[] {studyDisease.getDiseaseTerm().getCtepTerm()});
			}
			studyDisease.setDiseaseTerm(loadedDiseaseTerms.get(0));
		}
		
		// build all the companion studies of the study.
		for (CompanionStudyAssociation companionStudyAssociation : study.getStudyVersion().getCompanionStudyAssociations()){
			buildStudy(companionStudyAssociation.getCompanionStudy());
		}
		
		//generate stratum groups if necessary
		for(Epoch epoch: study.getEpochs()){
			if(epoch.getStratificationIndicator() && epoch.getStratificationCriteria().size()>0){
				epoch.generateStratumGroups();
			}
		}
		
		return study;
		
	}
	
	public void setC3PRExceptionHelper(C3PRExceptionHelper exceptionHelper) {
		exceptionHelper = exceptionHelper;
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

    public void setExceptionHelper(C3PRExceptionHelper exceptionHelper) {
        this.exceptionHelper = exceptionHelper;
    }


}
