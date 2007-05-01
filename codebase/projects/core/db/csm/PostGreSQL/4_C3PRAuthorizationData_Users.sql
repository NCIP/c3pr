
insert into csm_user (USER_ID, LOGIN_NAME,FIRST_NAME,LAST_NAME,PASSWORD,UPDATE_DATE)
values (2, 'c3pr_registrar','c3pr','registrar','r+lEhHNNKK8LZtouIzqBCw==','now');


insert into csm_user (USER_ID, LOGIN_NAME,FIRST_NAME,LAST_NAME,PASSWORD,UPDATE_DATE)
values (3, 'c3pr_study_coordinator','c3pr','study_coordinator','nH4FjhM3deAxZD+F13ogiENcBWB1ntZ2','now');




insert into csm_group(GROUP_ID,GROUP_NAME,GROUP_DESC,APPLICATION_ID,UPDATE_DATE)
values(2,'registrar','study coordinator group',1,'now');

insert into csm_group(GROUP_ID,GROUP_NAME,GROUP_DESC,APPLICATION_ID,UPDATE_DATE)
values(3,'study_coordinator','study coordinator group',1,'now');


insert into CSM_USER_GROUP(USER_GROUP_ID, USER_ID,GROUP_ID)
values(2,2,2);

insert into CSM_USER_GROUP(USER_GROUP_ID, USER_ID,GROUP_ID)
values(3,3,3);



insert into csm_user_group_role_pg (USER_GROUP_ROLE_PG_ID,
            GROUP_ID, ROLE_ID, PROTECTION_GROUP_ID,
            UPDATE_DATE)
            values(16,2,1,1,'now');

insert into csm_user_group_role_pg (USER_GROUP_ROLE_PG_ID,
            GROUP_ID, ROLE_ID, PROTECTION_GROUP_ID,
            UPDATE_DATE)
            values(17,2,2,1,'now');

insert into csm_user_group_role_pg (USER_GROUP_ROLE_PG_ID,
            GROUP_ID, ROLE_ID, PROTECTION_GROUP_ID,
            UPDATE_DATE)
            values(18,2,3,1,'now');

insert into csm_user_group_role_pg (USER_GROUP_ROLE_PG_ID,
            GROUP_ID, ROLE_ID, PROTECTION_GROUP_ID,
            UPDATE_DATE)
            values(19,2,7,3,'now');

insert into csm_user_group_role_pg (USER_GROUP_ROLE_PG_ID,
            GROUP_ID, ROLE_ID, PROTECTION_GROUP_ID,
            UPDATE_DATE)
            values(20,2,8,3,'now');

insert into csm_user_group_role_pg (USER_GROUP_ROLE_PG_ID,
            GROUP_ID, ROLE_ID, PROTECTION_GROUP_ID,
            UPDATE_DATE)
            values(21,2,9,3,'now');

insert into csm_user_group_role_pg (USER_GROUP_ROLE_PG_ID,
            GROUP_ID, ROLE_ID, PROTECTION_GROUP_ID,
            UPDATE_DATE)
            values(22,2,6,2,'now');

insert into csm_user_group_role_pg (USER_GROUP_ROLE_PG_ID,
            GROUP_ID, ROLE_ID, PROTECTION_GROUP_ID,
            UPDATE_DATE)
            values(23,3,3,1,'now');

insert into csm_user_group_role_pg (USER_GROUP_ROLE_PG_ID,
            GROUP_ID, ROLE_ID, PROTECTION_GROUP_ID,
            UPDATE_DATE)
            values(24,3,4,2,'now');

insert into csm_user_group_role_pg (USER_GROUP_ROLE_PG_ID,
            GROUP_ID, ROLE_ID, PROTECTION_GROUP_ID,
            UPDATE_DATE)
            values(25,3,5,2,'now');

insert into csm_user_group_role_pg (USER_GROUP_ROLE_PG_ID,
            GROUP_ID, ROLE_ID, PROTECTION_GROUP_ID,
            UPDATE_DATE)
            values(26,3,6,2,'now');

insert into csm_user_group_role_pg (USER_GROUP_ROLE_PG_ID,
            GROUP_ID, ROLE_ID, PROTECTION_GROUP_ID,
            UPDATE_DATE)
            values(27,3,9,3,'now');
commit;
