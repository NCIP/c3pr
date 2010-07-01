update csm_application set application_name='c3pr old' where application_id = 1;
insert into csm_application (application_id ,application_name ,application_description,declarative_flag ,active_flag) values (-1, 'c3pr', 'Application Description', 0, 0);
update csm_protection_element set application_id=-1 where application_id=1;
update csm_protection_group set application_id=-1 where application_id=1;
update csm_role set application_id=-1 where application_id=1;
update csm_group set application_id=-1 where application_id=1;
delete from csm_application where application_id = 1;

delete from csm_role_privilege;
delete from csm_user_group_role_pg;
delete from csm_role;
delete from csm_privilege where privilege_id > 0;
delete from csm_pg_pe;
delete from csm_protection_element;
delete from csm_protection_group;
delete from csm_group where group_name like 'edu.duke.cabig.c3pr%';

update csm_group set group_desc='Accepts and approves/denies subject registration requests, requests subject registration on a particular study' where group_name='registrar';

INSERT INTO csm_group(group_id, group_name, group_desc, application_id) VALUES (-23,	'system_administrator',	'Configures the technical system level properties and behavior of the applications (i.e. the password policy, email setup, ESB, etc).', -1);
INSERT INTO csm_group(group_id, group_name, group_desc, application_id) VALUES (-24,	'business_administrator',	'Manages the domain related application wide properties and behavior (i.e. label names, reference data lists, etc)',		-1);
INSERT INTO csm_group(group_id, group_name, group_desc, application_id) VALUES (-25,	'person_and_organization_information_manager',	'Manages organizations and rosters / Creates and updates person info including contact info, degrees/certifications, rosters they?re associated with',		-1);
INSERT INTO csm_group(group_id, group_name, group_desc, application_id) VALUES (-26,	'data_importer',	'Identifies studies defined by Coordinating Center and imports as a consumer that data defined elsewhere',		-1);
INSERT INTO csm_group(group_id, group_name, group_desc, application_id) VALUES (-27,	'user_administrator',	'Ability to read system personnel (research staff and investigators) and create/manage user accounts/application roles, defines Custom Combination Roles', 		-1);
INSERT INTO csm_group(group_id, group_name, group_desc, application_id) VALUES (-28,	'study_qa_manager',	'Updates core study info after saving / opening study.(e.g. PI, title, phase, epochs/arms, basic study design)  Read-only review of study calendar template / releases templates to participating sites', 		-1);
INSERT INTO csm_group(group_id, group_name, group_desc, application_id) VALUES (-29,	'study_creator',	'Creates the core study info (e.g. PI, title, description, phase, epochs/arms & basic study design, etc.) NOTE:  some sites may want to combine the supplemental study info roles into this role', 		-1);
INSERT INTO csm_group(group_id, group_name, group_desc, application_id) VALUES (-30,	'supplemental_study_information_manager',	'Adds treatment assignment, drugs, adEERS-specific diseases, adEERs reporting criteria, CTC/MedDRA version, etc.  Update/manage registration metadata (e.g. stratifications, eligibility criteria, notifi',		-1);
INSERT INTO csm_group(group_id, group_name, group_desc, application_id) VALUES (-31,	'study_team_administrator',	'Connects study level people to study and internal staff to study, Assigns internal staff to protocol, determines study artifact accessiblity for staff (e.g. study calendar templates, CRFs, etc.)',		-1);
INSERT INTO csm_group(group_id, group_name, group_desc, application_id) VALUES (-32,	'study_site_participation_administrator',	'Connects participating sites to a protocol',		-1);
INSERT INTO csm_group(group_id, group_name, group_desc, application_id) VALUES (-33,  'ae_rule_and_report_manager',	'Creates, manages, imports AE rules / Creates, manages, imports AE report definitions',		-1);
INSERT INTO csm_group(group_id, group_name, group_desc, application_id) VALUES (-34,	'study_calendar_template_builder',	'Creates and updates study calendar templates', 		-1);
INSERT INTO csm_group(group_id, group_name, group_desc, application_id) VALUES (-35,	'registration_qa_manager',	'Updates registration information (study subject ID, Date of consent) after enrollment. Can waive the eligibility criteria for certain study subjects.', 		-1);
INSERT INTO csm_group(group_id, group_name, group_desc, application_id) VALUES (-36, 'subject_manager',	'Defines patient to system (remaining subject data managed by other roles which are not defined)',		-1);
INSERT INTO csm_group(group_id, group_name, group_desc, application_id) VALUES (-37,	'study_subject_calendar_manager',	'Creates and updates a subject-specific study calendar based on a study calendar template', 		-1);
INSERT INTO csm_group(group_id, group_name, group_desc, application_id) VALUES (-38,	'ae_reporter',	'Creates / updates info about AE that needs reported / submits report to appropriate parties per report definition. Enters set of required AEs to be assessed and any other AEs that patient experienced.',		-1);
INSERT INTO csm_group(group_id, group_name, group_desc, application_id) VALUES (-39,	'ae_expedited_report_reviewer',	'Read-only: reviews, provides comments, and routes expedited reports through the review workflow',		-1);
INSERT INTO csm_group(group_id, group_name, group_desc, application_id) VALUES (-40,	'ae_study_data_reviewer',	'Read-only: reviews, provides comments, and adverse event data through a review workflow',		-1);
INSERT INTO csm_group(group_id, group_name, group_desc, application_id) VALUES (-41,	'lab_impact_calendar_notifier',	'Creates a calendar notification for a potential lab-based treatment modification',		-1);
INSERT INTO csm_group(group_id, group_name, group_desc, application_id) VALUES (-42,	'lab_data_user',	'Enters, edits, and imports labs from LIMS, viewing labs, selecting and sending labs to CDMS and caAERS',		-1);
INSERT INTO csm_group(group_id, group_name, group_desc, application_id) VALUES (-43,	'data_reader',	'Read-only: typically not part of org being audited, granted temporary access (no updates) to whole study or specific study/subjects, or any data entered by site for study/subject, crosses all apps.',		-1);
INSERT INTO csm_group(group_id, group_name, group_desc, application_id) VALUES (-44, 'data_analyst',	'Read-only: searches for data, uses built-in analysis tools, exports data to third party tools',		-1);

INSERT INTO csm_role(role_id, role_name, role_description, application_id, active_flag) VALUES (csm_role_role_id_seq.nextval,	'system_administrator',	'Configures the technical system level properties and behavior of the applications (i.e. the password policy, email setup, ESB, etc).',	-1, 1);
INSERT INTO csm_privilege(privilege_id, privilege_name, privilege_description) VALUES (csm_privilege_privilege_id_seq.nextval,	'system_administrator',	'Configures the technical system level properties and behavior of the applications (i.e. the password policy, email setup, ESB, etc).');
INSERT INTO csm_role_privilege(role_privilege_id, role_id, privilege_id) VALUES (csm_role_priv_role_privile_seq.nextval,csm_role_role_id_seq.currval,csm_privilege_privilege_id_seq.currval);

INSERT INTO csm_role(role_id, role_name, role_description, application_id, active_flag) VALUES (csm_role_role_id_seq.nextval,	'business_administrator',	'Manages the domain related application wide properties and behavior (i.e. label names, reference data lists, etc)',		-1, 1);
INSERT INTO csm_privilege(privilege_id, privilege_name, privilege_description) VALUES (csm_privilege_privilege_id_seq.nextval,	'business_administrator',	'Manages the domain related application wide properties and behavior (i.e. label names, reference data lists, etc)');
INSERT INTO csm_role_privilege(role_privilege_id, role_id, privilege_id) VALUES (csm_role_priv_role_privile_seq.nextval,csm_role_role_id_seq.currval,csm_privilege_privilege_id_seq.currval);

INSERT INTO csm_role(role_id, role_name, role_description, application_id, active_flag) VALUES (csm_role_role_id_seq.nextval,	'person_and_organization_information_manager',	'Manages organizations and rosters / Creates and updates person info including contact info, degrees/certifications, rosters they?re associated with',		-1, 1);
INSERT INTO csm_privilege(privilege_id, privilege_name, privilege_description) VALUES (csm_privilege_privilege_id_seq.nextval,	'person_and_organization_information_manager',	'Manages organizations and rosters / Creates and updates person info including contact info, degrees/certifications, rosters they?re associated with');
INSERT INTO csm_role_privilege(role_privilege_id, role_id, privilege_id) VALUES (csm_role_priv_role_privile_seq.nextval,csm_role_role_id_seq.currval,csm_privilege_privilege_id_seq.currval);

INSERT INTO csm_role(role_id, role_name, role_description, application_id, active_flag) VALUES (csm_role_role_id_seq.nextval,	'data_importer',	'Identifies studies defined by Coordinating Center and imports as a consumer that data defined elsewhere',		-1, 1);
INSERT INTO csm_privilege(privilege_id, privilege_name, privilege_description) VALUES (csm_privilege_privilege_id_seq.nextval,	'data_importer',	'Identifies studies defined by Coordinating Center and imports as a consumer that data defined elsewhere');
INSERT INTO csm_role_privilege(role_privilege_id, role_id, privilege_id) VALUES (csm_role_priv_role_privile_seq.nextval,csm_role_role_id_seq.currval,csm_privilege_privilege_id_seq.currval);

INSERT INTO csm_role(role_id, role_name, role_description, application_id, active_flag) VALUES (csm_role_role_id_seq.nextval,	'user_administrator',	'Ability to read system personnel (research staff and investigators) and create/manage user accounts/application roles, defines Custom Combination Roles', 		-1, 1);
INSERT INTO csm_privilege(privilege_id, privilege_name, privilege_description) VALUES (csm_privilege_privilege_id_seq.nextval,	'user_administrator',	'Ability to read system personnel (research staff and investigators) and create/manage user accounts/application roles, defines Custom Combination Roles');
INSERT INTO csm_role_privilege(role_privilege_id, role_id, privilege_id) VALUES (csm_role_priv_role_privile_seq.nextval,csm_role_role_id_seq.currval,csm_privilege_privilege_id_seq.currval);

INSERT INTO csm_role(role_id, role_name, role_description, application_id, active_flag) VALUES (csm_role_role_id_seq.nextval,	'study_qa_manager',	'Updates core study info after saving / opening study.(e.g. PI, title, phase, epochs/arms, basic study design)  Read-only review of study calendar template / releases templates to participating sites', 		-1, 1);
INSERT INTO csm_privilege(privilege_id, privilege_name, privilege_description) VALUES (csm_privilege_privilege_id_seq.nextval,	'study_qa_manager',	'Updates core study info after saving / opening study.(e.g. PI, title, phase, epochs/arms, basic study design)  Read-only review of study calendar template / releases templates to participating sites');
INSERT INTO csm_role_privilege(role_privilege_id, role_id, privilege_id) VALUES (csm_role_priv_role_privile_seq.nextval,csm_role_role_id_seq.currval,csm_privilege_privilege_id_seq.currval);

INSERT INTO csm_role(role_id, role_name, role_description, application_id, active_flag) VALUES (csm_role_role_id_seq.nextval,	'study_creator',	'Creates the core study info (e.g. PI, title, description, phase, epochs/arms & basic study design, etc.) NOTE:  some sites may want to combine the supplemental study info roles into this role', 		-1, 1);
INSERT INTO csm_privilege(privilege_id, privilege_name, privilege_description) VALUES (csm_privilege_privilege_id_seq.nextval,	'study_creator',	'Creates the core study info (e.g. PI, title, description, phase, epochs/arms & basic study design, etc.) NOTE:  some sites may want to combine the supplemental study info roles into this role');
INSERT INTO csm_role_privilege(role_privilege_id, role_id, privilege_id) VALUES (csm_role_priv_role_privile_seq.nextval,csm_role_role_id_seq.currval,csm_privilege_privilege_id_seq.currval);

INSERT INTO csm_role(role_id, role_name, role_description, application_id, active_flag) VALUES (csm_role_role_id_seq.nextval,	'supplemental_study_information_manager',	'Adds treatment assignment, drugs, adEERS-specific diseases, adEERs reporting criteria, CTC/MedDRA version, etc.  Update/manage registration metadata (e.g. stratifications, eligibility criteria, notifi',		-1, 1);
INSERT INTO csm_privilege(privilege_id, privilege_name, privilege_description) VALUES (csm_privilege_privilege_id_seq.nextval,	'supplemental_study_information_manager',	'Adds treatment assignment, drugs, adEERS-specific diseases, adEERs reporting criteria, CTC/MedDRA version, etc.  Update/manage registration metadata (e.g. stratifications, eligibility criteria, notifi');
INSERT INTO csm_role_privilege(role_privilege_id, role_id, privilege_id) VALUES (csm_role_priv_role_privile_seq.nextval,csm_role_role_id_seq.currval,csm_privilege_privilege_id_seq.currval);

INSERT INTO csm_role(role_id, role_name, role_description, application_id, active_flag) VALUES (csm_role_role_id_seq.nextval,	'study_team_administrator',	'Connects study level people to study and internal staff to study, Assigns internal staff to protocol, determines study artifact accessiblity for staff (e.g. study calendar templates, CRFs, etc.)',		-1, 1);
INSERT INTO csm_privilege(privilege_id, privilege_name, privilege_description) VALUES (csm_privilege_privilege_id_seq.nextval,	'study_team_administrator',	'Connects study level people to study and internal staff to study, Assigns internal staff to protocol, determines study artifact accessiblity for staff (e.g. study calendar templates, CRFs, etc.)');
INSERT INTO csm_role_privilege(role_privilege_id, role_id, privilege_id) VALUES (csm_role_priv_role_privile_seq.nextval,csm_role_role_id_seq.currval,csm_privilege_privilege_id_seq.currval);

INSERT INTO csm_role(role_id, role_name, role_description, application_id, active_flag) VALUES (csm_role_role_id_seq.nextval,	'study_site_participation_administrator',	'Connects participating sites to a protocol',		-1, 1);
INSERT INTO csm_privilege(privilege_id, privilege_name, privilege_description) VALUES (csm_privilege_privilege_id_seq.nextval,	'study_site_participation_administrator',	'Connects participating sites to a protocol');
INSERT INTO csm_role_privilege(role_privilege_id, role_id, privilege_id) VALUES (csm_role_priv_role_privile_seq.nextval,csm_role_role_id_seq.currval,csm_privilege_privilege_id_seq.currval);

INSERT INTO csm_role(role_id, role_name, role_description, application_id, active_flag) VALUES (csm_role_role_id_seq.nextval,  'ae_rule_and_report_manager',	'Creates, manages, imports AE rules / Creates, manages, imports AE report definitions',		-1, 1);
INSERT INTO csm_privilege(privilege_id, privilege_name, privilege_description) VALUES (csm_privilege_privilege_id_seq.nextval,  'ae_rule_and_report_manager',	'Creates, manages, imports AE rules / Creates, manages, imports AE report definitions');
INSERT INTO csm_role_privilege(role_privilege_id, role_id, privilege_id) VALUES (csm_role_priv_role_privile_seq.nextval,csm_role_role_id_seq.currval,csm_privilege_privilege_id_seq.currval);

INSERT INTO csm_role(role_id, role_name, role_description, application_id, active_flag) VALUES (csm_role_role_id_seq.nextval,	'study_calendar_template_builder',	'Creates and updates study calendar templates', 		-1, 1);
INSERT INTO csm_privilege(privilege_id, privilege_name, privilege_description) VALUES (csm_privilege_privilege_id_seq.nextval,	'study_calendar_template_builder',	'Creates and updates study calendar templates');
INSERT INTO csm_role_privilege(role_privilege_id, role_id, privilege_id) VALUES (csm_role_priv_role_privile_seq.nextval,csm_role_role_id_seq.currval,csm_privilege_privilege_id_seq.currval);

INSERT INTO csm_role(role_id, role_name, role_description, application_id, active_flag) VALUES (csm_role_role_id_seq.nextval,	'registration_qa_manager',	'Updates registration information (study subject ID, Date of consent) after enrollment. Can waive the eligibility criteria for certain study subjects.', 		-1, 1);
INSERT INTO csm_privilege(privilege_id, privilege_name, privilege_description) VALUES (csm_privilege_privilege_id_seq.nextval,	'registration_qa_manager',	'Updates registration information (study subject ID, Date of consent) after enrollment. Can waive the eligibility criteria for certain study subjects.');
INSERT INTO csm_role_privilege(role_privilege_id, role_id, privilege_id) VALUES (csm_role_priv_role_privile_seq.nextval,csm_role_role_id_seq.currval,csm_privilege_privilege_id_seq.currval);

INSERT INTO csm_role(role_id, role_name, role_description, application_id, active_flag) VALUES (csm_role_role_id_seq.nextval, 'subject_manager',	'Defines patient to system (remaining subject data managed by other roles which are not defined)',		-1, 1);
INSERT INTO csm_privilege(privilege_id, privilege_name, privilege_description) VALUES (csm_privilege_privilege_id_seq.nextval, 'subject_manager',	'Defines patient to system (remaining subject data managed by other roles which are not defined)');
INSERT INTO csm_role_privilege(role_privilege_id, role_id, privilege_id) VALUES (csm_role_priv_role_privile_seq.nextval,csm_role_role_id_seq.currval,csm_privilege_privilege_id_seq.currval);

INSERT INTO csm_role(role_id, role_name, role_description, application_id, active_flag) VALUES (csm_role_role_id_seq.nextval,	'study_subject_calendar_manager',	'Creates and updates a subject-specific study calendar based on a study calendar template', 		-1, 1);
INSERT INTO csm_privilege(privilege_id, privilege_name, privilege_description) VALUES (csm_privilege_privilege_id_seq.nextval,	'study_subject_calendar_manager',	'Creates and updates a subject-specific study calendar based on a study calendar template');
INSERT INTO csm_role_privilege(role_privilege_id, role_id, privilege_id) VALUES (csm_role_priv_role_privile_seq.nextval,csm_role_role_id_seq.currval,csm_privilege_privilege_id_seq.currval);

INSERT INTO csm_role(role_id, role_name, role_description, application_id, active_flag) VALUES (csm_role_role_id_seq.nextval,	'registrar',	'Accepts and approves/denies subject registration requests, requests subject registration on a particular study',		-1, 1);
INSERT INTO csm_privilege(privilege_id, privilege_name, privilege_description) VALUES (csm_privilege_privilege_id_seq.nextval,	'registrar',	'Accepts and approves/denies subject registration requests, requests subject registration on a particular study');
INSERT INTO csm_role_privilege(role_privilege_id, role_id, privilege_id) VALUES (csm_role_priv_role_privile_seq.nextval,csm_role_role_id_seq.currval,csm_privilege_privilege_id_seq.currval);

INSERT INTO csm_role(role_id, role_name, role_description, application_id, active_flag) VALUES (csm_role_role_id_seq.nextval,	'ae_reporter',	'Creates / updates info about AE that needs reported / submits report to appropriate parties per report definition. Enters set of required AEs to be assessed and any other AEs that patient experienced.',		-1, 1);
INSERT INTO csm_privilege(privilege_id, privilege_name, privilege_description) VALUES (csm_privilege_privilege_id_seq.nextval,	'ae_reporter',	'Creates / updates info about AE that needs reported / submits report to appropriate parties per report definition. Enters set of required AEs to be assessed and any other AEs that patient experienced.');
INSERT INTO csm_role_privilege(role_privilege_id, role_id, privilege_id) VALUES (csm_role_priv_role_privile_seq.nextval,csm_role_role_id_seq.currval,csm_privilege_privilege_id_seq.currval);

INSERT INTO csm_role(role_id, role_name, role_description, application_id, active_flag) VALUES (csm_role_role_id_seq.nextval,	'ae_expedited_report_reviewer',	'Read-only: reviews, provides comments, and routes expedited reports through the review workflow',		-1, 1);
INSERT INTO csm_privilege(privilege_id, privilege_name, privilege_description) VALUES (csm_privilege_privilege_id_seq.nextval,	'ae_expedited_report_reviewer',	'Read-only: reviews, provides comments, and routes expedited reports through the review workflow');
INSERT INTO csm_role_privilege(role_privilege_id, role_id, privilege_id) VALUES (csm_role_priv_role_privile_seq.nextval,csm_role_role_id_seq.currval,csm_privilege_privilege_id_seq.currval);

INSERT INTO csm_role(role_id, role_name, role_description, application_id, active_flag) VALUES (csm_role_role_id_seq.nextval,	'ae_study_data_reviewer',	'Read-only: reviews, provides comments, and adverse event data through a review workflow',		-1, 1);
INSERT INTO csm_privilege(privilege_id, privilege_name, privilege_description) VALUES (csm_privilege_privilege_id_seq.nextval,	'ae_study_data_reviewer',	'Read-only: reviews, provides comments, and adverse event data through a review workflow');
INSERT INTO csm_role_privilege(role_privilege_id, role_id, privilege_id) VALUES (csm_role_priv_role_privile_seq.nextval,csm_role_role_id_seq.currval,csm_privilege_privilege_id_seq.currval);

INSERT INTO csm_role(role_id, role_name, role_description, application_id, active_flag) VALUES (csm_role_role_id_seq.nextval,	'lab_impact_calendar_notifier',	'Creates a calendar notification for a potential lab-based treatment modification',		-1, 1);
INSERT INTO csm_privilege(privilege_id, privilege_name, privilege_description) VALUES (csm_privilege_privilege_id_seq.nextval,	'lab_impact_calendar_notifier',	'Creates a calendar notification for a potential lab-based treatment modification');
INSERT INTO csm_role_privilege(role_privilege_id, role_id, privilege_id) VALUES (csm_role_priv_role_privile_seq.nextval,csm_role_role_id_seq.currval,csm_privilege_privilege_id_seq.currval);

INSERT INTO csm_role(role_id, role_name, role_description, application_id, active_flag) VALUES (csm_role_role_id_seq.nextval,	'lab_data_user',	'Enters, edits, and imports labs from LIMS, viewing labs, selecting and sending labs to CDMS and caAERS',		-1, 1);
INSERT INTO csm_privilege(privilege_id, privilege_name, privilege_description) VALUES (csm_privilege_privilege_id_seq.nextval,	'lab_data_user',	'Enters, edits, and imports labs from LIMS, viewing labs, selecting and sending labs to CDMS and caAERS');
INSERT INTO csm_role_privilege(role_privilege_id, role_id, privilege_id) VALUES (csm_role_priv_role_privile_seq.nextval,csm_role_role_id_seq.currval,csm_privilege_privilege_id_seq.currval);

INSERT INTO csm_role(role_id, role_name, role_description, application_id, active_flag) VALUES (csm_role_role_id_seq.nextval,	'data_reader',	'Read-only: typically not part of org being audited, granted temporary access (no updates) to whole study or specific study/subjects, or any data entered by site for study/subject, crosses all apps.',		-1, 1);
INSERT INTO csm_privilege(privilege_id, privilege_name, privilege_description) VALUES (csm_privilege_privilege_id_seq.nextval,	'data_reader',	'Read-only: typically not part of org being audited, granted temporary access (no updates) to whole study or specific study/subjects, or any data entered by site for study/subject, crosses all apps.');
INSERT INTO csm_role_privilege(role_privilege_id, role_id, privilege_id) VALUES (csm_role_priv_role_privile_seq.nextval,csm_role_role_id_seq.currval,csm_privilege_privilege_id_seq.currval);

INSERT INTO csm_role(role_id, role_name, role_description, application_id, active_flag) VALUES (csm_role_role_id_seq.nextval, 'data_analyst',	'Read-only: searches for data, uses built-in analysis tools, exports data to third party tools',		-1, 1);
INSERT INTO csm_privilege(privilege_id, privilege_name, privilege_description) VALUES (csm_privilege_privilege_id_seq.nextval, 'data_analyst',	'Read-only: searches for data, uses built-in analysis tools, exports data to third party tools');
INSERT INTO csm_role_privilege(role_privilege_id, role_id, privilege_id) VALUES (csm_role_priv_role_privile_seq.nextval,csm_role_role_id_seq.currval,csm_privilege_privilege_id_seq.currval)


