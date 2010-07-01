INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('user_administrator','edu.duke.cabig.c3pr.domain.ResearchStaff', 'READ', role_privileges_id_seq.nextval);

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('study_qa_manager','edu.duke.cabig.c3pr.domain.StudyPersonnel', 'CREATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('study_qa_manager','edu.duke.cabig.c3pr.domain.StudyPersonnel', 'UPDATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('study_qa_manager','edu.duke.cabig.c3pr.domain.StudyPersonnel', 'READ', role_privileges_id_seq.nextval);

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('study_qa_manager','edu.duke.cabig.c3pr.domain.StudySite', 'CREATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('study_qa_manager','edu.duke.cabig.c3pr.domain.StudySite', 'UPDATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('study_qa_manager','edu.duke.cabig.c3pr.domain.StudySite', 'READ', role_privileges_id_seq.nextval);

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('study_creator','edu.duke.cabig.c3pr.domain.HealthcareSite', 'READ', role_privileges_id_seq.nextval);

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('registrar','edu.duke.cabig.c3pr.domain.Study', 'READ', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('registrar','edu.duke.cabig.c3pr.domain.Subject', 'READ', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('registrar','edu.duke.cabig.c3pr.domain.StudySite', 'READ', role_privileges_id_seq.nextval);

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('study_team_administrator','edu.duke.cabig.c3pr.domain.Study', 'READ', role_privileges_id_seq.nextval);

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('business_administrator','edu.duke.cabig.c3pr.domain.Summary3Report', 'READ', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('business_administrator','edu.duke.cabig.c3pr.domain.Summary3Report', 'CREATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('business_administrator','edu.duke.cabig.c3pr.domain.Summary3Report', 'UPDATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('business_administrator','edu.duke.cabig.c3pr.domain.Study', 'READ', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('business_administrator','edu.duke.cabig.c3pr.domain.StudySubject', 'READ', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('business_administrator','edu.duke.cabig.c3pr.domain.Subject', 'READ', role_privileges_id_seq.nextval);

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('data_reader','edu.duke.cabig.c3pr.domain.Study', 'READ', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('data_reader','edu.duke.cabig.c3pr.domain.StudySubject', 'READ', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('data_reader','edu.duke.cabig.c3pr.domain.Subject', 'READ', role_privileges_id_seq.nextval);

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('data_analyst','edu.duke.cabig.c3pr.domain.Study', 'READ', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('data_analyst','edu.duke.cabig.c3pr.domain.StudySubject', 'READ', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('data_analyst','edu.duke.cabig.c3pr.domain.Subject', 'READ', role_privileges_id_seq.nextval);

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('registration_qa_manager','edu.duke.cabig.c3pr.domain.Study', 'READ', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('registration_qa_manager','edu.duke.cabig.c3pr.domain.Subject', 'READ', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('registration_qa_manager','edu.duke.cabig.c3pr.domain.StudySite', 'READ', role_privileges_id_seq.nextval);

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('study_qa_manager','edu.duke.cabig.c3pr.domain.StudySubject', 'READ', role_privileges_id_seq.nextval);

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('study_site_participation_administrator','edu.duke.cabig.c3pr.domain.Study', 'READ', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('study_site_participation_administrator','edu.duke.cabig.c3pr.domain.Study', 'UPDATE', role_privileges_id_seq.nextval);

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('data_importer','edu.duke.cabig.c3pr.domain.Study', 'CREATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('data_importer','edu.duke.cabig.c3pr.domain.StudySubject', 'CREATE', role_privileges_id_seq.nextval);

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('study_team_administrator','edu.duke.cabig.c3pr.domain.Study', 'UPDATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('study_team_administrator','edu.duke.cabig.c3pr.domain.ResearchStaff', 'READ', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('study_team_administrator','edu.duke.cabig.c3pr.domain.Investigator', 'READ', role_privileges_id_seq.nextval);

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('system_administrator','edu.duke.cabig.c3pr.domain.Inbox', 'READ', role_privileges_id_seq.nextval);

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('business_administrator','edu.duke.cabig.c3pr.domain.Inbox', 'READ', role_privileges_id_seq.nextval);


INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('person_and_organization_information_manager','edu.duke.cabig.c3pr.domain.Inbox', 'READ', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('data_importer','edu.duke.cabig.c3pr.domain.Inbox', 'READ', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('user_administrator','edu.duke.cabig.c3pr.domain.Inbox', 'READ', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('study_qa_manager','edu.duke.cabig.c3pr.domain.Inbox', 'READ', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('study_creator','edu.duke.cabig.c3pr.domain.Inbox', 'READ', role_privileges_id_seq.nextval);

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('supplemental_study_information_manager','edu.duke.cabig.c3pr.domain.Inbox', 'READ', role_privileges_id_seq.nextval);

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('study_team_administrator','edu.duke.cabig.c3pr.domain.Inbox', 'READ', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('study_site_participation_administrator','edu.duke.cabig.c3pr.domain.Inbox', 'READ', role_privileges_id_seq.nextval);

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('registration_qa_manager','edu.duke.cabig.c3pr.domain.Inbox', 'READ', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('subject_manager','edu.duke.cabig.c3pr.domain.Inbox', 'READ', role_privileges_id_seq.nextval);

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('registrar','edu.duke.cabig.c3pr.domain.Inbox', 'READ', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('data_reader','edu.duke.cabig.c3pr.domain.Inbox', 'READ', role_privileges_id_seq.nextval);

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('data_analyst','edu.duke.cabig.c3pr.domain.Inbox', 'READ', role_privileges_id_seq.nextval);

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('study_creator','edu.duke.cabig.c3pr.domain.Study.Definition.Details', 'READ', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('study_creator','edu.duke.cabig.c3pr.domain.Study.Definition.Details', 'CREATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('study_creator','edu.duke.cabig.c3pr.domain.Study.Definition.Details', 'UPDATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('study_creator','edu.duke.cabig.c3pr.domain.Study.Definition', 'UPDATE', role_privileges_id_seq.nextval);

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('supplemental_study_information_manager','edu.duke.cabig.c3pr.domain.Study.Definition.Supplemental', 'READ', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('supplemental_study_information_manager','edu.duke.cabig.c3pr.domain.Study.Definition.Supplemental', 'CREATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('supplemental_study_information_manager','edu.duke.cabig.c3pr.domain.Study.Definition.Supplemental', 'UPDATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('supplemental_study_information_manager','edu.duke.cabig.c3pr.domain.Study.Definition', 'UPDATE', role_privileges_id_seq.nextval);

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('study_qa_manager','edu.duke.cabig.c3pr.domain.Study.Definition.Details', 'READ', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('study_qa_manager','edu.duke.cabig.c3pr.domain.Study.Definition.Details', 'CREATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('study_qa_manager','edu.duke.cabig.c3pr.domain.Study.Definition.Details', 'UPDATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('study_qa_manager','edu.duke.cabig.c3pr.domain.Study.Definition.Supplemental', 'READ', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('study_qa_manager','edu.duke.cabig.c3pr.domain.Study.Definition.Supplemental', 'CREATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('study_qa_manager','edu.duke.cabig.c3pr.domain.Study.Definition.Supplemental', 'UPDATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('study_qa_manager','edu.duke.cabig.c3pr.domain.Study.Definition', 'UPDATE', role_privileges_id_seq.nextval);

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('person_and_organization_information_manager','edu.duke.cabig.c3pr.utils.web.navigation.Task.ResearchStaff', 'READ', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('person_and_organization_information_manager','edu.duke.cabig.c3pr.utils.web.navigation.Task.ResearchStaff', 'CREATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('person_and_organization_information_manager','edu.duke.cabig.c3pr.utils.web.navigation.Task.ResearchStaff', 'UPDATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('person_and_organization_information_manager','edu.duke.cabig.c3pr.utils.web.navigation.Task.Investigator', 'READ', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('person_and_organization_information_manager','edu.duke.cabig.c3pr.utils.web.navigation.Task.Investigator', 'CREATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('person_and_organization_information_manager','edu.duke.cabig.c3pr.utils.web.navigation.Task.Investigator', 'UPDATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('person_and_organization_information_manager','edu.duke.cabig.c3pr.utils.web.navigation.Task.HealthcareSite', 'READ', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('person_and_organization_information_manager','edu.duke.cabig.c3pr.utils.web.navigation.Task.HealthcareSite', 'CREATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('person_and_organization_information_manager','edu.duke.cabig.c3pr.utils.web.navigation.Task.HealthcareSite', 'UPDATE', role_privileges_id_seq.nextval);

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('user_administrator','edu.duke.cabig.c3pr.utils.web.navigation.Task.ResearchStaff', 'READ', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('user_administrator','edu.duke.cabig.c3pr.utils.web.navigation.Task.ResearchStaff', 'UPDATE', role_privileges_id_seq.nextval);

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('study_qa_manager','edu.duke.cabig.c3pr.utils.web.navigation.Task.Study', 'READ', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('study_qa_manager','edu.duke.cabig.c3pr.utils.web.navigation.Task.Study', 'UPDATE', role_privileges_id_seq.nextval);

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('study_creator','edu.duke.cabig.c3pr.utils.web.navigation.Task.Study', 'READ', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('study_creator','edu.duke.cabig.c3pr.utils.web.navigation.Task.Study', 'CREATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('study_creator','edu.duke.cabig.c3pr.utils.web.navigation.Task.Study', 'UPDATE', role_privileges_id_seq.nextval);

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('supplemental_study_information_manager','edu.duke.cabig.c3pr.utils.web.navigation.Task.Study', 'READ', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('supplemental_study_information_manager','edu.duke.cabig.c3pr.utils.web.navigation.Task.Study', 'UPDATE', role_privileges_id_seq.nextval);

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('study_team_administrator','edu.duke.cabig.c3pr.utils.web.navigation.Task.Study', 'READ', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('study_team_administrator','edu.duke.cabig.c3pr.utils.web.navigation.Task.ResearchStaff', 'READ', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('study_team_administrator','edu.duke.cabig.c3pr.utils.web.navigation.Task.Investigator', 'READ', role_privileges_id_seq.nextval);

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('study_site_participation_administrator','edu.duke.cabig.c3pr.utils.web.navigation.Task.Study', 'READ', role_privileges_id_seq.nextval);

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('registration_qa_manager','edu.duke.cabig.c3pr.utils.web.navigation.Task.StudySubject', 'READ', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('registration_qa_manager','edu.duke.cabig.c3pr.utils.web.navigation.Task.StudySubject', 'UPDATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('registration_qa_manager','edu.duke.cabig.c3pr.utils.web.navigation.Task.Subject', 'READ', role_privileges_id_seq.nextval);

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('subject_manager','edu.duke.cabig.c3pr.utils.web.navigation.Task.Subject', 'READ', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('subject_manager','edu.duke.cabig.c3pr.utils.web.navigation.Task.Subject', 'CREATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('subject_manager','edu.duke.cabig.c3pr.utils.web.navigation.Task.Subject', 'UPDATE', role_privileges_id_seq.nextval);

INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('registrar','edu.duke.cabig.c3pr.utils.web.navigation.Task.StudySubject', 'READ', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('registrar','edu.duke.cabig.c3pr.utils.web.navigation.Task.StudySubject', 'CREATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('registrar','edu.duke.cabig.c3pr.utils.web.navigation.Task.StudySubject', 'UPDATE', role_privileges_id_seq.nextval);
INSERT INTO role_privileges(ROLE_NAME,OBJECT_ID,PRIVILEGE, ID) VALUES('registrar','edu.duke.cabig.c3pr.utils.web.navigation.Task.Subject', 'READ', role_privileges_id_seq.nextval);
