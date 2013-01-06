--Registrar role to Admin and Site Co
Insert into csm_user_group(user_id,group_id)
select csm_user.user_id, 2 from csm_user, csm_user_group
where csm_user.user_id = csm_user_group.user_id and csm_user_group.group_id = -25;

--Delete for Admin
delete from csm_user_group_role_pg where 
user_id in (select distinct u.user_id from csm_user u, csm_user_group ug
where u.user_id = ug.user_id and ug.group_id = -23);

--Delete for Site Coordinator
delete from csm_user_group_role_pg where 
user_id in (select distinct u.user_id from csm_user u, csm_user_group ug
where u.user_id = ug.user_id and ug.group_id = -25) and
protection_group_id in (select protection_group_id from csm_protection_group
where protection_group_name like 'Study.%');

--All Site access to admin
Insert into csm_user_group_role_pg(USER_GROUP_ROLE_PG_ID,user_id,role_id,protection_group_id,update_date)
select CSM_USER_GROU_USER_GROUP_R_SEQ.nextval, aaa.user_id,aaa.role_id,-1,aaa.tempdate from (select distinct u.user_id,r.role_id,(select SYSDATE from dual) as 
tempdate from csm_user u, csm_group g, csm_user_group ug, csm_role r where u.user_id = ug.user_id and ug.group_id = -23 and 
r.role_name='person_and_organization_information_manager') aaa;

Insert into csm_user_group_role_pg(USER_GROUP_ROLE_PG_ID,user_id,role_id,protection_group_id,update_date)
select CSM_USER_GROU_USER_GROUP_R_SEQ.nextval, aaa.user_id,aaa.role_id,-1,aaa.tempdate from (select distinct u.user_id,r.role_id,(select SYSDATE from dual) as 
tempdate from csm_user u, csm_group g, csm_user_group ug, csm_role r where u.user_id = ug.user_id and ug.group_id = -23 and r.role_name='user_administrator') aaa;

Insert into csm_user_group_role_pg(USER_GROUP_ROLE_PG_ID,user_id,role_id,protection_group_id,update_date)
select CSM_USER_GROU_USER_GROUP_R_SEQ.nextval, aaa.user_id,aaa.role_id,-1,aaa.tempdate from (select distinct u.user_id,r.role_id,(select SYSDATE from dual) as 
tempdate from csm_user u, csm_group g, csm_user_group ug, csm_role r where u.user_id = ug.user_id and ug.group_id = -23 and r.role_name='study_qa_manager') aaa;

Insert into csm_user_group_role_pg(USER_GROUP_ROLE_PG_ID,user_id,role_id,protection_group_id,update_date)
select CSM_USER_GROU_USER_GROUP_R_SEQ.nextval, aaa.user_id,aaa.role_id,-1,aaa.tempdate from (select distinct u.user_id,r.role_id,(select SYSDATE from dual) as 
tempdate from csm_user u, csm_group g, csm_user_group ug, csm_role r where u.user_id = ug.user_id and ug.group_id = -23 and r.role_name='study_creator') aaa;

Insert into csm_user_group_role_pg(USER_GROUP_ROLE_PG_ID,user_id,role_id,protection_group_id,update_date)
select CSM_USER_GROU_USER_GROUP_R_SEQ.nextval, aaa.user_id,aaa.role_id,-1,aaa.tempdate from (select distinct u.user_id,r.role_id,(select SYSDATE from dual) as 
tempdate from csm_user u, csm_group g, csm_user_group ug, csm_role r where u.user_id = ug.user_id and ug.group_id = -23 and r.role_name='supplemental_study_information_manager') aaa;

Insert into csm_user_group_role_pg(USER_GROUP_ROLE_PG_ID,user_id,role_id,protection_group_id,update_date)
select CSM_USER_GROU_USER_GROUP_R_SEQ.nextval, aaa.user_id,aaa.role_id,-1,aaa.tempdate from (select distinct u.user_id,r.role_id,(select SYSDATE from dual) as 
tempdate from csm_user u, csm_group g, csm_user_group ug, csm_role r where u.user_id = ug.user_id and ug.group_id = -23 and r.role_name='study_site_participation_administrator') aaa;

Insert into csm_user_group_role_pg(USER_GROUP_ROLE_PG_ID,user_id,role_id,protection_group_id,update_date)
select CSM_USER_GROU_USER_GROUP_R_SEQ.nextval, aaa.user_id,aaa.role_id,-1,aaa.tempdate from (select distinct u.user_id,r.role_id,(select SYSDATE from dual) as 
tempdate from csm_user u, csm_group g, csm_user_group ug, csm_role r where u.user_id = ug.user_id and ug.group_id = -23 and r.role_name='study_team_administrator') aaa;

Insert into csm_user_group_role_pg(USER_GROUP_ROLE_PG_ID,user_id,role_id,protection_group_id,update_date)
select CSM_USER_GROU_USER_GROUP_R_SEQ.nextval, aaa.user_id,aaa.role_id,-1,aaa.tempdate from (select distinct u.user_id,r.role_id,(select SYSDATE from dual) as 
tempdate from csm_user u, csm_group g, csm_user_group ug, csm_role r where u.user_id = ug.user_id and ug.group_id = -23 and r.role_name='registration_qa_manager') aaa;

Insert into csm_user_group_role_pg(USER_GROUP_ROLE_PG_ID,user_id,role_id,protection_group_id,update_date)
select CSM_USER_GROU_USER_GROUP_R_SEQ.nextval, aaa.user_id,aaa.role_id,-1,aaa.tempdate from (select distinct u.user_id,r.role_id,(select SYSDATE from dual) as 
tempdate from csm_user u, csm_group g, csm_user_group ug, csm_role r where u.user_id = ug.user_id and ug.group_id = -23 and r.role_name='subject_manager') aaa;

Insert into csm_user_group_role_pg(USER_GROUP_ROLE_PG_ID,user_id,role_id,protection_group_id,update_date)
select CSM_USER_GROU_USER_GROUP_R_SEQ.nextval, aaa.user_id,aaa.role_id,-1,aaa.tempdate from (select distinct u.user_id,r.role_id,(select SYSDATE from dual) as 
tempdate from csm_user u, csm_group g, csm_user_group ug, csm_role r where u.user_id = ug.user_id and ug.group_id = -23 and r.role_name='registrar') aaa;

Insert into csm_user_group_role_pg(USER_GROUP_ROLE_PG_ID,user_id,role_id,protection_group_id,update_date)
select CSM_USER_GROU_USER_GROUP_R_SEQ.nextval, aaa.user_id,aaa.role_id,-1,aaa.tempdate from (select distinct u.user_id,r.role_id,(select SYSDATE from dual) as 
tempdate from csm_user u, csm_group g, csm_user_group ug, csm_role r where u.user_id = ug.user_id and ug.group_id = -23 and r.role_name='data_reader') aaa;

Insert into csm_user_group_role_pg(USER_GROUP_ROLE_PG_ID,user_id,role_id,protection_group_id,update_date)
select CSM_USER_GROU_USER_GROUP_R_SEQ.nextval, aaa.user_id,aaa.role_id,-1,aaa.tempdate from (select distinct u.user_id,r.role_id,(select SYSDATE from dual) as 
tempdate from csm_user u, csm_group g, csm_user_group ug, csm_role r where u.user_id = ug.user_id and ug.group_id = -23 and r.role_name='data_analyst') aaa;

--All Study access to admin and Site Co
Insert into csm_user_group_role_pg(USER_GROUP_ROLE_PG_ID,user_id,role_id,protection_group_id,update_date)
select CSM_USER_GROU_USER_GROUP_R_SEQ.nextval, aaa.user_id,aaa.role_id,-2,aaa.tempdate from (select distinct u.user_id,r.role_id,(select SYSDATE from dual) as 
tempdate from csm_user u, csm_group g, csm_user_group ug, csm_role r where u.user_id = ug.user_id and ug.group_id = -25 and r.role_name='registrar') aaa;

Insert into csm_user_group_role_pg(USER_GROUP_ROLE_PG_ID,user_id,role_id,protection_group_id,update_date)
select CSM_USER_GROU_USER_GROUP_R_SEQ.nextval, aaa.user_id,aaa.role_id,-2,aaa.tempdate from (select distinct u.user_id,r.role_id,(select SYSDATE from dual) as 
tempdate from csm_user u, csm_group g, csm_user_group ug, csm_role r where u.user_id = ug.user_id and ug.group_id = -25 and r.role_name='data_reader') aaa;

Insert into csm_user_group_role_pg(USER_GROUP_ROLE_PG_ID,user_id,role_id,protection_group_id,update_date)
select CSM_USER_GROU_USER_GROUP_R_SEQ.nextval, aaa.user_id,aaa.role_id,-2,aaa.tempdate from (select distinct u.user_id,r.role_id,(select SYSDATE from dual) as 
tempdate from csm_user u, csm_group g, csm_user_group ug, csm_role r where u.user_id = ug.user_id and ug.group_id = -25 and r.role_name='data_analyst') aaa;