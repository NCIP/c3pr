

CREATE SEQUENCE IDENTIFIERS_ID_seq INCREMENT 1 START 1 ;

CREATE TABLE IDENTIFIERS (
	PRIMARY_INDICATOR boolean,
	GRID_ID varchar(100),
	VERSION BIGINT NOT NULL,
	VALUE varchar(200) NOT NULL,
	TYPE varchar(200),
	SOURCE varchar(200) NOT NULL,
	STU_ID BIGINT,
	PRT_ID BIGINT,
	SPA_ID BIGINT,
	ID BIGINT DEFAULT NEXTVAL('"IDENTIFIERS_ID_seq"'::TEXT) NOT NULL
) ;
CREATE SEQUENCE ADDRESSES_ID_seq INCREMENT 1 START 1 ;

CREATE TABLE ADDRESSES (
	GRID_ID varchar(100),
	VERSION BIGINT NOT NULL,
	STREET_ADDRESS varchar(200),
	CITY varchar(200),
	STATE_CODE varchar(200),
	COUNTRY_CODE varchar(200),
	POSTAL_CODE varchar(10),
	ID BIGINT DEFAULT NEXTVAL('"ADDRESSES_ID_seq"'::TEXT) NOT NULL
) ;

CREATE SEQUENCE ARMS_ID_seq INCREMENT 1 START 1 ;

CREATE TABLE ARMS (
	GRID_ID varchar(100),
	VERSION BIGINT NOT NULL,
	ID BIGINT DEFAULT NEXTVAL('"ARMS_ID_seq"'::TEXT) NOT NULL,
	NAME varchar(50) NOT NULL,
	DESCRIPTION_TEXT varchar(50),
	TARGET_ACCRUAL_NUMBER decimal(8,2),
	EPH_ID BIGINT NOT NULL
) ;

CREATE SEQUENCE EPOCHS_ID_seq INCREMENT 1 START 1 ;

CREATE TABLE EPOCHS (
	GRID_ID varchar(100),
	VERSION BIGINT NOT NULL,
	ID BIGINT DEFAULT NEXTVAL('"EPOCHS_ID_seq"'::TEXT) NOT NULL,
	NAME varchar(50) NOT NULL,
	DESCRIPTION_TEXT varchar(200),
	STU_ID BIGINT NOT NULL,
	TYPE varchar(20) NOT NULL,
	ACCRUAL_CEILING SMALLINT DEFAULT 0,
	RESERVATION_INDICATOR varchar(10),
	ENROLLMENT_INDICATOR varchar(10),
	ACCRUAL_INDICATOR varchar(10)

) ;

CREATE SEQUENCE HC_SITE_INVESTIGATORS_ID_seq INCREMENT 1 START 1 ;

CREATE TABLE HC_SITE_INVESTIGATORS (
	GRID_ID varchar(100),
	VERSION BIGINT NOT NULL,
	ID BIGINT DEFAULT NEXTVAL('"HC_SITE_INVESTIGATORS_ID_seq"'::TEXT) NOT NULL,
	STATUS_CODE varchar(200),
	STATUS_DATE timestamp,
	INV_ID BIGINT NOT NULL,
	HCS_ID BIGINT NOT NULL
) ;

CREATE SEQUENCE ORGANIZATIONS_ID_seq INCREMENT 1 START 1 ;

CREATE TABLE ORGANIZATIONS (
	GRID_ID varchar(100),
	VERSION BIGINT NOT NULL,
	ID BIGINT DEFAULT NEXTVAL('"ORGANIZATIONS_ID_seq"'::TEXT) NOT NULL,
	NAME varchar(200) NOT NULL,
	DESCRIPTION_TEXT varchar(200),
	ADDRESS_ID BIGINT NOT NULL,
	NCI_INSTITUTE_CODE varchar(50)
) ;


CREATE SEQUENCE INVESTIGATORS_ID_seq INCREMENT 1 START 1 ;

CREATE TABLE INVESTIGATORS (
	GRID_ID varchar(100),
	VERSION BIGINT NOT NULL,
	ID BIGINT DEFAULT NEXTVAL('"INVESTIGATORS_ID_seq"'::TEXT) NOT NULL,
	FIRST_NAME varchar(200),
	LAST_NAME varchar(200),
	MAIDEN_NAME varchar(200),
	NCI_IDENTIFIER varchar(50),
	ADD_ID BIGINT,
	CON_ID BIGINT
) ;

CREATE SEQUENCE PARTICIPANTS_ID_seq INCREMENT 1 START 1 ;

CREATE TABLE PARTICIPANTS (
	GRID_ID varchar(100),
	VERSION BIGINT NOT NULL,
	ID BIGINT DEFAULT NEXTVAL('"PARTICIPANTS_ID_seq"'::TEXT) NOT NULL,
	FIRST_NAME varchar(200),
	LAST_NAME varchar(200),
	MAIDEN_NAME varchar(200),
	BIRTH_DATE timestamp,
	ADMINISTRATIVE_GENDER_CODE varchar(50) NOT NULL,
	RACE_CODE varchar(50),
	ETHNIC_GROUP_CODE varchar(50),
	MARITAL_STATUS_CODE varchar(50),
	ADD_ID BIGINT,
        CON_ID BIGINT
) ;

CREATE SEQUENCE SCHEDULED_ARMS_ID_seq INCREMENT 1 START 1 ;

CREATE TABLE SCHEDULED_ARMS (
	GRID_ID varchar(100),
	VERSION BIGINT NOT NULL,
	ID BIGINT DEFAULT NEXTVAL('"SCHEDULED_ARMS_ID_seq"'::TEXT) NOT NULL,
	START_DATE timestamp NOT NULL,
	ELIGIBILITY_INDICATOR varchar(10) NOT NULL,
	ELIGIBILITY_WAIVER_REASON_TEXT varchar(200),
	ARM_ID BIGINT,
	SPA_ID BIGINT NOT NULL
) ;

CREATE SEQUENCE STUDIES_ID_seq INCREMENT 1 START 1 ;

CREATE TABLE STUDIES (
	GRID_ID varchar(100),
	VERSION BIGINT NOT NULL,
	ID BIGINT DEFAULT NEXTVAL('"STUDIES_ID_seq"'::TEXT) NOT NULL,
	BLINDED_INDICATOR varchar(10),
	LONG_TITLE_TEXT varchar(200) NOT NULL,
	MULTI_INSTITUTION_INDICATOR varchar(10),
	PHASE_CODE varchar(50) NOT NULL,
	RANDOMIZED_INDICATOR varchar(10),
	SHORT_TITLE_TEXT varchar(200),
	TARGET_ACCRUAL_NUMBER decimal(8,2),
	TYPE varchar(50) NOT NULL,
	DESCRIPTION_TEXT varchar(2000),
	PRECIS_TEXT varchar(200),
	STATUS varchar(50) NOT NULL
) ;

CREATE SEQUENCE STUDY_INVESTIGATORS_ID_seq INCREMENT 1 START 1 ;

CREATE TABLE STUDY_INVESTIGATORS (
	GRID_ID varchar(100),
	VERSION BIGINT NOT NULL,
	ID BIGINT DEFAULT NEXTVAL('"STUDY_INVESTIGATORS_ID_seq"'::TEXT) NOT NULL,
	STS_ID BIGINT NOT NULL,
	HSI_ID BIGINT NOT NULL,
	ROLE_CODE varchar(50) NOT NULL,
	START_DATE timestamp,
	END_DATE timestamp,
	STATUS_CODE varchar(50)
) ;

CREATE SEQUENCE STUDY_PARTICIPANT_ASSIG_ID_seq INCREMENT 1 START 1 ;

CREATE TABLE STUDY_PARTICIPANT_ASSIGNMENTS (
	GRID_ID varchar(100),
	VERSION BIGINT NOT NULL,
	ID BIGINT DEFAULT NEXTVAL('"STUDY_PARTICIPANT_ASSIG_ID_seq"'::TEXT) NOT NULL,
	STUDY_PARTICIPANT_IDENTIFIER varchar(50) NOT NULL,
	REGISTRATION_STATUS varchar(200),
	START_DATE timestamp NOT NULL,
	ELIGIBILITY_INDICATOR boolean,
	ELIGIBILITY_WAIVER_REASON_TEXT varchar(200),
	INFORMED_CONSENT_SIGNED_DATE timestamp,
	PRT_ID BIGINT NOT NULL,
	STS_ID BIGINT NOT NULL,
	STI_ID BIGINT,
	DISEASE_HISTORY_ID BIGINT,
	INFORMED_CONSENT_VERSION varchar(200)
) ;

CREATE SEQUENCE STUDY_SITES_ID_seq INCREMENT 1 START 1 ;

CREATE TABLE STUDY_SITES (
	GRID_ID varchar(100),
	VERSION BIGINT NOT NULL,
	ID BIGINT DEFAULT NEXTVAL('"STUDY_SITES_ID_seq"'::TEXT) NOT NULL,
	IRB_APPROVAL_DATE timestamp,
	ROLE_CODE varchar(200) NOT NULL,
	STATUS_CODE varchar(200) NOT NULL,
	START_DATE timestamp,
	END_DATE timestamp,
	STUDY_ID BIGINT NOT NULL,
	HCS_ID BIGINT NOT NULL
) ;

CREATE SEQUENCE study_personnel_ID_seq INCREMENT 1 START 1 ;

CREATE TABLE study_personnel (
  ID BIGINT DEFAULT NEXTVAL('"study_personnel_ID_seq"'::TEXT) NOT NULL,
  VERSION BIGINT NOT NULL,
  GRID_ID varchar(100),
  research_staff_ID BIGINT,
  STS_ID BIGINT,
  ROLE_CODE varchar(50) NOT NULL,
  START_DATE timestamp,
  END_DATE timestamp,
  STATUS_CODE varchar(50)
);

CREATE SEQUENCE research_staff_ID_seq INCREMENT 1 START 1 ;

CREATE TABLE research_staff (
  ID BIGINT DEFAULT NEXTVAL('"research_staff_ID_seq"'::TEXT) NOT NULL,
  VERSION decimal(4,2) NOT NULL,
  GRID_ID varchar(100),
  FIRST_NAME varchar(200),
  LAST_NAME varchar(200),
  MAIDEN_NAME varchar(200),
  ADD_ID BIGINT,
  CON_ID BIGINT,
  HCS_ID BIGINT NOT NULL
);

CREATE SEQUENCE eligibility_criteria_ID_seq INCREMENT 1 START 1 ;

CREATE TABLE eligibility_criteria (
  ID BIGINT DEFAULT NEXTVAL('"eligibility_criteria_ID_seq"'::TEXT) NOT NULL,
  VERSION BIGINT NOT NULL,
  GRID_ID varchar(100),
  QUESTION_NUMBER decimal(4,2) NOT NULL,
  QUESTION_TEXT varchar(500) NOT NULL,
  NOT_APPLICABLE_INDICATOR  boolean,
  STU_ID BIGINT,
  DTYPE varchar(3) NOT NULL,
  EPH_ID BIGINT
);

CREATE SEQUENCE subject_strat_ans_ID_seq INCREMENT 1 START 1 ;
CREATE TABLE subject_strat_ans (
  ID BIGINT DEFAULT NEXTVAL('"PRT_STRAT_ANS_ID_seq"'::TEXT) NOT NULL,
  VERSION BIGINT NOT NULL,
  GRID_ID varchar(100),
  STRAT_ANS_ID BIGINT,
  STR_CRI_ID BIGINT,
  SPA_ID BIGINT
);

CREATE SEQUENCE subject_eligibility_ans_ID_seq INCREMENT 1 START 1 ;

CREATE TABLE subject_eligibility_ans (
  ID BIGINT DEFAULT NEXTVAL('"PRT_ELIGIBILITY_ANS_ID_seq"'::TEXT) NOT NULL,
  VERSION BIGINT NOT NULL,
  GRID_ID varchar(100),
  ANSWER_TEXT varchar(500),
  ELGCT_ID BIGINT NOT NULL,
  SPA_ID BIGINT
);

CREATE SEQUENCE STUDY_DISEASES_ID_seq INCREMENT 1 START 1 ;
CREATE TABLE study_diseases (
	id BIGINT DEFAULT nextval('STUDY_DISEASES_ID_seq'::regclass) NOT NULL,
	study_id BIGINT NOT NULL,
	disease_term_id BIGINT NOT NULL,
	version BIGINT DEFAULT 0 NOT NULL,
	grid_id varchar(100),
	lead_disease boolean
);

CREATE SEQUENCE DISEASE_HISTORY_ID_seq INCREMENT 1 START 1 ;
CREATE TABLE disease_history (
	id BIGINT DEFAULT NEXTVAL('DISEASE_HISTORY_ID_seq'::TEXT) NOT NULL,
	grid_id varchar(100),
	study_disease_id BIGINT,
	anatomic_site_id BIGINT,
	other_disease_code varchar(500),
	other_disease_site_code varchar(500),
	version BIGINT DEFAULT 0 NOT NULL
);

CREATE SEQUENCE ANATOMIC_SITES_ID_seq INCREMENT 1 START 1 ;
CREATE TABLE anatomic_sites (
	id BIGINT DEFAULT NEXTVAL('ANATOMIC_SITES_ID_seq'::TEXT) NOT NULL,
	name varchar(500),
	category varchar(500),
	grid_id varchar(100),
	version BIGINT DEFAULT 0 NOT NULL
);

CREATE SEQUENCE DISEASE_CATEGORIES_ID_seq INCREMENT 1 START 1 ;
CREATE TABLE disease_categories (
	id BIGINT DEFAULT nextval('DISEASE_CATEGORIES_ID_seq'::regclass) NOT NULL,
	grid_id varchar(100),
	parent_id BIGINT,
	name varchar(200),
	version BIGINT DEFAULT 0 NOT NULL
);

CREATE SEQUENCE DISEASE_TERMS_ID_seq INCREMENT 1 START 1 ;
CREATE TABLE disease_terms (
	id BIGINT DEFAULT nextval('DISEASE_TERMS_ID_seq'::regclass) NOT NULL,
	grid_id varchar(100),
	term varchar(200),
	ctep_term varchar(200),
	category_id BIGINT NOT NULL,
	medra_code BIGINT,
	version BIGINT DEFAULT 0 NOT NULL
);

CREATE SEQUENCE strat_criteria_ID_SEQ INCREMENT 1 START 1 ;
CREATE TABLE strat_criteria (
	id BIGINT DEFAULT nextval('strat_criteria_ID_SEQ'::regclass) NOT NULL,
	grid_id varchar(100),
	version BIGINT DEFAULT 0 NOT NULL,
	question_text varchar(200),
	question_number BIGINT,
	study_id BIGINT,
	eph_id BIGINT
);

CREATE SEQUENCE STRAT_CRI_PER_ANS_ID_SEQ INCREMENT 1 START 1 ;
CREATE TABLE STRAT_CRI_PER_ANS (
	id BIGINT DEFAULT nextval('STRAT_CRI_PER_ANS_ID_SEQ'::regclass) NOT NULL,
	grid_id varchar(100),
	version BIGINT DEFAULT 0 NOT NULL,
	permissible_answer varchar(200),
	str_cri_id BIGINT NOT NULL
);

CREATE SEQUENCE CONTACT_MECHANISMS_ID_SEQ INCREMENT 1 START 1 ;
CREATE TABLE CONTACT_MECHANISMS (
	id BIGINT DEFAULT nextval('CONTACT_MECHANISMS_ID_SEQ'::regclass) NOT NULL,
	grid_id varchar(100),
	version BIGINT DEFAULT 0 NOT NULL,
	type varchar(100),
	value varchar(200),
	prt_id BIGINT,
	inv_id BIGINT,
	rs_id  BIGINT
);

ALTER TABLE ADDRESSES ADD CONSTRAINT PK_ADDRESSES
	PRIMARY KEY (ID) ;

ALTER TABLE ARMS ADD CONSTRAINT PK_ARMS
	PRIMARY KEY (ID) ;

ALTER TABLE EPOCHS ADD CONSTRAINT PK_EPOCHS
	PRIMARY KEY (ID) ;

ALTER TABLE HC_SITE_INVESTIGATORS ADD CONSTRAINT PK_HC_SITE_INVESTIGATORS
	PRIMARY KEY (ID) ;

ALTER TABLE ORGANIZATIONS ADD CONSTRAINT PK_ORG
	PRIMARY KEY (ID) ;

ALTER TABLE INVESTIGATORS ADD CONSTRAINT PK_INVESTIGATORS
	PRIMARY KEY (ID) ;

ALTER TABLE PARTICIPANTS ADD CONSTRAINT PK_PARTICIPANTS
	PRIMARY KEY (ID) ;

ALTER TABLE SCHEDULED_ARMS ADD CONSTRAINT PK_SCHEDULED_ARMS
	PRIMARY KEY (ID) ;

ALTER TABLE STUDIES ADD CONSTRAINT PK_Study
	PRIMARY KEY (ID) ;

ALTER TABLE STUDY_INVESTIGATORS ADD CONSTRAINT PK_STUDY_INVESTIGATORS
	PRIMARY KEY (ID) ;

ALTER TABLE IDENTIFIERS ADD CONSTRAINT PK_IDENTIFIERS
	PRIMARY KEY (ID);

ALTER TABLE STUDY_PARTICIPANT_ASSIGNMENTS ADD CONSTRAINT PK_STUDY_PARTICIPANT_ASSIGNMEN
	PRIMARY KEY (ID) ;

ALTER TABLE STUDY_SITES ADD CONSTRAINT PK_STUDY_SITES
	PRIMARY KEY (ID) ;

ALTER TABLE study_personnel ADD CONSTRAINT PK_study_personnel
	PRIMARY KEY (ID) ;

ALTER TABLE research_staff ADD CONSTRAINT PK_research_staff
	PRIMARY KEY (ID) ;

ALTER TABLE eligibility_criteria ADD CONSTRAINT PK_eligibility_criteria
	PRIMARY KEY (ID) ;

ALTER TABLE subject_eligibility_ans ADD CONSTRAINT PK_PRT_ELIGIBILITY_ANSWERS
	PRIMARY KEY (ID) ;

ALTER TABLE subject_strat_ans ADD CONSTRAINT PK_PRT_STRAT_ANSWERS
	PRIMARY KEY (ID) ;

ALTER TABLE STUDY_DISEASES ADD CONSTRAINT PK_STUDY_DISEASES PRIMARY KEY (id);

ALTER TABLE DISEASE_CATEGORIES ADD CONSTRAINT PK_DISEASE_CATEGORIES PRIMARY KEY (id);

ALTER TABLE DISEASE_TERMS ADD CONSTRAINT PK_DISEASE_TERMS PRIMARY KEY (id);

ALTER TABLE strat_criteria ADD CONSTRAINT PK_strat_criteria PRIMARY KEY (id);

ALTER TABLE STRAT_CRI_PER_ANS ADD CONSTRAINT PK_STRAT_CRI_PER_ANS PRIMARY KEY (id);

ALTER TABLE CONTACT_MECHANISMS ADD CONSTRAINT PK_CONTACT_MECHANISMS PRIMARY KEY (ID) ;

ALTER TABLE ARMS ADD CONSTRAINT FK_ARM_EPH
	FOREIGN KEY (EPH_ID) REFERENCES EPOCHS (ID) ;

ALTER TABLE EPOCHS ADD CONSTRAINT FK_EPH_STU
	FOREIGN KEY (STU_ID) REFERENCES STUDIES (ID) ;

ALTER TABLE HC_SITE_INVESTIGATORS ADD CONSTRAINT FK_HSI_ORG
	FOREIGN KEY (HCS_ID) REFERENCES ORGANIZATIONS (ID) ;

ALTER TABLE HC_SITE_INVESTIGATORS ADD CONSTRAINT FK_HSI_INV
	FOREIGN KEY (INV_ID) REFERENCES INVESTIGATORS (ID) ;

ALTER TABLE ORGANIZATIONS ADD CONSTRAINT FK_ORG_ADD
	FOREIGN KEY (ADDRESS_ID) REFERENCES ADDRESSES (ID) ;

ALTER TABLE PARTICIPANTS ADD CONSTRAINT FK_PRT_ADD
	FOREIGN KEY (ADD_ID) REFERENCES ADDRESSES (ID) ;

ALTER TABLE research_staff ADD CONSTRAINT FK_RSF_ADD
	FOREIGN KEY (ADD_ID) REFERENCES ADDRESSES (ID) ;

ALTER TABLE INVESTIGATORS ADD CONSTRAINT FK_INV_ADD
	FOREIGN KEY (ADD_ID) REFERENCES ADDRESSES (ID) ;

ALTER TABLE SCHEDULED_ARMS ADD CONSTRAINT FK_SCA_ARM
	FOREIGN KEY (ARM_ID) REFERENCES ARMS (ID) ;

ALTER TABLE SCHEDULED_ARMS ADD CONSTRAINT FK_SCA_SPA
	FOREIGN KEY (SPA_ID) REFERENCES STUDY_PARTICIPANT_ASSIGNMENTS (ID) ;

ALTER TABLE STUDY_INVESTIGATORS ADD CONSTRAINT FK_STI_HSI
	FOREIGN KEY (HSI_ID) REFERENCES HC_SITE_INVESTIGATORS (ID) ;

ALTER TABLE STUDY_INVESTIGATORS ADD CONSTRAINT FK_STI_STS
	FOREIGN KEY (STS_ID) REFERENCES STUDY_SITES (ID) ;

ALTER TABLE STUDY_PARTICIPANT_ASSIGNMENTS ADD CONSTRAINT FK_SPA_PRT
	FOREIGN KEY (PRT_ID) REFERENCES PARTICIPANTS (ID) ;

ALTER TABLE STUDY_PARTICIPANT_ASSIGNMENTS ADD CONSTRAINT FK_SPA_STS
	FOREIGN KEY (STS_ID) REFERENCES STUDY_SITES (ID) ;

ALTER TABLE STUDY_SITES ADD CONSTRAINT FK_STS_ORG
	FOREIGN KEY (HCS_ID) REFERENCES ORGANIZATIONS (ID) ;

ALTER TABLE STUDY_SITES ADD CONSTRAINT FK_STS_STU
	FOREIGN KEY (STUDY_ID) REFERENCES STUDIES (ID) ;

ALTER TABLE IDENTIFIERS ADD CONSTRAINT FK_IDENTIFIERS_PARTICIPANTS
	FOREIGN KEY (PRT_ID) REFERENCES PARTICIPANTS (ID);

ALTER TABLE IDENTIFIERS ADD CONSTRAINT FK_IDENTIFIERS_SPA
	FOREIGN KEY (SPA_ID) REFERENCES STUDY_PARTICIPANT_ASSIGNMENTS (ID);

ALTER TABLE IDENTIFIERS ADD CONSTRAINT FK_IDENTIFIERS_STUDIES
	FOREIGN KEY (STU_ID) REFERENCES STUDIES (ID);

ALTER TABLE study_personnel ADD CONSTRAINT FK_STDPER_RESSTF
	FOREIGN KEY (research_staff_ID) REFERENCES research_staff (ID) ;

ALTER TABLE study_personnel ADD CONSTRAINT FK_STDPER_STDSIT
	FOREIGN KEY (STS_ID) REFERENCES STUDY_SITES (ID) ;

ALTER TABLE eligibility_criteria ADD CONSTRAINT FK_ELGCT_STU
	FOREIGN KEY (STU_ID) REFERENCES STUDIES (ID) ;

ALTER TABLE eligibility_criteria ADD CONSTRAINT FK_ELGCT_EPH
	FOREIGN KEY (EPH_ID) REFERENCES EPOCHS (ID) ;

ALTER TABLE subject_eligibility_ans ADD CONSTRAINT FK_PEA_SPA
	FOREIGN KEY (SPA_ID) REFERENCES STUDY_PARTICIPANT_ASSIGNMENTS (ID) ;

ALTER TABLE subject_eligibility_ans ADD CONSTRAINT FK_PEA_EC
	FOREIGN KEY (ELGCT_ID) REFERENCES eligibility_criteria (ID) ;

ALTER TABLE subject_strat_ans ADD CONSTRAINT FK_PSA_SC
	FOREIGN KEY (STR_CRI_ID) REFERENCES strat_criteria (ID) ;

ALTER TABLE subject_strat_ans ADD CONSTRAINT FK_PSA_SPA
	FOREIGN KEY (SPA_ID) REFERENCES STUDY_PARTICIPANT_ASSIGNMENTS (ID) ;

ALTER TABLE subject_strat_ans ADD CONSTRAINT FK_PSA_SC_ANS
	FOREIGN KEY (STRAT_ANS_ID) REFERENCES STRAT_CRI_PER_ANS (ID) ;

ALTER TABLE strat_criteria ADD CONSTRAINT FK_STR_CRI_STUDY
	FOREIGN KEY (study_id) REFERENCES STUDIES (ID) ;

ALTER TABLE strat_criteria ADD CONSTRAINT FK_STR_CRI_EPH
	FOREIGN KEY (eph_id) REFERENCES EPOCHS (ID) ;

ALTER TABLE STRAT_CRI_PER_ANS ADD CONSTRAINT FK_STR_CRI_PER_ANS_STR_CRI
	FOREIGN KEY (str_cri_id) REFERENCES strat_criteria (ID) ;

ALTER TABLE DISEASE_HISTORY ADD CONSTRAINT PK_DISEASE_HISTORY PRIMARY KEY (ID);

ALTER TABLE ANATOMIC_SITES ADD CONSTRAINT PK_ANATOMIC_SITES PRIMARY KEY (ID);

ALTER TABLE DISEASE_HISTORY ADD CONSTRAINT FK_DIS_HIST_ST_DIS
	FOREIGN KEY (STUDY_DISEASE_ID) REFERENCES STUDY_DISEASES (ID) ;

ALTER TABLE DISEASE_HISTORY ADD CONSTRAINT FK_DIS_HIST_ANA_DIS_SITE
	FOREIGN KEY (ANATOMIC_SITE_ID) REFERENCES ANATOMIC_SITES (ID) ;

ALTER TABLE STUDY_PARTICIPANT_ASSIGNMENTS ADD CONSTRAINT FK_SPA_DIS_HIST
	FOREIGN KEY (DISEASE_HISTORY_ID) REFERENCES DISEASE_HISTORY (ID) ;

ALTER TABLE CONTACT_MECHANISMS ADD CONSTRAINT FK_CM_PRT
	FOREIGN KEY (PRT_ID) REFERENCES PARTICIPANTS (ID) ;

ALTER TABLE CONTACT_MECHANISMS ADD CONSTRAINT FK_CM_INV
	FOREIGN KEY (INV_ID) REFERENCES INVESTIGATORS (ID) ;

ALTER TABLE CONTACT_MECHANISMS ADD CONSTRAINT FK_CM_RS
	FOREIGN KEY (RS_ID) REFERENCES research_staff (ID) ;

ALTER TABLE research_staff ADD CONSTRAINT FK_RS_ORG
	FOREIGN KEY (HCS_ID) REFERENCES ORGANIZATIONS(ID);