INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('system_administrator','edu.duke.cabig.c3pr.utils.web.navigation.Task.Configuration', 'CREATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('system_administrator','edu.duke.cabig.c3pr.utils.web.navigation.Task.Configuration', 'UPDATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('system_administrator','edu.duke.cabig.c3pr.utils.web.navigation.Task.Configuration', 'READ', role_privileges_id_seq.nextval);


INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('business_administrator','edu.duke.cabig.c3pr.utils.web.navigation.Task.Notification', 'CREATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('business_administrator','edu.duke.cabig.c3pr.utils.web.navigation.Task.Notification', 'UPDATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('business_administrator','edu.duke.cabig.c3pr.utils.web.navigation.Task.Notification', 'READ', role_privileges_id_seq.nextval);


INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('person_and_organization_information_manager','edu.duke.cabig.c3pr.domain.HealthcareSite', 'CREATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('person_and_organization_information_manager','edu.duke.cabig.c3pr.domain.HealthcareSite', 'UPDATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('person_and_organization_information_manager','edu.duke.cabig.c3pr.domain.HealthcareSite', 'READ', role_privileges_id_seq.nextval);

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('person_and_organization_information_manager','edu.duke.cabig.c3pr.domain.ResearchStaff', 'CREATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('person_and_organization_information_manager','edu.duke.cabig.c3pr.domain.ResearchStaff', 'UPDATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('person_and_organization_information_manager','edu.duke.cabig.c3pr.domain.ResearchStaff', 'READ', role_privileges_id_seq.nextval);

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('person_and_organization_information_manager','edu.duke.cabig.c3pr.domain.Investigator', 'CREATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('person_and_organization_information_manager','edu.duke.cabig.c3pr.domain.Investigator', 'UPDATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('person_and_organization_information_manager','edu.duke.cabig.c3pr.domain.Investigator', 'READ', role_privileges_id_seq.nextval);


INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('data_importer','edu.duke.cabig.c3pr.utils.web.navigation.Task.Import', 'CREATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('data_importer','edu.duke.cabig.c3pr.utils.web.navigation.Task.Import', 'UPDATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('data_importer','edu.duke.cabig.c3pr.utils.web.navigation.Task.Import', 'READ', role_privileges_id_seq.nextval);


INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('user_administrator','edu.duke.cabig.c3pr.domain.C3PRUser', 'CREATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('user_administrator','edu.duke.cabig.c3pr.domain.C3PRUser', 'UPDATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('user_administrator','edu.duke.cabig.c3pr.domain.C3PRUser', 'READ', role_privileges_id_seq.nextval);


INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_qa_manager','edu.duke.cabig.c3pr.domain.Study', 'OVERRIDE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_qa_manager','edu.duke.cabig.c3pr.domain.Study', 'UPDATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_qa_manager','edu.duke.cabig.c3pr.domain.Study', 'READ', role_privileges_id_seq.nextval);


INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_creator','edu.duke.cabig.c3pr.domain.Study', 'CREATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_creator','edu.duke.cabig.c3pr.domain.Study', 'UPDATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_creator','edu.duke.cabig.c3pr.domain.Study', 'READ', role_privileges_id_seq.nextval);


INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('supplemental_study_information_manager','edu.duke.cabig.c3pr.domain.Study', 'UPDATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('supplemental_study_information_manager','edu.duke.cabig.c3pr.domain.Study', 'READ', role_privileges_id_seq.nextval);


INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_team_administrator','edu.duke.cabig.c3pr.domain.StudyPersonnel', 'CREATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_team_administrator','edu.duke.cabig.c3pr.domain.StudyPersonnel', 'UPDATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_team_administrator','edu.duke.cabig.c3pr.domain.StudyPersonnel', 'READ', role_privileges_id_seq.nextval);

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_site_participation_administrator','edu.duke.cabig.c3pr.domain.StudySite', 'CREATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_site_participation_administrator','edu.duke.cabig.c3pr.domain.StudySite', 'UPDATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_site_participation_administrator','edu.duke.cabig.c3pr.domain.StudySite', 'READ', role_privileges_id_seq.nextval);


INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('registration_qa_manager','edu.duke.cabig.c3pr.domain.StudySubject', 'OVERRIDE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('registration_qa_manager','edu.duke.cabig.c3pr.domain.StudySubject', 'UPDATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('registration_qa_manager','edu.duke.cabig.c3pr.domain.StudySubject', 'READ', role_privileges_id_seq.nextval);


INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('subject_manager','edu.duke.cabig.c3pr.domain.Subject', 'CREATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('subject_manager','edu.duke.cabig.c3pr.domain.Subject', 'UPDATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('subject_manager','edu.duke.cabig.c3pr.domain.Subject', 'READ', role_privileges_id_seq.nextval);


INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('registrar','edu.duke.cabig.c3pr.domain.StudySubject', 'CREATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('registrar','edu.duke.cabig.c3pr.domain.StudySubject', 'UPDATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('registrar','edu.duke.cabig.c3pr.domain.StudySubject', 'READ', role_privileges_id_seq.nextval);


INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('data_reader','edu.duke.cabig.c3pr.domain.Summary3Report', 'CREATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('data_reader','edu.duke.cabig.c3pr.domain.Summary3Report', 'UPDATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('data_reader','edu.duke.cabig.c3pr.domain.Summary3Report', 'READ', role_privileges_id_seq.nextval);


INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('data_analyst','edu.duke.cabig.c3pr.domain.Summary3Report', 'CREATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('data_analyst','edu.duke.cabig.c3pr.domain.Summary3Report', 'UPDATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('data_analyst','edu.duke.cabig.c3pr.domain.Summary3Report', 'READ', role_privileges_id_seq.nextval);
