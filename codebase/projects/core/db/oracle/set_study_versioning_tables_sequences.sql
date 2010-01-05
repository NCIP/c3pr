drop sequence consents_id_seq;
drop sequence study_versions_id_seq;
drop sequence study_subject_consents_id_seq;
drop sequence study_site_versions_id_seq;
drop sequence stu_site_status_history_id_seq;
drop sequence study_subject_versions_id_seq;

declare
l_cnt   NUMBER(10,0);
begin
 execute immediate 'select max(id) from consents' into l_cnt;
 execute immediate'CREATE SEQUENCE consents_id_seq INCREMENT BY 1  START WITH ' || NVL( l_cnt, 1 )  || 'NOMAXVALUE MINVALUE 1 NOCYCLE NOCACHE NOORDER';
 execute immediate 'select max(id) from study_versions' into l_cnt;
 execute immediate'CREATE SEQUENCE study_versions_id_seq INCREMENT BY 1  START WITH ' || NVL( l_cnt, 1 )  || 'NOMAXVALUE MINVALUE 1 NOCYCLE NOCACHE NOORDER';
 execute immediate 'select max(id) from study_subject_consents' into l_cnt;
 execute immediate'CREATE SEQUENCE study_subject_consents_id_seq INCREMENT BY 1  START WITH ' || NVL( l_cnt, 1 )  || 'NOMAXVALUE MINVALUE 1 NOCYCLE NOCACHE NOORDER';
 execute immediate 'select max(id) from study_site_versions' into l_cnt;
 execute immediate'CREATE SEQUENCE study_site_versions_id_seq INCREMENT BY 1  START WITH ' || NVL( l_cnt, 1 )  || 'NOMAXVALUE MINVALUE 1 NOCYCLE NOCACHE NOORDER';
 execute immediate 'select max(id) from stu_site_status_history' into l_cnt;
 execute immediate'CREATE SEQUENCE stu_site_status_history_id_seq INCREMENT BY 1  START WITH ' || NVL( l_cnt, 1 )  || 'NOMAXVALUE MINVALUE 1 NOCYCLE NOCACHE NOORDER';
 execute immediate 'select max(id) from study_subject_versions' into l_cnt;
 execute immediate'CREATE SEQUENCE study_subject_versions_id_seq INCREMENT BY 1  START WITH ' || NVL( l_cnt, 1 )  || 'NOMAXVALUE MINVALUE 1 NOCYCLE NOCACHE NOORDER';
 end

commit;