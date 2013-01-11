/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.webservice.subjectregistry.converters;

import static edu.duke.cabig.c3pr.webservice.helpers.ISO21090Helper.BL;
import static edu.duke.cabig.c3pr.webservice.helpers.ISO21090Helper.CD;
import static edu.duke.cabig.c3pr.webservice.helpers.ISO21090Helper.ED;
import static edu.duke.cabig.c3pr.webservice.helpers.ISO21090Helper.ENXP;
import static edu.duke.cabig.c3pr.webservice.helpers.ISO21090Helper.II;
import static edu.duke.cabig.c3pr.webservice.helpers.ISO21090Helper.IVLTSDateTime;
import static edu.duke.cabig.c3pr.webservice.helpers.ISO21090Helper.ST;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import edu.duke.cabig.c3pr.constants.ConsentingMethod;
import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;
import edu.duke.cabig.c3pr.constants.RaceCodeEnum;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.Consent;
import edu.duke.cabig.c3pr.domain.ConsentQuestion;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.PermissibleStudySubjectRegistryStatus;
import edu.duke.cabig.c3pr.domain.RegistryStatus;
import edu.duke.cabig.c3pr.domain.RegistryStatusReason;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubjectConsentVersion;
import edu.duke.cabig.c3pr.domain.StudySubjectDemographics;
import edu.duke.cabig.c3pr.domain.StudySubjectRegistryStatus;
import edu.duke.cabig.c3pr.domain.StudySubjectStudyVersion;
import edu.duke.cabig.c3pr.domain.SubjectConsentQuestionAnswer;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.webservice.common.BiologicEntityIdentifier;
import edu.duke.cabig.c3pr.webservice.common.Document;
import edu.duke.cabig.c3pr.webservice.common.DocumentVersion;
import edu.duke.cabig.c3pr.webservice.common.Organization;
import edu.duke.cabig.c3pr.webservice.common.OrganizationIdentifier;
import edu.duke.cabig.c3pr.webservice.common.PerformedStudySubjectMilestone;
import edu.duke.cabig.c3pr.webservice.common.Person;
import edu.duke.cabig.c3pr.webservice.common.StudyProtocolDocumentVersion;
import edu.duke.cabig.c3pr.webservice.common.StudyProtocolVersion;
import edu.duke.cabig.c3pr.webservice.common.StudySiteProtocolVersionRelationship;
import edu.duke.cabig.c3pr.webservice.common.StudySubjectProtocolVersionRelationship;
import edu.duke.cabig.c3pr.webservice.common.Subject;
import edu.duke.cabig.c3pr.webservice.common.SubjectIdentifier;
import edu.duke.cabig.c3pr.webservice.converters.JAXBToDomainObjectConverter;
import edu.duke.cabig.c3pr.webservice.converters.JAXBToDomainObjectConverterImpl;
import edu.duke.cabig.c3pr.webservice.iso21090.CD;
import edu.duke.cabig.c3pr.webservice.iso21090.DSETCD;
import edu.duke.cabig.c3pr.webservice.iso21090.DSETENPN;
import edu.duke.cabig.c3pr.webservice.iso21090.ENPN;
import edu.duke.cabig.c3pr.webservice.iso21090.EntityNamePartType;
import edu.duke.cabig.c3pr.webservice.iso21090.II;
import edu.duke.cabig.c3pr.webservice.iso21090.NullFlavor;
import edu.duke.cabig.c3pr.webservice.subjectregistry.DSETStudySubject;
import edu.duke.cabig.c3pr.webservice.subjectregistry.StudySubject;

/**
 * Default implementation of {@link JAXBToDomainObjectConverter}.
 * 
 * @author dkrylov
 * 
 */
public class SubjectRegistryJAXBToDomainObjectConverterImpl extends JAXBToDomainObjectConverterImpl implements
		SubjectRegistryJAXBToDomainObjectConverter , ApplicationContextAware{

	/** The Constant MISSING_IDENTIFIER. */
	static final int MISSING_IDENTIFIER = 917;
	
	/** The Constant STUDY_SUBJECT_IDENTIFIER_HAS_TO_CONTAIN_EXACTLY_ONE_AMONG_ORGANIZATION_AND_SYSTEMNAME. */
	static final int STUDY_SUBJECT_IDENTIFIER_HAS_TO_CONTAIN_EXACTLY_ONE_AMONG_ORGANIZATION_AND_SYSTEMNAME = 918;
	
	/** The Constant MISSING_OR_INVALID_CONSENT_NAME. */
	static final int MISSING_OR_INVALID_CONSENT_NAME = 919;
	
	/** The Constant INVALID_CONSENT_QUESTION_ANSWER_AGREEMENTINDICATOR. */
	static final int INVALID_CONSENT_QUESTION_ANSWER_AGREEMENTINDICATOR = 920;
	
	/** The Constant INVALID_CONSENT_QUESTION. */
	static final int INVALID_CONSENT_QUESTION = 921;
	
	/** The Constant MISSING_STATUS_CODE. */
	static final int MISSING_STATUS_CODE = 922;
	
	/** The Constant MISSING_STATUS_DATE. */
	static final int MISSING_STATUS_DATE = 923;
	
	private StudySubjectDao studySubjectDao;
	
	public StudySubjectDao getStudySubjectDao() {
		return studySubjectDao;
	}
	public void setStudySubjectDao(StudySubjectDao studySubjectDao) {
		this.studySubjectDao = studySubjectDao;
	}



	private ApplicationContext applicationContext;
	
	private boolean isLoadStudy() {
		Configuration configuration = (Configuration) applicationContext.getBean("configuration");
		return "true".equalsIgnoreCase(configuration.get(Configuration.SRS_LOAD_STUDY));
	}
	private boolean isLoadStudySite() {
		Configuration configuration = (Configuration) applicationContext.getBean("configuration");
		return "true".equalsIgnoreCase(configuration.get(Configuration.SRS_LOAD_STUDY_SITE));
	}
	private boolean isLoadStudyIdentifier() {
		Configuration configuration = (Configuration) applicationContext.getBean("configuration");
		return "true".equalsIgnoreCase(configuration.get(Configuration.SRS_LOAD_STUDY_IDENTIFIER));
	}
	
	/** The log. */
	private static Log log = LogFactory
			.getLog(SubjectRegistryJAXBToDomainObjectConverterImpl.class);

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.subjectregistry.converters.SubjectRegistryJAXBToDomainObjectConverter#convertHealthcareSitePrimaryIdentifier(edu.duke.cabig.c3pr.webservice.common.Organization)
	 */
	public String convertHealthcareSitePrimaryIdentifier(Organization org) {
		List<OrganizationIdentifier> idList = org.getOrganizationIdentifier();
		if (CollectionUtils.isEmpty(idList)) {
			throw exceptionHelper
					.getConversionException(ORGANIZATION_IDENTIFIER_MISSING_TYPECODE);
		}
		OrganizationIdentifier orgId = idList.get(0);
		II id = orgId.getIdentifier();
		if (id == null || StringUtils.isBlank(id.getExtension())) {
			throw exceptionHelper
					.getConversionException(ORGANIZATION_IDENTIFIER_MISSING_TYPECODE);
		}
		return id.getExtension();
	}
	
	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.subjectregistry.converters.SubjectRegistryJAXBToDomainObjectConverter#convertHealthcareSitePrimaryIdentifier(edu.duke.cabig.c3pr.webservice.common.OrganizationIdentifier)
	 */
	public String convertHealthcareSitePrimaryIdentifier(OrganizationIdentifier orgId) {
		II id = orgId.getIdentifier();
		if (id == null || StringUtils.isBlank(id.getExtension())) {
			throw exceptionHelper
					.getConversionException(ORGANIZATION_IDENTIFIER_MISSING_TYPECODE);
		}
		return id.getExtension();
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.subjectregistry.converters.SubjectRegistryJAXBToDomainObjectConverter#convertSubjectConsent(java.util.List)
	 */
	public List<StudySubjectConsentVersion> convertSubjectConsent(
			List<edu.duke.cabig.c3pr.webservice.common.StudySubjectConsentVersion> subjectConsents) {
		List<StudySubjectConsentVersion> studySubjectConsentVersions = new ArrayList<StudySubjectConsentVersion>();
		for(edu.duke.cabig.c3pr.webservice.common.StudySubjectConsentVersion subjectConsent : subjectConsents){
			StudySubjectConsentVersion studySubjectConsentVersion = new StudySubjectConsentVersion();
			studySubjectConsentVersion.setConsentDeliveryDate(convertToDate(subjectConsent.getConsentDeliveryDate()));
			studySubjectConsentVersion.setConsentDeclinedDate(convertToDate(subjectConsent.getConsentDeclinedDate()));
			studySubjectConsentVersion.setDocumentId(subjectConsent.getIdentifier() !=null ?
					subjectConsent.getIdentifier().getExtension(): null);
			studySubjectConsentVersion.setConsentingMethod(subjectConsent.getConsentingMethod() !=null ? 
					ConsentingMethod.getByCode(subjectConsent.getConsentingMethod().getCode()) : null);
			studySubjectConsentVersion.setConsentPresenter(subjectConsent.getConsentPresenter() !=null ?
					subjectConsent.getConsentPresenter().getValue() : null);
			studySubjectConsentVersion.setInformedConsentSignedDate(convertToDate(subjectConsent.getInformedConsentDate()));
			Consent consent = new Consent();
			if(subjectConsent.getConsent() == null ||
					subjectConsent.getConsent().getOfficialTitle() == null ||
					StringUtils.isBlank(subjectConsent.getConsent().getOfficialTitle().getValue())){
				throw exceptionHelper
					.getConversionException(MISSING_OR_INVALID_CONSENT_NAME);
			}
			consent.setName(subjectConsent.getConsent().getOfficialTitle().getValue());
			if(subjectConsent.getConsent().getText() != null){
				consent.setDescriptionText(subjectConsent.getConsent().getText().getValue());
			}
			
			if(subjectConsent.getConsent().getVersionNumberText() != null &&
					!StringUtils.isBlank(subjectConsent.getConsent().getVersionNumberText().getValue())){
				consent.setVersionId(subjectConsent.getConsent().getVersionNumberText().getValue());
			}
			studySubjectConsentVersion.setConsent(consent);
			for(PerformedStudySubjectMilestone subjectConsetAnswer : subjectConsent.getSubjectConsentAnswer()){
				SubjectConsentQuestionAnswer subjectConsentQuestionAnswer = new SubjectConsentQuestionAnswer();
				if(subjectConsetAnswer.getMissedIndicator() == null ||
						subjectConsetAnswer.getMissedIndicator().isValue() == null){
					throw exceptionHelper
					.getConversionException(INVALID_CONSENT_QUESTION_ANSWER_AGREEMENTINDICATOR);
				}
				subjectConsentQuestionAnswer.setAgreementIndicator(subjectConsetAnswer.getMissedIndicator().isValue());
				subjectConsentQuestionAnswer.setConsentQuestion(new ConsentQuestion());
				if(subjectConsetAnswer.getConsentQuestion() == null ||
						subjectConsetAnswer.getConsentQuestion().getOfficialTitle() == null ||
						StringUtils.isBlank(subjectConsetAnswer.getConsentQuestion().getOfficialTitle().getValue())){
					throw exceptionHelper
					.getConversionException(INVALID_CONSENT_QUESTION);
				}
				subjectConsentQuestionAnswer.getConsentQuestion().setCode(subjectConsetAnswer.getConsentQuestion().getOfficialTitle().getValue());
				studySubjectConsentVersion.getSubjectConsentAnswers().add(subjectConsentQuestionAnswer);
			}
			studySubjectConsentVersions.add(studySubjectConsentVersion);
		}
		return studySubjectConsentVersions;
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.subjectregistry.converters.SubjectRegistryJAXBToDomainObjectConverter#convertSubjectIdentifiers(java.util.List)
	 */
	public List<Identifier> convertSubjectIdentifiers(
			List<SubjectIdentifier> subjectIdentifiers) {
		List<Identifier> identifiers = new ArrayList<Identifier>();
		for(SubjectIdentifier subId : subjectIdentifiers){
			final II ii = subId.getIdentifier();
			final CD typeCode = subId.getTypeCode();
			Organization org = subId.getAssigningOrganization();
			if (ii == null || typeCode == null) {
				throw exceptionHelper
						.getConversionException(MISSING_IDENTIFIER);
			}
			
			String systemName = typeCode.getCodeSystemName();
			
			// exactly one among organization and system_name are required
			if ((org == null && systemName == null) || (org != null && systemName != null) ) {
				throw exceptionHelper
						.getConversionException(STUDY_SUBJECT_IDENTIFIER_HAS_TO_CONTAIN_EXACTLY_ONE_AMONG_ORGANIZATION_AND_SYSTEMNAME);
			}
			Identifier id = null;
			if(org != null){
				id = new OrganizationAssignedIdentifier();
				id.setTypeInternal(OrganizationIdentifierTypeEnum.valueOf(typeCode.getCode()).getName());
				HealthcareSite healthcareSite = resolveHealthcareSite(org);
				((OrganizationAssignedIdentifier)id).setHealthcareSite(healthcareSite);
			} else if(systemName != null){
				id = new SystemAssignedIdentifier();
				id.setTypeInternal(typeCode.getCode());
				((SystemAssignedIdentifier)id).setSystemName(systemName);
			}
			id.setPrimaryIndicator(subId.getPrimaryIndicator().isValue());
			id.setValue(ii.getExtension());
			
			identifiers.add(id);
		}
		return identifiers;
	}
	
	/**
	 * Convert to subject identifier.
	 *
	 * @param identifiers the identifiers
	 * @return the list
	 */
	protected List<SubjectIdentifier> convertToSubjectIdentifier(
			List<Identifier> identifiers) {
		List<SubjectIdentifier> result = new ArrayList<SubjectIdentifier>();
		for (Identifier source : identifiers) {
			SubjectIdentifier id = new SubjectIdentifier();
			id.setIdentifier(II(source.getValue()));
			id.setTypeCode(CD(source.getTypeInternal()));
			id.setPrimaryIndicator(BL(source.getPrimaryIndicator()));
			if (source instanceof OrganizationAssignedIdentifier) {
				
				id.setTypeCode(CD(((OrganizationAssignedIdentifier)source).getType().getName()));
				HealthcareSite site = ((OrganizationAssignedIdentifier) source)
						.getHealthcareSite();
				Organization org = new Organization();
				for (Identifier siteId : site
						.getIdentifiersAssignedToOrganization()) {
					OrganizationIdentifier orgId = new OrganizationIdentifier();
					orgId.setTypeCode(CD(siteId.getTypeInternal()));
					orgId.setIdentifier(II(siteId.getValue()));
					orgId.setPrimaryIndicator(BL(siteId
							.getPrimaryIndicator()));
					org.getOrganizationIdentifier().add(orgId);
				}
				id.setAssigningOrganization(org);
			} else if(source instanceof SystemAssignedIdentifier){
				id.getTypeCode().setCodeSystemName(((SystemAssignedIdentifier)source).getSystemName());
			}
			result.add(id);
		}
		return result;
	}
	
	/**
	 * Convert to organization identifier.
	 *
	 * @param identifiers the identifiers
	 * @return the list
	 */
	protected List<OrganizationIdentifier> convertToOrganizationIdentifier(
			List<Identifier> identifiers) {
		List<OrganizationIdentifier> result = new ArrayList<OrganizationIdentifier>();
		for (Identifier source : identifiers) {
			if (source instanceof OrganizationAssignedIdentifier) {
				OrganizationIdentifier id = new OrganizationIdentifier();
				id.setTypeCode(CD(source.getTypeInternal()));
				id.setIdentifier(II(source.getValue()));
				id.setPrimaryIndicator(BL(source
						.getPrimaryIndicator()));
				result.add(id);
			}
		}
		return result;
	}
	
	
	public DSETStudySubject optionallyLoadStudySubjectData(DSETStudySubject studySubjects, List<edu.duke.cabig.c3pr.domain.StudySubject> domainStudySubjects){
		
			List<Integer> studySubjectIds = new ArrayList<Integer>();
			// Map<Integer,edu.duke.cabig.c3pr.domain.StudySubject> map = new HashMap<Integer,edu.duke.cabig.c3pr.domain.StudySubject>();
			for(edu.duke.cabig.c3pr.domain.StudySubject ss : domainStudySubjects) {
				studySubjectIds.add(ss.getId());
			}
		
			List<Integer> studySubjectStudyVersionIds = new ArrayList<Integer>();
			// Map<Integer,edu.duke.cabig.c3pr.domain.StudySubject> map = new HashMap<Integer,edu.duke.cabig.c3pr.domain.StudySubject>();
			for(edu.duke.cabig.c3pr.domain.StudySubject ss : domainStudySubjects) {
				studySubjectStudyVersionIds.add(ss.getStudySubjectVersionId());
			}
			
			// load study site
			Map<Integer, StudySite> studySitesMap = new HashMap<Integer,StudySite>();
			if(isLoadStudySite() && !studySubjectStudyVersionIds.isEmpty()){
				List<StudySite> studySites = studySubjectDao.loadStudySitesByStudySubjectVersionIds(studySubjectStudyVersionIds);
				for(StudySite studySite : studySites){
					studySitesMap.put(studySite.getStudySubjectId(), studySite);
				}
			}
			
			// build study ids list
			//eliminate duplicate study ids by adding to a set and then copy it to a list
			Set<Integer> studyIdsSet = new HashSet<Integer>();
			for(edu.duke.cabig.c3pr.domain.StudySubject studySub : domainStudySubjects){
				studyIdsSet.add(studySub.getStudyId());
			}
			List<Integer> studyIds = new ArrayList<Integer>();
			
			studyIds.addAll(studyIdsSet);
			
			// load study identifier
			Map<Integer, Identifier> studyIdentifiersMap = new HashMap<Integer,Identifier>();
			if(isLoadStudyIdentifier() && !studyIds.isEmpty()){
				List<Object> studyIdentifierObjects = studySubjectDao.loadPrimaryStudyIdentifierByStudyIds(studyIds);
				for(Object studyIdentifierObj : studyIdentifierObjects){
					Integer studyId = (Integer) ((Object[])studyIdentifierObj)[0];
					Identifier identifier = (Identifier)((Object[])studyIdentifierObj)[1];
					studyIdentifiersMap.put(studyId, identifier);
				}
			}
			
		// load study 
			Map<Integer, Study> studiesMap = new HashMap<Integer,Study>();
			if(isLoadStudy() && !studyIds.isEmpty()){
				List<Study> studies = studySubjectDao.loadStudiesFromStudyIds(studyIds);
				for(Study study : studies){
					studiesMap.put(study.getId(), study);
				}
			}
			
			for (edu.duke.cabig.c3pr.domain.StudySubject result : domainStudySubjects) {
				// load study site
				StudySite studySite= null;
				if(isLoadStudySite()){
					studySite = studySitesMap.get(result.getId());
				}
				
				// load study primary identifier
				Identifier studyPrimaryIdentifier = null;
				if(isLoadStudyIdentifier()){
					studyPrimaryIdentifier = studyIdentifiersMap.get(result.getStudyId());
				}
				
				// load study
				Study study = null;
				if(isLoadStudy()){
					study = studiesMap.get(result.getStudyId());
				}
				
				studySubjects.getItem().add(convert(result,study, studyPrimaryIdentifier,studySite));
			}
		
		return studySubjects;
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.subjectregistry.converters.SubjectRegistryJAXBToDomainObjectConverter#convert(edu.duke.cabig.c3pr.domain.StudySubject)
	 */
	public StudySubject convert(
			edu.duke.cabig.c3pr.domain.StudySubject domainObject) {
		StudySubject studySubject = new StudySubject();
		//set person
		Person person = convertSubjectDemographics(domainObject.getStudySubjectDemographics());
		
		//copy enrollment
		studySubject.setEntity(person);
		studySubject.setPaymentMethodCode(CD(domainObject.getPaymentMethod()));
		studySubject.setStatusCode(CD(domainObject.getRegDataEntryStatus().getCode()));
		
		//copy identifiers
		studySubject.getSubjectIdentifier().addAll(convertToSubjectIdentifier(domainObject.getIdentifiers()));
		
		//set studySubjectProtocolVersion
		studySubject.setStudySubjectProtocolVersion(getStudySubjectProtocolVersion(domainObject.getStudySubjectStudyVersion()));
		//set status history
		studySubject.getStudySubjectStatus().addAll(convertToStudySubjectRegistryStatus(domainObject.getStudySubjectRegistryStatusHistory()));
		
		return studySubject;
	}
	
	private StudySubject convert(
			edu.duke.cabig.c3pr.domain.StudySubject domainObject,
			Study study, Identifier studyIdentifier, StudySite studySite) {
		StudySubject studySubject = new StudySubject();
		//set person
		Person person = convertSubjectDemographics(domainObject.getStudySubjectDemographics());
		
		//copy enrollment
		studySubject.setEntity(person);
		studySubject.setPaymentMethodCode(CD(domainObject.getPaymentMethod()));
		studySubject.setStatusCode(CD(domainObject.getRegDataEntryStatus().getCode()));
		
		//copy identifiers
		studySubject.getSubjectIdentifier().addAll(convertToSubjectIdentifier(domainObject.getIdentifiers()));
		
		//set studySubjectProtocolVersion
		studySubject.setStudySubjectProtocolVersion(getStudySubjectProtocolVersion(domainObject.getStudySubjectStudyVersion(), study,
				studyIdentifier, studySite));
		//set status history
		studySubject.getStudySubjectStatus().addAll(convertToStudySubjectRegistryStatus(domainObject.getStudySubjectRegistryStatusHistory()));
		
		return studySubject;
	}
	
	
	protected StudySubjectProtocolVersionRelationship getStudySubjectProtocolVersion(StudySubjectStudyVersion studySubjectStudyVersion,
			Study study, Identifier studyIdentifier, StudySite studySite){
		StudySubjectProtocolVersionRelationship studySubjectProtocolVersion = new StudySubjectProtocolVersionRelationship();
//		StudySite studySite = studySubjectStudyVersion.getStudySiteStudyVersion().getStudySite();
		studySubjectProtocolVersion.setStudySiteProtocolVersion(new StudySiteProtocolVersionRelationship());
		//setup study
		studySubjectProtocolVersion.getStudySiteProtocolVersion().setStudyProtocolVersion(new StudyProtocolVersion());
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudyProtocolVersion().setStudyProtocolDocument(new StudyProtocolDocumentVersion());
		
		if(study!=null)
		{
			studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudyProtocolVersion().getStudyProtocolDocument().setPublicTitle(ST(study.getLongTitleText()));
			studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudyProtocolVersion().getStudyProtocolDocument().setOfficialTitle(ST(study.getShortTitleText()));
			studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudyProtocolVersion().getStudyProtocolDocument().setPublicDescription(ST(study.getDescriptionText()));
		}
		
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudyProtocolVersion().getStudyProtocolDocument().setDocument(new Document());
		if(studyIdentifier != null){
			List<Identifier> studyIdentifiers = new ArrayList<Identifier>();
			studyIdentifiers.add(studyIdentifier);
			studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudyProtocolVersion().getStudyProtocolDocument().getDocument()
			.getDocumentIdentifier().addAll(convertToDocumentIdentifier(studyIdentifiers));
		}
		//setup studysite
		studySubjectProtocolVersion.getStudySiteProtocolVersion().setStudySite(new edu.duke.cabig.c3pr.webservice.common.StudySite());
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudySite().setOrganization(new Organization());
			if (studySite != null) {
				studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudySite().getOrganization().getOrganizationIdentifier()
						.addAll(convertToOrganizationIdentifier(studySite.getHealthcareSite().getIdentifiersAssignedToOrganization()));
			}
		studySubjectProtocolVersion.getStudySubjectConsentVersion().addAll(convertToSubjectConsent(studySubjectStudyVersion.getStudySubjectConsentVersionsInternal()));
		return studySubjectProtocolVersion;
	}
	
	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.subjectregistry.converters.SubjectRegistryJAXBToDomainObjectConverter#convertRegistryStatus(edu.duke.cabig.c3pr.webservice.common.PerformedStudySubjectMilestone)
	 */
	public StudySubjectRegistryStatus convertRegistryStatus(
			PerformedStudySubjectMilestone status) {
		StudySubjectRegistryStatus studySubjectRegistryStatus = new StudySubjectRegistryStatus();
		if(status.getStatusCode() == null ||
				StringUtils.isBlank(status.getStatusCode().getCode())){
			throw exceptionHelper
				.getConversionException(MISSING_STATUS_CODE);
		}else if(status.getStatusDate() == null){
			throw exceptionHelper
				.getConversionException(MISSING_STATUS_DATE);
		}
		studySubjectRegistryStatus.setPermissibleStudySubjectRegistryStatus(new PermissibleStudySubjectRegistryStatus());
		studySubjectRegistryStatus.getPermissibleStudySubjectRegistryStatus().setRegistryStatus(new RegistryStatus());
		studySubjectRegistryStatus.getPermissibleStudySubjectRegistryStatus().getRegistryStatus().setCode(status.getStatusCode().getCode());
		studySubjectRegistryStatus.setEffectiveDate(convertToDate(status.getStatusDate()));
		studySubjectRegistryStatus.setCommentText(status.getComment()==null ? null : status.getComment().getValue());
		if(status.getReasonCode() != null && status.getReasonCode().getItem() != null){
			for(CD reasonCode : status.getReasonCode().getItem()){
				if(reasonCode !=null && !StringUtils.isBlank(reasonCode.getCode())){
					studySubjectRegistryStatus.getReasons().add(new RegistryStatusReason(reasonCode.getCode(),"",null,false));
				}
			}
		}
		return studySubjectRegistryStatus;
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.subjectregistry.converters.SubjectRegistryJAXBToDomainObjectConverter#convertSubjectDemographics(edu.duke.cabig.c3pr.domain.StudySubjectDemographics)
	 */
	public Person convertSubjectDemographics(
			StudySubjectDemographics studySubjectDemographics) {
		Person person = new Person();
		if (studySubjectDemographics != null) {
			for (Identifier id : studySubjectDemographics.getIdentifiers()) {
				BiologicEntityIdentifier bioId = new BiologicEntityIdentifier();
				bioId.setIdentifier(II(id.getValue()));
				bioId
						.setEffectiveDateRange(IVLTSDateTime(
								NullFlavor.NI));
				bioId.setPrimaryIndicator(BL(id.getPrimaryIndicator()));
				if (id instanceof OrganizationAssignedIdentifier) {
					bioId.setTypeCode(CD(((OrganizationAssignedIdentifier)id).getType().getName()));
					HealthcareSite site = ((OrganizationAssignedIdentifier) id)
							.getHealthcareSite();
					Organization org = new Organization();

					for (Identifier siteId : site
							.getIdentifiersAssignedToOrganization()) {
						OrganizationIdentifier orgId = new OrganizationIdentifier();
						orgId.setTypeCode(CD(siteId.getTypeInternal()));
						orgId.setIdentifier(II(siteId.getValue()));
						orgId.setPrimaryIndicator(BL(siteId
								.getPrimaryIndicator()));
						org.getOrganizationIdentifier().add(orgId);
					}

					bioId.setAssigningOrganization(org);
				}else if (id instanceof SystemAssignedIdentifier){
					bioId.setTypeCode(CD((id).getTypeInternal()));
					bioId.getTypeCode().setCodeSystemName(((SystemAssignedIdentifier)id).getSystemName());
				}
				person.getBiologicEntityIdentifier().add(bioId);
			}
			person
					.setAdministrativeGenderCode(studySubjectDemographics
							.getAdministrativeGenderCode() != null ? CD(studySubjectDemographics
							.getAdministrativeGenderCode()) : CD(
							NullFlavor.NI));
			person.setBirthDate(convertToTsDateTime(studySubjectDemographics.getBirthDate()));
			person.setEthnicGroupCode(getEthnicGroupCode(studySubjectDemographics));
			person
					.setMaritalStatusCode(studySubjectDemographics.getMaritalStatusCode() != null ? CD(
							studySubjectDemographics.getMaritalStatusCode())
							: CD(NullFlavor.NI));
			person.setName(getName(studySubjectDemographics));
			person.setPostalAddress(getPostalAddress(studySubjectDemographics.getAddresses()));
			person.setRaceCode(getRaceCodes(studySubjectDemographics));
			person.setTelecomAddress(getTelecomAddress(studySubjectDemographics));
		}
		return person;
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.subjectregistry.converters.SubjectRegistryJAXBToDomainObjectConverter#convertToSubjectDemographics(edu.duke.cabig.c3pr.domain.StudySubjectDemographics, edu.duke.cabig.c3pr.webservice.common.Subject)
	 */
	public void convertToSubjectDemographics(
			StudySubjectDemographics studySubjectDemographics, Subject subject) {
		if (subject != null && subject.getEntity() != null) {
			try {
				Person person = (Person) subject.getEntity();
				// gender
				CD gender = person.getAdministrativeGenderCode();
				studySubjectDemographics
						.setAdministrativeGenderCode(!isNull(gender) ? gender
								.getCode() : null);
				// birth date
				studySubjectDemographics.setBirthDate(convertToDate(person.getBirthDate()));
				studySubjectDemographics.setEthnicGroupCode(getEthnicGroupCode(person));
				studySubjectDemographics.setMaritalStatusCode(!isNull(person
						.getMaritalStatusCode()) ? person
						.getMaritalStatusCode().getCode() : null);
				studySubjectDemographics.setFirstName(getFirstName(person));
				studySubjectDemographics.setLastName(getLastName(person));
				studySubjectDemographics.setMiddleName(getMiddleName(person));
				studySubjectDemographics.setNamePrefix(getNamePrefix(person));
				studySubjectDemographics.setNameSuffix(getNameSuffix(person));
				studySubjectDemographics.setMaidenName(StringUtils.EMPTY);
				studySubjectDemographics.replaceAddresses(getAddresses(person));
				studySubjectDemographics.setRaceCodes(getRaceCodes(person));
				updateContactMechanism(studySubjectDemographics, person);
				// remove the existing identifiers of studySubjectDemographics and add the ones from subject in the request
				studySubjectDemographics.getIdentifiers().clear();
				studySubjectDemographics.getIdentifiers().addAll(convertBiologicIdentifiers(person.getBiologicEntityIdentifier()));
			} catch (IllegalArgumentException e) {
				throw exceptionHelper.getConversionException(
						INVALID_SUBJECT_DATA_REPRESENTATION, new Object[] { e
								.getMessage() });
			}
		} else {
			throw exceptionHelper
					.getConversionException(NO_SUBJECT_DATA_PROVIDED_CODE);
		}
	}
	
	/**
	 * Gets the study subject protocol version.
	 *
	 * @param studySubjectStudyVersion the study subject study version
	 * @return the study subject protocol version
	 */
	protected StudySubjectProtocolVersionRelationship getStudySubjectProtocolVersion(StudySubjectStudyVersion studySubjectStudyVersion){
		StudySubjectProtocolVersionRelationship studySubjectProtocolVersion = new StudySubjectProtocolVersionRelationship();
		StudySite studySite = studySubjectStudyVersion.getStudySiteStudyVersion().getStudySite();
		studySubjectProtocolVersion.setStudySiteProtocolVersion(new StudySiteProtocolVersionRelationship());
		
		//setup study
		studySubjectProtocolVersion.getStudySiteProtocolVersion().setStudyProtocolVersion(new StudyProtocolVersion());
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudyProtocolVersion().setStudyProtocolDocument(new StudyProtocolDocumentVersion());
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudyProtocolVersion().getStudyProtocolDocument().setPublicTitle(ST(studySite.getStudy().getLongTitleText()));
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudyProtocolVersion().getStudyProtocolDocument().setOfficialTitle(ST(studySite.getStudy().getShortTitleText()));
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudyProtocolVersion().getStudyProtocolDocument().setPublicDescription(ST(studySite.getStudy().getDescriptionText()));
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudyProtocolVersion().getStudyProtocolDocument().setDocument(new Document());
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudyProtocolVersion().getStudyProtocolDocument().getDocument().getDocumentIdentifier().addAll(convertToDocumentIdentifier(studySite.getStudy().getIdentifiers()));
		studySubjectProtocolVersion.getStudySiteProtocolVersion().setStudySite(new edu.duke.cabig.c3pr.webservice.common.StudySite());
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudySite().setOrganization(new Organization());
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudySite().getOrganization().getOrganizationIdentifier().addAll(convertToOrganizationIdentifier(studySite.getHealthcareSite().getIdentifiersAssignedToOrganization()));
		studySubjectProtocolVersion.getStudySubjectConsentVersion().addAll(convertToSubjectConsent(studySubjectStudyVersion.getStudySubjectConsentVersions()));
		return studySubjectProtocolVersion;
	}
	
	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.subjectregistry.converters.SubjectRegistryJAXBToDomainObjectConverter#convertToStudySubjectRegistryStatus(java.util.List)
	 */
	public List<PerformedStudySubjectMilestone> convertToStudySubjectRegistryStatus(List<StudySubjectRegistryStatus> statuses){
		List<PerformedStudySubjectMilestone> result = new ArrayList<PerformedStudySubjectMilestone>();
		for(StudySubjectRegistryStatus studySubjectRegistryStatus : statuses){
			PerformedStudySubjectMilestone target = new PerformedStudySubjectMilestone();
			target.setStatusCode(CD(studySubjectRegistryStatus.getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode()));
			target.setStatusDate(convertToTsDateTime(studySubjectRegistryStatus.getEffectiveDate()));
			target.setComment(StringUtils.isEmpty(studySubjectRegistryStatus.getCommentText()) ? null : ST(studySubjectRegistryStatus.getCommentText()));
			if(CollectionUtils.isNotEmpty(studySubjectRegistryStatus.getReasons())){
				target.setReasonCode(new DSETCD());
				for(RegistryStatusReason reason : studySubjectRegistryStatus.getReasons()){
					target.getReasonCode().getItem().add(CD(reason.getCode()));
				}
			}
			result.add(target);
		}
		return result;
	}
	
	/**
	 * Gets the ethnic group code.
	 *
	 * @param p the p
	 * @return the ethnic group code
	 */
	protected DSETCD getEthnicGroupCode(StudySubjectDemographics p) {
		DSETCD dsetcd = new DSETCD();
		if (StringUtils.isNotBlank(p.getEthnicGroupCode())) {
			dsetcd.getItem().add(CD(p.getEthnicGroupCode()));
		} else {
			dsetcd.setNullFlavor(NullFlavor.NI);
		}
		return dsetcd;
	}
	
	/**
	 * Gets the name.
	 *
	 * @param p the p
	 * @return the name
	 */
	protected DSETENPN getName(StudySubjectDemographics p) {
		DSETENPN dsetenpn = new DSETENPN();
		ENPN enpn = new ENPN();
		if (StringUtils.isNotBlank(p.getFirstName()))
			enpn.getPart()
					.add(
							ENXP(p.getFirstName(), EntityNamePartType
									.valueOf(GIV)));
		if (StringUtils.isNotBlank(p.getMiddleName()))
			enpn.getPart()
					.add(
							ENXP(p.getMiddleName(), EntityNamePartType
									.valueOf(GIV)));
		if (StringUtils.isNotBlank(p.getLastName()))
			enpn.getPart().add(
					ENXP(p.getLastName(), EntityNamePartType.valueOf(FAM)));
		if (StringUtils.isNotBlank(p.getNamePrefix()))
			enpn.getPart().add(
					ENXP(p.getNamePrefix(), EntityNamePartType.valueOf(PFX)));
		if (StringUtils.isNotBlank(p.getNameSuffix()))
			enpn.getPart().add(
					ENXP(p.getNameSuffix(), EntityNamePartType.valueOf(SFX)));
		dsetenpn.getItem().add(enpn);
		return dsetenpn;
	}
	
	/**
	 * Gets the race codes.
	 *
	 * @param p the p
	 * @return the race codes
	 */
	protected DSETCD getRaceCodes(StudySubjectDemographics p) {
		DSETCD dsetcd = new DSETCD();
		for (RaceCodeEnum raceCode : p.getRaceCodes()) {
			dsetcd.getItem().add(CD(raceCode.getCode()));
		}
		return dsetcd;
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.subjectregistry.converters.SubjectRegistryJAXBToDomainObjectConverter#convertToSubjectConsent(java.util.List)
	 */
	public List<edu.duke.cabig.c3pr.webservice.common.StudySubjectConsentVersion> convertToSubjectConsent(
			List<StudySubjectConsentVersion> studySubjectConsentVersions) {
		List<edu.duke.cabig.c3pr.webservice.common.StudySubjectConsentVersion> returnList = new ArrayList<edu.duke.cabig.c3pr.webservice.common.StudySubjectConsentVersion>();
		for(StudySubjectConsentVersion studySubjectConsentVersion : studySubjectConsentVersions){
			returnList.add(convertToSubjectConsent(studySubjectConsentVersion));
		}
		return returnList;
	}
	
	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.subjectregistry.converters.SubjectRegistryJAXBToDomainObjectConverter#convertToSubjectConsent(edu.duke.cabig.c3pr.domain.StudySubjectConsentVersion)
	 */
	public edu.duke.cabig.c3pr.webservice.common.StudySubjectConsentVersion convertToSubjectConsent(StudySubjectConsentVersion studySubjectConsentVersion){
		edu.duke.cabig.c3pr.webservice.common.StudySubjectConsentVersion target = new edu.duke.cabig.c3pr.webservice.common.StudySubjectConsentVersion();
		if(!StringUtils.isBlank(studySubjectConsentVersion.getDocumentId())){
			target.setIdentifier(II(studySubjectConsentVersion.getDocumentId()));
		}
		if(studySubjectConsentVersion.getConsentDeliveryDate() != null){
			target.setConsentDeliveryDate(convertToTsDateTime(studySubjectConsentVersion.getConsentDeliveryDate()));
		}
		if(studySubjectConsentVersion.getConsentDeclinedDate() != null){
			target.setConsentDeclinedDate(convertToTsDateTime(studySubjectConsentVersion.getConsentDeclinedDate()));
		}
		if(studySubjectConsentVersion.getConsentingMethod() != null){
			target.setConsentingMethod(CD(studySubjectConsentVersion.getConsentingMethod().getCode()));
		}
		target.setConsentPresenter(ST(studySubjectConsentVersion.getConsentPresenter()));
		target.setInformedConsentDate(convertToTsDateTime(studySubjectConsentVersion.getInformedConsentSignedDate()));
		edu.duke.cabig.c3pr.webservice.common.Consent consent = new edu.duke.cabig.c3pr.webservice.common.Consent();
		consent.setOfficialTitle(ST(studySubjectConsentVersion.getConsent().getName()));
		consent.setText(ED(studySubjectConsentVersion.getConsent().getDescriptionText()));
		if(!StringUtils.isBlank(studySubjectConsentVersion.getConsent().getVersionId())){
			consent.setVersionNumberText(ST(studySubjectConsentVersion.getConsent().getVersionId()));
		}
		consent.setMandatoryIndicator(BL(studySubjectConsentVersion.getConsent().getMandatoryIndicator()));
		target.setConsent(consent);
		for(SubjectConsentQuestionAnswer subjectAnswer : studySubjectConsentVersion.getSubjectConsentAnswers()){
			PerformedStudySubjectMilestone subjectAnswerTarget = new PerformedStudySubjectMilestone();
			subjectAnswerTarget.setMissedIndicator(BL(subjectAnswer.getAgreementIndicator()));
			subjectAnswerTarget.setConsentQuestion(new DocumentVersion());
			subjectAnswerTarget.getConsentQuestion().setOfficialTitle(ST(subjectAnswer.getConsentQuestion().getCode()));
			target.getSubjectConsentAnswer().add(subjectAnswerTarget);
		}
		return target;
	}
	
	/**
	 * @return the applicationContext
	 */
	public final ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * @param applicationContext
	 *            the applicationContext to set
	 */
	public final void setApplicationContext(
			ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
}
