package edu.duke.cabig.c3pr.constants;

public class ReferenceDataConstants {

	//The codes of the CODE_PARTICIPANT_ROLE
	public static final String PATIENT = "PT";
	public static final String DATA_MANAGER = "DM";
	public static final String PRINCIPAL_INVESTIGATOR = "PI";
	public static final String ASSOCIATED_INVESTIGATOR = "AI";
	public static final String REFERRING_PHYSICIAN = "RP";
	public static final String TREATING_PHYSICIAN = "TP";
	
	//The Description of the CODE_PARTICIPANT_ROLE
	public static final String PATIENT_DESCRIPTION = "Patient";
	
	//Codes for CODE_REGISTRATION_ROLE
	public static final String REG_ROLE_PATIENT = "PT";
	public static final String REG_ROLE_PRIMARY_INVESTIGATOR = "PI";
	
	//Codes for CODE_ORG_PROTOCOL_TYPE
	public static final String PROTOCOL_OWNER = "Owner";
	public static final String PROTOCOL_SPONSOR = "Sponsor";
	public static final String PROTOCOL_MONITOR = "Monitor";
	public static final String PROTOCOL_NAVY_INST = "NAVY";
	public static final String PROTOCOL_OTHER = "Other";
	
	//Codes for CODE_ORG_TYPE
	public static final String BRANCH = "BR";
	public static final String INSTITUTION = "IC";

	
	//Codes for CODE_ELIGIBILITY_STATUS
	public static final String EC_STATUS_CD_DRAFT="D";
	public static final String EC_STATUS_CD_APPROVED="A";
	public static final String EC_STATUS_CD_REJECTED="R";
	
	
	//Generic "ALL" value for drop down lists
	public static final String ALL = "ALL";
	public static final String NON_PATIENT = "NONPATIENT";
	public static final String SELECT_FUNDING = "Select Funding";
	
	//Codes for CODE_PROT_STATUS
	public static final String PROT_STATUS_CLOSED = "C";
	public static final String PROT_STATUS_OPEN = "O";
	public static final String PROT_STATUS_SUSPENDED = "S";
	public static final String PROT_STATUS_TERMINATED = "T";
	public static final String PROT_STATUS_IRB_APPROVED = "I";
	
	//Codes for CODE_REGISTRATION_STATUS
	public static final String REG_STATUS_PENDING = "P";
	public static final String REG_STATUS_DELETED = "D";
	public static final String REG_STATUS_PENDING_DELETE = "PD";
	public static final String REG_STATUS_REGISTERED= "R";
	public static final String REG_STATUS_OFF_STUDY= "OS";
	public static final String REG_STATUS_OFF_TREATMENT= "OT";
	public static final String REG_STATUS_CLOSED_NOT_COUNTED = "NC";
	public static final String REG_STATUS_TREATMENT= "T";
	
	public static final String PROTOCOL_DESIGN_TREATMENT= "Treatment";
	public static final String PROTOCOL_DESIGN_SCREENING= "Screening";
	public static final String PROTOCOL_DESIGN_OTHER= "Other";
	
	//Code for the CODE_ADDRESS_TYPE
	public static final String HOME = "H";
	public static final String HOSPITAL = "HSP";
	
	//No table at present 
	public static final String ACTIVE = "Y";
	public static final String IN_ACTIVE = "N";

	
	//FOR TABLES THAT RECORD AUDIT DATAE AND HAVE A "MODIFICATION_TYPE" FIELD
	public static final String MODIFICATION_TYPE_UPDATE="U"; //FOR UPDATE
	


	//No table at present 
	public static final String ACTIVE_FLAG = "Active";
	public static final String INACTIVE_FLAG = "Inactive";
		

}