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
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.semanticbits.querybuilder.AdvancedSearchCriteriaParameter;
import com.semanticbits.querybuilder.AdvancedSearchHelper;

import edu.duke.cabig.c3pr.constants.ConsentRequired;
import edu.duke.cabig.c3pr.constants.ConsentingMethod;
import edu.duke.cabig.c3pr.constants.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;
import edu.duke.cabig.c3pr.constants.ParticipantStateCode;
import edu.duke.cabig.c3pr.constants.RaceCodeEnum;
import edu.duke.cabig.c3pr.constants.StudyDataEntryStatus;
import edu.duke.cabig.c3pr.dao.ConsentDao;
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.RegistryStatusDao;
import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.Consent;
import edu.duke.cabig.c3pr.domain.ConsentQuestion;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.LocalStudy;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.RegistryStatus;
import edu.duke.cabig.c3pr.domain.RegistryStatusReason;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyCoordinatingCenter;
import edu.duke.cabig.c3pr.domain.StudyFundingSponsor;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.exception.ConversionException;
import edu.duke.cabig.c3pr.webservice.common.AdvanceSearchCriterionParameter;
import edu.duke.cabig.c3pr.webservice.common.Document;
import edu.duke.cabig.c3pr.webservice.common.DocumentIdentifier;
import edu.duke.cabig.c3pr.webservice.common.DocumentVersion;
import edu.duke.cabig.c3pr.webservice.common.DocumentVersionRelationship;
import edu.duke.cabig.c3pr.webservice.common.OrganizationIdentifier;
import edu.duke.cabig.c3pr.webservice.common.PermissibleStudySubjectRegistryStatus;
import edu.duke.cabig.c3pr.webservice.common.StudyProtocolDocumentVersion;
import edu.duke.cabig.c3pr.webservice.common.StudyProtocolVersion;
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
import edu.duke.cabig.c3pr.webservice.iso21090.ED;
import edu.duke.cabig.c3pr.webservice.iso21090.ENPN;
import edu.duke.cabig.c3pr.webservice.iso21090.ENXP;
import edu.duke.cabig.c3pr.webservice.iso21090.EntityNamePartType;
import edu.duke.cabig.c3pr.webservice.iso21090.II;
import edu.duke.cabig.c3pr.webservice.iso21090.IVLTSDateTime;
import edu.duke.cabig.c3pr.webservice.iso21090.NullFlavor;
import edu.duke.cabig.c3pr.webservice.iso21090.ST;
import edu.duke.cabig.c3pr.webservice.iso21090.TEL;
import edu.duke.cabig.c3pr.webservice.iso21090.TSDateTime;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.BiologicEntityIdentifier;
import edu.duke.cabig.c3pr.webservice.common.Organization;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.Person;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.Subject;

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
	private static final String STUDY_VERSION_NAME = "Original Version";
	private static final String STUDY_TYPE = "Basic Science";
	private static final int STUDY_TARGET_ACCRUAL = 100;
	public static final String STUDY_PHASE = "Phase 0 Trial";
	private static final String SEMICOLON = ":";
	static final String X_TEXT_FAX = "x-text-fax";
	static final String TEL = "tel";
	static final String MAILTO = "mailto";
	private static final String NAME_SEP = " ";
	public static final String FAM = "FAM";
	public static final String GIV = "GIV";
	private static final String CTEP = "CTEP";
	private static final String NCI = "NCI";

	private static final String TS_DATETIME_PATTERN = "yyyyMMddHHmmss";

	public static final int NO_SUBJECT_DATA_PROVIDED_CODE = 900;
	static final int INVALID_SUBJECT_DATA_REPRESENTATION = 901;
	static final int MISSING_SUBJECT_IDENTIFIER = 902;
	static final int SUBJECT_IDENTIFIER_MISSING_ORGANIZATION = 903;
	private static final int ORGANIZATION_IDENTIFIER_MISSING_TYPECODE = 904;
	private static final int UNABLE_TO_FIND_ORGANIZATION = 905;
	private static final int WRONG_DATE_FORMAT = 906;
	private static final int WRONG_RACE_CODE = 907;
	private static final int INVALID_SUBJECT_STATE_CODE = 908;
	private static final int MISSING_ELEMENT = 909;
	private static final int INVALID_STUDY_IDENTIFIER = 925;
	private static final int UNSUPPORTED_ORG_ID_TYPE = 926;
	private static final int INVALID_STUDY_REPRESENTATION = 927;
	private static final int UNSUPPORTED_DOC_REL_TYPE = 928;
	private static final int INVALID_CONSENT_REPRESENTATION = 929;
	private static final int INVALID_REGISTRY_STATUS_CODE = 930;

	/** The exception helper. */
	protected C3PRExceptionHelper exceptionHelper;

	private HealthcareSiteDao healthcareSiteDao;

	private RegistryStatusDao registryStatusDao;

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
				participant.setMaidenName(skipEmptyNameParts ? null
						: StringUtils.EMPTY);
				participant.replaceAddresses(getAddresses(person));
				participant.setRaceCodes(getRaceCodes(person));
				participant.setEmail(getTelecomAddress(person, MAILTO));
				participant.setPhone(getTelecomAddress(person, TEL));
				participant.setFax(getTelecomAddress(person, X_TEXT_FAX));
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

	private boolean isNull(ANY cd) {
		return cd == null || cd.getNullFlavor() != null;
	}

	String getTelecomAddress(Person person, String type) {
		type = type.toLowerCase();
		String addr = null;
		BAGTEL bagtel = person.getTelecomAddress();
		if (!isNull(bagtel) && bagtel.getItem() != null) {
			for (TEL tel : bagtel.getItem()) {
				if (tel.getValue() != null
						&& tel.getValue().toLowerCase().startsWith(type)) {
					addr = tel.getValue().toLowerCase()
							.replaceFirst("^" + type + SEMICOLON, "");
				}
			}
		}
		return addr;
	}

	/**
	 * @param person
	 * @return
	 */
	List<RaceCodeEnum> getRaceCodes(Person person) {
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

	private Set<Address> getAddresses(Person person) {
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
					set.add(address);
				}
			}
		}
		return set;
	}

	String getMiddleName(Person person) {
		String name = "";
		List<String> list = extractNameParts(person, EntityNamePartType.GIV);
		if (CollectionUtils.isNotEmpty(list) && list.size() > 1) {
			name = list.get(1);
		}
		return name;
	}

	String getLastName(Person person) {
		String name = "";
		List<String> list = extractNameParts(person, EntityNamePartType.FAM);
		if (CollectionUtils.isNotEmpty(list)) {
			name = StringUtils.join(list, NAME_SEP);
		}
		return name;
	}

	String getFirstName(Person person) {
		String name = "";
		List<String> list = extractNameParts(person, EntityNamePartType.GIV);
		if (CollectionUtils.isNotEmpty(list)) {
			name = list.get(0);
		}
		return name;
	}

	private List<String> extractNameParts(Person person, EntityNamePartType type) {
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

	String getEthnicGroupCode(Person person) {
		DSETCD codes = person.getEthnicGroupCode();
		return getFirstCode(codes);
	}

	/**
	 * @param codes
	 * @return
	 */
	private String getFirstCode(DSETCD codes) {
		String code = null;
		if (codes != null && CollectionUtils.isNotEmpty(codes.getItem())) {
			code = codes.getItem().get(0).getCode();
		}
		return code;
	}

	Date convertToDate(TSDateTime tsDateTime) {
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

	private void processIdentifiers(List<BiologicEntityIdentifier> identifiers,
			Participant participant) {
		for (BiologicEntityIdentifier bioId : identifiers) {
			OrganizationAssignedIdentifier id = convert(bioId);
			participant.addIdentifier(id);
		}
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
	public OrganizationAssignedIdentifier convert(BiologicEntityIdentifier bioId)
			throws ConversionException {
		final II ii = bioId.getIdentifier();
		final CD typeCode = bioId.getTypeCode();
		Organization org = bioId.getAssigningOrganization();
		if (ii == null || typeCode == null) {
			throw exceptionHelper
					.getConversionException(MISSING_SUBJECT_IDENTIFIER);
		}
		if (org == null) {
			throw exceptionHelper
					.getConversionException(SUBJECT_IDENTIFIER_MISSING_ORGANIZATION);
		}
		OrganizationAssignedIdentifier id = new OrganizationAssignedIdentifier();
		id.setPrimaryIndicator(true);
		id.setValue(ii.getExtension());
		id.setTypeInternal(typeCode.getCode());
		HealthcareSite healthcareSite = resolveHealthcareSite(org);
		id.setHealthcareSite(healthcareSite);
		return id;
	}

	HealthcareSite resolveHealthcareSite(Organization org) {
		List<OrganizationIdentifier> idList = org.getOrganizationIdentifier();
		if (CollectionUtils.isEmpty(idList)) {
			throw exceptionHelper
					.getConversionException(SUBJECT_IDENTIFIER_MISSING_ORGANIZATION);
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
	private HealthcareSite resolveHealthcareSite(II id, BL isPrimary,
			CD typeCode) throws ConversionException {
		if (id == null || StringUtils.isBlank(id.getExtension())) {
			throw exceptionHelper
					.getConversionException(SUBJECT_IDENTIFIER_MISSING_ORGANIZATION);
		}
		if (isPrimary != null && isPrimary.isValue()) {
			return healthcareSiteDao.getByPrimaryIdentifier(id.getExtension());
		}
		if (typeCode == null || StringUtils.isBlank(typeCode.getCode())) {
			throw exceptionHelper
					.getConversionException(ORGANIZATION_IDENTIFIER_MISSING_TYPECODE);
		}
		if (typeCode.getCode().contains(CTEP)) {
			return healthcareSiteDao.getByCtepCodeFromLocal(id.getExtension());
		} else if (typeCode.getCode().contains(NCI)) {
			return healthcareSiteDao.getByNciCodeFromLocal(id.getExtension());
		}
		throw exceptionHelper
				.getConversionException(UNABLE_TO_FIND_ORGANIZATION);
	}

	private String getCity(AD ad) {
		String city = null;
		List<ADXP> adXps = ad.getPart();
		for (ADXP adXp : adXps) {
			if (adXp.getType().equals(AddressPartType.CTY)) {
				city = adXp.getValue();
			}
		}
		return city;
	}

	private String getCountry(AD ad) {
		String ctry = null;
		List<ADXP> adXps = ad.getPart();
		for (ADXP adXp : adXps) {
			if (adXp.getType().equals(AddressPartType.CNT)) {
				ctry = adXp.getValue();
			}
		}
		return ctry;
	}

	private String getState(AD ad) {
		String state = null;
		List<ADXP> adXps = ad.getPart();
		for (ADXP adXp : adXps) {
			if (adXp.getType().equals(AddressPartType.STA)) {
				state = adXp.getValue();
			}
		}
		return state;
	}

	private String getStreet(AD ad) {
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

	private String getZip(AD ad) {
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
			for (Identifier id : p.getIdentifiers()) {
				if (id instanceof OrganizationAssignedIdentifier) {
					BiologicEntityIdentifier bioId = new BiologicEntityIdentifier();
					bioId.setTypeCode(new CD(id.getTypeInternal()));
					bioId.setIdentifier(new II(id.getValue()));
					bioId.setEffectiveDateRange(new IVLTSDateTime(NullFlavor.NI));

					HealthcareSite site = ((OrganizationAssignedIdentifier) id)
							.getHealthcareSite();
					Organization org = new Organization();

					for (Identifier siteId : site
							.getIdentifiersAssignedToOrganization()) {
						OrganizationIdentifier orgId = new OrganizationIdentifier();
						orgId.setTypeCode(new CD(siteId.getTypeInternal()));
						orgId.setIdentifier(new II(siteId.getValue()));
						orgId.setPrimaryIndicator(new BL(siteId
								.getPrimaryIndicator()));
						org.getOrganizationIdentifier().add(orgId);
					}

					bioId.setAssigningOrganization(org);
					person.getBiologicEntityIdentifier().add(bioId);
				}
			}
			person.setAdministrativeGenderCode(p.getAdministrativeGenderCode() != null ? new CD(
					p.getAdministrativeGenderCode()) : new CD(NullFlavor.NI));
			person.setBirthDate(convertToTsDateTime(p.getBirthDate()));
			person.setDeathDate(convertToTsDateTime(p.getDeathDate()));
			person.setDeathIndicator(p.getDeathIndicator() != null ? new BL(p
					.getDeathIndicator()) : new BL(NullFlavor.NI));
			person.setEthnicGroupCode(getEthnicGroupCode(p));
			person.setMaritalStatusCode(p.getMaritalStatusCode() != null ? new CD(
					p.getMaritalStatusCode()) : new CD(NullFlavor.NI));
			person.setName(getName(p));
			person.setPostalAddress(getPostalAddress(p));
			person.setRaceCode(getRaceCodes(p));
			person.setTelecomAddress(getTelecomAddress(p));
			if (p.getStateCode() != null) {
				subject.setStateCode(new ST(p.getStateCode().getCode()));
			}
		}
		return subject;
	}

	private BAGTEL getTelecomAddress(Participant p) {
		BAGTEL addr = new BAGTEL();
		if (StringUtils.isNotBlank(p.getEmail()))
			addr.getItem().add(new TEL(MAILTO + SEMICOLON + p.getEmail()));
		if (StringUtils.isNotBlank(p.getPhone()))
			addr.getItem().add(new TEL(TEL + SEMICOLON + p.getPhone()));
		if (StringUtils.isNotBlank(p.getFax()))
			addr.getItem().add(new TEL(X_TEXT_FAX + SEMICOLON + p.getFax()));
		return addr;
	}

	private DSETCD getRaceCodes(Participant p) {
		DSETCD dsetcd = new DSETCD();
		for (RaceCodeEnum raceCode : p.getRaceCodes()) {
			dsetcd.getItem().add(new CD(raceCode.getCode()));
		}
		return dsetcd;
	}

	/**
	 * @param p
	 * @return
	 */
	private DSETAD getPostalAddress(Participant p) {
		DSETAD set = new DSETAD();
		for (Address address : p.getAddresses()) {
			AD ad = new AD();
			if (address != null && !address.isBlank()) {
				if (StringUtils.isNotBlank(address.getStreetAddress()))
					ad.getPart().add(
							new ADXP(address.getStreetAddress(),
									AddressPartType.SAL));
				if (StringUtils.isNotBlank(address.getCity()))
					ad.getPart().add(
							new ADXP(address.getCity(), AddressPartType.CTY));
				if (StringUtils.isNotBlank(address.getStateCode()))
					ad.getPart().add(
							new ADXP(address.getStateCode(),
									AddressPartType.STA));
				if (StringUtils.isNotBlank(address.getPostalCode()))
					ad.getPart().add(
							new ADXP(address.getPostalCode(),
									AddressPartType.ZIP));
				if (StringUtils.isNotBlank(address.getCountryCode()))
					ad.getPart().add(
							new ADXP(address.getCountryCode(),
									AddressPartType.CNT));
			} else {
				ad.setNullFlavor(NullFlavor.NI);
			}
			set.getItem().add(ad);
		}
		return set;
	}

	private DSETENPN getName(Participant p) {
		DSETENPN dsetenpn = new DSETENPN();
		ENPN enpn = new ENPN();
		if (StringUtils.isNotBlank(p.getFirstName()))
			enpn.getPart()
					.add(new ENXP(p.getFirstName(), EntityNamePartType
							.valueOf(GIV)));
		if (StringUtils.isNotBlank(p.getMiddleName()))
			enpn.getPart()
					.add(new ENXP(p.getMiddleName(), EntityNamePartType
							.valueOf(GIV)));
		if (StringUtils.isNotBlank(p.getLastName()))
			enpn.getPart().add(
					new ENXP(p.getLastName(), EntityNamePartType.valueOf(FAM)));
		dsetenpn.getItem().add(enpn);
		return dsetenpn;
	}

	private DSETCD getEthnicGroupCode(Participant p) {
		DSETCD dsetcd = new DSETCD();
		if (StringUtils.isNotBlank(p.getEthnicGroupCode())) {
			dsetcd.getItem().add(new CD(p.getEthnicGroupCode()));
		} else {
			dsetcd.setNullFlavor(NullFlavor.NI);
		}
		return dsetcd;
	}

	private TSDateTime convertToTsDateTime(Date date) {
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

	private String convertAndErrorIfBlank(ST st, String elementName) {
		if (isNull(st) || StringUtils.isBlank(st.getValue())) {
			throw exceptionHelper.getConversionException(MISSING_ELEMENT,
					new Object[] { elementName });
		} else {
			return st.getValue();
		}
	}

	private String convertAndErrorIfBlank(CD st, String elementName) {
		if (isNull(st) || StringUtils.isBlank(st.getCode())) {
			throw exceptionHelper.getConversionException(MISSING_ELEMENT,
					new Object[] { elementName });
		} else {
			return st.getCode();
		}
	}

	private List<OrganizationAssignedIdentifier> convert(
			List<DocumentIdentifier> docIds) {
		List<OrganizationAssignedIdentifier> list = new ArrayList<OrganizationAssignedIdentifier>();
		for (DocumentIdentifier docId : docIds) {
			list.add(convert(docId));
		}
		return list;
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
		id.setTypeInternal(typeCode.getCode());
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
		for (OrganizationAssignedIdentifier id : convert(xmlStudy
				.getStudyProtocolDocument().getDocument()
				.getDocumentIdentifier())) {
			HealthcareSite healthcareSite = id.getHealthcareSite();
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
			study.addIdentifier(id);
		}
		return study;
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

		List<edu.duke.cabig.c3pr.domain.PermissibleStudySubjectRegistryStatus> statuses = new ArrayList<edu.duke.cabig.c3pr.domain.PermissibleStudySubjectRegistryStatus>();
		for (PermissibleStudySubjectRegistryStatus status : version
				.getPermissibleStudySubjectRegistryStatus()) {
			statuses.add(convert(status));
		}
		study.getPermissibleStudySubjectRegistryStatusesInternal().clear();
		study.getPermissibleStudySubjectRegistryStatusesInternal().addAll(
				statuses);
	}

	/**
	 * @param study
	 * @param ver
	 */
	private void convert(Study study, StudyProtocolDocumentVersion ver,
			boolean updateConsents) {
		study.setShortTitleText(isNull(ver.getPublicTitle()) ? "" : ver
				.getPublicTitle().getValue());
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
				.asList(new ConsentingMethod[] { ConsentingMethod.WRITTEN }));
		consent.setName(isNull(xml.getOfficialTitle()) ? "" : xml
				.getOfficialTitle().getValue());
		consent.setVersionId(isNull(xml.getVersionNumberText()) ? "" : xml
				.getVersionNumberText().getValue());

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

	
	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.converters.JAXBToDomainObjectConverter#convertConsentForSearchByExample(edu.duke.cabig.c3pr.webservice.common.Consent)
	 */
	public Consent convertConsentForSearchByExample(
			edu.duke.cabig.c3pr.webservice.common.Consent xml) {
		Consent consent = new Consent();
		consent.setMandatoryIndicator(xml.getMandatoryIndicator().isValue());
		consent.setName(isNull(xml.getOfficialTitle()) ? null : xml
				.getOfficialTitle().getValue());
		consent.setVersionId(isNull(xml.getVersionNumberText()) ? null : xml
				.getVersionNumberText().getValue());
		return consent;
	}

	private ConsentQuestion convertConsentQuestion(DocumentVersion doc) {
		if (doc == null) {
			throw exceptionHelper
					.getConversionException(INVALID_CONSENT_REPRESENTATION);
		}
		ConsentQuestion question = new ConsentQuestion();
		question.setCode(doc.getOfficialTitle().getValue());
		question.setText(doc.getText().getValue());
		return question;
	}

	private edu.duke.cabig.c3pr.domain.PermissibleStudySubjectRegistryStatus convert(
			PermissibleStudySubjectRegistryStatus xml) {
		edu.duke.cabig.c3pr.domain.PermissibleStudySubjectRegistryStatus status = new edu.duke.cabig.c3pr.domain.PermissibleStudySubjectRegistryStatus();
		status.setRegistryStatus(convert(xml.getRegistryStatus()));
		status.setSecondaryReasons(convertRegistryStatusReasons(xml
				.getSecondaryReason()));
		return status;
	}

	private RegistryStatus convert(
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

	private List<RegistryStatusReason> convertRegistryStatusReasons(
			List<edu.duke.cabig.c3pr.webservice.common.RegistryStatusReason> xmlList) {
		List<RegistryStatusReason> list = new ArrayList<RegistryStatusReason>();
		for (edu.duke.cabig.c3pr.webservice.common.RegistryStatusReason xmlReason : xmlList) {
			list.add(convert(xmlReason));
		}
		return list;
	}

	private RegistryStatusReason convert(
			edu.duke.cabig.c3pr.webservice.common.RegistryStatusReason xmlReason) {
		RegistryStatusReason reason = new RegistryStatusReason();
		reason.setCode(xmlReason.getCode().getCode());
		reason.setDescription(xmlReason.getDescription().getValue());
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
			xmlStudy.setTargetRegistrationSystem(new ST(study
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
		doc.setOfficialTitle(new ST(study.getShortTitleText()));
		doc.setPublicDescription(new ST(study.getDescriptionText()));
		doc.setPublicTitle(new ST(study.getShortTitleText()));
		doc.setText(new ED(study.getDescriptionText()));
		doc.setVersionDate(convertToTsDateTime(study.getVersionDate()));
		doc.setVersionNumberText(new ST(study.getVersionName()));
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
		rel.setTypeCode(new CD(CONSENT));
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
		consent.setMandatoryIndicator(new BL(c.getMandatoryIndicator()));
		consent.setOfficialTitle(new ST(c.getName()));
		//consent.setText(new ED(c.getName()));
		consent.setVersionNumberText(new ST(c.getVersionId()));
		consent.setDocument(new Document());
		for (ConsentQuestion q : c.getQuestions()) {
			consent.getDocumentVersionRelationship().add(
					convertConsentQuestionRelationship(q));
		}
		return consent;
	}

	private DocumentVersionRelationship convertConsentQuestionRelationship(
			ConsentQuestion q) {
		DocumentVersionRelationship rel = new DocumentVersionRelationship();
		rel.setTypeCode(new CD(CONSENT_QUESTION));
		rel.setTarget(convertConsentQuestion(q));
		return rel;

	}

	private DocumentVersion convertConsentQuestion(ConsentQuestion cq) {
		DocumentVersion q = new DocumentVersion();
		q.setOfficialTitle(new ST(cq.getCode()));
		q.setText(new ED(cq.getText()));
		q.setVersionNumberText(new ST(cq.getVersion().toString()));
		q.setDocument(new Document());
		return q;
	}

	private Document convertStudyDocument(Study study) {
		Document doc = new Document();
		for (OrganizationAssignedIdentifier oai : study
				.getOrganizationAssignedIdentifiers()) {
			doc.getDocumentIdentifier().add(convert(oai));
		}
		return doc;
	}

	private Collection<PermissibleStudySubjectRegistryStatus> convertPermissibleStudySubjectRegistryStatus(
			Study study) {
		Collection<PermissibleStudySubjectRegistryStatus> list = new ArrayList<PermissibleStudySubjectRegistryStatus>();
		for (edu.duke.cabig.c3pr.domain.PermissibleStudySubjectRegistryStatus status : study
				.getPermissibleStudySubjectRegistryStatuses()) {
			PermissibleStudySubjectRegistryStatus stat = new PermissibleStudySubjectRegistryStatus();
			stat.setRegistryStatus(convertRegistryStatus(status
					.getRegistryStatus()));
			stat.getSecondaryReason().addAll(
					convertDomainRegistryStatusReasons(status
							.getSecondaryReasons()));
			list.add(stat);
		}
		return list;
	}

	private Collection<edu.duke.cabig.c3pr.webservice.common.RegistryStatusReason> convertDomainRegistryStatusReasons(
			List<RegistryStatusReason> reasons) {
		Collection<edu.duke.cabig.c3pr.webservice.common.RegistryStatusReason> list = new ArrayList<edu.duke.cabig.c3pr.webservice.common.RegistryStatusReason>();
		for (RegistryStatusReason domainObj : reasons) {
			edu.duke.cabig.c3pr.webservice.common.RegistryStatusReason r = new edu.duke.cabig.c3pr.webservice.common.RegistryStatusReason();
			r.setCode(new CD(domainObj.getCode()));
			r.setDescription(new ST(domainObj.getDescription()));
			r.setPrimaryIndicator(new BL(domainObj.getPrimaryIndicator()));
			list.add(r);
		}
		return list;
	}

	private edu.duke.cabig.c3pr.webservice.common.RegistryStatus convertRegistryStatus(
			RegistryStatus domainObj) {
		edu.duke.cabig.c3pr.webservice.common.RegistryStatus status = null;
		if (domainObj != null) {
			status = new edu.duke.cabig.c3pr.webservice.common.RegistryStatus();
			status.setCode(new CD(domainObj.getCode()));
		}
		return status;
	}

	private DocumentIdentifier convert(OrganizationAssignedIdentifier id) {
		DocumentIdentifier studyId = new DocumentIdentifier();
		studyId.setTypeCode(new CD(id.getTypeInternal()));
		studyId.setIdentifier(new II(id.getValue()));
		studyId.setPrimaryIndicator(new BL(id.getPrimaryIndicator()));

		HealthcareSite site = ((OrganizationAssignedIdentifier) id)
				.getHealthcareSite();
		final edu.duke.cabig.c3pr.webservice.common.Organization org = new edu.duke.cabig.c3pr.webservice.common.Organization();
		studyId.setAssigningOrganization(org);
		for (Identifier siteId : site.getIdentifiersAssignedToOrganization()) {
			OrganizationIdentifier orgId = new OrganizationIdentifier();
			orgId.setTypeCode(new CD(siteId.getTypeInternal()));
			orgId.setIdentifier(new II(siteId.getValue()));
			orgId.setPrimaryIndicator(new BL(siteId.getPrimaryIndicator()));
			org.getOrganizationIdentifier().add(orgId);
		}
		return studyId;

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
