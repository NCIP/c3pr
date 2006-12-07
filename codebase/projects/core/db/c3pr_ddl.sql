CREATE TABLE STUDYS (
		ID NUMBER(10 , 0) NOT NULL,
		BLINDED_INDICATOR VARCHAR2(4),
		DISEASE_CODE VARCHAR2(20),
		DISEASE_CODE_LONG_TITLE_TEXT VARCHAR2(50) NOT NULL,
		MONITOR_CODE VARCHAR2(20),
		MULTI_INSTITUTION_INDICATOR VARCHAR2(4) NOT NULL,
		NCI_IDENTIFIER VARCHAR2(4),
		PHASE_CODE VARCHAR2(20) NOT NULL,
		RANDOMIZED_INDICATOR NVARCHAR2(20),
		SHORT_TITLE_TEXT VARCHAR2(30),
		SPONSOR_CODE VARCHAR2(20) NOT NULL,
		TARGET_ACCRUAL_NUMBER NUMBER(10 , 0),
		TYPE_CODE VARCHAR2(20) NOT NULL,
		DESCRIPTION_TEXT VARCHAR2(50),
		PRECIS_TEXT VARCHAR2(30),
		STATUS_CODE VARCHAR2(6) NOT NULL
	);

CREATE INDEX PK_STUDYS ON STUDYS (ID ASC);

ALTER TABLE STUDYS ADD CONSTRAINT PK_STUDYS PRIMARY KEY (ID);

