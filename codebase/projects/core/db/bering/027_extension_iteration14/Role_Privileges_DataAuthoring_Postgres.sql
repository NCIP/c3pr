INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('system_administrator','edu.duke.cabig.c3pr.utils.web.navigation.Task.Configuration', 'CREATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('system_administrator','edu.duke.cabig.c3pr.utils.web.navigation.Task.Configuration', 'UPDATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('system_administrator','edu.duke.cabig.c3pr.utils.web.navigation.Task.Configuration', 'READ', (select nextval('role_privileges_ID_SEQ')));


INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('business_administrator','edu.duke.cabig.c3pr.utils.web.navigation.Task.Notification', 'CREATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('business_administrator','edu.duke.cabig.c3pr.utils.web.navigation.Task.Notification', 'UPDATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('business_administrator','edu.duke.cabig.c3pr.utils.web.navigation.Task.Notification', 'READ', (select nextval('role_privileges_ID_SEQ')));


INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('person_and_organization_information_manager','edu.duke.cabig.c3pr.domain.HealthcareSite', 'CREATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('person_and_organization_information_manager','edu.duke.cabig.c3pr.domain.HealthcareSite', 'UPDATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('person_and_organization_information_manager','edu.duke.cabig.c3pr.domain.HealthcareSite', 'READ', (select nextval('role_privileges_ID_SEQ')));

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('person_and_organization_information_manager','edu.duke.cabig.c3pr.domain.ResearchStaff', 'CREATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('person_and_organization_information_manager','edu.duke.cabig.c3pr.domain.ResearchStaff', 'UPDATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('person_and_organization_information_manager','edu.duke.cabig.c3pr.domain.ResearchStaff', 'READ', (select nextval('role_privileges_ID_SEQ')));

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('person_and_organization_information_manager','edu.duke.cabig.c3pr.domain.Investigator', 'CREATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('person_and_organization_information_manager','edu.duke.cabig.c3pr.domain.Investigator', 'UPDATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('person_and_organization_information_manager','edu.duke.cabig.c3pr.domain.Investigator', 'READ', (select nextval('role_privileges_ID_SEQ')));


INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('data_importer','edu.duke.cabig.c3pr.utils.web.navigation.Task.Import', 'CREATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('data_importer','edu.duke.cabig.c3pr.utils.web.navigation.Task.Import', 'UPDATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('data_importer','edu.duke.cabig.c3pr.utils.web.navigation.Task.Import', 'READ', (select nextval('role_privileges_ID_SEQ')));


INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('user_administrator','edu.duke.cabig.c3pr.domain.C3PRUser', 'CREATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('user_administrator','edu.duke.cabig.c3pr.domain.C3PRUser', 'UPDATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('user_administrator','edu.duke.cabig.c3pr.domain.C3PRUser', 'READ', (select nextval('role_privileges_ID_SEQ')));


INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_qa_manager','edu.duke.cabig.c3pr.domain.Study', 'OVERRIDE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_qa_manager','edu.duke.cabig.c3pr.domain.Study', 'UPDATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_qa_manager','edu.duke.cabig.c3pr.domain.Study', 'READ', (select nextval('role_privileges_ID_SEQ')));


INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_creator','edu.duke.cabig.c3pr.domain.Study', 'CREATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_creator','edu.duke.cabig.c3pr.domain.Study', 'UPDATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_creator','edu.duke.cabig.c3pr.domain.Study', 'READ', (select nextval('role_privileges_ID_SEQ')));


INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('supplemental_study_information_manager','edu.duke.cabig.c3pr.domain.Study', 'UPDATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('supplemental_study_information_manager','edu.duke.cabig.c3pr.domain.Study', 'READ', (select nextval('role_privileges_ID_SEQ')));


INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_team_administrator','edu.duke.cabig.c3pr.domain.StudyPersonnel', 'CREATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_team_administrator','edu.duke.cabig.c3pr.domain.StudyPersonnel', 'UPDATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_team_administrator','edu.duke.cabig.c3pr.domain.StudyPersonnel', 'READ', (select nextval('role_privileges_ID_SEQ')));

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_site_participation_administrator','edu.duke.cabig.c3pr.domain.StudySite', 'CREATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_site_participation_administrator','edu.duke.cabig.c3pr.domain.StudySite', 'UPDATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_site_participation_administrator','edu.duke.cabig.c3pr.domain.StudySite', 'READ', (select nextval('role_privileges_ID_SEQ')));


INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('registration_qa_manager','edu.duke.cabig.c3pr.domain.StudySubject', 'OVERRIDE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('registration_qa_manager','edu.duke.cabig.c3pr.domain.StudySubject', 'UPDATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('registration_qa_manager','edu.duke.cabig.c3pr.domain.StudySubject', 'READ', (select nextval('role_privileges_ID_SEQ')));


INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('subject_manager','edu.duke.cabig.c3pr.domain.Subject', 'CREATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('subject_manager','edu.duke.cabig.c3pr.domain.Subject', 'UPDATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('subject_manager','edu.duke.cabig.c3pr.domain.Subject', 'READ', (select nextval('role_privileges_ID_SEQ')));


INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('registrar','edu.duke.cabig.c3pr.domain.StudySubject', 'CREATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('registrar','edu.duke.cabig.c3pr.domain.StudySubject', 'UPDATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('registrar','edu.duke.cabig.c3pr.domain.StudySubject', 'READ', (select nextval('role_privileges_ID_SEQ')));


INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('data_reader','edu.duke.cabig.c3pr.domain.Summary3Report', 'CREATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('data_reader','edu.duke.cabig.c3pr.domain.Summary3Report', 'UPDATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('data_reader','edu.duke.cabig.c3pr.domain.Summary3Report', 'READ', (select nextval('role_privileges_ID_SEQ')));


INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('data_analyst','edu.duke.cabig.c3pr.domain.Summary3Report', 'CREATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('data_analyst','edu.duke.cabig.c3pr.domain.Summary3Report', 'UPDATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('data_analyst','edu.duke.cabig.c3pr.domain.Summary3Report', 'READ', (select nextval('role_privileges_ID_SEQ')));
