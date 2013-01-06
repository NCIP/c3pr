---  Create Tables
CREATE TABLE CSM_APPLICATION (
	APPLICATION_ID integer NOT NULL DEFAULT nextval('CSM_APPLICATI_APPLICATION__SEQ'::text),
	APPLICATION_NAME varchar(100) NOT NULL,
	APPLICATION_DESCRIPTION varchar(200) NOT NULL,
	DECLARATIVE_FLAG integer,
	ACTIVE_FLAG integer NOT NULL,
	UPDATE_DATE timestamp,
	DATABASE_URL varchar(100),
	DATABASE_USER_NAME varchar(100),
	DATABASE_PASSWORD varchar(100),
	DATABASE_DIALECT varchar(100),
	DATABASE_DRIVER varchar(100)
)
;

CREATE TABLE CSM_GROUP (
	GROUP_ID integer NOT NULL DEFAULT nextval('CSM_GROUP_GROUP_ID_SEQ'::text),
	GROUP_NAME varchar(100) NOT NULL,
	GROUP_DESC varchar(200),
	UPDATE_DATE timestamp,
	APPLICATION_ID integer NOT NULL
)
;

CREATE TABLE CSM_PG_PE (
	PG_PE_ID integer NOT NULL DEFAULT nextval('CSM_PG_PE_ID_SEQ'::text),
	PROTECTION_GROUP_ID integer NOT NULL,
	PROTECTION_ELEMENT_ID integer NOT NULL,
	UPDATE_DATE timestamp
)
;

CREATE TABLE CSM_PRIVILEGE (
	PRIVILEGE_ID integer NOT NULL DEFAULT nextval('CSM_PRIVILEGE_PRIVILEGE_ID_SEQ'::text),
	PRIVILEGE_NAME varchar(100) NOT NULL,
	PRIVILEGE_DESCRIPTION varchar(200),
	UPDATE_DATE timestamp
)
;

CREATE TABLE CSM_PROTECTION_ELEMENT (
	PROTECTION_ELEMENT_ID integer NOT NULL DEFAULT nextval('CSM_PROTECTIO_PROTECTION_E_SEQ'::text),
	PROTECTION_ELEMENT_NAME varchar(100) NOT NULL,
	PROTECTION_ELEMENT_DESCRIPTION varchar(200),
	OBJECT_ID varchar(100) NOT NULL,
	ATTRIBUTE varchar(100),
	PROTECTION_ELEMENT_TYPE_ID integer,
	APPLICATION_ID integer NOT NULL,
	UPDATE_DATE timestamp
)
;

CREATE TABLE CSM_PROTECTION_GROUP (
	PROTECTION_GROUP_ID integer NOT NULL DEFAULT nextval('CSM_PROTECTIO_PROTECTION_G_SEQ'::text),
	PROTECTION_GROUP_NAME varchar(100) NOT NULL,
	PROTECTION_GROUP_DESCRIPTION varchar(200),
	APPLICATION_ID integer NOT NULL,
	LARGE_ELEMENT_COUNT_FLAG integer NOT NULL,
	UPDATE_DATE timestamp,
	PARENT_PROTECTION_GROUP_ID integer
)
;

CREATE TABLE CSM_ROLE (
	ROLE_ID integer NOT NULL DEFAULT nextval('CSM_ROLE_ROLE_ID_SEQ'::text),
	ROLE_NAME varchar(100) NOT NULL,
	ROLE_DESCRIPTION varchar(200),
	APPLICATION_ID integer NOT NULL,
	ACTIVE_FLAG integer NOT NULL,
	UPDATE_DATE timestamp
)
;

CREATE TABLE CSM_ROLE_PRIVILEGE (
	ROLE_PRIVILEGE_ID integer NOT NULL DEFAULT nextval('CSM_ROLE_PRIV_SEQ'::text),
	ROLE_ID integer NOT NULL,
	PRIVILEGE_ID integer NOT NULL,
	UPDATE_DATE timestamp
)
;

CREATE TABLE CSM_USER (
	USER_ID integer NOT NULL DEFAULT nextval('CSM_USER_USER_ID_SEQ'::text),
	LOGIN_NAME varchar(100) NOT NULL,
	FIRST_NAME varchar(100) NOT NULL,
	LAST_NAME varchar(100) NOT NULL,
	ORGANIZATION varchar(100),
	DEPARTMENT varchar(100),
	TITLE varchar(100),
	PHONE_NUMBER varchar(15),
	PASSWORD varchar(100),
	EMAIL_ID varchar(100),
	START_DATE timestamp,
	END_DATE timestamp,
	UPDATE_DATE timestamp
)
;

CREATE TABLE CSM_USER_GROUP (
	USER_GROUP_ID integer NOT NULL DEFAULT nextval('csm_user_grou_user_group_i_seq'::text),
	USER_ID integer NOT NULL,
	GROUP_ID integer NOT NULL
)
;

CREATE TABLE CSM_USER_GROUP_ROLE_PG (
	USER_GROUP_ROLE_PG_ID integer NOT NULL DEFAULT nextval('CSM_USER_GROU_USER_GROUP_R_SEQ'::text),
	USER_ID integer,
	GROUP_ID integer,
	ROLE_ID integer NOT NULL,
	PROTECTION_GROUP_ID integer NOT NULL,
	UPDATE_DATE timestamp
)
;

CREATE TABLE CSM_USER_PE (
	USER_PROTECTION_ELEMENT_ID integer NOT NULL DEFAULT nextval('CSM_USER_PE_USER_PROTECTIO_SEQ'::text),
	PROTECTION_ELEMENT_ID integer NOT NULL,
	USER_ID integer NOT NULL,
	UPDATE_DATE timestamp
)
;


--  Create Primary Key Constraints
ALTER TABLE CSM_APPLICATION ADD CONSTRAINT PK_APPLICATION
PRIMARY KEY (APPLICATION_ID)
;

ALTER TABLE CSM_GROUP ADD CONSTRAINT PK_GROUP
PRIMARY KEY (GROUP_ID)
;

ALTER TABLE CSM_PG_PE ADD CONSTRAINT PK_PG_PE
PRIMARY KEY (PG_PE_ID)
;

ALTER TABLE CSM_PRIVILEGE ADD CONSTRAINT PK_PRIVILEGE
PRIMARY KEY (PRIVILEGE_ID)
;

ALTER TABLE CSM_PROTECTION_ELEMENT ADD CONSTRAINT PK_PROTECTION_ELEMENT
PRIMARY KEY (PROTECTION_ELEMENT_ID)
;

ALTER TABLE CSM_PROTECTION_GROUP ADD CONSTRAINT PK_PROTECTION_GROUP
PRIMARY KEY (PROTECTION_GROUP_ID)
;

ALTER TABLE CSM_ROLE ADD CONSTRAINT PK_ROLE
PRIMARY KEY (ROLE_ID)
;

ALTER TABLE CSM_ROLE_PRIVILEGE ADD CONSTRAINT PK_ROLE_PRIVILEGE
PRIMARY KEY (ROLE_PRIVILEGE_ID)
;

ALTER TABLE CSM_USER ADD CONSTRAINT PK_USER
PRIMARY KEY (USER_ID)
;

ALTER TABLE CSM_USER_GROUP ADD CONSTRAINT PK_USER_GROUP
PRIMARY KEY (USER_GROUP_ID)
;

ALTER TABLE CSM_USER_GROUP_ROLE_PG ADD CONSTRAINT PK_USER_GROUP_ROLE_PG
PRIMARY KEY (USER_GROUP_ROLE_PG_ID)
;

ALTER TABLE CSM_USER_PE ADD CONSTRAINT PK_USER_PROTECTION_ELEMENT
PRIMARY KEY (USER_PROTECTION_ELEMENT_ID)
;


--  Create Indexes
ALTER TABLE CSM_APPLICATION
ADD CONSTRAINT UQ_APPLICATION_NAME UNIQUE (APPLICATION_NAME)
;
ALTER TABLE CSM_GROUP
ADD CONSTRAINT UQ_GROUP_GROUP_NAME UNIQUE (APPLICATION_ID, GROUP_NAME)
;
ALTER TABLE CSM_PG_PE
ADD CONSTRAINT UQ_PG_PE_PG_PE_ID UNIQUE (PROTECTION_ELEMENT_ID, PROTECTION_GROUP_ID)
;
ALTER TABLE CSM_PRIVILEGE
ADD CONSTRAINT UQ_PRIVILEGE_NAME UNIQUE (PRIVILEGE_NAME)
;
ALTER TABLE CSM_PROTECTION_ELEMENT
ADD CONSTRAINT UQ_PE_OBJ_ATT_APP_ID UNIQUE (OBJECT_ID, ATTRIBUTE, APPLICATION_ID)
;
ALTER TABLE CSM_PROTECTION_GROUP
ADD CONSTRAINT UQ_PG_PG_NAME UNIQUE (APPLICATION_ID, PROTECTION_GROUP_NAME)
;
ALTER TABLE CSM_ROLE
ADD CONSTRAINT UQ_ROLE_ROLE_NAME UNIQUE (APPLICATION_ID, ROLE_NAME)
;
ALTER TABLE CSM_ROLE_PRIVILEGE
ADD CONSTRAINT UQ_ROLE_ID_PRIVILEGE_ID UNIQUE (PRIVILEGE_ID, ROLE_ID)
;
ALTER TABLE CSM_USER
ADD CONSTRAINT UQ_LOGIN_NAME UNIQUE (LOGIN_NAME)
;
ALTER TABLE CSM_USER_PE
ADD CONSTRAINT UQ_USER_PE_PE_ID UNIQUE (USER_ID, PROTECTION_ELEMENT_ID)
;


--  Create Foreign Key Constraints
ALTER TABLE CSM_GROUP ADD CONSTRAINT FK_CSM_GROUP_CSM_APPLICATION
FOREIGN KEY (APPLICATION_ID) REFERENCES CSM_APPLICATION (APPLICATION_ID)
ON DELETE CASCADE
;

ALTER TABLE CSM_PG_PE ADD CONSTRAINT FK_PG_PE_PE
FOREIGN KEY (PROTECTION_ELEMENT_ID) REFERENCES CSM_PROTECTION_ELEMENT (PROTECTION_ELEMENT_ID)
ON DELETE CASCADE
;

ALTER TABLE CSM_PG_PE ADD CONSTRAINT FK_PG_PE_PG
FOREIGN KEY (PROTECTION_GROUP_ID) REFERENCES CSM_PROTECTION_GROUP (PROTECTION_GROUP_ID)
ON DELETE CASCADE
;

ALTER TABLE CSM_PROTECTION_ELEMENT ADD CONSTRAINT FK_PE_APPLICATION
FOREIGN KEY (APPLICATION_ID) REFERENCES CSM_APPLICATION (APPLICATION_ID)
ON DELETE CASCADE
;

ALTER TABLE CSM_PROTECTION_GROUP ADD CONSTRAINT FK_PG_APPLICATION
FOREIGN KEY (APPLICATION_ID) REFERENCES CSM_APPLICATION (APPLICATION_ID)
ON DELETE CASCADE
;

ALTER TABLE CSM_PROTECTION_GROUP ADD CONSTRAINT FK_PG_PG
FOREIGN KEY (PARENT_PROTECTION_GROUP_ID) REFERENCES CSM_PROTECTION_GROUP (PROTECTION_GROUP_ID)
;

ALTER TABLE CSM_ROLE ADD CONSTRAINT FK_ROLE_APPLICATION
FOREIGN KEY (APPLICATION_ID) REFERENCES CSM_APPLICATION (APPLICATION_ID)
ON DELETE CASCADE
;

ALTER TABLE CSM_ROLE_PRIVILEGE ADD CONSTRAINT FK_ROLE_PRIVILEGE_PRIVILEGE
FOREIGN KEY (PRIVILEGE_ID) REFERENCES CSM_PRIVILEGE (PRIVILEGE_ID)
ON DELETE CASCADE
;

ALTER TABLE CSM_ROLE_PRIVILEGE ADD CONSTRAINT FK_ROLE_PRIVILEGE_ROLE
FOREIGN KEY (ROLE_ID) REFERENCES CSM_ROLE (ROLE_ID)
ON DELETE CASCADE
;

ALTER TABLE CSM_USER_GROUP ADD CONSTRAINT FK_USER_GROUP_GROUP
FOREIGN KEY (GROUP_ID) REFERENCES CSM_GROUP (GROUP_ID)
ON DELETE CASCADE
;

ALTER TABLE CSM_USER_GROUP ADD CONSTRAINT FK_USER_GROUP_USER
FOREIGN KEY (USER_ID) REFERENCES CSM_USER (USER_ID)
ON DELETE CASCADE
;

ALTER TABLE CSM_USER_GROUP_ROLE_PG ADD CONSTRAINT FK_USER_GROUP_ROLE_PG_GROUP
FOREIGN KEY (GROUP_ID) REFERENCES CSM_GROUP (GROUP_ID)
;

ALTER TABLE CSM_USER_GROUP_ROLE_PG ADD CONSTRAINT FK_USER_GROUP_ROLE_PG_PG
FOREIGN KEY (PROTECTION_GROUP_ID) REFERENCES CSM_PROTECTION_GROUP (PROTECTION_GROUP_ID)
ON DELETE CASCADE
;

ALTER TABLE CSM_USER_GROUP_ROLE_PG ADD CONSTRAINT FK_USER_GROUP_ROLE_PG_ROLE
FOREIGN KEY (ROLE_ID) REFERENCES CSM_ROLE (ROLE_ID)
ON DELETE CASCADE
;

ALTER TABLE CSM_USER_GROUP_ROLE_PG ADD CONSTRAINT FK_USER_GROUP_ROLE_PG_USER
FOREIGN KEY (USER_ID) REFERENCES CSM_USER (USER_ID)
ON DELETE CASCADE
;

ALTER TABLE CSM_USER_PE ADD CONSTRAINT FK_USER_PE_USER
FOREIGN KEY (USER_ID) REFERENCES CSM_USER (USER_ID)
ON DELETE CASCADE
;

ALTER TABLE CSM_USER_PE ADD CONSTRAINT FK_USER_PE_PE
FOREIGN KEY (PROTECTION_ELEMENT_ID) REFERENCES CSM_PROTECTION_ELEMENT (PROTECTION_ELEMENT_ID)
ON DELETE CASCADE
;

CREATE SEQUENCE CSM_APPLICATI_APPLICATION__SEQ;
CREATE SEQUENCE CSM_GROUP_GROUP_ID_SEQ;
CREATE SEQUENCE CSM_PG_PE_ID_SEQ;
CREATE SEQUENCE CSM_PRIVILEGE_PRIVILEGE_ID_SEQ;
CREATE SEQUENCE CSM_PROTECTIO_PROTECTION_E_SEQ;
CREATE SEQUENCE CSM_PROTECTIO_PROTECTION_G_SEQ;
CREATE SEQUENCE CSM_ROLE_ROLE_ID_SEQ;
CREATE SEQUENCE CSM_ROLE_PRIV_SEQ;
CREATE SEQUENCE CSM_USER_USER_ID_SEQ;
CREATE SEQUENCE csm_user_grou_user_group_i_seq;
CREATE SEQUENCE CSM_USER_GROU_USER_GROUP_R_SEQ;
CREATE SEQUENCE CSM_USER_PE_USER_PROTECTIO_SEQ;