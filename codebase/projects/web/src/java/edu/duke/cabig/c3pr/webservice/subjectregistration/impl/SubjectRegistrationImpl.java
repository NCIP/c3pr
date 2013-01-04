/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 *   Distributed under the OSI-approved BSD 3-Clause License.
 *   See https://github.com/NCIP/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.webservice.subjectregistration.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.jws.WebService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.semanticbits.querybuilder.AdvancedSearchCriteriaParameter;

import edu.duke.cabig.c3pr.dao.ICD9DiseaseSiteDao;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.dao.ReasonDao;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.Consent;
import edu.duke.cabig.c3pr.domain.ConsentQuestion;
import edu.duke.cabig.c3pr.domain.DiseaseHistory;
import edu.duke.cabig.c3pr.domain.EligibilityCriteria;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.ICD9DiseaseSite;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.Reason;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.StratificationCriterion;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyDisease;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubjectConsentVersion;
import edu.duke.cabig.c3pr.domain.SubjectConsentQuestionAnswer;
import edu.duke.cabig.c3pr.domain.SubjectEligibilityAnswer;
import edu.duke.cabig.c3pr.domain.SubjectStratificationAnswer;
import edu.duke.cabig.c3pr.domain.repository.StudyRepository;
import edu.duke.cabig.c3pr.domain.repository.StudySubjectRepository;
import edu.duke.cabig.c3pr.exception.C3PRCodedRuntimeException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.exception.ConversionException;
import edu.duke.cabig.c3pr.webservice.common.AdvanceSearchCriterionParameter;
import edu.duke.cabig.c3pr.webservice.common.BiologicEntityIdentifier;
import edu.duke.cabig.c3pr.webservice.common.DocumentIdentifier;
import edu.duke.cabig.c3pr.webservice.common.DuplicateStudySubjectExceptionFault;
import edu.duke.cabig.c3pr.webservice.common.InvalidQueryExceptionFault;
import edu.duke.cabig.c3pr.webservice.common.InvalidSiteExceptionFault;
import edu.duke.cabig.c3pr.webservice.common.InvalidStudyProtocolExceptionFault;
import edu.duke.cabig.c3pr.webservice.common.InvalidStudySubjectDataExceptionFault;
import edu.duke.cabig.c3pr.webservice.common.OrganizationIdentifier;
import edu.duke.cabig.c3pr.webservice.common.Person;
import edu.duke.cabig.c3pr.webservice.common.SubjectIdentifier;
import edu.duke.cabig.c3pr.webservice.iso21090.ANY;
import edu.duke.cabig.c3pr.webservice.iso21090.CD;
import edu.duke.cabig.c3pr.webservice.subjectregistration.ChangeStudySubjectEpochAssignmentRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.ChangeStudySubjectEpochAssignmentResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.DSETStudySubject;
import edu.duke.cabig.c3pr.webservice.subjectregistration.DiscontinueEnrollmentRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.DiscontinueEnrollmentResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.DuplicateStudySubjectExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectregistration.EnrollSubjectExistingRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.EnrollSubjectExistingResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.EnrollSubjectNewRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.EnrollSubjectNewResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.FailSubjectScreeningRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.FailSubjectScreeningResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.GenerateSummary3ReportRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.GenerateSummary3ReportResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.HealthcareProvider;
import edu.duke.cabig.c3pr.webservice.subjectregistration.ImportRegistrationsRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.ImportRegistrationsResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.InitiateSubjectEnrollmentRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.InitiateSubjectEnrollmentResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.InvalidQueryExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectregistration.InvalidSiteExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectregistration.InvalidStudyProtocolExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectregistration.InvalidStudySubjectDataExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectregistration.NoSuchPatientExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectregistration.NoSuchStudySubjectExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectregistration.PerformedDiagnosis;
import edu.duke.cabig.c3pr.webservice.subjectregistration.QuerySubjectRegistrationRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.QuerySubjectRegistrationResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.ReconsentStudySubjectRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.ReconsentStudySubjectResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.RegisterButNotEnrollSubjectExistingRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.RegisterButNotEnrollSubjectExistingResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.RegisterButNotEnrollSubjectNewRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.RegisterButNotEnrollSubjectNewResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.ReserveSlotExistingRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.ReserveSlotExistingResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.ReserveSlotNewRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.ReserveSlotNewResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.RetrieveAccrualDataRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.RetrieveAccrualDataResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.RetrieveSubjectDemographyHistoryRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.RetrieveSubjectDemographyHistoryResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.SecurityExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectregistration.StudyInvestigator;
import edu.duke.cabig.c3pr.webservice.subjectregistration.StudySubject;
import edu.duke.cabig.c3pr.webservice.subjectregistration.StudySubjectProtocolVersionRelationship;
import edu.duke.cabig.c3pr.webservice.subjectregistration.SubjectRegistration;
import edu.duke.cabig.c3pr.webservice.subjectregistration.SubjectRegistrationRejectedExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectregistration.TakeSubjectOffStudyRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.TakeSubjectOffStudyResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.UpdateRegistrationRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.UpdateRegistrationResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.UpdateStudySubjectConsentRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.UpdateStudySubjectConsentResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.coverters.SubjectRegistrationJAXBToDomainObjectConverter;
import edu.duke.cabig.c3pr.webservice.subjectregistry.InitiateStudySubjectRegistryResponse;

/**
 * The Class SubjectRegistrationImpl.
 */
@WebService(wsdlLocation="/WEB-INF/wsdl/SubjectRegistration.wsdl", targetNamespace = "http://enterpriseservices.nci.nih.gov/SubjectRegistrationService", endpointInterface = "edu.duke.cabig.c3pr.webservice.subjectregistration.SubjectRegistration", portName = "SubjectRegistration", serviceName = "SubjectRegistrationService")
public class SubjectRegistrationImpl implements SubjectRegistration {
	
	/** Logger for this class. */
	private static final Logger log = Logger
			.getLogger(SubjectRegistrationImpl.class);
	
	/** The Constant NON_UNIQUE_IDENTIFIER. */
	private static final int NON_UNIQUE_IDENTIFIER = 910;
	
	/** The Constant MISSING_SUBJECT_IDENTIFIER. */
	private static final int MISSING_SUBJECT_IDENTIFIER = 902;
	
	/** The Constant SUBJECT_NOT_FOUND. */
	private static final int SUBJECT_NOT_FOUND = 911;
	
	/** The Constant DUPLICATE_STUDYSUBJECT_IDENTIFIER. */
	private static final int DUPLICATE_STUDYSUBJECT_IDENTIFIER = 913;
	
	/** The Constant RE_REGISTRATION. */
	private static final int RE_REGISTRATION = 924;
	
	/** The Constant INVALID_CONSENT_NAME. */
	private static final int INVALID_CONSENT_NAME = 914;
	
	/** The Constant INVALID_CONSENT_QUESTION. */
	private static final int INVALID_CONSENT_QUESTION = 915;
	
	/** The Constant INVALID_REASON. */
	private static final int INVALID_REASON = 916;
	
	/** The Constant NON_UNIQUE_ANATOMIC_SITE_NAME. */
	private static final int NON_UNIQUE_ANATOMIC_SITE_NAME = 932;
	
	
	/** The exception helper. */
	private C3PRExceptionHelper exceptionHelper;
	
	/** The converter. */
	private SubjectRegistrationJAXBToDomainObjectConverter converter;
	
	/** The participant dao. */
	private ParticipantDao participantDao;
	
	/** The study repository. */
	private StudyRepository studyRepository;
	
	/** The study subject repository. */
	private StudySubjectRepository studySubjectRepository;
	
	/** The study subject dao. */
	private StudySubjectDao studySubjectDao;
	
	/** The icd9 disease site dao. */
	private ICD9DiseaseSiteDao icd9DiseaseSiteDao;
	
	/** The reason dao. */
	private ReasonDao reasonDao;

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.subjectregistration.SubjectRegistration#enrollStudySubject(edu.duke.cabig.c3pr.webservice.subjectregistration.EnrollSubjectNewRequest)
	 */
	public EnrollSubjectNewResponse enrollStudySubject(EnrollSubjectNewRequest arg0)
		throws DuplicateStudySubjectExceptionFaultMessage,
		InvalidSiteExceptionFaultMessage,
		InvalidStudyProtocolExceptionFaultMessage,
		InvalidStudySubjectDataExceptionFaultMessage,
		SecurityExceptionFaultMessage,
		SubjectRegistrationRejectedExceptionFaultMessage {
		
		StudySubject studySubject = arg0.getStudySubject();
		//test duplicate study subject
		try {
			getStudySubject(studySubject.getSubjectIdentifier());
			handleDuplicateStudySubject(exceptionHelper
					.getConversionException(DUPLICATE_STUDYSUBJECT_IDENTIFIER));
		} catch (C3PRCodedRuntimeException e) {
			if(e.getExceptionCode() != 231)
				handleDuplicateStudySubject(e);
		}
		
		edu.duke.cabig.c3pr.domain.StudySubject domainObject = new edu.duke.cabig.c3pr.domain.StudySubject();
		
		Participant participant = getParticipant(arg0.getSubjectIdentifier());
		Study study = getStudy(arg0.getStudyIdentifier());
		StudySite studySite = getStudySite(study, arg0.getSiteIdentifier());
		
		//set study, studySite and subject demographics
		domainObject.setStudySubjectDemographics(participant.createStudySubjectDemographics());
		domainObject.setStudySite(studySite);
		
		//copy organic attributes, identifiers
		copyEnrollmentDetails(domainObject , studySubject);
		
		//copy consent information
		if(studySubject.getStudySubjectProtocolVersion() != null &&
				studySubject.getStudySubjectProtocolVersion().getStudySubjectConsentVersion() !=null &&
				studySubject.getStudySubjectProtocolVersion().getStudySubjectConsentVersion().size()>0)
		copyConsentDetails(domainObject, studySubject.getStudySubjectProtocolVersion().getStudySubjectConsentVersion());
		
		//set epoch details
		StudySubjectProtocolVersionRelationship studySubjectProtocolVersionRelationship = 
			(StudySubjectProtocolVersionRelationship)studySubject.getStudySubjectProtocolVersion();
		setScheduledEpoch(domainObject, getScheduledEpoch(study, studySubjectProtocolVersionRelationship.getScheduledEpoch().get(0)));
		
		
		//test re-registration
		List<edu.duke.cabig.c3pr.domain.StudySubject> registrations = studySubjectRepository.findRegistrations(domainObject);
		if (registrations.size() > 0) {
			handleDuplicateStudySubject(exceptionHelper
					.getConversionException(RE_REGISTRATION));
        }
		
		domainObject = studySubjectRepository.save(domainObject);
		
		EnrollSubjectNewResponse response = new EnrollSubjectNewResponse();
		response.setStudySubject(converter.convertToStudySubjectRegistration(domainObject));
		return response;
	}
	
	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.subjectregistration.SubjectRegistration#enrollExistingStudySubject(edu.duke.cabig.c3pr.webservice.subjectregistration.EnrollSubjectExistingRequest)
	 */
	public EnrollSubjectExistingResponse enrollExistingStudySubject(
		EnrollSubjectExistingRequest arg0)
		throws InvalidStudySubjectDataExceptionFaultMessage,
		NoSuchStudySubjectExceptionFaultMessage,
		SecurityExceptionFaultMessage,
		SubjectRegistrationRejectedExceptionFaultMessage {
	// TODO Auto-generated method stub
		/*
		 * <xsd:element name="studySubjectIdentifier" type="common:SubjectIdentifier"/>
			<xsd:element name="enrollmentDate" type="ISO:TS.DateTime"/>
			<xsd:element name="offPreviousEpochReasons" type="sr:DSET_PerformedObservationResult" minOccurs="0"/>
			<xsd:element name="offPreviousEpochDate" type="ISO:TS.DateTime" minOccurs="0"/>
		 */
		SubjectIdentifier ssIdentifier = arg0.getStudySubjectIdentifier();
		String studySubjectId = ssIdentifier.getIdentifier().getExtension();
		
		edu.duke.cabig.c3pr.domain.StudySubject existingStudySubject = studySubjectDao.getById(Integer.valueOf(studySubjectId).intValue());
		return null;
	}
	
	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.subjectregistration.SubjectRegistration#registerButNotEnrollStudySubject(edu.duke.cabig.c3pr.webservice.subjectregistration.RegisterButNotEnrollSubjectNewRequest)
	 */
	public RegisterButNotEnrollSubjectNewResponse registerButNotEnrollStudySubject(
		RegisterButNotEnrollSubjectNewRequest arg0)
		throws DuplicateStudySubjectExceptionFaultMessage,
		InvalidSiteExceptionFaultMessage,
		InvalidStudyProtocolExceptionFaultMessage,
		InvalidStudySubjectDataExceptionFaultMessage,
		SecurityExceptionFaultMessage,
		SubjectRegistrationRejectedExceptionFaultMessage {
	// TODO Auto-generated method stub
		return null;
	}
	
	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.subjectregistration.SubjectRegistration#registerButNotEnrollExisitingStudySubject(edu.duke.cabig.c3pr.webservice.subjectregistration.RegisterButNotEnrollSubjectExistingRequest)
	 */
	public RegisterButNotEnrollSubjectExistingResponse registerButNotEnrollExisitingStudySubject(
		RegisterButNotEnrollSubjectExistingRequest arg0)
		throws InvalidStudySubjectDataExceptionFaultMessage,
		NoSuchStudySubjectExceptionFaultMessage,
		SecurityExceptionFaultMessage,
		SubjectRegistrationRejectedExceptionFaultMessage {
	// TODO Auto-generated method stub
		return null;
	}
	
	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.subjectregistration.SubjectRegistration#reserveStudySubjectSlot(edu.duke.cabig.c3pr.webservice.subjectregistration.ReserveSlotNewRequest)
	 */
	public ReserveSlotNewResponse reserveStudySubjectSlot(ReserveSlotNewRequest arg0)
		throws DuplicateStudySubjectExceptionFaultMessage,
		InvalidSiteExceptionFaultMessage,
		InvalidStudyProtocolExceptionFaultMessage,
		InvalidStudySubjectDataExceptionFaultMessage,
		SecurityExceptionFaultMessage,
		SubjectRegistrationRejectedExceptionFaultMessage {
		// TODO Auto-generated method stub
		return null;
	}
	
	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.subjectregistration.SubjectRegistration#reserveExistingStudySubjectSlot(edu.duke.cabig.c3pr.webservice.subjectregistration.ReserveSlotExistingRequest)
	 */
	public ReserveSlotExistingResponse reserveExistingStudySubjectSlot(
		ReserveSlotExistingRequest arg0)
		throws InvalidStudySubjectDataExceptionFaultMessage,
		NoSuchStudySubjectExceptionFaultMessage,
		SecurityExceptionFaultMessage,
		SubjectRegistrationRejectedExceptionFaultMessage {
	// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.subjectregistration.SubjectRegistration#updateStudySubjectConsent(edu.duke.cabig.c3pr.webservice.subjectregistration.UpdateStudySubjectConsentRequest)
	 */
	public UpdateStudySubjectConsentResponse updateStudySubjectConsent(
		UpdateStudySubjectConsentRequest arg0)
		throws InvalidStudySubjectDataExceptionFaultMessage,
		NoSuchStudySubjectExceptionFaultMessage,
		SecurityExceptionFaultMessage {
	// TODO Auto-generated method stub
		return null;
	}
	
	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.subjectregistration.SubjectRegistration#changeStudySubjectEpochAssignment(edu.duke.cabig.c3pr.webservice.subjectregistration.ChangeStudySubjectEpochAssignmentRequest)
	 */
	public ChangeStudySubjectEpochAssignmentResponse changeStudySubjectEpochAssignment(
			ChangeStudySubjectEpochAssignmentRequest arg0)
			throws InvalidStudyProtocolExceptionFaultMessage,
			InvalidStudySubjectDataExceptionFaultMessage,
			NoSuchStudySubjectExceptionFaultMessage,
			SecurityExceptionFaultMessage,
			SubjectRegistrationRejectedExceptionFaultMessage {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.subjectregistration.SubjectRegistration#discontinueSubjectEnrollment(edu.duke.cabig.c3pr.webservice.subjectregistration.DiscontinueEnrollmentRequest)
	 */
	public DiscontinueEnrollmentResponse discontinueSubjectEnrollment(
			DiscontinueEnrollmentRequest arg0)
			throws InvalidStudySubjectDataExceptionFaultMessage,
			NoSuchStudySubjectExceptionFaultMessage,
			SecurityExceptionFaultMessage {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.subjectregistration.SubjectRegistration#failSubjectScreening(edu.duke.cabig.c3pr.webservice.subjectregistration.FailSubjectScreeningRequest)
	 */
	public FailSubjectScreeningResponse failSubjectScreening(
			FailSubjectScreeningRequest arg0)
			throws InvalidStudySubjectDataExceptionFaultMessage,
			NoSuchStudySubjectExceptionFaultMessage,
			SecurityExceptionFaultMessage {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.subjectregistration.SubjectRegistration#generateSummary3Report(edu.duke.cabig.c3pr.webservice.subjectregistration.GenerateSummary3ReportRequest)
	 */
	public GenerateSummary3ReportResponse generateSummary3Report(
			GenerateSummary3ReportRequest arg0)
			throws InvalidSiteExceptionFaultMessage,
			SecurityExceptionFaultMessage {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.subjectregistration.SubjectRegistration#importRegistrations(edu.duke.cabig.c3pr.webservice.subjectregistration.ImportRegistrationsRequest)
	 */
	public ImportRegistrationsResponse importRegistrations(
			ImportRegistrationsRequest arg0)
			throws InvalidStudySubjectDataExceptionFaultMessage,
			SecurityExceptionFaultMessage {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Just save the Registration with status=PENDING. consent need not be signed.
	 *
	 * @param parameters the parameters
	 * @return the initiate subject enrollment response
	 * @throws DuplicateStudySubjectExceptionFaultMessage the duplicate study subject exception fault message
	 * @throws InvalidSiteExceptionFaultMessage the invalid site exception fault message
	 * @throws InvalidStudyProtocolExceptionFaultMessage the invalid study protocol exception fault message
	 * @throws InvalidStudySubjectDataExceptionFaultMessage the invalid study subject data exception fault message
	 * @throws SecurityExceptionFaultMessage the security exception fault message
	 * @throws SubjectRegistrationRejectedExceptionFaultMessage the subject registration rejected exception fault message
	 */
	public InitiateSubjectEnrollmentResponse initiateStudySubjectEnrollment(
			InitiateSubjectEnrollmentRequest parameters)
			throws DuplicateStudySubjectExceptionFaultMessage,
			InvalidSiteExceptionFaultMessage,
			InvalidStudyProtocolExceptionFaultMessage,
			InvalidStudySubjectDataExceptionFaultMessage,
			SecurityExceptionFaultMessage,
			SubjectRegistrationRejectedExceptionFaultMessage {
		StudySubject studySubject = parameters.getStudySubject();
		//test duplicate study subject
		try {
			getStudySubject(studySubject.getSubjectIdentifier());
			handleDuplicateStudySubject(exceptionHelper
					.getConversionException(DUPLICATE_STUDYSUBJECT_IDENTIFIER));
		} catch (C3PRCodedRuntimeException e) {
			if(e.getExceptionCode() != 231)
				handleDuplicateStudySubject(e);
		}
		
		edu.duke.cabig.c3pr.domain.StudySubject domainObject = new edu.duke.cabig.c3pr.domain.StudySubject();
		
		//getParticipant
		Participant participant = getParticipant(parameters.getSubjectIdentifier());
		
		//getStudySite
		Study study = getStudy(parameters.getStudyIdentifier());
		
		//getStudySite
		StudySite studySite = getStudySite(study, parameters.getSiteIdentifier());
		
		//set study, studysite and subject demography
		domainObject.setStudySubjectDemographics(participant.createStudySubjectDemographics());
		domainObject.setStudySite(studySite);

		//test re-registration
		List<edu.duke.cabig.c3pr.domain.StudySubject> registrations = studySubjectRepository.findRegistrations(domainObject);;
		if (registrations.size() > 0) {
			handleDuplicateStudySubject(exceptionHelper
					.getConversionException(RE_REGISTRATION));
        }
		
		//getScheduledEpoch
		
		
		//copy enrollment information like identifiers, paymentMethod, disease, treatingPhysician
		copyEnrollmentDetails(domainObject , studySubject);
		
		//copy consent information
		if(studySubject.getStudySubjectProtocolVersion() != null &&
				studySubject.getStudySubjectProtocolVersion().getStudySubjectConsentVersion() !=null &&
				studySubject.getStudySubjectProtocolVersion().getStudySubjectConsentVersion().size()>0){
			copyConsentDetails(domainObject, studySubject.getStudySubjectProtocolVersion().getStudySubjectConsentVersion());
		}
		
		domainObject = studySubjectRepository.save(domainObject);
		
//		InitiateStudySubjectRegistryResponse response = new InitiateStudySubjectRegistryResponse();
//		response.setStudySubject(converter.convert(domainObject));
//		return response;
		return null;
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.subjectregistration.SubjectRegistration#querySubjectRegistration(edu.duke.cabig.c3pr.webservice.subjectregistration.QuerySubjectRegistrationRequest)
	 */
	public QuerySubjectRegistrationResponse querySubjectRegistration(
			QuerySubjectRegistrationRequest arg0)
			throws InvalidQueryExceptionFaultMessage,
			SecurityExceptionFaultMessage {
		QuerySubjectRegistrationResponse response = new QuerySubjectRegistrationResponse();
		DSETStudySubject studySubjects = new DSETStudySubject();
		response.setStudySubjects(studySubjects);

		try {
			List<AdvancedSearchCriteriaParameter> advParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
			for (AdvanceSearchCriterionParameter param : arg0.getParameters().getItem()) {
				AdvancedSearchCriteriaParameter advParam = converter
						.convert(param);
				advParameters.add(advParam);
			}

			List<edu.duke.cabig.c3pr.domain.StudySubject> results = new ArrayList<edu.duke.cabig.c3pr.domain.StudySubject>(
					studySubjectDao.search(advParameters));
			for (edu.duke.cabig.c3pr.domain.StudySubject result : results) {
				studySubjects.getItem().add(converter.convertToStudySubjectRegistration(result));
			}
		} catch (ConversionException e) {
			handleInvalidQueryData(e);
		}
		return response;
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.subjectregistration.SubjectRegistration#reconsentSubject(edu.duke.cabig.c3pr.webservice.subjectregistration.ReconsentStudySubjectRequest)
	 */
	public ReconsentStudySubjectResponse reconsentSubject(
			ReconsentStudySubjectRequest arg0)
			throws InvalidStudyProtocolExceptionFaultMessage,
			InvalidStudySubjectDataExceptionFaultMessage,
			NoSuchStudySubjectExceptionFaultMessage,
			SecurityExceptionFaultMessage {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.subjectregistration.SubjectRegistration#retrieveAccrualData(edu.duke.cabig.c3pr.webservice.subjectregistration.RetrieveAccrualDataRequest)
	 */
	public RetrieveAccrualDataResponse retrieveAccrualData(
			RetrieveAccrualDataRequest arg0)
			throws InvalidSiteExceptionFaultMessage,
			InvalidStudyProtocolExceptionFaultMessage,
			SecurityExceptionFaultMessage {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.subjectregistration.SubjectRegistration#retrieveSubjectDemographyHistory(edu.duke.cabig.c3pr.webservice.subjectregistration.RetrieveSubjectDemographyHistoryRequest)
	 */
	public RetrieveSubjectDemographyHistoryResponse retrieveSubjectDemographyHistory(
			RetrieveSubjectDemographyHistoryRequest arg0)
			throws NoSuchPatientExceptionFaultMessage,
			SecurityExceptionFaultMessage {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.subjectregistration.SubjectRegistration#takeSubjectOffStudy(edu.duke.cabig.c3pr.webservice.subjectregistration.TakeSubjectOffStudyRequest)
	 */
	public TakeSubjectOffStudyResponse takeSubjectOffStudy(
			TakeSubjectOffStudyRequest arg0)
			throws InvalidStudySubjectDataExceptionFaultMessage,
			NoSuchStudySubjectExceptionFaultMessage,
			SecurityExceptionFaultMessage {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.subjectregistration.SubjectRegistration#updateSubjectRegistration(edu.duke.cabig.c3pr.webservice.subjectregistration.UpdateRegistrationRequest)
	 */
	public UpdateRegistrationResponse updateSubjectRegistration(
			UpdateRegistrationRequest arg0)
			throws InvalidStudySubjectDataExceptionFaultMessage,
			NoSuchStudySubjectExceptionFaultMessage,
			SecurityExceptionFaultMessage {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Handle invalid query data.
	 *
	 * @param e the e
	 * @throws InvalidQueryExceptionFaultMessage the invalid query exception fault message
	 */
	private void handleInvalidQueryData(Exception e)
			throws InvalidQueryExceptionFaultMessage {
		log.error(ExceptionUtils.getFullStackTrace(e));
		InvalidQueryExceptionFault fault = new InvalidQueryExceptionFault();
		fault.setMessage(e.getMessage());
		throw new InvalidQueryExceptionFaultMessage(e.getMessage(), fault);
	}
	
	/**
	 * Handle duplicate study subject.
	 *
	 * @param e the e
	 * @throws DuplicateStudySubjectExceptionFaultMessage the duplicate study subject exception fault message
	 */
	private void handleDuplicateStudySubject(Exception e)
			throws DuplicateStudySubjectExceptionFaultMessage {
		log.error(ExceptionUtils.getFullStackTrace(e));
		DuplicateStudySubjectExceptionFault fault = new DuplicateStudySubjectExceptionFault();
		fault.setMessage(e.getMessage());
		throw new DuplicateStudySubjectExceptionFaultMessage(e.getMessage(), fault);
	}
	
	/**
	 * Handle invalid study subject data.
	 *
	 * @param e the e
	 * @throws InvalidStudySubjectDataExceptionFaultMessage the invalid study subject data exception fault message
	 */
	private void handleInvalidStudySubjectData(Exception e)
			throws InvalidStudySubjectDataExceptionFaultMessage {
		log.error(ExceptionUtils.getFullStackTrace(e));
		InvalidStudySubjectDataExceptionFault fault = new InvalidStudySubjectDataExceptionFault();
		fault.setMessage(e.getMessage());
		throw new InvalidStudySubjectDataExceptionFaultMessage(e.getMessage(), fault);
	}
	
	/**
	 * Handle invalid study data.
	 *
	 * @param e the e
	 * @throws InvalidStudyProtocolExceptionFaultMessage the invalid study protocol exception fault message
	 */
	private void handleInvalidStudyData(Exception e)
			throws InvalidStudyProtocolExceptionFaultMessage {
		log.error(ExceptionUtils.getFullStackTrace(e));
		InvalidStudyProtocolExceptionFault fault = new InvalidStudyProtocolExceptionFault();
		fault.setMessage(e.getMessage());
		throw new InvalidStudyProtocolExceptionFaultMessage(e.getMessage(), fault);
	}
	
	/**
	 * Handle invalid site data.
	 *
	 * @param e the e
	 * @throws InvalidSiteExceptionFaultMessage the invalid site exception fault message
	 */
	private void handleInvalidSiteData(Exception e)
			throws InvalidSiteExceptionFaultMessage {
		log.error(ExceptionUtils.getFullStackTrace(e));
		InvalidSiteExceptionFault fault = new InvalidSiteExceptionFault();
		fault.setMessage(e.getMessage());
		throw new InvalidSiteExceptionFaultMessage(e.getMessage(), fault);
	}
	
	/**
	 * Gets the study subject.
	 *
	 * @param identifiers the identifiers
	 * @return the study subject
	 */
	private edu.duke.cabig.c3pr.domain.StudySubject getStudySubject(List<SubjectIdentifier> identifiers){
		edu.duke.cabig.c3pr.domain.StudySubject domainObject = new edu.duke.cabig.c3pr.domain.StudySubject();
		domainObject.getIdentifiers().addAll(converter.convertSubjectIdentifiers(identifiers));
		return studySubjectRepository.getUniqueStudySubject(domainObject.getPrimaryIdentifierObject());
	}
	
	/**
	 * Gets the participant.
	 *
	 * @param bioId the bio id
	 * @return the participant
	 * @throws InvalidStudySubjectDataExceptionFaultMessage the invalid study subject data exception fault message
	 */
	private Participant getParticipant(BiologicEntityIdentifier bioId) throws InvalidStudySubjectDataExceptionFaultMessage{
		List<BiologicEntityIdentifier> identifiers = new ArrayList<BiologicEntityIdentifier>();
		identifiers.add(bioId);
		if (CollectionUtils.isEmpty(identifiers)) {
			handleInvalidStudySubjectData(exceptionHelper
					.getConversionException(MISSING_SUBJECT_IDENTIFIER));
		}
		List<Participant> participantList = participantDao.searchByIdentifier(new Integer(converter.convertBiologicIdentifiers(identifiers).get(0).getValue()).intValue());
		if(participantList == null || participantList.size() == 0){
			handleInvalidStudySubjectData(exceptionHelper
					.getConversionException(SUBJECT_NOT_FOUND));
		}
		return participantList.get(0);
	}
	
	/**
	 * Gets the study.
	 *
	 * @param docId the doc id
	 * @return the study
	 * @throws InvalidStudyProtocolExceptionFaultMessage the invalid study protocol exception fault message
	 */
	private Study getStudy(DocumentIdentifier docId) throws InvalidStudyProtocolExceptionFaultMessage{
		Study study = null;
		try {
			study = studyRepository.getUniqueStudy(converter.convert(Arrays.asList(new DocumentIdentifier[]{docId})));
		} catch (C3PRCodedRuntimeException e) {
			handleInvalidStudyData(e);
		}
		return study;
	}
	
	/**
	 * Gets the study site.
	 *
	 * @param study the study
	 * @param orgId the org id
	 * @return the study site
	 * @throws InvalidSiteExceptionFaultMessage the invalid site exception fault message
	 * @throws InvalidStudyProtocolExceptionFaultMessage the invalid study protocol exception fault message
	 */
	private StudySite getStudySite(Study study, OrganizationIdentifier orgId)throws InvalidSiteExceptionFaultMessage, InvalidStudyProtocolExceptionFaultMessage{
		StudySite studySite = null;
		try {
			String siteId = converter.convertHealthcareSitePrimaryIdentifier(orgId);
			studySite = study.getStudySite(siteId);
			if(studySite == null){
				throw exceptionHelper.getRuntimeException(212, new Object[]{siteId});
			}
		} catch (C3PRCodedRuntimeException e) {
			handleInvalidSiteData(e);
		}
		return studySite;
	}

	/**
	 * Copy enrollment details.
	 *
	 * @param destination the destination
	 * @param source the source
	 * @throws InvalidStudySubjectDataExceptionFaultMessage the invalid study subject data exception fault message
	 */
	private void copyEnrollmentDetails(edu.duke.cabig.c3pr.domain.StudySubject destination , StudySubject source) throws InvalidStudySubjectDataExceptionFaultMessage{
		//copy organic attributes
		CD paymentMethod = source.getPaymentMethodCode();
		destination.setPaymentMethod(!isNull(paymentMethod) ? paymentMethod.getCode() : null);
		
		//copy identifiers
		List<Identifier> identifiers = converter.convertSubjectIdentifiers(source.getSubjectIdentifier());
		destination.getIdentifiers().clear();
		destination.getIdentifiers().addAll(identifiers);
		
		//set Disease History
		updateDiseaseHistory(destination, source.getDiseaseHistory());
		//replace the Disease History with a hibernate attached StudyDisease and ICD9DiseaseSite
//		for(StudyDisease studyDisease: destination.getStudySite().getStudy().getStudyDiseases()){
//			if(destination.getDiseaseHistory().getPrimaryDiseaseStr().equals(studyDisease.getDiseaseTerm())){
//				destination.getDiseaseHistory().setStudyDisease(studyDisease);
//				break;
//			}
//		}
//		ICD9DiseaseSite icd9DiseaseSite = icd9DiseaseSiteDao.getByCode(destination.getDiseaseHistory().getIcd9DiseaseSite().getCode());
//		destination.getDiseaseHistory().setIcd9DiseaseSite(icd9DiseaseSite);
		
		//set Treating/Enrolling physician
		updateTreatingPhysician(destination, source.getTreatingPhysician());
	}
	
	/**
	 * Copy consent details.
	 *
	 * @param destination the destination
	 * @param subjectConsents the subject consents
	 * @throws InvalidStudySubjectDataExceptionFaultMessage the invalid study subject data exception fault message
	 */
	private void copyConsentDetails(edu.duke.cabig.c3pr.domain.StudySubject destination , List<edu.duke.cabig.c3pr.webservice.common.StudySubjectConsentVersion> subjectConsents) throws InvalidStudySubjectDataExceptionFaultMessage{
		List<StudySubjectConsentVersion> studySubjectConsents = converter.convertSubjectConsent(subjectConsents);
		destination.getStudySubjectStudyVersion().getStudySubjectConsentVersions().clear();
		Study study = destination.getStudySite().getStudy();
		for(StudySubjectConsentVersion studySubjectConsentVersion : studySubjectConsents){
			Consent consent = study.getConsent(studySubjectConsentVersion.getConsent().getName(), studySubjectConsentVersion.getConsent().getVersionId());
			if(consent == null){
				handleInvalidStudySubjectData(exceptionHelper
						.getConversionException(INVALID_CONSENT_NAME, new Object[]{studySubjectConsentVersion.getConsent().getName()}));
				
			}
			studySubjectConsentVersion.setConsent(consent);
			for(SubjectConsentQuestionAnswer subjectAnswer : studySubjectConsentVersion.getSubjectConsentAnswers()){
				ConsentQuestion consentQuestion = consent.getQuestion(subjectAnswer.getConsentQuestion().getCode());
				if(consentQuestion == null){
					handleInvalidStudySubjectData(exceptionHelper
							.getConversionException(INVALID_CONSENT_QUESTION, new Object[]{subjectAnswer.getConsentQuestion().getCode()}));
				}
				subjectAnswer.setConsentQuestion(consentQuestion);
			}
			destination.getStudySubjectStudyVersion().getStudySubjectConsentVersions().add(studySubjectConsentVersion);
		}
	}
	
	/**
	 * Update disease history.
	 *
	 * @param destination the destination
	 * @param diseaseHistory the disease history
	 * @throws InvalidStudySubjectDataExceptionFaultMessage the invalid study subject data exception fault message
	 */
	private void updateDiseaseHistory(edu.duke.cabig.c3pr.domain.StudySubject destination, PerformedDiagnosis diseaseHistory) throws InvalidStudySubjectDataExceptionFaultMessage{
		DiseaseHistory convertedDiseaseHistory = converter.convertDiseaseHistory(diseaseHistory);
		if(!StringUtils.isBlank(convertedDiseaseHistory.getOtherPrimaryDiseaseCode())){
			for(StudyDisease studyDisease: destination.getStudySite().getStudy().getStudyDiseases()){
				if(studyDisease.getDiseaseTerm().getTerm().equals(convertedDiseaseHistory.getOtherPrimaryDiseaseCode())){
					convertedDiseaseHistory.setStudyDisease(studyDisease);
					convertedDiseaseHistory.setOtherPrimaryDiseaseCode(null);
					break;
				}
			}
		}
		if(!StringUtils.isBlank(convertedDiseaseHistory.getOtherPrimaryDiseaseSiteCode())){
			ICD9DiseaseSite icd9DiseaseSite = icd9DiseaseSiteDao.getByCode(convertedDiseaseHistory.getOtherPrimaryDiseaseSiteCode());
			convertedDiseaseHistory.setIcd9DiseaseSite(icd9DiseaseSite);
		}
		destination.setDiseaseHistory(convertedDiseaseHistory);
	}
	
	/**
	 * Update treating physician.
	 *
	 * @param destination the destination
	 * @param studyInvestigator the study investigator
	 */
	private void updateTreatingPhysician(edu.duke.cabig.c3pr.domain.StudySubject destination, StudyInvestigator studyInvestigator){
		edu.duke.cabig.c3pr.domain.StudyInvestigator convertedStudyInvestigator = converter.convertStudyInvestigator(studyInvestigator);
		if(convertedStudyInvestigator == null){
			return;
		}
		if(StringUtils.isBlank(convertedStudyInvestigator.getHealthcareSiteInvestigator().getInvestigator().getAssignedIdentifier())){
			destination.setOtherTreatingPhysician(convertedStudyInvestigator.getHealthcareSiteInvestigator().getInvestigator().getFullName());
		}else{
			for(edu.duke.cabig.c3pr.domain.StudyInvestigator stInv : destination.getStudySite().getStudyInvestigators()){
				if(stInv.getHealthcareSiteInvestigator().getInvestigator().getAssignedIdentifier().equals(
						convertedStudyInvestigator.getHealthcareSiteInvestigator().getInvestigator().getAssignedIdentifier())){
					destination.setTreatingPhysician(stInv);
					return;
				}
			}
		}
	}
	
	
	/**Ensures that an Epoch and an Arm corresponding to the one sent in, Exists.
	 * Returns a newly created C3PR domain scheduled epoch(with a C3PR domain scheduled arm.).
	 *
	 * @param study the study from C3PR db.
	 * @param scheduledEpoch the scheduled epoch present in the requestArg.
	 * @return the scheduled epoch
	 */
	private ScheduledEpoch getScheduledEpoch(Study study, edu.duke.cabig.c3pr.webservice.subjectregistration.ScheduledEpoch scheduledEpoch){
		ScheduledEpoch convertedScheduledEpoch =  converter.convertScheduledEpoch(scheduledEpoch);
		boolean notFound = true;
		Epoch legitimateEpoch = null;
		//plug in the legitimate epoch from dao
		for(Epoch epoch : study.getEpochs()){
			if(epoch.getName().equalsIgnoreCase(convertedScheduledEpoch.getEpoch().getName())){
				legitimateEpoch = epoch;
				convertedScheduledEpoch.setEpoch(legitimateEpoch);
				notFound = false;
				break;
			}
		}
		if(notFound){
			//throw meaningful "No matching Epoch found" exception
		} 
		
		//plug in the legitimate Arm from dao
		Arm legitimateArm = null;
		if(legitimateEpoch != null){
			for(Arm arm : legitimateEpoch.getArms()){
				if(arm.getName().equalsIgnoreCase(convertedScheduledEpoch.getScheduledArm().getArm().getName())){
					legitimateArm = arm;
					convertedScheduledEpoch.getScheduledArm().setArm(legitimateArm);
					notFound = false;
					break;
				}
			}
			if(notFound){
				//throw meaningful "No matching Arm found" exception
			}
		}
		
		//plug in the subEligAns and subStrAns from the study.
		for(SubjectEligibilityAnswer sea: convertedScheduledEpoch.getSubjectEligibilityAnswers()){
			for(EligibilityCriteria ec: legitimateEpoch.getEligibilityCriteria()){
				if(ec.equals(sea.getEligibilityCriteria())){
					sea.setEligibilityCriteria(ec);
				}
			}
			if(sea.getEligibilityCriteria().getId() == null){
				log.error("No existing EligibilityCriterion found.");
				//throw appropriate exception
			}
		}
		
		for(SubjectStratificationAnswer ssa: convertedScheduledEpoch.getSubjectStratificationAnswers()){
			for(StratificationCriterion sc: legitimateEpoch.getStratificationCriteria()){
				if(sc.equals(ssa.getStratificationCriterion())){
					ssa.setStratificationCriterion(sc);
				}
			}
			if(ssa.getStratificationCriterion().getId() == null){
				log.error("No existing StratificationCriterion found.");
				//throw appropriate exception
			}
		}
		
		//Dont handle off-epoch.
		return convertedScheduledEpoch;
	}
	
	/**
	 * Sets the scheduled epoch.
	 *
	 * @param studySubject the study subject
	 * @param convertedScheduledEpoch the converted scheduled epoch
	 */
	private void setScheduledEpoch(edu.duke.cabig.c3pr.domain.StudySubject studySubject, ScheduledEpoch convertedScheduledEpoch){
		studySubject.getStudySubjectStudyVersion().addScheduledEpoch(convertedScheduledEpoch);
	}
	
	/**
	 * Checks if is null.
	 *
	 * @param cd the cd
	 * @return true, if is null
	 */
	private boolean isNull(ANY cd) {
		return cd == null || cd.getNullFlavor() != null;
	}
	
	/**
	 * Sets the participant dao.
	 *
	 * @param participantDao the new participant dao
	 */
	public void setParticipantDao(ParticipantDao participantDao) {
		this.participantDao = participantDao;
	}

	/**
	 * Sets the study repository.
	 *
	 * @param studyRepository the new study repository
	 */
	public void setStudyRepository(StudyRepository studyRepository) {
		this.studyRepository = studyRepository;
	}

	/**
	 * Sets the study subject repository.
	 *
	 * @param studySubjectRepository the new study subject repository
	 */
	public void setStudySubjectRepository(
			StudySubjectRepository studySubjectRepository) {
		this.studySubjectRepository = studySubjectRepository;
	}

	/**
	 * Sets the exception helper.
	 *
	 * @param exceptionHelper the new exception helper
	 */
	public void setExceptionHelper(C3PRExceptionHelper exceptionHelper) {
		this.exceptionHelper = exceptionHelper;
	}
	
	/**
	 * Sets the converter.
	 *
	 * @param converter the new converter
	 */
	public void setConverter(
			SubjectRegistrationJAXBToDomainObjectConverter converter) {
		this.converter = converter;
	}

	/**
	 * Sets the study subject dao.
	 *
	 * @param studySubjectDao the new study subject dao
	 */
	public void setStudySubjectDao(StudySubjectDao studySubjectDao) {
		this.studySubjectDao = studySubjectDao;
	}

	/**
	 * Sets the icd9 disease site dao.
	 *
	 * @param icd9DiseaseSiteDao the new icd9 disease site dao
	 */
	public void setIcd9DiseaseSiteDao(ICD9DiseaseSiteDao icd9DiseaseSiteDao) {
		this.icd9DiseaseSiteDao = icd9DiseaseSiteDao;
	}

	/**
	 * Gets the reason dao.
	 *
	 * @return the reason dao
	 */
	public ReasonDao getReasonDao() {
		return reasonDao;
	}

	/**
	 * Sets the reason dao.
	 *
	 * @param reasonDao the new reason dao
	 */
	public void setReasonDao(ReasonDao reasonDao) {
		this.reasonDao = reasonDao;
	}
	
}
