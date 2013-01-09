/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.webservice.converters;

import static edu.duke.cabig.c3pr.webservice.helpers.ISO21090Helper.ADXP;
import static edu.duke.cabig.c3pr.webservice.helpers.ISO21090Helper.BL;
import static edu.duke.cabig.c3pr.webservice.helpers.ISO21090Helper.CD;
import static edu.duke.cabig.c3pr.webservice.helpers.ISO21090Helper.ED;
import static edu.duke.cabig.c3pr.webservice.helpers.ISO21090Helper.ENXP;
import static edu.duke.cabig.c3pr.webservice.helpers.ISO21090Helper.II;
import static edu.duke.cabig.c3pr.webservice.helpers.ISO21090Helper.IVLTSDateTime;
import static edu.duke.cabig.c3pr.webservice.helpers.ISO21090Helper.ST;
import static edu.duke.cabig.c3pr.webservice.helpers.ISO21090Helper.TEL;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.properties.SortOrderEnum;

import com.semanticbits.querybuilder.AdvancedSearchCriteriaParameter;
import com.semanticbits.querybuilder.AdvancedSearchHelper;

import edu.duke.cabig.c3pr.constants.AddressUse;
import edu.duke.cabig.c3pr.constants.ConsentRequired;
import edu.duke.cabig.c3pr.constants.ConsentingMethod;
import edu.duke.cabig.c3pr.constants.ContactMechanismUse;
import edu.duke.cabig.c3pr.constants.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;
import edu.duke.cabig.c3pr.constants.ParticipantStateCode;
import edu.duke.cabig.c3pr.constants.RaceCodeEnum;
import edu.duke.cabig.c3pr.constants.SortOrder;
import edu.duke.cabig.c3pr.constants.StudyDataEntryStatus;
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.RegistryStatusDao;
import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.Consent;
import edu.duke.cabig.c3pr.domain.ConsentQuestion;
import edu.duke.cabig.c3pr.domain.ContactMechanism;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.LocalStudy;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.PersonBase;
import edu.duke.cabig.c3pr.domain.Reason;
import edu.duke.cabig.c3pr.domain.RegistryStatus;
import edu.duke.cabig.c3pr.domain.RegistryStatusReason;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyCoordinatingCenter;
import edu.duke.cabig.c3pr.domain.StudyFundingSponsor;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.exception.ConversionException;
import edu.duke.cabig.c3pr.webservice.common.AdvanceSearchCriterionParameter;
import edu.duke.cabig.c3pr.webservice.common.BiologicEntityIdentifier;
import edu.duke.cabig.c3pr.webservice.common.Document;
import edu.duke.cabig.c3pr.webservice.common.DocumentIdentifier;
import edu.duke.cabig.c3pr.webservice.common.DocumentVersion;
import edu.duke.cabig.c3pr.webservice.common.DocumentVersionRelationship;
import edu.duke.cabig.c3pr.webservice.common.Organization;
import edu.duke.cabig.c3pr.webservice.common.OrganizationIdentifier;
import edu.duke.cabig.c3pr.webservice.common.PermissibleStudySubjectRegistryStatus;
import edu.duke.cabig.c3pr.webservice.common.Person;
import edu.duke.cabig.c3pr.webservice.common.SortParameter;
import edu.duke.cabig.c3pr.webservice.common.StudyProtocolDocumentVersion;
import edu.duke.cabig.c3pr.webservice.common.StudyProtocolVersion;
import edu.duke.cabig.c3pr.webservice.common.Subject;
import edu.duke.cabig.c3pr.webservice.helpers.ISO21090Helper;
import edu.duke.cabig.c3pr.webservice.iso21090.AD;
import edu.duke.cabig.c3pr.webservice.iso21090.ADXP;
import edu.duke.cabig.c3pr.webservice.iso21090.ANY;
import edu.duke.cabig.c3pr.webservice.iso21090.AddressPartType;
import edu.duke.cabig.c3pr.webservice.iso21090.BAGTEL;
import edu.duke.cabig.c3pr.webservice.iso21090.BL;
import edu.duke.cabig.c3pr.webservice.iso21090.CD;
import edu.duke.cabig.c3pr.webservice.iso21090.DSETAD;
import edu.duke.cabig.c3pr.webservice.iso21090.DSETCD;
import edu.duke.cabig.c3pr.webservice.iso21090.DSETENPN;
import edu.duke.cabig.c3pr.webservice.iso21090.ENPN;
import edu.duke.cabig.c3pr.webservice.iso21090.ENXP;
import edu.duke.cabig.c3pr.webservice.iso21090.EntityNamePartType;
import edu.duke.cabig.c3pr.webservice.iso21090.II;
import edu.duke.cabig.c3pr.webservice.iso21090.NullFlavor;
import edu.duke.cabig.c3pr.webservice.iso21090.PostalAddressUse;
import edu.duke.cabig.c3pr.webservice.iso21090.ST;
import edu.duke.cabig.c3pr.webservice.iso21090.TEL;
import edu.duke.cabig.c3pr.webservice.iso21090.TSDateTime;
import edu.duke.cabig.c3pr.webservice.iso21090.TelecommunicationAddressUse;

/**
 * Default implementation of {@link JAXBToDomainObjectConverter}.
 * 
 * @author dkrylov
 * 
 */
public class JAXBToDomainObjectConverterImpl implements
		JAXBToDomainObjectConverter {

	/** The Constant CONSENT_QUESTION. */
	public static final String CONSENT_QUESTION = "CONSENT_QUESTION";
	
	/** The Constant CONSENT. */
	public static final String CONSENT = "CONSENT";
	
	/** The Constant STUDY_VERSION_NAME. */
	public static final String STUDY_VERSION_NAME = "Original Version";
	
	/** The Constant STUDY_TYPE. */
	public static final String STUDY_TYPE = "Basic Science";
	
	/** The Constant STUDY_TARGET_ACCRUAL. */
	public static final int STUDY_TARGET_ACCRUAL = 100;
	
	/** The Constant STUDY_PHASE. */
	public static final String STUDY_PHASE = "Phase 0 Trial";
	
	/** The Constant SEMICOLON. */
	public static final String SEMICOLON = ":";
	
	/** The Constant X_TEXT_FAX. */
	public static final String X_TEXT_FAX = "x-text-fax";
	
	/** The Constant TEL. */
	public static final String TEL = "tel";
	
	/** The Constant MAILTO. */
	public static final String MAILTO = "mailto";
	
	/** The Constant OTHER. */
	public static final String OTHER = "user-defined";
	
	/** The Constant NAME_SEP. */
	public static final String NAME_SEP = " ";
	
	/** The Constant FAM. */
	public static final String FAM = "FAM";
	
	/** The Constant GIV. */
	public static final String GIV = "GIV";
	
	/** The Constant PFX. */
	public static final String PFX = "PFX";
	
	/** The Constant SFX. */
	public static final String SFX = "SFX";
	
	/** The Constant CTEP. */
	public static final String CTEP = "CTEP";
	
	/** The Constant NCI. */
	public static final String NCI = "NCI";

	/** The Constant TS_DATETIME_PATTERN. */
	public static final String TS_DATETIME_PATTERN = "yyyyMMddHHmmss";

	/** The Constant NO_SUBJECT_DATA_PROVIDED_CODE. */
	public static final int NO_SUBJECT_DATA_PROVIDED_CODE = 900;
	
	/** The Constant INVALID_SUBJECT_DATA_REPRESENTATION. */
	public static final int INVALID_SUBJECT_DATA_REPRESENTATION = 901;
	
	/** The Constant MISSING_SUBJECT_IDENTIFIER. */
	public static final int MISSING_SUBJECT_IDENTIFIER = 902;
	
	/** The Constant MISSING_PRIMARY_SUBJECT_IDENTIFIER. */
	public static final int MISSING_PRIMARY_SUBJECT_IDENTIFIER = 931;
	
	/** The Constant SUBJECT_IDENTIFIER_HAS_TO_CONTAIN_EXACTLY_ONE_AMONG_ORGANIZATION_AND_SYSTEMNAME. */
	public static final int SUBJECT_IDENTIFIER_HAS_TO_CONTAIN_EXACTLY_ONE_AMONG_ORGANIZATION_AND_SYSTEMNAME= 903;
	
	/** The Constant ORGANIZATION_IDENTIFIER_MISSING_TYPECODE. */
	public static final int ORGANIZATION_IDENTIFIER_MISSING_TYPECODE = 904;
	
	/** The Constant UNABLE_TO_FIND_ORGANIZATION. */
	public static final int UNABLE_TO_FIND_ORGANIZATION = 905;
	
	/** The Constant WRONG_DATE_FORMAT. */
	public static final int WRONG_DATE_FORMAT = 906;
	
	/** The Constant WRONG_RACE_CODE. */
	public static final int WRONG_RACE_CODE = 907;
	
	/** The Constant INVALID_SUBJECT_STATE_CODE. */
	public static final int INVALID_SUBJECT_STATE_CODE = 908;
	
	/** The Constant MISSING_ELEMENT. */
	public static final int MISSING_ELEMENT = 909;
	
	/** The Constant INVALID_STUDY_IDENTIFIER. */
	public static final int INVALID_STUDY_IDENTIFIER = 925;
	
	/** The Constant UNSUPPORTED_ORG_ID_TYPE. */
	public static final int UNSUPPORTED_ORG_ID_TYPE = 926;
	
	/** The Constant INVALID_STUDY_REPRESENTATION. */
	public static final int INVALID_STUDY_REPRESENTATION = 927;
	
	/** The Constant UNSUPPORTED_DOC_REL_TYPE. */
	public static final int UNSUPPORTED_DOC_REL_TYPE = 928;
	
	/** The Constant INVALID_CONSENT_REPRESENTATION. */
	public static final int INVALID_CONSENT_REPRESENTATION = 929;
	
	/** The Constant INVALID_REGISTRY_STATUS_CODE. */
	public static final int INVALID_REGISTRY_STATUS_CODE = 930;
	
	/** The Constant STUDY_IDENTIFIER_HAS_TO_CONTAIN_EXACTLY_ONE_OF_ORGANIZATION_OR_SYSTEMNAME. */
	public static final int STUDY_IDENTIFIER_HAS_TO_CONTAIN_EXACTLY_ONE_OF_ORGANIZATION_OR_SYSTEMNAME= 933;

	/** The Constant iso. */
	protected static final ISO21090Helper iso = new ISO21090Helper();

	/** The exception helper. */
	protected C3PRExceptionHelper exceptionHelper;

	/** The healthcare site dao. */
	protected HealthcareSiteDao healthcareSiteDao;

	/** The registry status dao. */
	protected RegistryStatusDao registryStatusDao;

	/** The log. */
	private static Log log = LogFactory
			.getLog(JAXBToDomainObjectConverterImpl.class);

	/**
	 * Gets the healthcare site dao.
	 *
	 * @return the healthcare site dao
	 */
	public HealthcareSiteDao getHealthcareSiteDao() {
		return healthcareSiteDao;
	}

	/**
	 * Sets the healthcare site dao.
	 *
	 * @param healthcareSiteDao the new healthcare site dao
	 */
	public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
		this.healthcareSiteDao = healthcareSiteDao;
	}

	/**
	 * Gets the exception helper.
	 *
	 * @return the exception helper
	 */
	public C3PRExceptionHelper getExceptionHelper() {
		return exceptionHelper;
	}

	/**
	 * Sets the exception helper.
	 *
	 * @param exceptionHelper the new exception helper
	 */
	public void setExceptionHelper(C3PRExceptionHelper exceptionHelper) {
		this.exceptionHelper = exceptionHelper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.duke.cabig.c3pr.webservice.converters.JAXBToDomainObjectCoverter#
	 * convert(edu.duke.cabig.c3pr.webservice.subjectmanagement.Subject)
	 */
	public Participant convert(Subject subject, boolean requireIdentifier,
			boolean skipEmptyNameParts) throws ConversionException {
		if (subject != null && subject.getEntity() != null) {
			Participant participant = new Participant();
			// the following cast is reasonably safe: there is only one
			// subclass
			// of BiologicalEntity.
			Person person = (Person) subject.getEntity();
			// ids
			List<BiologicEntityIdentifier> identifiers = person
					.getBiologicEntityIdentifier();
			if (CollectionUtils.isNotEmpty(identifiers)) {
				processIdentifiers(identifiers, participant);
			} else {
				if (requireIdentifier) {
					throw exceptionHelper
							.getConversionException(MISSING_SUBJECT_IDENTIFIER);
				}
			}
			if(participant.getPrimaryIdentifier() == null && requireIdentifier){
				throw exceptionHelper
				.getConversionException(MISSING_PRIMARY_SUBJECT_IDENTIFIER);
			}
			convert(participant, subject, skipEmptyNameParts);

			final ST subjectStCode = subject.getStateCode();
			if (subjectStCode != null && subjectStCode.getValue() != null) {
				String code = subjectStCode.getValue();
				ParticipantStateCode subjectCode = ParticipantStateCode
						.getByCode(code);
				if (subjectCode == null) {
					throw exceptionHelper
							.getConversionException(INVALID_SUBJECT_STATE_CODE);
				} else {
					participant.setStateCode(subjectCode);
				}
			} else {
				participant.setStateCode(ParticipantStateCode.ACTIVE);
			}
			return participant;

		}
		throw exceptionHelper
				.getConversionException(NO_SUBJECT_DATA_PROVIDED_CODE);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.duke.cabig.c3pr.webservice.converters.JAXBToDomainObjectConverter
	 * #convert(edu.duke.cabig.c3pr.domain.Participant,
	 * edu.duke.cabig.c3pr.webservice.subjectmanagement.Subject)
	 */
	public void convert(Participant participant, Subject subject,
			boolean skipEmptyNameParts) {
		if (subject != null && subject.getEntity() != null) {
			try {
				Person person = (Person) subject.getEntity();
				// gender
				CD gender = person.getAdministrativeGenderCode();
				participant
						.setAdministrativeGenderCode(!isNull(gender) ? gender
								.getCode() : null);
				// birth date
				participant.setBirthDate(convertToDate(person.getBirthDate()));
				participant.setDeathDate(convertToDate(person.getDeathDate()));
				participant.setDeathIndicator(!isNull(person
						.getDeathIndicator()) ? person.getDeathIndicator()
						.isValue() : null);
				participant.setEthnicGroupCode(getEthnicGroupCode(person));
				participant.setMaritalStatusCode(!isNull(person
						.getMaritalStatusCode()) ? person
						.getMaritalStatusCode().getCode() : null);
				participant.setFirstName(skipEmptyNameParts
						&& StringUtils.isEmpty(getFirstName(person)) ? null
						: getFirstName(person));
				participant.setLastName(skipEmptyNameParts
						&& StringUtils.isEmpty(getLastName(person)) ? null
						: getLastName(person));
				participant.setMiddleName(skipEmptyNameParts
						&& StringUtils.isEmpty(getMiddleName(person)) ? null
						: getMiddleName(person));
				participant.setNamePrefix(skipEmptyNameParts
						&& StringUtils.isEmpty(getNamePrefix(person)) ? null
						: getNamePrefix(person));
				participant.setNameSuffix(skipEmptyNameParts
						&& StringUtils.isEmpty(getNameSuffix(person)) ? null
						: getNameSuffix(person));
				participant.setMaidenName(skipEmptyNameParts ? null
						: StringUtils.EMPTY);
				participant.replaceAddresses(getAddresses(person));
				participant.setRaceCodes(getRaceCodes(person));
				// remove the existing identifiers of participant and add the ones from subject in the request
				participant.getIdentifiers().clear();
				processIdentifiers(subject.getEntity().getBiologicEntityIdentifier(),participant);
				updateContactMechanism(participant, person);
			} catch (IllegalArgumentException e) {
				throw exceptionHelper.getConversionException(
						INVALID_SUBJECT_DATA_REPRESENTATION,
						new Object[] { e.getMessage() });
			}
		} else {
			throw exceptionHelper
					.getConversionException(NO_SUBJECT_DATA_PROVIDED_CODE);
		}
	}

	/**
	 * Checks if is null.
	 *
	 * @param cd the cd
	 * @return true, if is null
	 */
	protected boolean isNull(ANY cd) {
		return cd == null || cd.getNullFlavor() != null;
	}

	/**
	 * Sets the contact mechanism.
	 *
	 * @param personBase the person base
	 * @param person the person
	 * @param type the type
	 */
	protected void setContactMechanism(PersonBase personBase, Person person, String type) {
		type = type.toLowerCase();
		BAGTEL bagtel = person.getTelecomAddress();
		if (!isNull(bagtel) && bagtel.getItem() != null) {
			for (TEL tel : bagtel.getItem()) {
				if (tel.getValue() != null
						&& tel.getValue().toLowerCase().startsWith(type)) {
					String value = tel.getValue().toLowerCase()
							.replaceFirst("^" + type + SEMICOLON, "");
					List<ContactMechanismUse> list = new ArrayList<ContactMechanismUse>();
					for(TelecommunicationAddressUse telecommunicationAddressUse : tel.getUse()){
						list.add(ContactMechanismUse.valueOf(telecommunicationAddressUse.name()));
					}
					if(type==MAILTO){
						personBase.setEmail(value, list);
					}else if(type==TEL){
						personBase.setPhone(value, list);
					}else if(type==X_TEXT_FAX){
						personBase.setFax(value, list);
					}else if(type==OTHER){
						personBase.setOther(value, list);
					}
					return;
				}
			}
		}
	}

	/**
	 * Update contact mechanism.
	 *
	 * @param personBase the person base
	 * @param person the person
	 */
	protected void updateContactMechanism(PersonBase personBase, Person person){
		personBase.getContactMechanisms().clear();
		setContactMechanism(personBase, person, MAILTO);
		setContactMechanism(personBase, person, TEL);
		setContactMechanism(personBase, person, X_TEXT_FAX);
		setContactMechanism(personBase, person, OTHER);
	}
	
	/**
	 * Gets the race codes.
	 *
	 * @param person the person
	 * @return the race codes
	 */
	protected List<RaceCodeEnum> getRaceCodes(Person person) {
		List<RaceCodeEnum> list = new ArrayList<RaceCodeEnum>();
		DSETCD dsetcd = person.getRaceCode();
		if (!isNull(dsetcd) && dsetcd.getItem() != null) {
			for (CD cd : dsetcd.getItem()) {
				String raceCodeStr = cd.getCode();
				RaceCodeEnum raceCode = RaceCodeEnum.getByCode(raceCodeStr);
				if (raceCode != null) {
					list.add(raceCode);
				} else {
					throw exceptionHelper.getConversionException(
							WRONG_RACE_CODE, new Object[] { raceCodeStr });
				}
			}
		}
		return list;
	}

	/**
	 * Gets the addresses.
	 *
	 * @param person the person
	 * @return the addresses
	 */
	protected Set<Address> getAddresses(Person person) {
		Set<Address> set = new LinkedHashSet<Address>();
		if (person.getPostalAddress() != null
				&& person.getPostalAddress().getItem() != null) {
			for (AD addr : person.getPostalAddress().getItem()) {
				if (!isNull(addr)) {
					Address address = new Address();
					address.setCity(getCity(addr));
					address.setCountryCode(getCountry(addr));
					address.setPostalCode(getZip(addr));
					address.setStateCode(getState(addr));
					address.setStreetAddress(getStreet(addr));
					for(PostalAddressUse postalAddressUse : addr.getUse()){
						address.addAddressUse(AddressUse.valueOf(postalAddressUse.name()));
					}
					set.add(address);
				}
			}
		}
		return set;
	}

	/**
	 * Gets the middle name.
	 *
	 * @param person the person
	 * @return the middle name
	 */
	protected String getMiddleName(Person person) {
		String name = "";
		List<String> list = extractNameParts(person, EntityNamePartType.GIV);
		if (CollectionUtils.isNotEmpty(list) && list.size() > 1) {
			name = list.get(1);
		}
		return name;
	}

	/**
	 * Gets the last name.
	 *
	 * @param person the person
	 * @return the last name
	 */
	protected String getLastName(Person person) {
		String name = "";
		List<String> list = extractNameParts(person, EntityNamePartType.FAM);
		if (CollectionUtils.isNotEmpty(list)) {
			name = StringUtils.join(list, NAME_SEP);
		}
		return name;
	}

	/**
	 * Gets the first name.
	 *
	 * @param person the person
	 * @return the first name
	 */
	protected String getFirstName(Person person) {
		String name = "";
		List<String> list = extractNameParts(person, EntityNamePartType.GIV);
		if (CollectionUtils.isNotEmpty(list)) {
			name = list.get(0);
		}
		return name;
	}
	
	/**
	 * Gets the name prefix.
	 *
	 * @param person the person
	 * @return the name prefix
	 */
	protected String getNamePrefix(Person person) {
		String name = "";
		List<String> list = extractNameParts(person, EntityNamePartType.PFX);
		if (CollectionUtils.isNotEmpty(list)) {
			name = list.get(0);
		}
		return name;
	}
	
	/**
	 * Gets the name suffix.
	 *
	 * @param person the person
	 * @return the name suffix
	 */
	protected String getNameSuffix(Person person) {
		String name = "";
		List<String> list = extractNameParts(person, EntityNamePartType.SFX);
		if (CollectionUtils.isNotEmpty(list)) {
			name = list.get(0);
		}
		return name;
	}

	/**
	 * Extract name parts.
	 *
	 * @param person the person
	 * @param type the type
	 * @return the list
	 */
	protected List<String> extractNameParts(Person person, EntityNamePartType type) {
		List<String> list = new ArrayList<String>();
		DSETENPN parts = person.getName();
		if (parts != null && CollectionUtils.isNotEmpty(parts.getItem())) {
			ENPN nameEntry = parts.getItem().get(0);
			if (nameEntry.getPart() != null) {
				for (ENXP nameEntryPart : nameEntry.getPart()) {
					if (type.equals(nameEntryPart.getType())) {
						list.add(nameEntryPart.getValue());
					}
				}
			}
		}
		return list;
	}

	/**
	 * Gets the ethnic group code.
	 *
	 * @param person the person
	 * @return the ethnic group code
	 */
	protected String getEthnicGroupCode(Person person) {
		DSETCD codes = person.getEthnicGroupCode();
		return getFirstCode(codes);
	}

	/**
	 * Gets the first code.
	 *
	 * @param codes the codes
	 * @return the first code
	 */
	protected String getFirstCode(DSETCD codes) {
		String code = null;
		if (codes != null && CollectionUtils.isNotEmpty(codes.getItem())) {
			code = codes.getItem().get(0).getCode();
		}
		return code;
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.converters.JAXBToDomainObjectConverter#convertToDate(edu.duke.cabig.c3pr.webservice.iso21090.TSDateTime)
	 */
	public Date convertToDate(TSDateTime tsDateTime) {
		try {
			if (tsDateTime != null && tsDateTime.getNullFlavor() == null) {
				String value = tsDateTime.getValue();
				if (value != null) {
					return DateUtils.parseDate(value,
							new String[] { TS_DATETIME_PATTERN });
				}
			}
		} catch (ParseException e) {
			throw exceptionHelper.getConversionException(WRONG_DATE_FORMAT,
					new Object[] { tsDateTime.getValue() });
		}
		return null;
	}

	/**
	 * Process identifiers.
	 *
	 * @param identifiers the identifiers
	 * @param participant the participant
	 */
	protected void processIdentifiers(List<BiologicEntityIdentifier> identifiers,
			Participant participant) {
		for (BiologicEntityIdentifier bioId : identifiers) {
			Identifier id = convert(bioId);
			participant.addIdentifier(id);
		}
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.converters.JAXBToDomainObjectConverter#convertBiologicIdentifiers(java.util.List)
	 */
	public List<Identifier> convertBiologicIdentifiers(
			List<BiologicEntityIdentifier> biologicIdentifiers) {
		List<Identifier> identifiers = new ArrayList<Identifier>();
		for(BiologicEntityIdentifier bioId : biologicIdentifiers){
			identifiers.add(convert(bioId));
		}
		return identifiers;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.duke.cabig.c3pr.webservice.converters.JAXBToDomainObjectConverter
	 * #convert
	 * (edu.duke.cabig.c3pr.webservice.subjectmanagement.BiologicEntityIdentifier
	 * )
	 */
	public Identifier convert(BiologicEntityIdentifier bioId)
			throws ConversionException {
		final II ii = bioId.getIdentifier();
		final CD typeCode = bioId.getTypeCode();
		Organization org = bioId.getAssigningOrganization();
		
		if (ii == null || typeCode == null) {
			throw exceptionHelper
					.getConversionException(MISSING_SUBJECT_IDENTIFIER);
		}
		
		String systemName = typeCode.getCodeSystemName();
		
		// throw exception either if both organization and system name are present or if none are present
		if((org != null && systemName != null) || (org == null && systemName == null)){
			throw exceptionHelper
			.getConversionException(SUBJECT_IDENTIFIER_HAS_TO_CONTAIN_EXACTLY_ONE_AMONG_ORGANIZATION_AND_SYSTEMNAME);
		}
		Identifier id = null;
		if(org != null){
			id = new OrganizationAssignedIdentifier();
			HealthcareSite healthcareSite = resolveHealthcareSite(org);
			((OrganizationAssignedIdentifier)id).setHealthcareSite(healthcareSite);
		}else{
			id = new SystemAssignedIdentifier();
			((SystemAssignedIdentifier)id).setSystemName(systemName);
		}
		 
		id.setPrimaryIndicator(bioId.getPrimaryIndicator()!=null?bioId.getPrimaryIndicator().isValue():new Boolean(true));
		id.setValue(ii.getExtension());
		id.setTypeInternal(typeCode.getCode());
		
		return id;
	}

	/**
	 * Resolve healthcare site.
	 *
	 * @param org the org
	 * @return the healthcare site
	 */
	protected HealthcareSite resolveHealthcareSite(Organization org) {
		List<OrganizationIdentifier> idList = org.getOrganizationIdentifier();
		if (CollectionUtils.isEmpty(idList)) {
			throw exceptionHelper
					.getConversionException(SUBJECT_IDENTIFIER_HAS_TO_CONTAIN_EXACTLY_ONE_AMONG_ORGANIZATION_AND_SYSTEMNAME);
		}
		OrganizationIdentifier orgId = idList.get(0);
		II id = orgId.getIdentifier();
		BL isPrimary = orgId.getPrimaryIndicator();
		CD typeCode = orgId.getTypeCode();
		return resolveHealthcareSite(id, isPrimary, typeCode);
	}

	/**
	 * Resolve healthcare site.
	 *
	 * @param id the id
	 * @param isPrimary the is primary
	 * @param typeCode the type code
	 * @return the healthcare site
	 * @throws ConversionException the conversion exception
	 */
	protected HealthcareSite resolveHealthcareSite(II id, BL isPrimary,
			CD typeCode) throws ConversionException {
		if (id == null || StringUtils.isBlank(id.getExtension())) {
			throw exceptionHelper
					.getConversionException(SUBJECT_IDENTIFIER_HAS_TO_CONTAIN_EXACTLY_ONE_AMONG_ORGANIZATION_AND_SYSTEMNAME);
		}
		if (typeCode == null || StringUtils.isBlank(typeCode.getCode())) {
			throw exceptionHelper
					.getConversionException(ORGANIZATION_IDENTIFIER_MISSING_TYPECODE);
		}

		Boolean primary = isPrimary != null ? isPrimary.isValue() : false;
		String codeName;
		if (typeCode.getCode().contains(CTEP)) {
			codeName = OrganizationIdentifierTypeEnum.CTEP.getName();
		} else if (typeCode.getCode().contains(NCI)) {
			codeName = OrganizationIdentifierTypeEnum.NCI.getName();
		} else {
			codeName = typeCode.getCode();
		}

		HealthcareSite org = healthcareSiteDao.getByTypeAndCodeFromLocal(
				typeCode.getCode(), id.getExtension(), primary);
		if (org == null)
			throw exceptionHelper
					.getConversionException(UNABLE_TO_FIND_ORGANIZATION);
		return org;
	}

	/**
	 * Gets the city.
	 *
	 * @param ad the ad
	 * @return the city
	 */
	protected String getCity(AD ad) {
		String city = null;
		List<ADXP> adXps = ad.getPart();
		for (ADXP adXp : adXps) {
			if (adXp.getType().equals(AddressPartType.CTY)) {
				city = adXp.getValue();
			}
		}
		return city;
	}

	/**
	 * Gets the country.
	 *
	 * @param ad the ad
	 * @return the country
	 */
	protected String getCountry(AD ad) {
		String ctry = null;
		List<ADXP> adXps = ad.getPart();
		for (ADXP adXp : adXps) {
			if (adXp.getType().equals(AddressPartType.CNT)) {
				ctry = adXp.getValue();
			}
		}
		return ctry;
	}

	/**
	 * Gets the state.
	 *
	 * @param ad the ad
	 * @return the state
	 */
	protected String getState(AD ad) {
		String state = null;
		List<ADXP> adXps = ad.getPart();
		for (ADXP adXp : adXps) {
			if (adXp.getType().equals(AddressPartType.STA)) {
				state = adXp.getValue();
			}
		}
		return state;
	}

	/**
	 * Gets the street.
	 *
	 * @param ad the ad
	 * @return the street
	 */
	protected String getStreet(AD ad) {
		String street = null;
		List<ADXP> adXps = ad.getPart();
		for (ADXP adXp : adXps) {
			if (adXp.getType().equals(AddressPartType.AL)
					|| adXp.getType().equals(AddressPartType.SAL)) {
				street = adXp.getValue();
			}
		}
		return street;
	}

	/**
	 * Gets the zip.
	 *
	 * @param ad the ad
	 * @return the zip
	 */
	protected String getZip(AD ad) {
		String zip = null;
		List<ADXP> adXps = ad.getPart();
		for (ADXP adXp : adXps) {
			if (adXp.getType().equals(AddressPartType.ZIP)) {
				zip = adXp.getValue();
			}
		}
		return zip;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.duke.cabig.c3pr.webservice.converters.JAXBToDomainObjectConverter
	 * #convert(edu.duke.cabig.c3pr.domain.Participant)
	 */
	public Subject convert(Participant p) {
		Subject subject = new Subject();
		if (p != null) {
			Person person = new Person();
			subject.setEntity(person);
			for (Identifier id : new LinkedHashSet<Identifier>(
					p.getIdentifiers())) {
				if (id instanceof OrganizationAssignedIdentifier) {
					BiologicEntityIdentifier bioId = new BiologicEntityIdentifier();
					bioId.setTypeCode(CD(id.getTypeInternal()));
					bioId.setIdentifier(II(id.getValue()));
					bioId.setEffectiveDateRange(IVLTSDateTime(NullFlavor.NI));
					bioId.setPrimaryIndicator(BL(id.getPrimaryIndicator()));

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
					person.getBiologicEntityIdentifier().add(bioId);
				}
				if (id instanceof SystemAssignedIdentifier) {
					BiologicEntityIdentifier bioId = new BiologicEntityIdentifier();
					bioId.setTypeCode(CD(id.getTypeInternal()));
					bioId.setIdentifier(II(id.getValue()));
					bioId.setEffectiveDateRange(IVLTSDateTime(NullFlavor.NI));
					bioId.setPrimaryIndicator(BL(id.getPrimaryIndicator()));
					bioId.getTypeCode().setCodeSystemName(((SystemAssignedIdentifier) id).getSystemName());
					person.getBiologicEntityIdentifier().add(bioId);
				}
			}
			person.setAdministrativeGenderCode(p.getAdministrativeGenderCode() != null ? CD(p.getAdministrativeGenderCode()) : CD(NullFlavor.NI));
			person.setBirthDate(convertToTsDateTime(p.getBirthDate()));
			person.setDeathDate(convertToTsDateTime(p.getDeathDate()));
			person.setDeathIndicator(p.getDeathIndicator() != null ? BL(p
					.getDeathIndicator()) : BL(NullFlavor.NI));
			person.setEthnicGroupCode(getEthnicGroupCode(p));
			person.setMaritalStatusCode(p.getMaritalStatusCode() != null ? CD(p.getMaritalStatusCode()) : CD(NullFlavor.NI));
			person.setName(getName(p));
			person.setPostalAddress(getPostalAddress(p.getAddresses()));
			person.setRaceCode(getRaceCodes(p));
			person.setTelecomAddress(getTelecomAddress(p));
			if (p.getStateCode() != null) {
				subject.setStateCode(ST(p.getStateCode().getCode()));
			}
		}
		return subject;
	}

	/**
	 * Gets the telecom address.
	 *
	 * @param p the p
	 * @return the telecom address
	 */
	protected BAGTEL getTelecomAddress(PersonBase p) {
		BAGTEL addr = new BAGTEL();
		ContactMechanism cm = null;
		if (StringUtils.isNotBlank(p.getEmail())){
			cm=p.getEmailContactMechanism();
			addr.getItem().add(TEL(MAILTO + SEMICOLON + cm.getValue(), getTelecomAddressUse(cm.getContactUses())));
		}
		if (StringUtils.isNotBlank(p.getPhone())){
			cm=p.getPhoneContactMechanism();
			addr.getItem().add(TEL(TEL + SEMICOLON + cm.getValue(), getTelecomAddressUse(cm.getContactUses())));
		}
		if (StringUtils.isNotBlank(p.getFax())){
			cm=p.getFaxContactMechanism();
			addr.getItem().add(TEL(X_TEXT_FAX + SEMICOLON + cm.getValue(), getTelecomAddressUse(cm.getContactUses())));
		}
		if (StringUtils.isNotBlank(p.getOther())){
			cm=p.getOtherContactMechanism();
			addr.getItem().add(TEL(OTHER + SEMICOLON + cm.getValue(), getTelecomAddressUse(cm.getContactUses())));
		}
		return addr;
	}

	/**
	 * Gets the race codes.
	 *
	 * @param p the p
	 * @return the race codes
	 */
	protected DSETCD getRaceCodes(Participant p) {
		DSETCD dsetcd = new DSETCD();
		for (RaceCodeEnum raceCode : p.getRaceCodes()) {
			dsetcd.getItem().add(CD(raceCode.getCode()));
		}
		return dsetcd;
	}

	/**
	 * Gets the telecom address use.
	 *
	 * @param uses the uses
	 * @return the telecom address use
	 */
	protected List<TelecommunicationAddressUse> getTelecomAddressUse(List<ContactMechanismUse> uses) {
		List<TelecommunicationAddressUse> telUses = new ArrayList<TelecommunicationAddressUse>();
		for(ContactMechanismUse use: uses){
			telUses.add(TelecommunicationAddressUse.valueOf(use.name()));
		}
		return telUses;
	}
	
	/**
	 * Gets the postal address.
	 *
	 * @param addresses the addresses
	 * @return the postal address
	 */
	protected DSETAD getPostalAddress(Collection<Address> addresses) {
		DSETAD set = new DSETAD();
		if(addresses.isEmpty()) return null;
		for (Address address : addresses) {
			AD ad = new AD();
			if (address != null && !address.isBlank()) {
				if (StringUtils.isNotBlank(address.getStreetAddress()))
					ad.getPart().add(
							ADXP(address.getStreetAddress(),
									AddressPartType.SAL));
				if (StringUtils.isNotBlank(address.getCity()))
					ad.getPart().add(
							ADXP(address.getCity(), AddressPartType.CTY));
				if (StringUtils.isNotBlank(address.getStateCode()))
					ad.getPart().add(
							ADXP(address.getStateCode(),
									AddressPartType.STA));
				if (StringUtils.isNotBlank(address.getPostalCode()))
					ad.getPart().add(
							ADXP(address.getPostalCode(),
									AddressPartType.ZIP));
				if (StringUtils.isNotBlank(address.getCountryCode()))
					ad.getPart().add(
							ADXP(address.getCountryCode(),
									AddressPartType.CNT));
				for(AddressUse use : address.getAddressUses()){
					ad.getUse().add(PostalAddressUse.valueOf(use.name()));
				}
			} else {
				ad.setNullFlavor(NullFlavor.NI);
			}
			set.getItem().add(ad);
		}
		return set;
	}

	/**
	 * Gets the name.
	 *
	 * @param p the p
	 * @return the name
	 */
	protected DSETENPN getName(Participant p) {
		DSETENPN dsetenpn = new DSETENPN();
		ENPN enpn = new ENPN();
		if (StringUtils.isNotBlank(p.getFirstName()))
			enpn.getPart()
					.add(ENXP(p.getFirstName(),
							EntityNamePartType.valueOf(GIV)));
		if (StringUtils.isNotBlank(p.getMiddleName()))
			enpn.getPart()
					.add(ENXP(p.getMiddleName(),
							EntityNamePartType.valueOf(GIV)));
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
	 * Gets the ethnic group code.
	 *
	 * @param p the p
	 * @return the ethnic group code
	 */
	protected DSETCD getEthnicGroupCode(Participant p) {
		DSETCD dsetcd = new DSETCD();
		if (StringUtils.isNotBlank(p.getEthnicGroupCode())) {
			dsetcd.getItem().add(CD(p.getEthnicGroupCode()));
		} else {
			dsetcd.setNullFlavor(NullFlavor.NI);
		}
		return dsetcd;
	}

	/**
	 * Convert to ts date time.
	 *
	 * @param date the date
	 * @return the tS date time
	 */
	protected TSDateTime convertToTsDateTime(Date date) {
		TSDateTime tsDateTime = new TSDateTime();
		if (date != null) {
			tsDateTime.setValue(DateFormatUtils.format(date,
					TS_DATETIME_PATTERN));
		} else {
			tsDateTime.setNullFlavor(NullFlavor.NI);
		}
		return tsDateTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.duke.cabig.c3pr.webservice.converters.JAXBToDomainObjectConverter
	 * #convert(edu.duke.cabig.c3pr.webservice.subjectmanagement.
	 * AdvanceSearchCriterionParameter)
	 */
	public AdvancedSearchCriteriaParameter convert(
			AdvanceSearchCriterionParameter param) {
		String contextObjectName = (isNull(param.getObjectContextName()) || StringUtils
				.isBlank(param.getObjectContextName().getValue())) ? StringUtils.EMPTY
				: param.getObjectContextName().getValue();
		String objectName = convertAndErrorIfBlank(param.getObjectName(),
				"objectName");
		String attributeName = convertAndErrorIfBlank(param.getAttributeName(),
				"attributeName");
		String predicate = convertAndErrorIfBlank(param.getPredicate(),
				"predicate");
		List<String> values = new ArrayList<String>();
		for (ST st : param.getValues().getItem()) {
			values.add(st.getValue());
		}
		return AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(objectName,
						contextObjectName, attributeName, values, predicate);
	}
	
	
	public edu.duke.cabig.c3pr.utils.SortParameter convert(
			SortParameter param) {
		String contextObjectName = (isNull(param.getObjectContextName()) || StringUtils
				.isBlank(param.getObjectContextName().getValue())) ? StringUtils.EMPTY
				: param.getObjectContextName().getValue();
		String objectName = convertAndErrorIfBlank(param.getObjectName(),
				"objectName");
		String attributeName = convertAndErrorIfBlank(param.getAttributeName(),
				"attributeName");
		return new edu.duke.cabig.c3pr.utils.SortParameter(contextObjectName,objectName,
						 attributeName, param.getSortOrder() == null? null:SortOrder.valueOf(param.getSortOrder().value()));
	}


	/**
	 * Convert and error if blank.
	 *
	 * @param st the st
	 * @param elementName the element name
	 * @return the string
	 */
	protected String convertAndErrorIfBlank(ST st, String elementName) {
		if (isNull(st) || StringUtils.isBlank(st.getValue())) {
			throw exceptionHelper.getConversionException(MISSING_ELEMENT,
					new Object[] { elementName });
		} else {
			return st.getValue();
		}
	}

	/**
	 * Convert and error if blank.
	 *
	 * @param st the st
	 * @param elementName the element name
	 * @return the string
	 */
	protected String convertAndErrorIfBlank(CD st, String elementName) {
		if (isNull(st) || StringUtils.isBlank(st.getCode())) {
			throw exceptionHelper.getConversionException(MISSING_ELEMENT,
					new Object[] { elementName });
		} else {
			return st.getCode();
		}
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.converters.JAXBToDomainObjectConverter#convert(java.util.List)
	 */
	public List<Identifier> convert(
			List<DocumentIdentifier> docIds) {
		List<Identifier> list = new ArrayList<Identifier>();
		for (DocumentIdentifier docId : docIds) {
			list.add(convert(docId));
		}
		return new ArrayList<Identifier>(list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.duke.cabig.c3pr.webservice.converters.JAXBToDomainObjectConverter
	 * #convert(DocumentIdentifier)
	 */
	public Identifier convert(DocumentIdentifier docId) {
		II ii = docId.getIdentifier();
		CD typeCode = docId.getTypeCode();
		BL primInd = docId.getPrimaryIndicator();
		edu.duke.cabig.c3pr.webservice.common.Organization org = docId.getAssigningOrganization();
		
		if (isNull(ii) || isNull(typeCode)) {
			throw exceptionHelper
					.getConversionException(INVALID_STUDY_IDENTIFIER);
		}
		String systemName = typeCode.getCodeSystemName();
		
		// throw exception either if both organization and system name are present or if none are present
		if((org != null && systemName != null) || (org == null && systemName == null)){
			throw exceptionHelper
			.getConversionException(STUDY_IDENTIFIER_HAS_TO_CONTAIN_EXACTLY_ONE_OF_ORGANIZATION_OR_SYSTEMNAME);
		}
		Identifier id = null;
		if(org != null){
			if (CollectionUtils.isEmpty(org.getOrganizationIdentifier())){
				throw exceptionHelper.getConversionException(INVALID_STUDY_IDENTIFIER);
			}
			OrganizationIdentifier orgId = org.getOrganizationIdentifier().get(0);
			try {
				OrganizationIdentifierTypeEnum.valueOf(typeCode.getCode());
			} catch (IllegalArgumentException e) {
				throw exceptionHelper
						.getConversionException(UNSUPPORTED_ORG_ID_TYPE);
			}
			
			id = new OrganizationAssignedIdentifier();
			id.setTypeInternal(OrganizationIdentifierTypeEnum.valueOf(typeCode.getCode()).getName());
			HealthcareSite healthcareSite = resolveHealthcareSite(orgId.getIdentifier(), orgId.getPrimaryIndicator(),
					orgId.getTypeCode());
			if (healthcareSite == null) {
				throw exceptionHelper
						.getConversionException(UNABLE_TO_FIND_ORGANIZATION);
			}
			((OrganizationAssignedIdentifier)id).setHealthcareSite(healthcareSite);
		}else{
			id = new SystemAssignedIdentifier();
			id.setTypeInternal(typeCode.getCode());
			((SystemAssignedIdentifier)id).setSystemName(systemName);
		}
		
		id.setPrimaryIndicator(primInd != null
				&& Boolean.TRUE.equals(primInd.isValue()));
		id.setValue(ii.getExtension());
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.duke.cabig.c3pr.webservice.converters.JAXBToDomainObjectConverter
	 * #convert(edu.duke.cabig.c3pr.webservice.studyutility.Study)
	 */
	public Study convert(StudyProtocolVersion xmlStudy) {

		// some quick checks.
		if (xmlStudy.getStudyProtocolDocument() == null
				|| xmlStudy.getStudyProtocolDocument().getDocument() == null
				|| CollectionUtils.isEmpty(xmlStudy.getStudyProtocolDocument()
						.getDocument().getDocumentIdentifier())) {
			throw exceptionHelper
					.getConversionException(INVALID_STUDY_REPRESENTATION);
		}

		Study study = new LocalStudy();
		convert(study, xmlStudy, true);

		// statuses
		List<edu.duke.cabig.c3pr.domain.PermissibleStudySubjectRegistryStatus> statuses = new ArrayList<edu.duke.cabig.c3pr.domain.PermissibleStudySubjectRegistryStatus>();
		for (PermissibleStudySubjectRegistryStatus status : xmlStudy
				.getPermissibleStudySubjectRegistryStatus()) {
			statuses.add(convert(status));
		}
		study.getPermissibleStudySubjectRegistryStatusesInternal().clear();
		study.getPermissibleStudySubjectRegistryStatusesInternal().addAll(
				statuses);

		// study
		study.setBlindedIndicator(false);
		study.setMultiInstitutionIndicator(true);
		study.setPhaseCode(STUDY_PHASE);
		study.setRandomizedIndicator(false);
		study.setTargetAccrualNumber(STUDY_TARGET_ACCRUAL);
		study.setType(STUDY_TYPE);
		study.setRetiredIndicatorAsFalse();
		study.setStratificationIndicator(false);
		study.setStandaloneIndicator(true);
		study.setCompanionIndicator(false);
		study.setConsentRequired(ConsentRequired.ONE);
		study.setTherapeuticIntentIndicator(false);

		// study version
		study.setPrecisText(StringUtils.EMPTY);
		study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.PENDING);
		study.setDataEntryStatus(StudyDataEntryStatus.INCOMPLETE);

		study.setOriginalIndicator(true);
		// study.getStudyVersion().setName(STUDY_VERSION_NAME);

		// identifiers and study organizations
		convert(study, convert(xmlStudy.getStudyProtocolDocument().getDocument().getDocumentIdentifier()));
		return study;
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.converters.JAXBToDomainObjectConverter#convert(edu.duke.cabig.c3pr.domain.Study, java.util.List)
	 */
	public void convert(Study study, List<Identifier> identifiers) {
		// identifiers and study organizations
		study.getIdentifiers().clear();
		study.getIdentifiers().addAll(identifiers);
		for (Identifier identifier : identifiers) {
			if(identifier instanceof OrganizationAssignedIdentifier){
				OrganizationAssignedIdentifier id = (OrganizationAssignedIdentifier) identifier;
				HealthcareSite healthcareSite = id.getHealthcareSite();
				if(contains(study, healthcareSite, id.getType())){
					continue;
				}
				if (OrganizationIdentifierTypeEnum.COORDINATING_CENTER_IDENTIFIER
						.equals(id.getType())) {
					StudyCoordinatingCenter scc = new StudyCoordinatingCenter();
					scc.setHealthcareSite(healthcareSite);
					study.addStudyOrganization(scc);
				} else if (OrganizationIdentifierTypeEnum.STUDY_FUNDING_SPONSOR
						.equals(id.getType())) {
					StudyFundingSponsor sfs = new StudyFundingSponsor();
					sfs.setHealthcareSite(healthcareSite);
					study.addStudyOrganization(sfs);
				} else if (OrganizationIdentifierTypeEnum.PROTOCOL_AUTHORITY_IDENTIFIER
						.equals(id.getType())) {
					// nothing to do here, I believe.
				} else if (OrganizationIdentifierTypeEnum.SITE_IDENTIFIER.equals(id
						.getType())) {
					StudySite studySite = new StudySite();
					studySite.setHealthcareSite(healthcareSite);
					study.addStudySite(studySite);
				} else {
					throw exceptionHelper
							.getConversionException(UNSUPPORTED_ORG_ID_TYPE);
				}
			}
		}
	}
	
	/**
	 * Contains.
	 *
	 * @param study the study
	 * @param site the site
	 * @param type the type
	 * @return true, if successful
	 */
	private boolean contains(Study study, HealthcareSite site, OrganizationIdentifierTypeEnum type){
		for(StudyOrganization studyOrganization : study.getStudyOrganizations()){
			if(studyOrganization.getHealthcareSite().equals(site) &&
				((OrganizationIdentifierTypeEnum.COORDINATING_CENTER_IDENTIFIER.equals(type) &&  studyOrganization instanceof StudyCoordinatingCenter) ||
				 (OrganizationIdentifierTypeEnum.STUDY_FUNDING_SPONSOR.equals(type) &&  studyOrganization instanceof StudyFundingSponsor) ||
				 (OrganizationIdentifierTypeEnum.SITE_IDENTIFIER.equals(type) &&  studyOrganization instanceof StudySite)
				)
			){
				return true;
			}
		}
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.duke.cabig.c3pr.webservice.converters.JAXBToDomainObjectConverter
	 * #convert(edu.duke.cabig.c3pr.domain.Study,
	 * edu.duke.cabig.c3pr.webservice.studyutility.Study)
	 */
	public void convert(Study study, StudyProtocolVersion version,
			boolean updateConsents) {
		StudyProtocolDocumentVersion doc = version.getStudyProtocolDocument();

		study.setTargetRegistrationSystem(isNull(version
				.getTargetRegistrationSystem()) ? null : version
				.getTargetRegistrationSystem().getValue());

		convert(study, doc, updateConsents);

	}

	/**
	 * Convert.
	 *
	 * @param study the study
	 * @param ver the ver
	 * @param updateConsents the update consents
	 */
	protected void convert(Study study, StudyProtocolDocumentVersion ver,
			boolean updateConsents) {
		study.setShortTitleText(isNull(ver.getOfficialTitle()) ? "" : ver
				.getOfficialTitle().getValue());
		study.setLongTitleText(isNull(ver.getPublicTitle()) ? "" : ver
				.getPublicTitle().getValue());
		study.setDescriptionText(isNull(ver.getPublicDescription()) ? "" : ver
				.getPublicDescription().getValue());
		study.getStudyVersion().setVersionDate(
				convertToDate(ver.getVersionDate()));
		study.getStudyVersion().setName(
				isNull(ver.getVersionNumberText()) ? "" : ver
						.getVersionNumberText().getValue());

		// consent
		if (updateConsents) {
			study.getConsents().clear();
			for (DocumentVersionRelationship rel : ver
					.getDocumentVersionRelationship()) {
				if (isNull(rel.getTypeCode())
						|| !CONSENT.equals(rel.getTypeCode().getCode())) {
					throw exceptionHelper
							.getConversionException(UNSUPPORTED_DOC_REL_TYPE);
				}
				study.addConsent(convertConsent(rel.getTarget()));
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.duke.cabig.c3pr.webservice.converters.JAXBToDomainObjectConverter
	 * #convertConsent(edu.duke.cabig.c3pr.webservice.common.DocumentVersion)
	 */
	public Consent convertConsent(DocumentVersion doc) {
		if (!(doc instanceof edu.duke.cabig.c3pr.webservice.common.Consent)) {
			throw exceptionHelper
					.getConversionException(INVALID_CONSENT_REPRESENTATION);
		}
		edu.duke.cabig.c3pr.webservice.common.Consent xml = (edu.duke.cabig.c3pr.webservice.common.Consent) doc;
		Consent consent = new Consent();
		consent.setMandatoryIndicator(xml.getMandatoryIndicator().isValue());
		consent.setConsentingMethods(Arrays
				.asList(ConsentingMethod.WRITTEN, ConsentingMethod.VERBAL ));
		consent.setName(isNull(xml.getOfficialTitle()) ? "" : xml
				.getOfficialTitle().getValue());
		consent.setVersionId(isNull(xml.getVersionNumberText()) ? "" : xml
				.getVersionNumberText().getValue());
		consent.setDescriptionText(isNull(xml.getText()) ? "" : xml.getText()
				.getValue());
		// consent questions
		for (DocumentVersionRelationship rel : xml
				.getDocumentVersionRelationship()) {
			if (isNull(rel.getTypeCode())
					|| !CONSENT_QUESTION.equals(rel.getTypeCode().getCode())) {
				throw exceptionHelper
						.getConversionException(UNSUPPORTED_DOC_REL_TYPE);
			}
			consent.addQuestion(convertConsentQuestion(rel.getTarget()));
		}
		return consent;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.duke.cabig.c3pr.webservice.converters.JAXBToDomainObjectConverter
	 * #convertConsentForSearchByExample
	 * (edu.duke.cabig.c3pr.webservice.common.Consent)
	 */
	public Consent convertConsentForSearchByExample(
			edu.duke.cabig.c3pr.webservice.common.Consent xml) {
		Consent consent = new Consent();
		consent.setMandatoryIndicator(xml.getMandatoryIndicator()==null ? null : xml.getMandatoryIndicator().isValue());
		consent.setName(isNull(xml.getOfficialTitle()) ? null : xml
				.getOfficialTitle().getValue());
		consent.setDescriptionText(isNull(xml.getText()) ? null : xml.getText()
				.getValue());
		consent.setVersionId(isNull(xml.getVersionNumberText()) ? null : xml
				.getVersionNumberText().getValue());
		return consent;
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.converters.JAXBToDomainObjectConverter#convertConsentQuestion(edu.duke.cabig.c3pr.webservice.common.DocumentVersion)
	 */
	public ConsentQuestion convertConsentQuestion(DocumentVersion doc) {
		if (doc == null) {
			throw exceptionHelper
					.getConversionException(INVALID_CONSENT_REPRESENTATION);
		}
		ConsentQuestion question = new ConsentQuestion();
		question.setCode(doc.getOfficialTitle().getValue());
		question.setText(doc.getText().getValue());
		return question;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.duke.cabig.c3pr.webservice.converters.JAXBToDomainObjectConverter
	 * #convert
	 * (edu.duke.cabig.c3pr.domain.PermissibleStudySubjectRegistryStatus)
	 */
	public edu.duke.cabig.c3pr.domain.PermissibleStudySubjectRegistryStatus convert(
			PermissibleStudySubjectRegistryStatus xml) {
		edu.duke.cabig.c3pr.domain.PermissibleStudySubjectRegistryStatus status = new edu.duke.cabig.c3pr.domain.PermissibleStudySubjectRegistryStatus();
		status.setRegistryStatus(convert(xml.getRegistryStatus()));
		status.setSecondaryReasons(convertRegistryStatusReasons(xml
				.getSecondaryReason()));
		for(RegistryStatusReason secondaryRegistryReason: status.getSecondaryReasons()){
			if(secondaryRegistryReason.getPrimaryReason() != null){
				secondaryRegistryReason.setPrimaryReason(status.getRegistryStatus().getPrimaryReason
						(secondaryRegistryReason.getPrimaryReason().getCode()));
			}
		}
		return status;
	}
	

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.converters.JAXBToDomainObjectConverter#convert(edu.duke.cabig.c3pr.domain.RegistryStatus)
	 */
	protected RegistryStatus convert(
			edu.duke.cabig.c3pr.webservice.common.RegistryStatus xml) {
		RegistryStatus rs = null;
		if (xml != null && !isNull(xml.getCode())) {
			rs = registryStatusDao.getRegistryStatusByCode(xml.getCode()
					.getCode());
			if (rs == null) {
				throw exceptionHelper
						.getConversionException(INVALID_REGISTRY_STATUS_CODE);
			}
		}
		return rs;
	}

	/**
	 * Convert registry status reasons.
	 *
	 * @param xmlList the xml list
	 * @return the list
	 */
	protected List<RegistryStatusReason> convertRegistryStatusReasons(
			List<edu.duke.cabig.c3pr.webservice.common.RegistryStatusReason> xmlList) {
		List<RegistryStatusReason> list = new ArrayList<RegistryStatusReason>();
		for (edu.duke.cabig.c3pr.webservice.common.RegistryStatusReason xmlReason : xmlList) {
		if (xmlReason != null)
			list.add(convert(xmlReason));
		}
		return list;
	}

	/**
	 * Convert.
	 *
	 * @param xmlReason the xml reason
	 * @return the registry status reason
	 */
	protected RegistryStatusReason convert(
			edu.duke.cabig.c3pr.webservice.common.RegistryStatusReason xmlReason) {
		RegistryStatusReason reason = new RegistryStatusReason();
		reason.setDescription(xmlReason.getDescription().getValue());
		reason.setCode(xmlReason.getCode().getCode());
		reason.setPrimaryIndicator(xmlReason.getPrimaryIndicator().isValue());
		if(xmlReason.getPrimaryReason()!=null){
			reason.setPrimaryReason(convert((edu.duke.cabig.c3pr.webservice.common.RegistryStatusReason)xmlReason.getPrimaryReason()));
		}
		return reason;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.duke.cabig.c3pr.webservice.converters.JAXBToDomainObjectConverter
	 * #convert(edu.duke.cabig.c3pr.webservice.studyutility.Study)
	 */
	public StudyProtocolVersion convert(Study study) {
		StudyProtocolVersion xmlStudy = new StudyProtocolVersion();
		if (study.getTargetRegistrationSystem() != null) {
			xmlStudy.setTargetRegistrationSystem(ST(study
					.getTargetRegistrationSystem()));
		}
		xmlStudy.setStudyProtocolDocument(convertStudyProtocolDocument(study));
		xmlStudy.getPermissibleStudySubjectRegistryStatus().addAll(
				convertPermissibleStudySubjectRegistryStatus(study));
		return xmlStudy;
	}

	/**
	 * Convert study protocol document.
	 *
	 * @param study the study
	 * @return the study protocol document version
	 */
	private StudyProtocolDocumentVersion convertStudyProtocolDocument(
			Study study) {
		StudyProtocolDocumentVersion doc = new StudyProtocolDocumentVersion();
		doc.setOfficialTitle(ST(study.getShortTitleText()));
		doc.setPublicDescription(ST(study.getDescriptionText()));
		doc.setPublicTitle(ST(study.getLongTitleText()));
		doc.setText(ED(study.getDescriptionText()));
		doc.setVersionDate(convertToTsDateTime(study.getVersionDate()));
		doc.setVersionNumberText(ST(study.getVersionName()));
		doc.setDocument(convertStudyDocument(study));
		doc.getDocumentVersionRelationship().addAll(
				convertConsentRelationships(study));
		return doc;
	}

	/**
	 * Convert consent relationships.
	 *
	 * @param study the study
	 * @return the collection
	 */
	private Collection<DocumentVersionRelationship> convertConsentRelationships(
			Study study) {
		Collection<DocumentVersionRelationship> list = new ArrayList<DocumentVersionRelationship>();
		for (Consent c : study.getConsents()) {
			list.add(convertConsentRelationship(c));
		}
		return list;
	}

	/**
	 * Convert consent relationship.
	 *
	 * @param c the c
	 * @return the document version relationship
	 */
	private DocumentVersionRelationship convertConsentRelationship(Consent c) {
		DocumentVersionRelationship rel = new DocumentVersionRelationship();
		rel.setTypeCode(CD(CONSENT));
		rel.setTarget(convertConsent(c));
		return rel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.duke.cabig.c3pr.webservice.converters.JAXBToDomainObjectConverter
	 * #convertConsent(edu.duke.cabig.c3pr.domain.Consent)
	 */
	public edu.duke.cabig.c3pr.webservice.common.Consent convertConsent(
			Consent c) {
		edu.duke.cabig.c3pr.webservice.common.Consent consent = new edu.duke.cabig.c3pr.webservice.common.Consent();
		consent.setMandatoryIndicator(BL(c.getMandatoryIndicator()));
		consent.setOfficialTitle(ST(c.getName()));
		consent.setText(ED(c.getDescriptionText()));
		consent.setVersionNumberText(ST(c.getVersionId()));
		consent.setDocument(new Document());
		for (ConsentQuestion q : c.getQuestions()) {
			consent.getDocumentVersionRelationship().add(
					convertConsentQuestionRelationship(q));
		}
		if(c.getRetiredIndicator().equals("true")){
			consent.getVersionNumberText().setNullFlavor(NullFlavor.INV);
		}
		return consent;
	}

	/**
	 * Convert consent question relationship.
	 *
	 * @param q the q
	 * @return the document version relationship
	 */
	protected DocumentVersionRelationship convertConsentQuestionRelationship(
			ConsentQuestion q) {
		DocumentVersionRelationship rel = new DocumentVersionRelationship();
		rel.setTypeCode(CD(CONSENT_QUESTION));
		rel.setTarget(convertConsentQuestion(q));
		return rel;

	}

	/**
	 * Convert consent question.
	 *
	 * @param cq the cq
	 * @return the document version
	 */
	protected DocumentVersion convertConsentQuestion(ConsentQuestion cq) {
		DocumentVersion q = new DocumentVersion();
		q.setOfficialTitle(ST(cq.getCode()));
		q.setText(ED(cq.getText()));
		q.setDocument(new Document());
		if(cq.getRetiredIndicator().equals("true")){
			// set the version number and the null flavor only when the consent question is retired
			q.setVersionNumberText(cq.getVersion() != null ?
					ST(cq.getVersion().toString()) : null);
			q.getVersionNumberText().setNullFlavor(NullFlavor.INV);
		}
		return q;
	}

	/**
	 * Convert study document.
	 *
	 * @param study the study
	 * @return the document
	 */
	protected Document convertStudyDocument(Study study) {
		Document doc = new Document();
		for (Identifier id : study
				.getIdentifiers()) {
			doc.getDocumentIdentifier().add(convert(id));
		}
		return doc;
	}

	/**
	 * Convert permissible study subject registry status.
	 *
	 * @param study the study
	 * @return the collection
	 */
	protected Collection<PermissibleStudySubjectRegistryStatus> convertPermissibleStudySubjectRegistryStatus(
			Study study) {
		Collection<PermissibleStudySubjectRegistryStatus> list = new ArrayList<PermissibleStudySubjectRegistryStatus>();
		for (edu.duke.cabig.c3pr.domain.PermissibleStudySubjectRegistryStatus status : study
				.getPermissibleStudySubjectRegistryStatuses()) {
			PermissibleStudySubjectRegistryStatus stat = convert(status);
			list.add(stat);
		}
		return list;
	}

	/**
	 * Convert.
	 *
	 * @param status the status
	 * @return the permissible study subject registry status
	 */
	public PermissibleStudySubjectRegistryStatus convert(
			edu.duke.cabig.c3pr.domain.PermissibleStudySubjectRegistryStatus status) {
		PermissibleStudySubjectRegistryStatus stat = new PermissibleStudySubjectRegistryStatus();
		stat.setRegistryStatus(convert(status.getRegistryStatus()));
		for(RegistryStatusReason secondaryRegistryReason : status.getSecondaryReasons()){
			stat.getSecondaryReason().add(convertSecondaryDomainRegistryStatusReason(secondaryRegistryReason,secondaryRegistryReason.getPrimaryReason()));
		}
	
		return stat;
	}
	
	/**
	 * Convert secondary domain registry status reason.
	 *
	 * @param secondaryReason the secondary reason
	 * @param primaryReason the primary reason
	 * @return the edu.duke.cabig.c3pr.webservice.common. registry status reason
	 */
	protected edu.duke.cabig.c3pr.webservice.common.RegistryStatusReason convertSecondaryDomainRegistryStatusReason(
			RegistryStatusReason secondaryReason, Reason primaryReason) {
		edu.duke.cabig.c3pr.webservice.common.RegistryStatusReason convertedSecondaryReason = new edu.duke.cabig.c3pr.webservice.common.RegistryStatusReason();
			convertedSecondaryReason = (convert(secondaryReason));
			if (primaryReason != null)
			convertedSecondaryReason.setPrimaryReason(convert(primaryReason));
		return convertedSecondaryReason;
	}

	/**
	 * Convert domain registry status reasons.
	 *
	 * @param reasons the reasons
	 * @return the collection
	 */
	protected Collection<edu.duke.cabig.c3pr.webservice.common.RegistryStatusReason> convertDomainRegistryStatusReasons(
			List<RegistryStatusReason> reasons) {
		Collection<edu.duke.cabig.c3pr.webservice.common.RegistryStatusReason> list = new ArrayList<edu.duke.cabig.c3pr.webservice.common.RegistryStatusReason>();
		for (RegistryStatusReason domainObj : reasons) {
		if (domainObj != null)
			list.add(convert(domainObj));
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.duke.cabig.c3pr.webservice.converters.JAXBToDomainObjectConverter
	 * #convertRegistryStatus(RegistryStatus)
	 */
	public edu.duke.cabig.c3pr.webservice.common.RegistryStatus convert(
			RegistryStatus domainObj) {
		edu.duke.cabig.c3pr.webservice.common.RegistryStatus status = null;
		if (domainObj != null) {
			status = new edu.duke.cabig.c3pr.webservice.common.RegistryStatus();
			status.setCode(CD(domainObj.getCode()));
			status.setDescription(ST(domainObj.getDescription()));
			for (RegistryStatusReason reason : domainObj.getPrimaryReasons()) {
			if (reason != null)
				status.getPrimaryReason().add(convert(reason));
			}
		}
		return status;
	}

	/**
	 * Convert.
	 *
	 * @param reason the reason
	 * @return the edu.duke.cabig.c3pr.webservice.common. registry status reason
	 */
	protected edu.duke.cabig.c3pr.webservice.common.RegistryStatusReason convert(Reason reason) {
		edu.duke.cabig.c3pr.webservice.common.RegistryStatusReason xml = new edu.duke.cabig.c3pr.webservice.common.RegistryStatusReason();
		if (reason == null)
			return xml;
		xml.setCode(CD(reason.getCode()));
		xml.setDescription(ST(reason.getDescription()));
		xml.setPrimaryIndicator(BL(reason.getPrimaryIndicator()));
		// primary cannot have primary reason
		/*if (reason.getPrimaryReason() != null) {
			xml.setPrimaryReason(convert(reason.getPrimaryReason()));
		}*/
		return xml;
	}
	
	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.converters.JAXBToDomainObjectConverter#convert(edu.duke.cabig.c3pr.domain.Identifier)
	 */
	public DocumentIdentifier convert(Identifier id) {
		DocumentIdentifier studyId = new DocumentIdentifier();
		studyId.setTypeCode(CD(id.getTypeInternal()));
		studyId.setIdentifier(II(id.getValue()));
		studyId.setPrimaryIndicator(BL(id.getPrimaryIndicator()));
		
		if(id instanceof OrganizationAssignedIdentifier){
			HealthcareSite site = ((OrganizationAssignedIdentifier) id)
					.getHealthcareSite();
			final edu.duke.cabig.c3pr.webservice.common.Organization org = new edu.duke.cabig.c3pr.webservice.common.Organization();
			studyId.setAssigningOrganization(org);
			for (Identifier siteId : site.getIdentifiersAssignedToOrganization()) {
				OrganizationIdentifier orgId = new OrganizationIdentifier();
				orgId.setTypeCode(CD(siteId.getTypeInternal()));
				orgId.setIdentifier(II(siteId.getValue()));
				orgId.setPrimaryIndicator(BL(siteId.getPrimaryIndicator()));
				org.getOrganizationIdentifier().add(orgId);
			}
		}else if (id instanceof SystemAssignedIdentifier){
			studyId.getTypeCode().setCodeSystemName(((SystemAssignedIdentifier)id).getSystemName());
		}
		return studyId;

	}

	/**
	 * Convert to document identifier.
	 *
	 * @param identifiers the identifiers
	 * @return the list
	 */
	protected List<DocumentIdentifier> convertToDocumentIdentifier(List<Identifier> identifiers) {
		List<DocumentIdentifier> result = new ArrayList<DocumentIdentifier>();
		for (Identifier source : identifiers) {
				result.add(convert(source));
		}
		return result;
	}
	
	/**
	 * Gets the registry status dao.
	 *
	 * @return the registryStatusDao
	 */
	public RegistryStatusDao getRegistryStatusDao() {
		return registryStatusDao;
	}

	/**
	 * Sets the registry status dao.
	 *
	 * @param registryStatusDao the registryStatusDao to set
	 */
	public void setRegistryStatusDao(RegistryStatusDao registryStatusDao) {
		this.registryStatusDao = registryStatusDao;
	}

}
