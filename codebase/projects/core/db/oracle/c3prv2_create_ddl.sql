CREATE TABLE IDENTIFIERS ( 
	ID NUMBER(10) NOT NULL,
	VERSION NUMBER(4) NOT NULL,
	GRID_ID VARCHAR2(500),
	VALUE VARCHAR2(200) NOT NULL,
	TYPE VARCHAR2(200),
	SOURCE VARCHAR2(200) NOT NULL,
	STU_ID NUMBER(10),
	PRT_ID NUMBER(10),
	SPA_ID NUMBER(10),
	PRIMARY_INDICATOR VARCHAR2(3)
);

CREATE TABLE ADDRESSES ( 
	ID NUMBER(10) NOT NULL,
	VERSION NUMBER(4) NOT NULL,
	GRID_ID VARCHAR2(500),	
	STREET_ADDRESS VARCHAR2(200),
	CITY VARCHAR2(200),
	STATE_CODE VARCHAR2(200),
	COUNTRY_CODE VARCHAR2(200),
	POSTAL_CODE VARCHAR2(10)
) ;


CREATE TABLE PARTICIPANTS ( 
	ID NUMBER(10) NOT NULL,
	VERSION NUMBER(4) NOT NULL,	
	GRID_ID VARCHAR2(500),		
	FIRST_NAME VARCHAR2(200),
	LAST_NAME VARCHAR2(200),
	MAIDEN_NAME VARCHAR2(200),
	BIRTH_DATE DATE,
	ADMINISTRATIVE_GENDER_CODE VARCHAR(50) NOT NULL,
	RACE_CODE VARCHAR(50),
	ETHNIC_GROUP_CODE VARCHAR2(50),
	MARITAL_STATUS_CODE VARCHAR2(50),
	ADD_ID NUMBER(10)
) ;

CREATE TABLE STUDIES ( 
	ID NUMBER(10) NOT NULL,
	VERSION NUMBER(4) NOT NULL,	
	GRID_ID VARCHAR2(500),			
	BLINDED_INDICATOR VARCHAR2(10),
	LONG_TITLE_TEXT VARCHAR2(200) NOT NULL,
	MULTI_INSTITUTION_INDICATOR VARCHAR2(10),
	PHASE_CODE VARCHAR2(50) NOT NULL,
	RANDOMIZED_INDICATOR VARCHAR2(10),
	SHORT_TITLE_TEXT VARCHAR2(200),
	TARGET_ACCRUAL_NUMBER NUMBER(4),
	TYPE VARCHAR2(50) NOT NULL,
	DESCRIPTION_TEXT VARCHAR2(2000),
	PRECIS_TEXT VARCHAR2(200),
	STATUS VARCHAR2(50) NOT NULL
) ;

CREATE TABLE EPOCHS ( 
	ID NUMBER(10) NOT NULL,
	VERSION NUMBER(4) NOT NULL,		
	GRID_ID VARCHAR2(500),			
	NAME VARCHAR2(50) NOT NULL,
	DESCRIPTION_TEXT VARCHAR2(200),
	STU_ID NUMBER(10) NOT NULL,
	TYPE VARCHAR2(20) NOT NULL,
	ACCRUAL_CEILING NUMBER(6)DEFAULT 0,
	RESERVATION_INDICATOR VARCHAR2(3),
	ENROLLMENT_INDICATOR VARCHAR2(3),
	ACCRUAL_INDICATOR VARCHAR2(3)
) ;


CREATE TABLE ARMS ( 
	ID NUMBER(10) NOT NULL,
	VERSION NUMBER(4) NOT NULL,					
	GRID_ID VARCHAR2(500),	
	NAME VARCHAR2(50) NOT NULL,
	DESCRIPTION_TEXT VARCHAR2(50),
	TARGET_ACCRUAL_NUMBER NUMBER(4),
	EPH_ID NUMBER(10) NOT NULL
) ;

CREATE TABLE STUDY_SITES ( 
	ID NUMBER(10) NOT NULL,
	VERSION NUMBER(4) NOT NULL,
	GRID_ID VARCHAR2(500),
	IRB_APPROVAL_DATE DATE,
	ROLE_CODE VARCHAR2(200) NOT NULL,
	STATUS_CODE VARCHAR2(200) NOT NULL,
	START_DATE DATE,
	END_DATE DATE,
	STUDY_ID NUMBER(10) NOT NULL,
	HCS_ID NUMBER(10) NOT NULL
) ;

CREATE TABLE ORGANIZATIONS ( 
	ID NUMBER(10) NOT NULL,
	VERSION NUMBER(4) NOT NULL,
	GRID_ID VARCHAR2(500),		
	NAME VARCHAR2(200) NOT NULL,
	DESCRIPTION_TEXT VARCHAR2(200),
	ADDRESS_ID NUMBER(10) NOT NULL,
	NCI_INSTITUTE_CODE VARCHAR2(50)
) ;


CREATE TABLE INVESTIGATORS ( 
	ID NUMBER(10) NOT NULL,
	VERSION NUMBER(4) NOT NULL,
	GRID_ID VARCHAR2(500),			
	FIRST_NAME VARCHAR2(200),
	LAST_NAME VARCHAR2(200),
	MAIDEN_NAME VARCHAR2(200),
	NCI_IDENTIFIER VARCHAR2(50),
	ADD_ID NUMBER(10)
) ;

CREATE TABLE STUDY_INVESTIGATORS ( 
	ID NUMBER(10) NOT NULL,
	VERSION NUMBER(4) NOT NULL,
	GRID_ID VARCHAR2(500),				
	STS_ID NUMBER(10) NOT NULL,
	HSI_ID NUMBER(10) NOT NULL,
	ROLE_CODE VARCHAR(50) NOT NULL,
	START_DATE DATE,
	END_DATE DATE,
	STATUS_CODE VARCHAR2(50)
) ;

CREATE TABLE HC_SITE_INVESTIGATORS ( 
	ID NUMBER(10) NOT NULL,
	VERSION NUMBER(4) NOT NULL,
	GRID_ID VARCHAR2(500),	
	STATUS_CODE VARCHAR2(200),
  	STATUS_DATE DATE,
	INV_ID NUMBER(10) NOT NULL,
	HCS_ID NUMBER(10) NOT NULL
) ;

CREATE TABLE SCHEDULED_ARMS ( 
	ID NUMBER(10) NOT NULL,
	VERSION NUMBER(4) NOT NULL,
	GRID_ID VARCHAR2(500),	
	START_DATE DATE NOT NULL,
	ELIGIBILITY_INDICATOR VARCHAR2(10) NOT NULL,
	ELIGIBILITY_WAIVER_REASON_TEXT VARCHAR2(200),
	ARM_ID NUMBER(10) ,
	SPA_ID NUMBER(10) NOT NULL
) ;

CREATE TABLE STUDY_PARTICIPANT_ASSIGNMENTS ( 
	ID NUMBER(10) NOT NULL,
	VERSION NUMBER(4) NOT NULL,
	GRID_ID VARCHAR2(500),						
	STUDY_PARTICIPANT_IDENTIFIER VARCHAR2(50) NOT NULL,
	ELIGIBILITY_INDICATOR  VARCHAR2(50) NOT NULL,
	START_DATE DATE NOT NULL,
	ELIGIBILITY_WAIVER_REASON_TEXT VARCHAR2(200),
	REGISTRATION_STATUS VARCHAR2(200),
	INFORMED_CONSENT_SIGNED_DATE DATE,
	PRT_ID NUMBER(10) NOT NULL,
	STS_ID NUMBER(10) NOT NULL,
	STI_ID NUMBER(10),	
	DISEASE_HISTORY_ID NUMBER(10),
	INFORMED_CONSENT_VERSION VARCHAR2(200)
) ;

CREATE TABLE STUDY_PERSONNELS (
  ID NUMBER(10) NOT NULL,
  VERSION NUMBER(4) NOT NULL,	 
  GRID_ID VARCHAR2(500),		
  RESEARCH_STAFFS_ID NUMBER(10),
  STS_ID NUMBER(10),
  ROLE_CODE VARCHAR(50) NOT NULL,
  START_DATE DATE,
  END_DATE DATE,
  STATUS_CODE VARCHAR2(50)
);


CREATE TABLE RESEARCH_STAFFS (
  ID NUMBER(10) NOT NULL,
  VERSION NUMBER(4) NOT NULL,	 
  GRID_ID VARCHAR2(500),		
  FIRST_NAME VARCHAR2(200),
  LAST_NAME VARCHAR2(200),
  MAIDEN_NAME VARCHAR2(200),
  ADD_ID NUMBER(10),
  HCS_ID NUMBER(10) NOT NULL
);

CREATE TABLE ELIGIBILITY_CRITERIAS (
  ID NUMBER(10) NOT NULL,
  VERSION NUMBER(4) NOT NULL,	 
  GRID_ID VARCHAR2(500),	
  QUESTION_NUMBER NUMBER(4) NOT NULL,	 
  QUESTION_TEXT VARCHAR2(500) NOT NULL,
  NOT_APPLICABLE_INDICATOR VARCHAR2(3),
  STU_ID NUMBER(10),  
  DTYPE VARCHAR2(3) NOT NULL,
  EPH_ID NUMBER(10)
);

CREATE TABLE SUBJECT_ELIGIBILITY_ANSWERS (
  ID NUMBER(10) NOT NULL,
  VERSION NUMBER(4) NOT NULL,  
  GRID_ID VARCHAR2(500),		
  ANSWER_TEXT VARCHAR2(500),
  ELGCT_ID NUMBER(10) NOT NULL,  
  SPA_ID NUMBER(10) 
);

CREATE TABLE SUBJECT_STRATIFICATION_ANSWERS (
  ID NUMBER(10) NOT NULL,
  VERSION NUMBER(4) NOT NULL,  
  GRID_ID VARCHAR2(500),		
  STRAT_ANS_ID NUMBER(10),  
  STR_CRI_ID NUMBER(10),  
  SPA_ID NUMBER(10) 
);

CREATE TABLE STUDY_DISEASES (
	ID NUMBER(4) NOT NULL,
	STUDY_ID NUMBER(4) NOT NULL,
	DISEASE_TERM_ID NUMBER(4) NOT NULL,
	VERSION NUMBER(4) NOT NULL, 
	GRID_ID VARCHAR2(500),
	LEAD_DISEASE VARCHAR2(3)
);
	
CREATE TABLE DISEASE_HISTORY (
	ID NUMBER(4) NOT NULL,
	ANATOMIC_SITE_ID NUMBER(10),
	STUDY_DISEASE_ID NUMBER(10),
	OTHER_DISEASE_CODE VARCHAR2(500),
	OTHER_DISEASE_SITE_CODE VARCHAR2(500),
	VERSION NUMBER(4) NOT NULL
);

CREATE TABLE ANATOMIC_SITES (
	ID NUMBER(4) NOT NULL,
	GRID_ID VARCHAR2(500),
	NAME VARCHAR2(500),
	CATEGORY VARCHAR2(500),
	VERSION NUMBER(4) NOT NULL
);

CREATE TABLE DISEASE_CATEGORIES (
	ID NUMBER(4) NOT NULL,
	GRID_ID VARCHAR2(500),
	PARENT_ID NUMBER(4),
	NAME VARCHAR2(500),
	VERSION NUMBER(4) NOT NULL
);

CREATE TABLE DISEASE_TERMS (
	ID NUMBER(4) NOT NULL,
	GRID_ID VARCHAR2(500),
	TERM VARCHAR2(200),
	CTEP_TERM VARCHAR2(200),
	CATEGORY_ID NUMBER(4) NOT NULL,
	MEDRA_CODE NUMBER(10) NOT NULL,
	VERSION NUMBER(4) NOT NULL
);

CREATE TABLE STRATIFICATION_CRITERION (
	ID NUMBER(10) NOT NULL,
	GRID_ID VARCHAR2(500),
	VERSION NUMBER(4) NOT NULL,		
	QUESTION_TEXT VARCHAR2(200),
	QUESTION_NUMBER NUMBER(10),
	STUDY_ID NUMBER(10),
	EPH_ID NUMBER(10)
);

CREATE TABLE STRATIFICATION_CRI_PER_ANS (
	ID NUMBER(10) NOT NULL,
	GRID_ID VARCHAR2(500),
	VERSION NUMBER(4) NOT NULL,		
	PERMISSIBLE_ANSWER varchar(200),
	STR_CRI_ID NUMBER(10) NOT NULL
);

CREATE TABLE CONTACT_MECHANISMS (
	ID NUMBER(10) NOT NULL,
	GRID_ID VARCHAR2(500),
	VERSION NUMBER(4) NOT NULL,		
	TYPE VARCHAR2(200),
	VALUE VARCHAR2(200),
	PRT_ID NUMBER(10),
	INV_ID NUMBER(10),
	RS_ID NUMBER(10)
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
	
ALTER TABLE STUDY_PERSONNELS ADD CONSTRAINT PK_STUDY_PERSONNELS
	PRIMARY KEY (ID) ;

ALTER TABLE RESEARCH_STAFFS ADD CONSTRAINT PK_RESEARCH_STAFFS 
	PRIMARY KEY (ID) ;	
	
ALTER TABLE ELIGIBILITY_CRITERIAS ADD CONSTRAINT PK_ELIGIBILITY_CRITERIAS 
	PRIMARY KEY (ID) ;

ALTER TABLE SUBJECT_ELIGIBILITY_ANSWERS ADD CONSTRAINT PK_PRT_ELIGIBILITY_ANSWERS 
	PRIMARY KEY (ID) ;

ALTER TABLE SUBJECT_STRATIFICATION_ANSWERS ADD CONSTRAINT PK_PRT_STRAT_ANSWERS 
	PRIMARY KEY (ID) ;
	
ALTER TABLE STUDY_DISEASES ADD CONSTRAINT PK_STUDY_DISEASES PRIMARY KEY (id);

ALTER TABLE DISEASE_CATEGORIES ADD CONSTRAINT PK_DISEASE_CATEGORIES PRIMARY KEY (id);

ALTER TABLE DISEASE_TERMS ADD CONSTRAINT PK_DISEASE_TERMS PRIMARY KEY (id);

ALTER TABLE STRATIFICATION_CRITERION ADD CONSTRAINT PK_STRATIFICATION_CRITERION PRIMARY KEY (id);

ALTER TABLE STRATIFICATION_CRI_PER_ANS ADD CONSTRAINT PK_STRATIFICATION_CRI_PER_ANS PRIMARY KEY (id);

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

ALTER TABLE RESEARCH_STAFFS ADD CONSTRAINT FK_RSF_ADD 
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
	
ALTER TABLE STUDY_PERSONNELS ADD CONSTRAINT FK_STDPER_RESSTF
	FOREIGN KEY (RESEARCH_STAFFS_ID) REFERENCES RESEARCH_STAFFS (ID) ;

ALTER TABLE STUDY_PERSONNELS ADD CONSTRAINT FK_STDPER_STDSIT
	FOREIGN KEY (STS_ID) REFERENCES STUDY_SITES (ID) ;

ALTER TABLE ELIGIBILITY_CRITERIAS ADD CONSTRAINT FK_ELGCT_STU 
	FOREIGN KEY (STU_ID) REFERENCES STUDIES (ID) ;
	
ALTER TABLE ELIGIBILITY_CRITERIAS ADD CONSTRAINT FK_ELGCT_EPH 
	FOREIGN KEY (EPH_ID) REFERENCES EPOCHS (ID) ;

ALTER TABLE SUBJECT_ELIGIBILITY_ANSWERS ADD CONSTRAINT FK_PEA_SPA 
	FOREIGN KEY (SPA_ID) REFERENCES STUDY_PARTICIPANT_ASSIGNMENTS (ID) ;

ALTER TABLE SUBJECT_ELIGIBILITY_ANSWERS ADD CONSTRAINT FK_PEA_EC 
	FOREIGN KEY (ELGCT_ID) REFERENCES ELIGIBILITY_CRITERIAS (ID) ;

ALTER TABLE SUBJECT_STRATIFICATION_ANSWERS ADD CONSTRAINT FK_PSA_SC 
	FOREIGN KEY (STR_CRI_ID) REFERENCES STRATIFICATION_CRITERION (ID) ;
	
ALTER TABLE SUBJECT_STRATIFICATION_ANSWERS ADD CONSTRAINT FK_PSA_SPA 
	FOREIGN KEY (SPA_ID) REFERENCES STUDY_PARTICIPANT_ASSIGNMENTS (ID) ;

ALTER TABLE SUBJECT_STRATIFICATION_ANSWERS ADD CONSTRAINT FK_PSA_SC_ANS 
	FOREIGN KEY (STRAT_ANS_ID) REFERENCES STRATIFICATION_CRI_PER_ANS (ID) ;
			
ALTER TABLE STRATIFICATION_CRITERION ADD CONSTRAINT FK_STR_CRI_STUDY 
	FOREIGN KEY (study_id) REFERENCES STUDIES (ID) ;
	
ALTER TABLE STRATIFICATION_CRITERION ADD CONSTRAINT FK_STR_CRI_EPH 
	FOREIGN KEY (eph_id) REFERENCES EPOCHS (ID) ;

ALTER TABLE STRATIFICATION_CRI_PER_ANS ADD CONSTRAINT FK_STR_CRI_PER_ANS_STR_CRI 
	FOREIGN KEY (str_cri_id) REFERENCES STRATIFICATION_CRITERION (ID) ;	

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
	FOREIGN KEY (RS_ID) REFERENCES RESEARCH_STAFFS (ID) ;
	
ALTER TABLE RESEARCH_STAFFS ADD CONSTRAINT FK_RS_ORG 
	FOREIGN KEY (HCS_ID) REFERENCES ORGANIZATIONS(ID);


CREATE SEQUENCE IDENTIFIERS_ID_SEQ
INCREMENT BY 1
START WITH 1
NOMAXVALUE
MINVALUE 1
NOCYCLE
NOCACHE
NOORDER;	

CREATE SEQUENCE ADDRESSES_ID_SEQ
INCREMENT BY 1
START WITH 0
NOMAXVALUE
MINVALUE 0
NOCYCLE
NOCACHE
NOORDER;

CREATE SEQUENCE PARTICIPANTS_ID_SEQ
INCREMENT BY 1
START WITH 0
NOMAXVALUE
MINVALUE 0
NOCYCLE
NOCACHE
NOORDER;

CREATE SEQUENCE STUDIES_ID_SEQ
INCREMENT BY 1
START WITH 0
NOMAXVALUE
MINVALUE 0
NOCYCLE
NOCACHE
NOORDER;

CREATE SEQUENCE EPOCHS_ID_SEQ
INCREMENT BY 1
START WITH 0
NOMAXVALUE
MINVALUE 0
NOCYCLE
NOCACHE
NOORDER;

CREATE SEQUENCE ARMS_ID_SEQ
INCREMENT BY 1
START WITH 0
NOMAXVALUE
MINVALUE 0
NOCYCLE
NOCACHE
NOORDER;

CREATE SEQUENCE STUDY_SITES_ID_SEQ
INCREMENT BY 1
START WITH 0
NOMAXVALUE
MINVALUE 0
NOCYCLE
NOCACHE
NOORDER;

CREATE SEQUENCE ORGANIZATIONS_ID_SEQ
INCREMENT BY 1
START WITH 0
NOMAXVALUE
MINVALUE 0
NOCYCLE
NOCACHE
NOORDER;

CREATE SEQUENCE INVESTIGATORS_ID_SEQ
INCREMENT BY 1
START WITH 0
NOMAXVALUE
MINVALUE 0
NOCYCLE
NOCACHE
NOORDER;

CREATE SEQUENCE STUDY_INVESTIGATORS_ID_SEQ
INCREMENT BY 1
START WITH 0
NOMAXVALUE
MINVALUE 0
NOCYCLE
NOCACHE
NOORDER;

CREATE SEQUENCE HC_SITE_INVEStIGATORS_ID_SEQ
INCREMENT BY 1
START WITH 0
NOMAXVALUE
MINVALUE 0
NOCYCLE
NOCACHE
NOORDER;


CREATE SEQUENCE SCHEDULED_ARMS_ID_SEQ
INCREMENT BY 1
START WITH 0
NOMAXVALUE
MINVALUE 0
NOCYCLE
NOCACHE
NOORDER;


CREATE SEQUENCE STUDY_PARTICIPANT_ASSIG_ID_SEQ
INCREMENT BY 1
START WITH 0
NOMAXVALUE
MINVALUE 0
NOCYCLE
NOCACHE
NOORDER;


CREATE SEQUENCE STUDY_PERSONNELS_ID_SEQ
INCREMENT BY 1
START WITH 1
NOMAXVALUE
MINVALUE 1
NOCYCLE
NOCACHE
NOORDER;	
	

CREATE SEQUENCE RESEARCH_STAFFS_ID_SEQ
INCREMENT BY 1
START WITH 1
NOMAXVALUE
MINVALUE 1
NOCYCLE
NOCACHE
NOORDER;	

CREATE SEQUENCE ELIGIBILITY_CRITERIAS_ID_SEQ
INCREMENT BY 1
START WITH 1
NOMAXVALUE
MINVALUE 1
NOCYCLE
NOCACHE
NOORDER;	

CREATE SEQUENCE PRT_ELIGIBILITY_ANS_ID_SEQ
INCREMENT BY 1
START WITH 1
NOMAXVALUE
MINVALUE 1
NOCYCLE
NOCACHE
NOORDER;	

CREATE SEQUENCE PRT_STRAT_ANS_ID_SEQ
INCREMENT BY 1
START WITH 1
NOMAXVALUE
MINVALUE 1
NOCYCLE
NOCACHE
NOORDER;

CREATE SEQUENCE STUDY_DISEASES_ID_SEQ
INCREMENT BY 1
START WITH 1
NOMAXVALUE
MINVALUE 1
NOCYCLE
NOCACHE
NOORDER;

CREATE SEQUENCE DISEASE_CATEGORIES_ID_SEQ
INCREMENT BY 1
START WITH 1
NOMAXVALUE
MINVALUE 1
NOCYCLE
NOCACHE
NOORDER;

CREATE SEQUENCE DISEASE_TERMS_ID_SEQ
INCREMENT BY 1
START WITH 1
NOMAXVALUE
MINVALUE 1
NOCYCLE
NOCACHE
NOORDER;

CREATE SEQUENCE STRATIFICATION_CRI_ID_SEQ
INCREMENT BY 1
START WITH 1
NOMAXVALUE
MINVALUE 1
NOCYCLE
NOCACHE
NOORDER;

CREATE SEQUENCE STRATIFICATION_CRI_ANS_ID_SEQ
INCREMENT BY 1
START WITH 1
NOMAXVALUE
MINVALUE 1
NOCYCLE
NOCACHE
NOORDER;

CREATE SEQUENCE ANATOMIC_SITES_ID_SEQ
INCREMENT BY 1
START WITH 1
NOMAXVALUE
MINVALUE 1
NOCYCLE
NOCACHE
NOORDER;

CREATE SEQUENCE DISEASE_HISTORY_ID_SEQ
INCREMENT BY 1
START WITH 1
NOMAXVALUE
MINVALUE 1
NOCYCLE
NOCACHE
NOORDER;

CREATE SEQUENCE CONTACT_MECHANISMS_ID_SEQ
INCREMENT BY 1
START WITH 1
NOMAXVALUE
MINVALUE 1
NOCYCLE
NOCACHE
NOORDER;