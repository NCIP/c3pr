
insert into csm_user (USER_ID, LOGIN_NAME,FIRST_NAME,LAST_NAME,PASSWORD,UPDATE_DATE)
values (2, 'c3pr_registrar','c3pr','registrar','r+lEhHNNKK8LZtouIzqBCw==',sysdate);
select CSM_USER_USER_ID_SEQ.nextval from dual;

insert into csm_user (USER_ID, LOGIN_NAME,FIRST_NAME,LAST_NAME,PASSWORD,UPDATE_DATE)
values (3, 'c3pr_study_coordinator','c3pr','study_coordinator','nH4FjhM3deAxZD+F13ogiENcBWB1ntZ2',sysdate);
select CSM_USER_USER_ID_SEQ.nextval from dual;

insert into csm_group(GROUP_ID,GROUP_NAME,APPLICATION_ID,UPDATE_DATE)
values(2,'registrar',1,sysdate);

insert into csm_group(GROUP_ID,GROUP_NAME,APPLICATION_ID,UPDATE_DATE)
values(3,'study_coordinator',1,sysdate);


insert into CSM_USER_GROUP(USER_GROUP_ID, USER_ID,GROUP_ID)
values(2,2,2);

insert into CSM_USER_GROUP(USER_GROUP_ID, USER_ID,GROUP_ID)
values(3,3,3);



insert into CSM_PROTECTION_GROUP(PROTECTION_GROUP_ID,
            PROTECTION_GROUP_NAME, PROTECTION_GROUP_DESCRIPTION,
            APPLICATION_ID, LARGE_ELEMENT_COUNT_FLAG,
            UPDATE_DATE)
            values(1,'edu.duke.cabig.c3pr.domain.Participant',
            'Participant protection group',1,0,sysdate);

insert into CSM_PROTECTION_GROUP(PROTECTION_GROUP_ID,
            PROTECTION_GROUP_NAME, PROTECTION_GROUP_DESCRIPTION,
            APPLICATION_ID, LARGE_ELEMENT_COUNT_FLAG,
            UPDATE_DATE)
            values(2,'edu.duke.cabig.c3pr.domain.Study',
            'Study protection group',1,0,sysdate);

insert into CSM_PROTECTION_GROUP(PROTECTION_GROUP_ID,
            PROTECTION_GROUP_NAME, PROTECTION_GROUP_DESCRIPTION,
            APPLICATION_ID, LARGE_ELEMENT_COUNT_FLAG,
            UPDATE_DATE)
            values(3,'edu.duke.cabig.c3pr.domain.Registration',
            'Registration protection group',1,0,sysdate);

insert into CSM_PROTECTION_GROUP(PROTECTION_GROUP_ID,
            PROTECTION_GROUP_NAME, PROTECTION_GROUP_DESCRIPTION,
            APPLICATION_ID, LARGE_ELEMENT_COUNT_FLAG,
            UPDATE_DATE)
            values(4,'edu.duke.cabig.c3pr.domain.Investigator',
            'Investigator protection group',1,0,sysdate);

insert into CSM_PROTECTION_GROUP(PROTECTION_GROUP_ID,
            PROTECTION_GROUP_NAME, PROTECTION_GROUP_DESCRIPTION,
            APPLICATION_ID, LARGE_ELEMENT_COUNT_FLAG,
            UPDATE_DATE)
            values(5,'edu.duke.cabig.c3pr.domain.ResearchStaff',
            'Investigator protection group',1,0,sysdate);

insert into CSM_PROTECTION_GROUP(PROTECTION_GROUP_ID,
            PROTECTION_GROUP_NAME, PROTECTION_GROUP_DESCRIPTION,
            APPLICATION_ID, LARGE_ELEMENT_COUNT_FLAG,
            UPDATE_DATE)
            values(6,'edu.duke.cabig.c3pr.domain.HealthcareSite',
            'Investigator protection group',1,0,sysdate);



insert into csm_protection_element(protection_element_id,
            protection_element_name,protection_element_description,
            object_id,application_id,update_date)
            values(2,'edu.duke.cabig.c3pr.domain.Participant',
            'Participant Domain object','edu.duke.cabig.c3pr.domain.Participant',
            1,sysdate);

insert into csm_protection_element(protection_element_id,
            protection_element_name,protection_element_description,
            object_id,application_id,update_date)
            values(3,'edu.duke.cabig.c3pr.domain.Study',
            'Study Domain object','edu.duke.cabig.c3pr.domain.Study',
            1,sysdate);


insert into csm_protection_element(protection_element_id,
            protection_element_name,protection_element_description,
            object_id,application_id,update_date)
            values(4,'edu.duke.cabig.c3pr.domain.Registration',
            'Registration Domain object','edu.duke.cabig.c3pr.domain.Registration',
            1,sysdate);

insert into csm_protection_element(protection_element_id,
            protection_element_name,protection_element_description,
            object_id,application_id,update_date)
            values(5,'edu.duke.cabig.c3pr.domain.Investigator',
            'Investigator Domain object','edu.duke.cabig.c3pr.domain.Investigator',
            1,sysdate);

insert into csm_protection_element(protection_element_id,
            protection_element_name,protection_element_description,
            object_id,application_id,update_date)
            values(6,'edu.duke.cabig.c3pr.domain.ResearchStaff',
            'ResearchStaff Domain object','edu.duke.cabig.c3pr.domain.ResearchStaff',
            1,sysdate);

insert into csm_protection_element(protection_element_id,
            protection_element_name,protection_element_description,
            object_id,application_id,update_date)
            values(7,'edu.duke.cabig.c3pr.domain.HealthcareSite',
            'HealthCare Site Domain object','edu.duke.cabig.c3pr.domain.HealthcareSite',
            1,sysdate);
            

insert into csm_pg_pe(pg_pe_id,protection_group_id,
            protection_element_id)
            values(1,1,2);

insert into csm_pg_pe(pg_pe_id,protection_group_id,
            protection_element_id)
            values(2,2,3);

insert into csm_pg_pe(pg_pe_id,protection_group_id,
            protection_element_id)
            values(3,3,4);

insert into csm_pg_pe(pg_pe_id,protection_group_id,
            protection_element_id)
            values(4,4,5);

insert into csm_pg_pe(pg_pe_id,protection_group_id,
            protection_element_id)
            values(5,5,6);

insert into csm_pg_pe(pg_pe_id,protection_group_id,
            protection_element_id)
            values(6,6,7);



insert into CSM_ROLE(role_id,role_name,
            role_description,application_id,
            active_flag,update_date)
            values(1,'edu.duke.cabig.c3pr.domain.Participant.CREATE',
            'Participant create role',1,1,sysdate);

insert into CSM_ROLE(role_id,role_name,
            role_description,application_id,
            active_flag,update_date)
            values(2,'edu.duke.cabig.c3pr.domain.Participant.UPDATE',
            'Participant edit role',1,1,sysdate);

insert into CSM_ROLE(role_id,role_name,
            role_description,application_id,
            active_flag,update_date)
            values(3,'edu.duke.cabig.c3pr.domain.Participant.READ',
            'Participant read role',1,1,sysdate);

insert into CSM_ROLE(role_id,role_name,
                role_description,application_id,
                active_flag,update_date)
            values(4,'edu.duke.cabig.c3pr.domain.Study.CREATE',
            'Study create role',1,1,sysdate);

insert into CSM_ROLE(role_id,role_name,
            role_description,application_id,
            active_flag,update_date)
            values(5,'edu.duke.cabig.c3pr.domain.Study.UPDATE',
            'Study edit role',1,1,sysdate);

insert into CSM_ROLE(role_id,role_name,
            role_description,application_id,
            active_flag,update_date)
            values(6,'edu.duke.cabig.c3pr.domain.Study.READ',
            'Study read role',1,1,sysdate);

insert into CSM_ROLE(role_id,role_name,
            role_description,application_id,
            active_flag,update_date)
            values(7,'edu.duke.cabig.c3pr.domain.Registration.CREATE',
            'Registration read role',1,1,sysdate);

insert into CSM_ROLE(role_id,role_name,
            role_description,application_id,
            active_flag,update_date)
            values(8,'edu.duke.cabig.c3pr.domain.Registration.UPDATE',
            'Registration read role',1,1,sysdate);

insert into CSM_ROLE(role_id,role_name,
            role_description,application_id,
            active_flag,update_date)
            values(9,'edu.duke.cabig.c3pr.domain.Registration.READ',
            'Registration read role',1,1,sysdate);

insert into CSM_ROLE(role_id,role_name,
            role_description,application_id,
            active_flag,update_date)
            values(10,'edu.duke.cabig.c3pr.domain.Investigator.CREATE',
            'Investigator create role',1,1,sysdate);

insert into CSM_ROLE(role_id,role_name,
            role_description,application_id,
            active_flag,update_date)
            values(11,'edu.duke.cabig.c3pr.domain.Investigator.UPDATE',
            'Investigator update role',1,1,sysdate);

insert into CSM_ROLE(role_id,role_name,
            role_description,application_id,
            active_flag,update_date)
            values(12,'edu.duke.cabig.c3pr.domain.Investigator.READ',
            'Investigator read role',1,1,sysdate);

insert into CSM_ROLE(role_id,role_name,
            role_description,application_id,
            active_flag,update_date)
            values(13,'edu.duke.cabig.c3pr.domain.ResearchStaff.CREATE',
            'ResearchStaff create role',1,1,sysdate);

insert into CSM_ROLE(role_id,role_name,
            role_description,application_id,
            active_flag,update_date)
            values(14,'edu.duke.cabig.c3pr.domain.ResearchStaff.UPDATE',
            'ResearchStaff update role',1,1,sysdate);

insert into CSM_ROLE(role_id,role_name,
            role_description,application_id,
            active_flag,update_date)
            values(15,'edu.duke.cabig.c3pr.domain.ResearchStaff.READ',
            'ResearchStaff read role',1,1,sysdate);

insert into CSM_ROLE(role_id,role_name,
                role_description,application_id,
                active_flag,update_date)
                values(16,'edu.duke.cabig.c3pr.domain.HealthcareSite.READ',
                'HealthcareSite read role',1,1,sysdate);

insert into CSM_ROLE(role_id,role_name,
                role_description,application_id,
                active_flag,update_date)
                values(17,'edu.duke.cabig.c3pr.domain.HealthcareSite.UPDATE',
                'HealthcareSite read role',1,1,sysdate);

insert into CSM_ROLE(role_id,role_name,
                role_description,application_id,
                active_flag,update_date)
                values(18,'edu.duke.cabig.c3pr.domain.HealthcareSite.CREATE',
                'HealthcareSite read role',1,1,sysdate);



insert into CSM_ROLE_PRIVILEGE(role_privilege_id,
            role_id,privilege_id,update_date)
            values(1,1,1,sysdate);

insert into CSM_ROLE_PRIVILEGE(role_privilege_id,
            role_id,privilege_id,update_date)
            values(2,2,5,sysdate);

insert into CSM_ROLE_PRIVILEGE(role_privilege_id,
            role_id,privilege_id,update_date)
            values(3,3,3,sysdate);

insert into CSM_ROLE_PRIVILEGE(role_privilege_id,
            role_id,privilege_id,update_date)
            values(4,4,1,sysdate);

insert into CSM_ROLE_PRIVILEGE(role_privilege_id,
            role_id,privilege_id,update_date)
            values(5,5,5,sysdate);

insert into CSM_ROLE_PRIVILEGE(role_privilege_id,
            role_id,privilege_id,update_date)
            values(6,6,3,sysdate);

insert into CSM_ROLE_PRIVILEGE(role_privilege_id,
            role_id,privilege_id,update_date)
            values(7,7,1,sysdate);

insert into CSM_ROLE_PRIVILEGE(role_privilege_id,
            role_id,privilege_id,update_date)
            values(8,8,5,sysdate);

insert into CSM_ROLE_PRIVILEGE(role_privilege_id,
            role_id,privilege_id,update_date)
            values(9,9,3,sysdate);

insert into CSM_ROLE_PRIVILEGE(role_privilege_id,
            role_id,privilege_id,update_date)
            values(10,10,1,sysdate);

insert into CSM_ROLE_PRIVILEGE(role_privilege_id,
            role_id,privilege_id,update_date)
            values(11,11,5,sysdate);

insert into CSM_ROLE_PRIVILEGE(role_privilege_id,
            role_id,privilege_id,update_date)
            values(12,12,3,sysdate);

insert into CSM_ROLE_PRIVILEGE(role_privilege_id,
            role_id,privilege_id,update_date)
            values(13,13,1,sysdate);

insert into CSM_ROLE_PRIVILEGE(role_privilege_id,
            role_id,privilege_id,update_date)
            values(14,14,5,sysdate);

insert into CSM_ROLE_PRIVILEGE(role_privilege_id,
            role_id,privilege_id,update_date)
            values(15,15,3,sysdate);

insert into CSM_ROLE_PRIVILEGE(role_privilege_id,
            role_id,privilege_id,update_date)
            values(16,16,1,sysdate);

insert into CSM_ROLE_PRIVILEGE(role_privilege_id,
            role_id,privilege_id,update_date)
            values(17,17,5,sysdate);

insert into CSM_ROLE_PRIVILEGE(role_privilege_id,
            role_id,privilege_id,update_date)
            values(18,18,3,sysdate);



insert into csm_user_group_role_pg (USER_GROUP_ROLE_PG_ID,
            GROUP_ID, ROLE_ID, PROTECTION_GROUP_ID,
            UPDATE_DATE)
            values(1,1,1,1,sysdate);
            
insert into csm_user_group_role_pg (USER_GROUP_ROLE_PG_ID,
            GROUP_ID, ROLE_ID, PROTECTION_GROUP_ID,
            UPDATE_DATE)
            values(2,1,2,1,sysdate);

insert into csm_user_group_role_pg (USER_GROUP_ROLE_PG_ID,
            GROUP_ID, ROLE_ID, PROTECTION_GROUP_ID,
            UPDATE_DATE)
            values(3,1,3,1,sysdate);

insert into csm_user_group_role_pg (USER_GROUP_ROLE_PG_ID,
            GROUP_ID, ROLE_ID, PROTECTION_GROUP_ID,
            UPDATE_DATE)
            values(4,1,4,2,sysdate);

insert into csm_user_group_role_pg (USER_GROUP_ROLE_PG_ID,
            GROUP_ID, ROLE_ID, PROTECTION_GROUP_ID,
            UPDATE_DATE)
            values(5,1,5,2,sysdate);

insert into csm_user_group_role_pg (USER_GROUP_ROLE_PG_ID,
            GROUP_ID, ROLE_ID, PROTECTION_GROUP_ID,
            UPDATE_DATE)
            values(6,1,6,2,sysdate);

insert into csm_user_group_role_pg (USER_GROUP_ROLE_PG_ID,
            GROUP_ID, ROLE_ID, PROTECTION_GROUP_ID,
            UPDATE_DATE)
            values(7,1,7,3,sysdate);

insert into csm_user_group_role_pg (USER_GROUP_ROLE_PG_ID,
            GROUP_ID, ROLE_ID, PROTECTION_GROUP_ID,
            UPDATE_DATE)
            values(8,1,8,3,sysdate);

insert into csm_user_group_role_pg (USER_GROUP_ROLE_PG_ID,
            GROUP_ID, ROLE_ID, PROTECTION_GROUP_ID,
            UPDATE_DATE)
            values(9,1,9,3,sysdate);

insert into csm_user_group_role_pg (USER_GROUP_ROLE_PG_ID,
            GROUP_ID, ROLE_ID, PROTECTION_GROUP_ID,
            UPDATE_DATE)
            values(10,1,10,4,sysdate);

insert into csm_user_group_role_pg (USER_GROUP_ROLE_PG_ID,
            GROUP_ID, ROLE_ID, PROTECTION_GROUP_ID,
            UPDATE_DATE)
            values(11,1,11,4,sysdate);

insert into csm_user_group_role_pg (USER_GROUP_ROLE_PG_ID,
            GROUP_ID, ROLE_ID, PROTECTION_GROUP_ID,
            UPDATE_DATE)
            values(12,1,12,3,sysdate);

insert into csm_user_group_role_pg (USER_GROUP_ROLE_PG_ID,
            GROUP_ID, ROLE_ID, PROTECTION_GROUP_ID,
            UPDATE_DATE)
            values(13,1,13,5,sysdate);

insert into csm_user_group_role_pg (USER_GROUP_ROLE_PG_ID,
            GROUP_ID, ROLE_ID, PROTECTION_GROUP_ID,
            UPDATE_DATE)
            values(14,1,14,5,sysdate);

insert into csm_user_group_role_pg (USER_GROUP_ROLE_PG_ID,
            GROUP_ID, ROLE_ID, PROTECTION_GROUP_ID,
            UPDATE_DATE)
            values(15,1,15,5,sysdate);

insert into csm_user_group_role_pg (USER_GROUP_ROLE_PG_ID,
            GROUP_ID, ROLE_ID, PROTECTION_GROUP_ID,
            UPDATE_DATE)
            values(16,1,16,6,sysdate);

insert into csm_user_group_role_pg (USER_GROUP_ROLE_PG_ID,
            GROUP_ID, ROLE_ID, PROTECTION_GROUP_ID,
            UPDATE_DATE)
            values(17,1,17,6,sysdate);

insert into csm_user_group_role_pg (USER_GROUP_ROLE_PG_ID,
            GROUP_ID, ROLE_ID, PROTECTION_GROUP_ID,
            UPDATE_DATE)
            values(18,1,18,6,sysdate);

insert into csm_user_group_role_pg (USER_GROUP_ROLE_PG_ID,
            GROUP_ID, ROLE_ID, PROTECTION_GROUP_ID,
            UPDATE_DATE)
            values(19,2,1,1,sysdate);

insert into csm_user_group_role_pg (USER_GROUP_ROLE_PG_ID,
            GROUP_ID, ROLE_ID, PROTECTION_GROUP_ID,
            UPDATE_DATE)
            values(20,2,2,1,sysdate);

insert into csm_user_group_role_pg (USER_GROUP_ROLE_PG_ID,
            GROUP_ID, ROLE_ID, PROTECTION_GROUP_ID,
            UPDATE_DATE)
            values(21,2,3,1,sysdate);

insert into csm_user_group_role_pg (USER_GROUP_ROLE_PG_ID,
            GROUP_ID, ROLE_ID, PROTECTION_GROUP_ID,
            UPDATE_DATE)
            values(22,2,7,3,sysdate);

insert into csm_user_group_role_pg (USER_GROUP_ROLE_PG_ID,
            GROUP_ID, ROLE_ID, PROTECTION_GROUP_ID,
            UPDATE_DATE)
            values(23,2,8,3,sysdate);

insert into csm_user_group_role_pg (USER_GROUP_ROLE_PG_ID,
            GROUP_ID, ROLE_ID, PROTECTION_GROUP_ID,
            UPDATE_DATE)
            values(24,2,9,3,sysdate);

insert into csm_user_group_role_pg (USER_GROUP_ROLE_PG_ID,
            GROUP_ID, ROLE_ID, PROTECTION_GROUP_ID,
            UPDATE_DATE)
            values(25,2,6,2,sysdate);

insert into csm_user_group_role_pg (USER_GROUP_ROLE_PG_ID,
            GROUP_ID, ROLE_ID, PROTECTION_GROUP_ID,
            UPDATE_DATE)
            values(26,3,3,1,sysdate);

insert into csm_user_group_role_pg (USER_GROUP_ROLE_PG_ID,
            GROUP_ID, ROLE_ID, PROTECTION_GROUP_ID,
            UPDATE_DATE)
            values(27,3,4,2,sysdate);

insert into csm_user_group_role_pg (USER_GROUP_ROLE_PG_ID,
            GROUP_ID, ROLE_ID, PROTECTION_GROUP_ID,
            UPDATE_DATE)
            values(28,3,5,2,sysdate);

insert into csm_user_group_role_pg (USER_GROUP_ROLE_PG_ID,
            GROUP_ID, ROLE_ID, PROTECTION_GROUP_ID,
            UPDATE_DATE)
            values(29,3,6,2,sysdate);

insert into csm_user_group_role_pg (USER_GROUP_ROLE_PG_ID,
            GROUP_ID, ROLE_ID, PROTECTION_GROUP_ID,
            UPDATE_DATE)
            values(30,3,9,3,sysdate);

