package edu.duke.cabig.c3pr.webservice.converters;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.duke.cabig.c3pr.constants.RaceCode;
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.exception.ConversionException;
import edu.duke.cabig.c3pr.webservice.iso21090.AD;
import edu.duke.cabig.c3pr.webservice.iso21090.ADXP;
import edu.duke.cabig.c3pr.webservice.iso21090.AddressPartType;
import edu.duke.cabig.c3pr.webservice.iso21090.BAGTEL;
import edu.duke.cabig.c3pr.webservice.iso21090.BL;
import edu.duke.cabig.c3pr.webservice.iso21090.CD;
import edu.duke.cabig.c3pr.webservice.iso21090.DSETCD;
import edu.duke.cabig.c3pr.webservice.iso21090.DSETENPN;
import edu.duke.cabig.c3pr.webservice.iso21090.ENPN;
import edu.duke.cabig.c3pr.webservice.iso21090.ENXP;
import edu.duke.cabig.c3pr.webservice.iso21090.EntityNamePartType;
import edu.duke.cabig.c3pr.webservice.iso21090.II;
import edu.duke.cabig.c3pr.webservice.iso21090.TEL;
import edu.duke.cabig.c3pr.webservice.iso21090.TSDateTime;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.BiologicEntityIdentifier;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.Organization;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.OrganizationIdentifier;
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
	private static final int MISSING_SUBJECT_IDENTIFIER = 902;
	private static final int SUBJECT_IDENTIFIER_MISSING_ORGANIZATION = 903;
	private static final int ORGANIZATION_IDENTIFIER_MISSING_TYPECODE = 904;
	private static final int UNABLE_TO_FIND_ORGANIZATION = 905;
	private static final int WRONG_DATE_FORMAT = 906;
	private static final int WRONG_RACE_CODE = 907;

	/** The exception helper. */
	protected C3PRExceptionHelper exceptionHelper;

	private HealthcareSiteDao healthcareSiteDao;

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
	public Participant convert(Subject subject) throws ConversionException {
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
				throw exceptionHelper
						.getConversionException(MISSING_SUBJECT_IDENTIFIER);
			}

			convert(participant, subject);
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
	public void convert(Participant participant, Subject subject) {
		if (subject != null && subject.getEntity() != null) {
			try {
				Person person = (Person) subject.getEntity();
				// gender
				CD gender = person.getAdministrativeGenderCode();
				participant.setAdministrativeGenderCode(gender != null ? gender
						.getCode() : null);
				// birth date
				participant.setBirthDate(convertToDate(person.getBirthDate()));
				participant.setDeathDate(convertToDate(person.getDeathDate()));
				participant
						.setDeathIndicator(person.getDeathIndicator() != null ? person
								.getDeathIndicator().isValue()
								: null);
				participant.setEthnicGroupCode(getEthnicGroupCode(person));
				participant
						.setMaritalStatusCode(person.getMaritalStatusCode() != null ? person
								.getMaritalStatusCode().getCode()
								: null);
				participant.setFirstName(getFirstName(person));
				participant.setLastName(getLastName(person));
				participant.setMiddleName(getMiddleName(person));
				participant.setMaidenName(StringUtils.EMPTY);
				participant.setAddress(getAddress(person));
				participant.setRaceCodes(getRaceCodes(person));
				participant.setEmail(getTelecomAddress(person, MAILTO));
				participant.setPhone(getTelecomAddress(person, TEL));
				participant.setFax(getTelecomAddress(person, X_TEXT_FAX));
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

	String getTelecomAddress(Person person, String type) {
		type = type.toLowerCase();
		String addr = null;
		BAGTEL bagtel = person.getTelecomAddress();
		if (bagtel != null && bagtel.getItem() != null) {
			for (TEL tel : bagtel.getItem()) {
				if (tel.getValue() != null
						&& tel.getValue().toLowerCase().startsWith(type)) {
					addr = tel.getValue().toLowerCase().replaceFirst(
							"^" + type + ":", "");
				}
			}
		}
		return addr;
	}

	/**
	 * @param person
	 * @return
	 */
	List<RaceCode> getRaceCodes(Person person) {
		List<RaceCode> list = new ArrayList<RaceCode>();
		DSETCD dsetcd = person.getRaceCode();
		if (dsetcd != null && dsetcd.getItem() != null) {
			for (CD cd : dsetcd.getItem()) {
				String raceCodeStr = cd.getCode();
				RaceCode raceCode = RaceCode.getByCode(raceCodeStr);
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

	Address getAddress(Person person) {
		Address address = null;
		AD addr = person.getPostalAddress();
		if (addr != null) {
			address = new Address();
			address.setCity(getCity(addr));
			address.setCountryCode(getCountry(addr));
			address.setPostalCode(getZip(addr));
			address.setStateCode(getState(addr));
			address.setStreetAddress(getStreet(addr));
		}
		return address;
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
			if (tsDateTime != null) {
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
			participant.addIdentifier(id);
		}
	}

	private HealthcareSite resolveHealthcareSite(Organization org) {
		List<OrganizationIdentifier> idList = org.getOrganizationIdentifier();
		if (CollectionUtils.isEmpty(idList)) {
			throw exceptionHelper
					.getConversionException(SUBJECT_IDENTIFIER_MISSING_ORGANIZATION);
		}
		OrganizationIdentifier orgId = idList.get(0);
		II id = orgId.getIdentifier();
		BL isPrimary = orgId.getPrimaryIndicator();
		CD typeCode = orgId.getTypeCode();
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

}
