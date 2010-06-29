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
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_site_participation_administrator','edu.duke.cabig.c3pr.domain.Study', 'UPDATE', (select nextval('role_privileges_ID_SEQ')));

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('data_importer','edu.duke.cabig.c3pr.domain.Study', 'CREATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('data_importer','edu.duke.cabig.c3pr.domain.StudySubject', 'CREATE', (select nextval('role_privileges_ID_SEQ')));

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_team_administrator','edu.duke.cabig.c3pr.domain.Study', 'UPDATE', (select nextval('role_privileges_ID_SEQ')));

--Inbox related privileges
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('system_administrator','edu.duke.cabig.c3pr.domain.Inbox', 'READ', (select nextval('role_privileges_ID_SEQ')));


INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('business_administrator','edu.duke.cabig.c3pr.domain.Inbox', 'READ', (select nextval('role_privileges_ID_SEQ')));


INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('person_and_organization_information_manager','edu.duke.cabig.c3pr.domain.Inbox', 'READ', (select nextval('role_privileges_ID_SEQ')));

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('data_importer','edu.duke.cabig.c3pr.domain.Inbox', 'READ', (select nextval('role_privileges_ID_SEQ')));


INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('user_administrator','edu.duke.cabig.c3pr.domain.Inbox', 'READ', (select nextval('role_privileges_ID_SEQ')));


INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_qa_manager','edu.duke.cabig.c3pr.domain.Inbox', 'READ', (select nextval('role_privileges_ID_SEQ')));


INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_creator','edu.duke.cabig.c3pr.domain.Inbox', 'READ', (select nextval('role_privileges_ID_SEQ')));


INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('supplemental_study_information_manager','edu.duke.cabig.c3pr.domain.Inbox', 'READ', (select nextval('role_privileges_ID_SEQ')));


INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_team_administrator','edu.duke.cabig.c3pr.domain.Inbox', 'READ', (select nextval('role_privileges_ID_SEQ')));

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_site_participation_administrator','edu.duke.cabig.c3pr.domain.Inbox', 'READ', (select nextval('role_privileges_ID_SEQ')));


INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('registration_qa_manager','edu.duke.cabig.c3pr.domain.Inbox', 'READ', (select nextval('role_privileges_ID_SEQ')));


INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('subject_manager','edu.duke.cabig.c3pr.domain.Inbox', 'READ', (select nextval('role_privileges_ID_SEQ')));


INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('registrar','edu.duke.cabig.c3pr.domain.Inbox', 'READ', (select nextval('role_privileges_ID_SEQ')));


INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('data_reader','edu.duke.cabig.c3pr.domain.Inbox', 'READ', (select nextval('role_privileges_ID_SEQ')));


INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('data_analyst','edu.duke.cabig.c3pr.domain.Inbox', 'READ', (select nextval('role_privileges_ID_SEQ')));

--Fine grained study privileges
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_creator','edu.duke.cabig.c3pr.domain.Study.Definition.Details', 'READ', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_creator','edu.duke.cabig.c3pr.domain.Study.Definition.Details', 'CREATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_creator','edu.duke.cabig.c3pr.domain.Study.Definition.Details', 'UPDATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_creator','edu.duke.cabig.c3pr.domain.Study.Definition', 'UPDATE', (select nextval('role_privileges_ID_SEQ')));

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('supplemental_study_information_manager','edu.duke.cabig.c3pr.domain.Study.Definition.Supplemental', 'READ', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('supplemental_study_information_manager','edu.duke.cabig.c3pr.domain.Study.Definition.Supplemental', 'CREATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('supplemental_study_information_manager','edu.duke.cabig.c3pr.domain.Study.Definition.Supplemental', 'UPDATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('supplemental_study_information_manager','edu.duke.cabig.c3pr.domain.Study.Definition', 'UPDATE', (select nextval('role_privileges_ID_SEQ')));

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_qa_manager','edu.duke.cabig.c3pr.domain.Study.Definition.Details', 'READ', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_qa_manager','edu.duke.cabig.c3pr.domain.Study.Definition.Details', 'CREATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_qa_manager','edu.duke.cabig.c3pr.domain.Study.Definition.Details', 'UPDATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_qa_manager','edu.duke.cabig.c3pr.domain.Study.Definition.Supplemental', 'READ', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_qa_manager','edu.duke.cabig.c3pr.domain.Study.Definition.Supplemental', 'CREATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_qa_manager','edu.duke.cabig.c3pr.domain.Study.Definition.Supplemental', 'UPDATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_qa_manager','edu.duke.cabig.c3pr.domain.Study.Definition', 'UPDATE', (select nextval('role_privileges_ID_SEQ')));

--UI specific privileges
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('person_and_organization_information_manager','edu.duke.cabig.c3pr.utils.web.navigation.Task.ResearchStaff', 'READ', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('person_and_organization_information_manager','edu.duke.cabig.c3pr.utils.web.navigation.Task.ResearchStaff', 'CREATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('person_and_organization_information_manager','edu.duke.cabig.c3pr.utils.web.navigation.Task.ResearchStaff', 'UPDATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('person_and_organization_information_manager','edu.duke.cabig.c3pr.utils.web.navigation.Task.Investigator', 'READ', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('person_and_organization_information_manager','edu.duke.cabig.c3pr.utils.web.navigation.Task.Investigator', 'CREATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('person_and_organization_information_manager','edu.duke.cabig.c3pr.utils.web.navigation.Task.Investigator', 'UPDATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('person_and_organization_information_manager','edu.duke.cabig.c3pr.utils.web.navigation.Task.HealthcareSite', 'READ', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('person_and_organization_information_manager','edu.duke.cabig.c3pr.utils.web.navigation.Task.HealthcareSite', 'CREATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('person_and_organization_information_manager','edu.duke.cabig.c3pr.utils.web.navigation.Task.HealthcareSite', 'UPDATE', (select nextval('role_privileges_ID_SEQ')));

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('user_administrator','edu.duke.cabig.c3pr.utils.web.navigation.Task.ResearchStaff', 'READ', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('user_administrator','edu.duke.cabig.c3pr.utils.web.navigation.Task.ResearchStaff', 'UPDATE', (select nextval('role_privileges_ID_SEQ')));

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_qa_manager','edu.duke.cabig.c3pr.utils.web.navigation.Task.Study', 'READ', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_qa_manager','edu.duke.cabig.c3pr.utils.web.navigation.Task.Study', 'UPDATE', (select nextval('role_privileges_ID_SEQ')));

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_creator','edu.duke.cabig.c3pr.utils.web.navigation.Task.Study', 'READ', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_creator','edu.duke.cabig.c3pr.utils.web.navigation.Task.Study', 'CREATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_creator','edu.duke.cabig.c3pr.utils.web.navigation.Task.Study', 'UPDATE', (select nextval('role_privileges_ID_SEQ')));

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('supplemental_study_information_manager','edu.duke.cabig.c3pr.utils.web.navigation.Task.Study', 'READ', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('supplemental_study_information_manager','edu.duke.cabig.c3pr.utils.web.navigation.Task.Study', 'UPDATE', (select nextval('role_privileges_ID_SEQ')));

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_team_administrator','edu.duke.cabig.c3pr.utils.web.navigation.Task.Study', 'READ', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_team_administrator','edu.duke.cabig.c3pr.utils.web.navigation.Task.ResearchStaff', 'READ', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_team_administrator','edu.duke.cabig.c3pr.utils.web.navigation.Task.Investigator', 'READ', (select nextval('role_privileges_ID_SEQ')));

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('study_site_participation_administrator','edu.duke.cabig.c3pr.utils.web.navigation.Task.Study', 'READ', (select nextval('role_privileges_ID_SEQ')));

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('registration_qa_manager','edu.duke.cabig.c3pr.utils.web.navigation.Task.StudySubject', 'READ', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('registration_qa_manager','edu.duke.cabig.c3pr.utils.web.navigation.Task.StudySubject', 'UPDATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('registration_qa_manager','edu.duke.cabig.c3pr.utils.web.navigation.Task.Subject', 'READ', (select nextval('role_privileges_ID_SEQ')));

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('subject_manager','edu.duke.cabig.c3pr.utils.web.navigation.Task.Subject', 'READ', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('subject_manager','edu.duke.cabig.c3pr.utils.web.navigation.Task.Subject', 'CREATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('subject_manager','edu.duke.cabig.c3pr.utils.web.navigation.Task.Subject', 'UPDATE', (select nextval('role_privileges_ID_SEQ')));

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('registrar','edu.duke.cabig.c3pr.utils.web.navigation.Task.StudySubject', 'READ', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('registrar','edu.duke.cabig.c3pr.utils.web.navigation.Task.StudySubject', 'CREATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('registrar','edu.duke.cabig.c3pr.utils.web.navigation.Task.StudySubject', 'UPDATE', (select nextval('role_privileges_ID_SEQ')));
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) 
VALUES('registrar','edu.duke.cabig.c3pr.utils.web.navigation.Task.Subject', 'READ', (select nextval('role_privileges_ID_SEQ')));
