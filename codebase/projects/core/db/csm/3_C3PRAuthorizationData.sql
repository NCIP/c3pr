
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



insert into csm_pg_pe(pg_pe_id,protection_group_id,
            protection_element_id)
            values(1,1,2);

insert into csm_pg_pe(pg_pe_id,protection_group_id,
            protection_element_id)
            values(2,2,3);

insert into csm_pg_pe(pg_pe_id,protection_group_id,
            protection_element_id)
            values(3,3,4);



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