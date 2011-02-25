package edu.duke.cabig.c3pr.webservice.converters;

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
import static edu.duke.cabig.c3pr.webservice.helpers.ISO21090Helper.*;

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

	public static final String CONSENT_QUESTION = "CONSENT_QUESTION";
	public static final String CONSENT = "CONSENT";
	public static final String STUDY_VERSION_NAME = "Original Version";
	public static final String STUDY_TYPE = "Basic Science";
	public static final int STUDY_TARGET_ACCRUAL = 100;
	public static final String STUDY_PHASE = "Phase 0 Trial";
	public static final String SEMICOLON = ":";
	public static final String X_TEXT_FAX = "x-text-fax";
	public static final String TEL = "tel";
	public static final String MAILTO = "mailto";
	public static final String OTHER = "user-defined";
	public static final String NAME_SEP = " ";
	public static final String FAM = "FAM";
	public static final String GIV = "GIV";
	public static final String PFX = "PFX";
	public static final String SFX = "SFX";
	public static final String CTEP = "CTEP";
	public static final String NCI = "NCI";

	public static final String TS_DATETIME_PATTERN = "yyyyMMddHHmmss";

	public static final int NO_SUBJECT_DATA_PROVIDED_CODE = 900;
	public static final int INVALID_SUBJECT_DATA_REPRESENTATION = 901;
	public static final int MISSING_SUBJECT_IDENTIFIER = 902;
	public static final int MISSING_PRIMARY_SUBJECT_IDENTIFIER = 931;
	public static final int SUBJECT_IDENTIFIER_HAS_TO_CONTAIN_EXACTLY_ONE_OF_ORGANIZATION_OR_SYSTEMNAME= 903;
	public static final int ORGANIZATION_IDENTIFIER_MISSING_TYPECODE = 904;
	public static final int UNABLE_TO_FIND_ORGANIZATION = 905;
	public static final int WRONG_DATE_FORMAT = 906;
	public static final int WRONG_RACE_CODE = 907;
	public static final int INVALID_SUBJECT_STATE_CODE = 908;
	public static final int MISSING_ELEMENT = 909;
	public static final int INVALID_STUDY_IDENTIFIER = 925;
	public static final int UNSUPPORTED_ORG_ID_TYPE = 926;
	public static final int INVALID_STUDY_REPRESENTATION = 927;
	public static final int UNSUPPORTED_DOC_REL_TYPE = 928;
	public static final int INVALID_CONSENT_REPRESENTATION = 929;
	public static final int INVALID_REGISTRY_STATUS_CODE = 930;

	protected static final ISO21090Helper iso = new ISO21090Helper();

	/** The exception helper. */
	protected C3PRExceptionHelper exceptionHelper;

	protected HealthcareSiteDao healthcareSiteDao;

	protected RegistryStatusDao registryStatusDao;

	private static Log log = LogFactory
			.getLog(JAXBToDomainObjectConverterImpl.class);

	public HealthcareSiteDao getHealthcareSiteDao() {
		return healthcareSiteDao;
	}

	public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
		this.healthcareSiteDao = healthcareSiteDao;
	}

	public C3PRExceptionHelper getExceptionHelper() {
		return exceptionHelper;
	}

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

	protected boolean isNull(ANY cd) {
		return cd == null || cd.getNullFlavor() != null;
	}

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

	protected void updateContactMechanism(PersonBase personBase, Person person){
		personBase.getContactMechanisms().clear();
		setContactMechanism(personBase, person, MAILTO);
		setContactMechanism(personBase, person, TEL);
		setContactMechanism(personBase, person, X_TEXT_FAX);
		setContactMechanism(personBase, person, OTHER);
	}
	
	/**
	 * @param person
	 * @return
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

	protected String getMiddleName(Person person) {
		String name = "";
		List<String> list = extractNameParts(person, EntityNamePartType.GIV);
		if (CollectionUtils.isNotEmpty(list) && list.size() > 1) {
			name = list.get(1);
		}
		return name;
	}

	protected String getLastName(Person person) {
		String name = "";
		List<String> list = extractNameParts(person, EntityNamePartType.FAM);
		if (CollectionUtils.isNotEmpty(list)) {
			name = StringUtils.join(list, NAME_SEP);
		}
		return name;
	}

	protected String getFirstName(Person person) {
		String name = "";
		List<String> list = extractNameParts(person, EntityNamePartType.GIV);
		if (CollectionUtils.isNotEmpty(list)) {
			name = list.get(0);
		}
		return name;
	}
	
	protected String getNamePrefix(Person person) {
		String name = "";
		List<String> list = extractNameParts(person, EntityNamePartType.PFX);
		if (CollectionUtils.isNotEmpty(list)) {
			name = list.get(0);
		}
		return name;
	}
	
	protected String getNameSuffix(Person person) {
		String name = "";
		List<String> list = extractNameParts(person, EntityNamePartType.SFX);
		if (CollectionUtils.isNotEmpty(list)) {
			name = list.get(0);
		}
		return name;
	}

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

	protected String getEthnicGroupCode(Person person) {
		DSETCD codes = person.getEthnicGroupCode();
		return getFirstCode(codes);
	}

	/**
	 * @param codes
	 * @return
	 */
	protected String getFirstCode(DSETCD codes) {
		String code = null;
		if (codes != null && CollectionUtils.isNotEmpty(codes.getItem())) {
			code = codes.getItem().get(0).getCode();
		}
		return code;
	}

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

	protected void processIdentifiers(List<BiologicEntityIdentifier> identifiers,
			Participant participant) {
		for (BiologicEntityIdentifier bioId : identifiers) {
			Identifier id = convert(bioId);
			participant.addIdentifier(id);
		}
	}

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
			.getConversionException(SUBJECT_IDENTIFIER_HAS_TO_CONTAIN_EXACTLY_ONE_OF_ORGANIZATION_OR_SYSTEMNAME);
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

	protected HealthcareSite resolveHealthcareSite(Organization org) {
		List<OrganizationIdentifier> idList = org.getOrganizationIdentifier();
		if (CollectionUtils.isEmpty(idList)) {
			throw exceptionHelper
					.getConversionException(SUBJECT_IDENTIFIER_HAS_TO_CONTAIN_EXACTLY_ONE_OF_ORGANIZATION_OR_SYSTEMNAME);
		}
		OrganizationIdentifier orgId = idList.get(0);
		II id = orgId.getIdentifier();
		BL isPrimary = orgId.getPrimaryIndicator();
		CD typeCode = orgId.getTypeCode();
		return resolveHealthcareSite(id, isPrimary, typeCode);
	}

	/**
	 * @param id
	 * @param isPrimary
	 * @param typeCode
	 * @throws ConversionException
	 */
	protected HealthcareSite resolveHealthcareSite(II id, BL isPrimary,
			CD typeCode) throws ConversionException {
		if (id == null || StringUtils.isBlank(id.getExtension())) {
			throw exceptionHelper
					.getConversionException(SUBJECT_IDENTIFIER_HAS_TO_CONTAIN_EXACTLY_ONE_OF_ORGANIZATION_OR_SYSTEMNAME);
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

	protected DSETCD getRaceCodes(Participant p) {
		DSETCD dsetcd = new DSETCD();
		for (RaceCodeEnum raceCode : p.getRaceCodes()) {
			dsetcd.getItem().add(CD(raceCode.getCode()));
		}
		return dsetcd;
	}

	protected List<TelecommunicationAddressUse> getTelecomAddressUse(List<ContactMechanismUse> uses) {
		List<TelecommunicationAddressUse> telUses = new ArrayList<TelecommunicationAddressUse>();
		for(ContactMechanismUse use: uses){
			telUses.add(TelecommunicationAddressUse.valueOf(use.name()));
		}
		return telUses;
	}
	
	/**
	 * @param p
	 * @return
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

	protected DSETCD getEthnicGroupCode(Participant p) {
		DSETCD dsetcd = new DSETCD();
		if (StringUtils.isNotBlank(p.getEthnicGroupCode())) {
			dsetcd.getItem().add(CD(p.getEthnicGroupCode()));
		} else {
			dsetcd.setNullFlavor(NullFlavor.NI);
		}
		return dsetcd;
	}

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

	protected String convertAndErrorIfBlank(ST st, String elementName) {
		if (isNull(st) || StringUtils.isBlank(st.getValue())) {
			throw exceptionHelper.getConversionException(MISSING_ELEMENT,
					new Object[] { elementName });
		} else {
			return st.getValue();
		}
	}

	protected String convertAndErrorIfBlank(CD st, String elementName) {
		if (isNull(st) || StringUtils.isBlank(st.getCode())) {
			throw exceptionHelper.getConversionException(MISSING_ELEMENT,
					new Object[] { elementName });
		} else {
			return st.getCode();
		}
	}

	public List<Identifier> convert(
			List<DocumentIdentifier> docIds) {
		List<OrganizationAssignedIdentifier> list = new ArrayList<OrganizationAssignedIdentifier>();
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
	public OrganizationAssignedIdentifier convert(DocumentIdentifier docId) {
		II ii = docId.getIdentifier();
		CD typeCode = docId.getTypeCode();
		BL primInd = docId.getPrimaryIndicator();
		edu.duke.cabig.c3pr.webservice.common.Organization org = docId
				.getAssigningOrganization();
		if (isNull(ii) || isNull(typeCode) || org == null
				|| CollectionUtils.isEmpty(org.getOrganizationIdentifier())) {
			throw exceptionHelper
					.getConversionException(INVALID_STUDY_IDENTIFIER);
		}
		OrganizationIdentifier orgId = org.getOrganizationIdentifier().get(0);
		try {
			OrganizationIdentifierTypeEnum.valueOf(typeCode.getCode());
		} catch (IllegalArgumentException e) {
			throw exceptionHelper
					.getConversionException(UNSUPPORTED_ORG_ID_TYPE);
		}
		OrganizationAssignedIdentifier id = new OrganizationAssignedIdentifier();
		id.setPrimaryIndicator(primInd != null
				&& Boolean.TRUE.equals(primInd.isValue()));
		id.setValue(ii.getExtension());
		id.setType(OrganizationIdentifierTypeEnum.valueOf(typeCode.getCode()));
		HealthcareSite healthcareSite = resolveHealthcareSite(
				orgId.getIdentifier(), orgId.getPrimaryIndicator(),
				orgId.getTypeCode());
		if (healthcareSite == null) {
			throw exceptionHelper
					.getConversionException(UNABLE_TO_FIND_ORGANIZATION);
		}
		id.setHealthcareSite(healthcareSite);
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
	 * @param study
	 * @param ver
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

	protected ConsentQuestion convertConsentQuestion(DocumentVersion doc) {
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
		return status;
	}

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

	protected List<RegistryStatusReason> convertRegistryStatusReasons(
			List<edu.duke.cabig.c3pr.webservice.common.RegistryStatusReason> xmlList) {
		List<RegistryStatusReason> list = new ArrayList<RegistryStatusReason>();
		for (edu.duke.cabig.c3pr.webservice.common.RegistryStatusReason xmlReason : xmlList) {
			list.add(convert(xmlReason));
		}
		return list;
	}

	protected RegistryStatusReason convert(
			edu.duke.cabig.c3pr.webservice.common.RegistryStatusReason xmlReason) {
		RegistryStatusReason reason = new RegistryStatusReason();
		reason.setDescription(xmlReason.getDescription().getValue());
		reason.setCode(xmlReason.getCode().getCode());
		reason.setPrimaryIndicator(xmlReason.getPrimaryIndicator().isValue());
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

	private Collection<DocumentVersionRelationship> convertConsentRelationships(
			Study study) {
		Collection<DocumentVersionRelationship> list = new ArrayList<DocumentVersionRelationship>();
		for (Consent c : study.getConsents()) {
			list.add(convertConsentRelationship(c));
		}
		return list;
	}

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

	protected DocumentVersionRelationship convertConsentQuestionRelationship(
			ConsentQuestion q) {
		DocumentVersionRelationship rel = new DocumentVersionRelationship();
		rel.setTypeCode(CD(CONSENT_QUESTION));
		rel.setTarget(convertConsentQuestion(q));
		return rel;

	}

	protected DocumentVersion convertConsentQuestion(ConsentQuestion cq) {
		DocumentVersion q = new DocumentVersion();
		q.setOfficialTitle(ST(cq.getCode()));
		q.setText(ED(cq.getText()));
		// q.setVersionNumberText(cq.getVersion() != null ?
		// ST(cq.getVersion()
		// .toString()) : null);
		q.setDocument(new Document());
		return q;
	}

	protected Document convertStudyDocument(Study study) {
		Document doc = new Document();
		for (OrganizationAssignedIdentifier oai : study
				.getOrganizationAssignedIdentifiers()) {
			doc.getDocumentIdentifier().add(convert(oai));
		}
		return doc;
	}

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
	 * @param status
	 * @return
	 */
	public PermissibleStudySubjectRegistryStatus convert(
			edu.duke.cabig.c3pr.domain.PermissibleStudySubjectRegistryStatus status) {
		PermissibleStudySubjectRegistryStatus stat = new PermissibleStudySubjectRegistryStatus();
		stat.setRegistryStatus(convert(status.getRegistryStatus()));
		stat.getSecondaryReason()
				.addAll(convertDomainRegistryStatusReasons(status
						.getSecondaryReasons()));
		return stat;
	}

	protected Collection<edu.duke.cabig.c3pr.webservice.common.RegistryStatusReason> convertDomainRegistryStatusReasons(
			List<RegistryStatusReason> reasons) {
		Collection<edu.duke.cabig.c3pr.webservice.common.RegistryStatusReason> list = new ArrayList<edu.duke.cabig.c3pr.webservice.common.RegistryStatusReason>();
		for (RegistryStatusReason domainObj : reasons) {
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
				status.getPrimaryReason().add(convert(reason));
			}
		}
		return status;
	}

	protected edu.duke.cabig.c3pr.webservice.common.RegistryStatusReason convert(
			Reason reason) {
		edu.duke.cabig.c3pr.webservice.common.RegistryStatusReason xml = new edu.duke.cabig.c3pr.webservice.common.RegistryStatusReason();
		xml.setCode(CD(reason.getCode()));
		xml.setDescription(ST(reason.getDescription()));
		xml.setPrimaryIndicator(BL(reason.getPrimaryIndicator()));
		if (reason.getPrimaryReason() != null) {
			xml.setPrimaryReason(convert(reason.getPrimaryReason()));
		}
		return xml;
	}

	public DocumentIdentifier convert(OrganizationAssignedIdentifier id) {
		DocumentIdentifier studyId = new DocumentIdentifier();
		studyId.setTypeCode(CD(id.getTypeInternal()));
		studyId.setIdentifier(II(id.getValue()));
		studyId.setPrimaryIndicator(BL(id.getPrimaryIndicator()));

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
		return studyId;

	}

	protected List<DocumentIdentifier> convertToDocumentIdentifier(List<Identifier> identifiers) {
		List<DocumentIdentifier> result = new ArrayList<DocumentIdentifier>();
		for (Identifier source : identifiers) {
			if (source instanceof OrganizationAssignedIdentifier) {
				result.add(convert((OrganizationAssignedIdentifier)source));
			}
		}
		return result;
	}
	
	/**
	 * @return the registryStatusDao
	 */
	public RegistryStatusDao getRegistryStatusDao() {
		return registryStatusDao;
	}

	/**
	 * @param registryStatusDao
	 *            the registryStatusDao to set
	 */
	public void setRegistryStatusDao(RegistryStatusDao registryStatusDao) {
		this.registryStatusDao = registryStatusDao;
	}

}
