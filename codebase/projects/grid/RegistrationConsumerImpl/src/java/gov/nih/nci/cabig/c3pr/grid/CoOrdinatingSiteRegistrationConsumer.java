/**
 *    Test Registration Consumer. Just logs the incoming message
 */
package gov.nih.nci.cabig.c3pr.grid;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.ScheduledNonTreatmentEpoch;
import edu.duke.cabig.c3pr.domain.ScheduledTreatmentEpoch;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.TreatmentEpoch;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.service.StudySubjectService;
import gov.nih.nci.cagrid.metadata.security.ServiceSecurityMetadata;
import gov.nih.nci.ccts.grid.HealthcareSiteType;
import gov.nih.nci.ccts.grid.IdentifierType;
import gov.nih.nci.ccts.grid.OrganizationAssignedIdentifierType;
import gov.nih.nci.ccts.grid.ParticipantType;
import gov.nih.nci.ccts.grid.Registration;
import gov.nih.nci.ccts.grid.ScheduledEpochType;
import gov.nih.nci.ccts.grid.ScheduledTreatmentEpochType;
import gov.nih.nci.ccts.grid.StudySiteType;
import gov.nih.nci.ccts.grid.SystemAssignedIdentifierType;
import gov.nih.nci.ccts.grid.common.RegistrationConsumer;
import gov.nih.nci.ccts.grid.stubs.types.InvalidRegistrationException;
import gov.nih.nci.ccts.grid.stubs.types.RegistrationConsumptionException;

import java.rmi.RemoteException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;

public class CoOrdinatingSiteRegistrationConsumer implements RegistrationConsumer {

	private C3PRExceptionHelper exceptionHelper;
	
	private MessageSource multsiteErrorMessageSource;
	
	private static final Log logger = LogFactory.getLog(CoOrdinatingSiteRegistrationConsumer.class);

	private final String identifierTypeValueStr = "Coordinating Center Identifier";

	private final String prtIdentifierTypeValueStr = "MRN";

	private HealthcareSiteDao healthcareSiteDao;

	private StudyDao studyDao;

	private ParticipantDao participantDao;

	private StudySubjectDao studySubjectDao;

	private StudySubjectService studySubjectService;

	public void setStudySubjectService(StudySubjectService studySubjectService) {
		this.studySubjectService = studySubjectService;
	}

	public void setStudySubjectDao(StudySubjectDao studySubjectDao) {
		this.studySubjectDao = studySubjectDao;
	}

	public void setStudyDao(StudyDao studyDao) {
		this.studyDao = studyDao;
	}

	public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
		this.healthcareSiteDao = healthcareSiteDao;
	}

	public CoOrdinatingSiteRegistrationConsumer() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.cabig.ctms.common.RegistrationConsumer#createRegistration(gov.nih.nci.cabig.ctms.grid.RegistrationType)
	 */

	public Registration register(Registration registration)	throws InvalidRegistrationException,RegistrationConsumptionException, RemoteException {
		InvalidRegistrationException invalidRegistrationException = new InvalidRegistrationException();
		System.out.println("Registration received with Grid Id "
				+ registration.getGridId());
		Participant participant = null;
		Study study = new Study(true);
		StudySubject studySubject = new StudySubject();
		OrganizationAssignedIdentifier identifier = null;
		StudySite studySite = null;
		ParticipantType participantType = registration.getParticipant();
		ScheduledEpochType scheduledEpochType = registration
				.getScheduledEpoch();
		try {
			if (participantType.getIdentifier()==null || participantType.getIdentifier().length == 0) {
				throw this.exceptionHelper.getException(getCode("MULTISITE.EXCEPTION.MISSING.SUBJECT_IDENTIFIER.CODE"));
			}
			for (IdentifierType identifierType : participantType
					.getIdentifier()) {
				if (identifierType instanceof OrganizationAssignedIdentifierType
						&& ((OrganizationAssignedIdentifierType) identifierType).getType().equals(this.prtIdentifierTypeValueStr)) {
					OrganizationAssignedIdentifierType orgIdentifier = (OrganizationAssignedIdentifierType) identifierType;
					Participant temp = new Participant();
					OrganizationAssignedIdentifier prtIdentifier = temp
							.getOrganizationAssignedIdentifiers().get(0);
					prtIdentifier.setType(this.prtIdentifierTypeValueStr);
					prtIdentifier.setValue(orgIdentifier.getValue());
					HealthcareSiteType healthcareSiteType = orgIdentifier
							.getHealthcareSite();
					HealthcareSite healthcareSite = getHealthcareSite(healthcareSiteType);
					if (healthcareSite == null) {
						throw this.exceptionHelper.getException(getCode("MULTISITE.EXCEPTION.INVALID.HEALTHCARESITE_SUBJECT_IDENTIFIER.CODE")
								,new String[]{healthcareSiteType.getNciInstituteCode(), this.prtIdentifierTypeValueStr});
					}
					prtIdentifier.setHealthcareSite(healthcareSite);
					List<Participant> paList = participantDao.searchByExample(temp, true);
					if (paList.size() > 1) {
						throw this.exceptionHelper.getException(getCode("MULTISITE.EXCEPTION.MULTIPLE.SUBJECTS_SAME_MRN.CODE")
								,new String[]{prtIdentifier.getValue()});
					}
					if (paList.size() == 1) {
						System.out.println("Participant with the same MRN found in the database");
						temp = paList.get(0);
						if (temp.getFirstName() == participantType.getFirstName()
								&& temp.getLastName() == participantType.getLastName()
								&& temp.getBirthDate() == participantType.getBirthDate()) {
							participant = temp;
						} else {
							throw this.exceptionHelper.getException(getCode("MULTISITE.EXCEPTION.MULTIPLE.SUBJECTS_SAME_MRN.CODE"));
						}
					}
					break;
				}
			}
			if(registration.getStudyRef().getIdentifier()==null){
				throw this.exceptionHelper.getException(getCode("MULTISITE.EXCEPTION.MISSING.STUDY_IDENTIFIER.CODE"));
			}
			for (IdentifierType identifierType : registration.getStudyRef().getIdentifier()) {
				if (identifierType instanceof OrganizationAssignedIdentifierType
						&& ((OrganizationAssignedIdentifierType) identifierType).getType().equals(this.identifierTypeValueStr)) {
					OrganizationAssignedIdentifierType orgIdentifier = (OrganizationAssignedIdentifierType) identifierType;
					identifier = study.getOrganizationAssignedIdentifiers().get(0);
					identifier.setType(this.identifierTypeValueStr);
					identifier.setValue(orgIdentifier.getValue());
					HealthcareSiteType healthcareSiteType = orgIdentifier.getHealthcareSite();
					HealthcareSite healthcareSite = getHealthcareSite(healthcareSiteType);
					if (healthcareSite == null) {
						throw this.exceptionHelper.getException(getCode("MULTISITE.EXCEPTION.NOTFOUND.HEALTHCARESITE_STUDY_CO_IDENTIFIER.CODE")
								,new String[]{healthcareSiteType.getNciInstituteCode()});
					}
					identifier.setHealthcareSite(healthcareSite);
					break;
				}
			}
			if (identifier == null) {
				throw this.exceptionHelper.getException(getCode("MULTISITE.EXCEPTION.MISSING.STUDY_IDENTIFIER.CODE"));
			}
			List<Study> studies = studyDao.searchByExample(study, true);
			if (studies.size() == 0) {
				throw this.exceptionHelper.getException(getCode("MULTISITE.EXCEPTION.NOTFOUND.STUDY_WITH_IDENTIFIER.CODE")
						,new String[]{identifier.getHealthcareSite().getNciInstituteCode(),this.identifierTypeValueStr});
			}
			if (studies.size() > 1) {
				throw this.exceptionHelper.getException(getCode("MULTISITE.EXCEPTION.MULTIPLE.STUDY_SAME_CO_IDENTIFIER.CODE")
						,new String[]{identifier.getHealthcareSite().getNciInstituteCode(),this.identifierTypeValueStr});
			}
			study = studies.get(0);
			StudySiteType studySiteType = registration.getStudySite();
			for (StudySite temp : study.getStudySites()) {
				if (temp.getHealthcareSite().getNciInstituteCode().equals(studySiteType.getHealthcareSite(0).getNciInstituteCode())) {
					studySite = temp;
				}
			}
			if (studySite == null) {
				throw this.exceptionHelper.getException(getCode("MULTISITE.EXCEPTION.NOTFOUND.STUDYSITE_WITH_NCICODE.CODE")
						,new String[]{studySiteType.getHealthcareSite(0).getNciInstituteCode()});
			}
			if (participant != null) {
				StudySubject exampleSS = new StudySubject(true);
				exampleSS.setParticipant(participant);
				exampleSS.setStudySite(studySite);
				List<StudySubject> registrations = studySubjectDao
						.searchBySubjectAndStudySite(exampleSS);
				if (registrations.size() > 1) {
					throw this.exceptionHelper.getException(getCode("MULTISITE.EXCEPTION.MULTIPLE.STUDYSUBJECTS_SAME_SUBJECT_STUDYSITE.CODE"));
				}
				if (registrations.size() == 1) {
					System.out.println("A study subject with the participant on the study site already exists");
					if (!changeSubjectEpochRequest(registration, registrations.get(0))) {
						throw this.exceptionHelper.getException(getCode("MULTISITE.EXCEPTION.MULTIPLE.STUDYSUBJECTS_ALREADY_EXISTS.CODE"));
					} else {
						studySubject = registrations.get(0);
					}
				}
			} else {
				participant = createNewParticipant(participantType);
			}
			if (studySubject.getId() == null) {
				studySubject.setStudySite(studySite);
				studySubject.setParticipant(participant);
			}
			Epoch epoch = null;
			for (Epoch epochCurr : studySubject.getStudySite().getStudy().getEpochs()) {
				if (epochCurr.getName().equalsIgnoreCase(scheduledEpochType.getEpoch().getName())) {
					epoch = epochCurr;
				}
			}
			if (epoch == null) {
				throw this.exceptionHelper.getException(getCode("MULTISITE.EXCEPTION.NOTFOUND.EPOCH_NAME.CODE")
						,new String[]{scheduledEpochType.getEpoch().getName()});
			}
			ScheduledEpoch scheduledEpoch;
			if (epoch instanceof TreatmentEpoch) {
				scheduledEpoch = new ScheduledTreatmentEpoch();
				ScheduledTreatmentEpoch scheduledTreatmentEpoch=(ScheduledTreatmentEpoch) scheduledEpoch;
				ScheduledTreatmentEpochType scheduledTreatmentEpochType=(ScheduledTreatmentEpochType) scheduledEpochType;
				scheduledTreatmentEpoch.setEligibilityIndicator(scheduledTreatmentEpochType.isEligibilityIndicator());
				if(scheduledTreatmentEpochType.getScheduledArm()!=null
						&&scheduledTreatmentEpochType.getScheduledArm().getArm()!=null
						&&scheduledTreatmentEpochType.getScheduledArm().getArm().getName()!=null){
					Arm arm=null;
					for(Arm a: ((TreatmentEpoch)epoch).getArms()){
						if(a.getName().equals(scheduledTreatmentEpochType.getScheduledArm().getArm().getName())){
							arm=a;
						}
					}
					if(arm==null){
						throw this.exceptionHelper.getException(getCode("MULTISITE.EXCEPTION.NOTFOUND.ARM_NAME.CODE")
								,new String[]{scheduledTreatmentEpochType.getScheduledArm().getArm().getName(),scheduledEpochType.getEpoch().getName()});
					}
					scheduledTreatmentEpoch.getScheduledArms().get(0).setArm(arm);
				}
			} else {
				scheduledEpoch = new ScheduledNonTreatmentEpoch();
			}
			scheduledEpoch.setEpoch(epoch);
			studySubject.getScheduledEpochs().add(0, scheduledEpoch);
			fillStudySubjectDetails(studySubject, registration);
			this.studySubjectService.processAffliateSiteRegistrationRequest(studySubject);
		}catch (InvalidRegistrationException e){
			throw e;
		}catch (C3PRCodedException e) {
			e.printStackTrace();
			invalidRegistrationException.setFaultCode(e.getExceptionCode()+"");
			invalidRegistrationException.setFaultDetailString(e.getMessage());
			invalidRegistrationException.setStackTrace(e.getStackTrace());
			throw invalidRegistrationException;
		}catch (Exception e) {
			e.printStackTrace();
			invalidRegistrationException.setFaultCode("-1");
			invalidRegistrationException.setFaultDetailString(e.getMessage());
			invalidRegistrationException.setStackTrace(e.getStackTrace());
			throw invalidRegistrationException;
		}
		return registration;
	}

	private boolean changeSubjectEpochRequest(Registration cctsRegistration,
			StudySubject studySubject) {
		return false;
	}

	private Participant createNewParticipant(ParticipantType participantType) {
		Participant participant = new Participant();
		participant.setFirstName(participantType.getFirstName());
		participant.setLastName(participantType.getLastName());
		participant.setAdministrativeGenderCode(participantType
				.getAdministrativeGenderCode());
		participant.setBirthDate(participantType.getBirthDate());
		participant.setEthnicGroupCode(participantType.getEthnicGroupCode());
		participant
				.setMaritalStatusCode(participantType.getMaritalStatusCode());
		participant.setRaceCode(participantType.getRaceCode());
		IdentifierType[] identifierTypes = participantType.getIdentifier();
		for (IdentifierType identifierType : identifierTypes) {
			if (identifierType instanceof OrganizationAssignedIdentifierType) {
				OrganizationAssignedIdentifierType organizationAssignedIdentifierType = (OrganizationAssignedIdentifierType) identifierType;
				OrganizationAssignedIdentifier identifier = new OrganizationAssignedIdentifier();
				identifier
						.setHealthcareSite(getHealthcareSite(organizationAssignedIdentifierType
								.getHealthcareSite()));
				identifier.setType(identifierType.getType());
				identifier.setValue(identifierType.getValue());
				participant.addIdentifier(identifier);

			} else {
				SystemAssignedIdentifierType systemAssignedIdentifierType = (SystemAssignedIdentifierType) identifierType;
				SystemAssignedIdentifier identifier = new SystemAssignedIdentifier();
				identifier.setSystemName(systemAssignedIdentifierType
						.getSystemName());
				identifier.setType(identifierType.getType());
				identifier.setValue(identifierType.getValue());
				participant.addIdentifier(identifier);
			}
		}
		return participant;
	}

	public ServiceSecurityMetadata getServiceSecurityMetadata()
			throws RemoteException {
		throw new UnsupportedOperationException("Not implemented");
	}

	private void fillStudySubjectDetails(StudySubject studySubject,
			Registration cctsRegistration) {
		studySubject.setInformedConsentSignedDate(cctsRegistration
				.getInformedConsentFormSignedDate());
		studySubject.setInformedConsentVersion(cctsRegistration
				.getInformedConsentVersion());
		studySubject.setStartDate(cctsRegistration.getStartDate());
		studySubject
				.setStratumGroupNumber(extractStartumGroupNumber(cctsRegistration
						.getStratumGroup()));
	}

	private HealthcareSite getHealthcareSite(
			HealthcareSiteType healthcareSiteType) {
		return healthcareSiteDao.getByNciInstituteCode(healthcareSiteType
				.getNciInstituteCode());
	}

	private Integer extractStartumGroupNumber(String stratumGroup) {
		return Integer.parseInt(stratumGroup.split(":")[0]);
	}

	public void setParticipantDao(ParticipantDao participantDao) {
		this.participantDao = participantDao;
	}

	public void setExceptionHelper(C3PRExceptionHelper exceptionHelper) {
		this.exceptionHelper = exceptionHelper;
	}

	private int getCode(String errortypeString){
		return Integer.parseInt(this.multsiteErrorMessageSource.getMessage(errortypeString, null, null));
	}

	public void setMultsiteErrorMessageSource(
			MessageSource multsiteErrorMessageSource) {
		this.multsiteErrorMessageSource = multsiteErrorMessageSource;
	}

	
}