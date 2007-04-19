ALTER TABLE CSM_APPLICATION MODIFY  DECLARATIVE_FLAG NUMBER(1) DEFAULT 0 NOT NULL ;
ALTER TABLE CSM_PROTECTION_ELEMENT DROP COLUMN PROTECTION_ELEMENT_TYPE_ID;
ALTER TABLE CSM_PROTECTION_ELEMENT ADD PROTECTION_ELEMENT_TYPE VARCHAR(100);
ALTER TABLE CSM_PROTECTION_ELEMENT ADD ( CONSTRAINT UQ_PE_OBJ_ID_ATTR_APP_ID UNIQUE (PROTECTION_ELEMENT_NAME, ATTRIBUTE, APPLICATION_ID));

ALTER TABLE CSM_USER_GROUP_ROLE_PG DROP CONSTRAINT FK_USER_GROUP_ROLE_PG_GROUP;

ALTER TABLE CSM_USER_GROUP_ROLE_PG ADD
    ( CONSTRAINT FK_USER_GROUP_ROLE_PG_GROUP FOREIGN KEY (GROUP_ID) REFERENCES CSM_GROUP (GROUP_ID) ON DELETE CASCADE );

ALTER TABLE CSM_APPLICATION MODIFY APPLICATION_NAME varchar2(255) ;
ALTER TABLE CSM_GROUP MODIFY GROUP_NAME varchar2(255) ;
ALTER TABLE CSM_APPLICATION ADD DATABASE_URL VARCHAR(100);
ALTER TABLE CSM_APPLICATION ADD DATABASE_USER_NAME VARCHAR(100);
ALTER TABLE CSM_APPLICATION ADD DATABASE_PASSWORD VARCHAR(100);
ALTER TABLE CSM_APPLICATION ADD DATABASE_DIALECT VARCHAR(100);
ALTER TABLE CSM_APPLICATION ADD DATABASE_DRIVER VARCHAR(100);

UPDATE csm_application SET csm_application.DECLARATIVE_FLAG='0';
UPDATE csm_pg_pe SET UPDATE_DATE=NULL;
