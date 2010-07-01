insert into csm_protection_group(protection_group_id,protection_group_name,protection_group_description,application_id,large_element_count_flag,update_date) 
values(-1,'HealthcareSite','HealthcareSite',-1,0,(select SYSDATE from dual));

insert into csm_protection_group(protection_group_id,protection_group_name,protection_group_description,application_id,large_element_count_flag,update_date) 
values(-2,'Study','Study',-1,0,(select SYSDATE from dual));

insert into csm_protection_group(protection_group_name,protection_group_description,application_id,large_element_count_flag,update_date)
select distinct 'HealthcareSite.'||identifiers.value as pg_name,'HealthcareSite.'||identifiers.value as pg_value,-1 as application_id,0 as large_count,(select SYSDATE from dual) as update_date 
from  identifiers, organizations, research_staff where identifiers.org_id=organizations.id and identifiers.primary_indicator=1 and
organizations.id=research_staff.hcs_id;

insert into csm_protection_group(protection_group_name,protection_group_description,application_id,large_element_count_flag,update_date)
select distinct 'Study.'||identifiers.value as pg_name,'Study.'||identifiers.value as pg_value,-1 as application_id,0 as large_count,(select SYSDATE from dual) as update_date 
from  identifiers, studies where identifiers.stu_id=studies.id and identifiers.type='COORDINATING_CENTER_IDENTIFIER';

insert into csm_protection_element(protection_element_id, protection_element_name,protection_element_description,object_id,application_id,update_date) 
select CSM_PROTECTIO_PROTECTION_E_SEQ.nextval, pg.protection_group_name,pg.protection_group_name,pg.protection_group_name,-1,pg.update_date from csm_protection_group pg;

insert into csm_pg_pe(pg_pe_id, protection_group_id,protection_element_id,update_date)
select CSM_PG_PE_PG_PE_ID_SEQ.nextval, pg.protection_group_id, pe.protection_element_id, (select SYSDATE from dual) from csm_protection_group pg, csm_protection_element pe 
where pg.protection_group_name = pe.protection_element_name;

insert into csm_user_group_role_pg(user_id,role_id,protection_group_id,update_date)
select distinct u.user_id,r.role_id,pg.protection_group_id,(select SYSDATE from dual) from csm_user u, csm_role r, csm_protection_group pg, research_staff rs, organizations o, identifiers i, 
csm_group g, csm_user_group ug where u.user_id = rs.login_id and rs.hcs_id = o.id and i.org_id = o.id and i.primary_indicator = 1 and 
pg.protection_group_name = 'HealthcareSite.'||i.value and ug.user_id = u.user_id and ug.group_id = g.group_id and g.group_name=r.role_name;

insert into csm_user_group_role_pg(user_id,role_id,protection_group_id,update_date)
select distinct u.user_id,r.role_id,pg.protection_group_id,(select SYSDATE from dual) from csm_user u, csm_role r, csm_protection_group pg, research_staff rs, study_organizations so, 
study_personnel sp, studies s, identifiers i, csm_group g, csm_user_group ug 
where u.user_id = rs.login_id and sp.research_staff_id = rs.id and sp.sto_id = so.id and so.study_id = s.id and i.stu_id = s.id and i.type='COORDINATING_CENTER_IDENTIFIER' and 
pg.protection_group_name = 'Study.'||i.value and ug.user_id = u.user_id and ug.group_id = g.group_id and g.group_name=r.role_name;

--All Site access to admin
insert into csm_user_group_role_pg(user_id,role_id,protection_group_id,update_date)
select distinct u.user_id,r.role_id,-1,(select SYSDATE from dual) from csm_user u, csm_group g, csm_user_group ug, csm_role r 
where u.user_id = ug.user_id and ug.group_id = -23 and r.role_name='system_administrator';

insert into csm_user_group_role_pg(user_id,role_id,protection_group_id,update_date)
select distinct u.user_id,r.role_id,-1,(select SYSDATE from dual) from csm_user u, csm_group g, csm_user_group ug, csm_role r
where u.user_id = ug.user_id and ug.group_id = -23 and r.role_name='user_administrator';

insert into csm_user_group_role_pg(user_id,role_id,protection_group_id,update_date)
select distinct u.user_id,r.role_id,-1,(select SYSDATE from dual) from csm_user u, csm_group g, csm_user_group ug, csm_role r
where u.user_id = ug.user_id and ug.group_id = -23 and r.role_name='study_qa_manager';

insert into csm_user_group_role_pg(user_id,role_id,protection_group_id,update_date)
select distinct u.user_id,r.role_id,-1,(select SYSDATE from dual) from csm_user u, csm_group g, csm_user_group ug, csm_role r
where u.user_id = ug.user_id and ug.group_id = -23 and r.role_name='study_creator';

insert into csm_user_group_role_pg(user_id,role_id,protection_group_id,update_date)
select distinct u.user_id,r.role_id,-1,(select SYSDATE from dual) from csm_user u, csm_group g, csm_user_group ug, csm_role r
where u.user_id = ug.user_id and ug.group_id = -23 and r.role_name='supplemental_study_information_manager';

insert into csm_user_group_role_pg(user_id,role_id,protection_group_id,update_date)
select distinct u.user_id,r.role_id,-1,(select SYSDATE from dual) from csm_user u, csm_group g, csm_user_group ug, csm_role r
where u.user_id = ug.user_id and ug.group_id = -23 and r.role_name='study_site_participation_administrator';

insert into csm_user_group_role_pg(user_id,role_id,protection_group_id,update_date)
select distinct u.user_id,r.role_id,-1,(select SYSDATE from dual) from csm_user u, csm_group g, csm_user_group ug, csm_role r
where u.user_id = ug.user_id and ug.group_id = -23 and r.role_name='study_team_administrator';

insert into csm_user_group_role_pg(user_id,role_id,protection_group_id,update_date)
select distinct u.user_id,r.role_id,-1,(select SYSDATE from dual) from csm_user u, csm_group g, csm_user_group ug, csm_role r
where u.user_id = ug.user_id and ug.group_id = -23 and r.role_name='registration_qa_manager';

insert into csm_user_group_role_pg(user_id,role_id,protection_group_id,update_date)
select distinct u.user_id,r.role_id,-1,(select SYSDATE from dual) from csm_user u, csm_group g, csm_user_group ug, csm_role r
where u.user_id = ug.user_id and ug.group_id = -23 and r.role_name='subject_manager';

insert into csm_user_group_role_pg(user_id,role_id,protection_group_id,update_date)
select distinct u.user_id,r.role_id,-1,(select SYSDATE from dual) from csm_user u, csm_group g, csm_user_group ug, csm_role r
where u.user_id = ug.user_id and ug.group_id = -23 and r.role_name='registrar';

insert into csm_user_group_role_pg(user_id,role_id,protection_group_id,update_date)
select distinct u.user_id,r.role_id,-1,(select SYSDATE from dual) from csm_user u, csm_group g, csm_user_group ug, csm_role r
where u.user_id = ug.user_id and ug.group_id = -23 and r.role_name='data_reader';

insert into csm_user_group_role_pg(user_id,role_id,protection_group_id,update_date)
select distinct u.user_id,r.role_id,-1,(select SYSDATE from dual) from csm_user u, csm_group g, csm_user_group ug, csm_role r
where u.user_id = ug.user_id and ug.group_id = -23 and r.role_name='data_analyst';

--All Study access to admin
insert into csm_user_group_role_pg(user_id,role_id,protection_group_id,update_date)
select distinct u.user_id,r.role_id,-2,(select SYSDATE from dual) from csm_user u, csm_group g, csm_user_group ug, csm_role r
where u.user_id = ug.user_id and ug.group_id = -23 and r.role_name='registrar';

insert into csm_user_group_role_pg(user_id,role_id,protection_group_id,update_date)
select distinct u.user_id,r.role_id,-2,(select SYSDATE from dual) from csm_user u, csm_group g, csm_user_group ug, csm_role r
where u.user_id = ug.user_id and ug.group_id = -23 and r.role_name='data_reader';

insert into csm_user_group_role_pg(user_id,role_id,protection_group_id,update_date)
select distinct u.user_id,r.role_id,-2,(select SYSDATE from dual) from csm_user u, csm_group g, csm_user_group ug, csm_role r
where u.user_id = ug.user_id and ug.group_id = -23 and r.role_name='data_analyst';