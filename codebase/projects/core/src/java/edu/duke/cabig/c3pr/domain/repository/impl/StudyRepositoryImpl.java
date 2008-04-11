package edu.duke.cabig.c3pr.domain.repository.impl;

import java.util.List;

import javax.persistence.Transient;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.transaction.annotation.Transactional;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.HealthcareSiteInvestigatorDao;
import edu.duke.cabig.c3pr.dao.InvestigatorDao;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.domain.repository.StudyRepository;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.exception.StudyValidationException;
import edu.duke.cabig.c3pr.service.impl.StudyXMLImporterServiceImpl;


public class StudyRepositoryImpl implements StudyRepository {
	
	private StudyDao studyDao;

    private HealthcareSiteDao healthcareSiteDao;

    private InvestigatorDao investigatorDao;

    private HealthcareSiteInvestigatorDao healthcareInvestigatorDao;
    
    private Logger log = Logger.getLogger(StudyXMLImporterServiceImpl.class.getName());
    
    private C3PRExceptionHelper c3PRExceptionHelper;
	
	private MessageSource c3prErrorMessages;


    public StudyRepositoryImpl() {
		super();
		ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
		resourceBundleMessageSource.setBasename("error_messages_multisite");
		ResourceBundleMessageSource resourceBundleMessageSource1 = new ResourceBundleMessageSource();
		resourceBundleMessageSource1.setBasename("error_messages_c3pr");
		resourceBundleMessageSource1.setParentMessageSource(resourceBundleMessageSource);
		this.c3prErrorMessages = resourceBundleMessageSource1;
		this.c3PRExceptionHelper = new C3PRExceptionHelper(c3prErrorMessages);
	}

	@Transactional(readOnly = false)
    public void buildAndSave(Study study) throws Exception {

        // load study orgs from db Not to be imported
        for (StudyOrganization organization : study.getStudyOrganizations()) {
            HealthcareSite loadedSite = healthcareSiteDao.getByNciInstituteCode(organization
                            .getHealthcareSite().getNciInstituteCode());
            if (loadedSite == null) {
                throw new C3PRBaseRuntimeException(
                                "Could not load HealthcareSite from database for given NCI Institute code"
                                                + organization.getHealthcareSite()
                                                                .getNciInstituteCode());
            }
            organization.setHealthcareSite(loadedSite);

            // load Investigators from DB
            for (StudyInvestigator sInv : organization.getStudyInvestigators()) {
                Investigator inv = sInv.getHealthcareSiteInvestigator().getInvestigator();
                Investigator loadedInv = investigatorDao.getByNciInstituteCode(inv
                                .getNciIdentifier());
                if (loadedInv == null) {
                    throw new C3PRBaseRuntimeException(
                                    "Could not load Investigator from database for given NCI Identifier "
                                                    + inv.getNciIdentifier());

                }
                HealthcareSiteInvestigator loadedSiteInv = healthcareInvestigatorDao
                                .getSiteInvestigator(loadedSite, loadedInv);

                if (loadedSiteInv == null) {
                    throw new C3PRBaseRuntimeException(
                                    "Could not load HealthcareSiteInvestigator. No Investigator:"
                                                    + loadedInv.getNciIdentifier()
                                                    + " exists for HealthcareSite:"
                                                    + loadedSite.getNciInstituteCode());
                }
                sInv.setHealthcareSiteInvestigator(loadedSiteInv);
                sInv.setSiteInvestigator(loadedSiteInv);

            }

        }

        for (OrganizationAssignedIdentifier identifier : study.getOrganizationAssignedIdentifiers()) {
            HealthcareSite loadedSite = healthcareSiteDao.getByNciInstituteCode(identifier
                            .getHealthcareSite().getNciInstituteCode());
            identifier.setHealthcareSite(loadedSite);
        }

        studyDao.save(study);
        log.debug("Study saved with grid ID" + study.getGridId());
    }
    
    /**
     * Validate a study against a set of validation rules
     * 
     * @param study
     * @throws StudyValidationException
     */
    public void validate(Study study) throws StudyValidationException {
        // make sure grid id exists
        if (study.getGridId() != null) {
            if (studyDao.getByGridId(study.getGridId()) != null) {
                throw new StudyValidationException("Study exists");
            }
        }

        for (StudyOrganization organization : study.getStudyOrganizations()) {
            if (healthcareSiteDao.getByNciInstituteCode(organization.getHealthcareSite()
                            .getNciInstituteCode()) == null) {
                throw new StudyValidationException(
                                "Could not find Organization with NCI Institute code:"
                                                + organization.getHealthcareSite()
                                                                .getNciInstituteCode());
            }
        }
    }
    
    public List<Study> searchByCoOrdinatingCenterId(OrganizationAssignedIdentifier identifier)throws C3PRCodedException {
		HealthcareSite healthcareSite = this.healthcareSiteDao
				.getByNciInstituteCode(identifier.getHealthcareSite()
						.getNciInstituteCode());
		if (healthcareSite == null) {
			throw getC3PRExceptionHelper()
					.getException(
							getCode("C3PR.EXCEPTION.REGISTRATION.INVALID.HEALTHCARESITE_IDENTIFIER.CODE"),
							new String[] {
									identifier.getHealthcareSite()
											.getNciInstituteCode(),
									identifier.getType() });
		}
		identifier.setHealthcareSite(healthcareSite);
		return studyDao.searchByOrgIdentifier(identifier);
	}


	public HealthcareSiteInvestigatorDao getHealthcareInvestigatorDao() {
		return healthcareInvestigatorDao;
	}

	public void setHealthcareInvestigatorDao(
			HealthcareSiteInvestigatorDao healthcareInvestigatorDao) {
		this.healthcareInvestigatorDao = healthcareInvestigatorDao;
	}

	public HealthcareSiteDao getHealthcareSiteDao() {
		return healthcareSiteDao;
	}

	public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
		this.healthcareSiteDao = healthcareSiteDao;
	}

	public InvestigatorDao getInvestigatorDao() {
		return investigatorDao;
	}

	public void setInvestigatorDao(InvestigatorDao investigatorDao) {
		this.investigatorDao = investigatorDao;
	}

	public StudyDao getStudyDao() {
		return studyDao;
	}

	public void setStudyDao(StudyDao studyDao) {
		this.studyDao = studyDao;
	}
	
	@Transient
	public int getCode(String errortypeString) {
        return Integer.parseInt(this.c3prErrorMessages.getMessage(errortypeString, null, null));
    }
	
	@Transient
	public C3PRExceptionHelper getC3PRExceptionHelper() {
		return c3PRExceptionHelper;
	}
	
	public void setExceptionHelper(C3PRExceptionHelper c3PRExceptionHelper) {
		this.c3PRExceptionHelper = c3PRExceptionHelper;
	}
	
	@Transient
	public MessageSource getC3prErrorMessages() {
		return c3prErrorMessages;
	}
	
	public void setC3prErrorMessages(MessageSource errorMessages) {
		c3prErrorMessages = errorMessages;
	}

}
