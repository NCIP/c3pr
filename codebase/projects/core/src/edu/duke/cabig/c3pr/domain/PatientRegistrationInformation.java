package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.constants.ReferenceDataConstants;
import edu.duke.cabig.c3pr.utils.Constants;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

public class PatientRegistrationInformation {
	private Integer id;

	private String firstName;

	private String middleName;

	private String lastName;

	private Suffix suffix;

	private Ethnicity ethnicity;

	private Gender gender;

	private String ssn;

	private Date birthDate;

	private String addressLine1;

	private String addressLine2;

	private String zipPostalCode;

	private String city;

	private String stateProvince;

	private Collection<Race> raceCollection;

	private Collection<RegistrationParticipantIdentifier> participantIdentifierCollection;

	public PatientRegistrationInformation() {
		super();
	}

	public String getPrimaryIdAsString(){
		RegistrationParticipantIdentifier personIdentifier = getPrimaryIdentifier();
		if(personIdentifier !=null)
			return personIdentifier.getIdentifierCode();
		
		return "";		
	}
	
	/**
	 * 
	 * Convenience method to return the PrimaryIdentifier of the Participant
	 * @return ParticipantIdentifier
	 */
	public RegistrationParticipantIdentifier getPrimaryIdentifier() {
		if (participantIdentifierCollection == null || participantIdentifierCollection.size() == 0)
			return null;
		
		for (RegistrationParticipantIdentifier pi : participantIdentifierCollection) {
			if(pi.isPrimary())
				return pi;
		}
		
		return null;
	}

	
	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Ethnicity getEthnicity() {
		return ethnicity;
	}

	public void setEthnicity(Ethnicity ethnicity) {
		this.ethnicity = ethnicity;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getStateProvince() {
		return stateProvince;
	}

	public void setStateProvince(String stateProvince) {
		this.stateProvince = stateProvince;
	}

	public Suffix getSuffix() {
		return suffix;
	}

	public void setSuffix(Suffix suffix) {
		this.suffix = suffix;
	}

	public String getZipPostalCode() {
		return zipPostalCode;
	}

	public void setZipPostalCode(String zipPostalCode) {
		this.zipPostalCode = zipPostalCode;
	}

	public Collection getRaceCollection() {
		return raceCollection;
	}

	protected void setRaceCollection(Collection raceCollection) {
		this.raceCollection = raceCollection;
	}

	public Collection<RegistrationParticipantIdentifier> getParticipantIdentifierCollection() {
		return participantIdentifierCollection;
	}

	protected void setParticipantIdentifierCollection(
			Collection<RegistrationParticipantIdentifier> participantIdentifierCollection) {
		this.participantIdentifierCollection = participantIdentifierCollection;
	}

	public void addRegistrationParticipantIdentifier(
			RegistrationParticipantIdentifier registrationParticipantIdentifier) {
		if (participantIdentifierCollection == null)
			participantIdentifierCollection = new HashSet<RegistrationParticipantIdentifier>();

		participantIdentifierCollection.add(registrationParticipantIdentifier);
	}

	public void addRace(Race race) {
		if (raceCollection == null)
			raceCollection = new HashSet<Race>();

		raceCollection.add(race);
	}

	/**
	 * Method to create a non-persisted Participant object that contains the
	 * data in this PatientRegistrationInformation object. The Participant
	 * object produced should not be persisted or updated since the primary ids
	 * of the objects in the aggregate will not be populated.
	 * 
	 * @return Participant
	 */
	public Participant getParticipantForDisplay() {

		Participant p = new Participant();
		p.setBirthDate(birthDate);
		p.setFirstName(firstName);
		p.setLastName(lastName);
		p.setMiddleName(middleName);
		p.setSsn(ssn);
		p.setSuffix(suffix);
		p.setGender(gender);
		createParticipantIdentifierCollection(p);
		createParticipantAddress(p);
		createRaceCollection(p);
		
		p.addParticipantRole(new ParticipantRole(ReferenceDataConstants.PATIENT));

		return p;

	}

	protected void createRaceCollection(Participant p) {

		if (raceCollection != null) {
			for (Race r : raceCollection) {
				p.addRace(r);
			}
		}
	}

	protected void createParticipantAddress(Participant p) {

		ParticipantAddress pa = new ParticipantAddress();
		Address ad = new Address();
		ad.setAddressLine1(addressLine1);
		ad.setAddressLine2(addressLine2);
		ad.setCity(city);
		ad.setStateProvince(stateProvince);
		ad.setZipPostalCode(zipPostalCode);
		pa.setAddress(ad);

		p.addParticipantAddress(pa);

	}

	/**
	 * Copies the Identifiers to the Participant object passed in
	 * 
	 * @param p
	 */
	protected void createParticipantIdentifierCollection(Participant p) {

		if (participantIdentifierCollection != null) {
			for (RegistrationParticipantIdentifier rpi : participantIdentifierCollection) {
				ParticipantIdentifier pi = new ParticipantIdentifier();
				pi.setIdentifierCode(rpi.getIdentifierCode());
				pi.setInstitution(rpi.getInstitution());
				pi.setPrimaryIDFlag(rpi.getPrimaryIDFlag());
				if (Constants.TRUE.equalsIgnoreCase(pi.getPrimaryIDFlag()))
					p.addPrimaryIdentifier(pi);
				else
					p.addOtherIdentifier(pi);
			}
		}

	}

}