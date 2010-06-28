INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('user_administrator','edu.duke.cabig.c3pr.domain.ResearchStaff', 'READ', (select nextval('role_privileges_ID_SEQ')));

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_qa_manager','edu.duke.cabig.c3pr.domain.StudyPersonnel', 'CREATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_qa_manager','edu.duke.cabig.c3pr.domain.StudyPersonnel', 'UPDATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_qa_manager','edu.duke.cabig.c3pr.domain.StudyPersonnel', 'READ', (select nextval('role_privileges_ID_SEQ')));

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_qa_manager','edu.duke.cabig.c3pr.domain.StudySite', 'CREATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_qa_manager','edu.duke.cabig.c3pr.domain.StudySite', 'UPDATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_qa_manager','edu.duke.cabig.c3pr.domain.StudySite', 'READ', (select nextval('role_privileges_ID_SEQ')));

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_creator','edu.duke.cabig.c3pr.domain.HealthcareSite', 'READ', (select nextval('role_privileges_ID_SEQ')));


INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('registrar','edu.duke.cabig.c3pr.domain.Study', 'READ', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('registrar','edu.duke.cabig.c3pr.domain.Subject', 'READ', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('registrar','edu.duke.cabig.c3pr.domain.StudySite', 'READ', (select nextval('role_privileges_ID_SEQ')));

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_team_administrator','edu.duke.cabig.c3pr.domain.Study', 'READ', (select nextval('role_privileges_ID_SEQ')));

--Another set of updates
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('business_administrator','edu.duke.cabig.c3pr.domain.Summary3Report', 'READ', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('business_administrator','edu.duke.cabig.c3pr.domain.Summary3Report', 'CREATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('business_administrator','edu.duke.cabig.c3pr.domain.Summary3Report', 'UPDATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('business_administrator','edu.duke.cabig.c3pr.domain.Study', 'READ', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('business_administrator','edu.duke.cabig.c3pr.domain.StudySubject', 'READ', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('business_administrator','edu.duke.cabig.c3pr.domain.Subject', 'READ', (select nextval('role_privileges_ID_SEQ')));

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('data_reader','edu.duke.cabig.c3pr.domain.Study', 'READ', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('data_reader','edu.duke.cabig.c3pr.domain.StudySubject', 'READ', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('data_reader','edu.duke.cabig.c3pr.domain.Subject', 'READ', (select nextval('role_privileges_ID_SEQ')));

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('data_analyst','edu.duke.cabig.c3pr.domain.Study', 'READ', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('data_analyst','edu.duke.cabig.c3pr.domain.StudySubject', 'READ', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('data_analyst','edu.duke.cabig.c3pr.domain.Subject', 'READ', (select nextval('role_privileges_ID_SEQ')));

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('registration_qa_manager','edu.duke.cabig.c3pr.domain.Study', 'READ', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('registration_qa_manager','edu.duke.cabig.c3pr.domain.Subject', 'READ', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('registration_qa_manager','edu.duke.cabig.c3pr.domain.StudySite', 'READ', (select nextval('role_privileges_ID_SEQ')));

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_qa_manager','edu.duke.cabig.c3pr.domain.StudySubject', 'READ', (select nextval('role_privileges_ID_SEQ')));

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_site_participation_administrator','edu.duke.cabig.c3pr.domain.Study', 'READ', (select nextval('role_privileges_ID_SEQ')));