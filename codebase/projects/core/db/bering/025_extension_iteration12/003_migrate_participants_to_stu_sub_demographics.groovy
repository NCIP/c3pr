class MiagrateParticipantsToStudySubjectDemographics extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
         if (databaseMatches('postgres')) {
         	execute("ALTER table stu_sub_demographics add column study_subjects_id_temp integer");
		   	execute("insert into stu_sub_demographics (id,version,grid_id,retired_indicator,first_name,last_name,middle_name,maiden_name,administrative_gender_code,birth_date,ethnic_group_code,marital_status_code,race_code,prt_id,add_id,study_subjects_id_temp) (select nextVal('stu_sub_demographics_ID_SEQ'),0,participants.grid_id,participants.retired_indicator,first_name,last_name,middle_name, maiden_name,administrative_gender_code,birth_date,ethnic_group_code,marital_status_code,race_code,prt_id,add_id,study_subjects.id from participants join study_subjects on participants.id= study_subjects.prt_id where study_subjects.id not in (select study_subjects.id from study_subjects join stu_sub_associations on study_subjects.id = stu_sub_associations.child_stu_sub_id ))");
		   	execute("update study_subjects set stu_sub_dmgphcs_id = (select temp.stu_sub_demp_id from (select study_subjects_id_temp as stu_sub_id, stu_sub_demographics.id as stu_sub_demp_id from study_subjects join stu_sub_demographics on stu_sub_demographics.study_subjects_id_temp = study_subjects.id) as temp where temp.stu_sub_id = id)");
		   	execute("ALTER table stu_sub_demographics drop column study_subjects_id_temp");
		   	execute("update study_subjects set stu_sub_dmgphcs_id = (select stu_sub_dmgphcs_id from (select study_subjects.id , study_subjects.stu_sub_dmgphcs_id,stu_sub_associations.child_stu_sub_id,stu_sub_associations.parent_stu_sub_id from study_subjects  join stu_sub_associations on study_subjects.id = stu_sub_associations.parent_stu_sub_id) as temp1 where study_subjects.id = temp1.child_stu_sub_id) where id in (select study_subjects.id from study_subjects join stu_sub_associations on study_subjects.id = stu_sub_associations.child_stu_sub_id)");
		   	execute("insert into contact_mechanisms (id,version,retired_indicator,dtype,type,value,stu_sub_dmgphcs_id) (select nextVal('contact_mechanisms_ID_SEQ'),0,contact_mechanisms.retired_indicator,contact_mechanisms.dtype,contact_mechanisms.type,contact_mechanisms.value,stu_sub_demographics.id from contact_mechanisms join participants on participants.id= contact_mechanisms.prt_id join stu_sub_demographics on participants.id = stu_sub_demographics.prt_id)");
		   	execute("insert into identifiers (id,version,retired_indicator,primary_indicator,dtype,type,value,system_name,hcs_id,stu_sub_dmgphcs_id) (select nextVal('identifiers_ID_SEQ'),0,identifiers.retired_indicator,identifiers.primary_indicator,identifiers.dtype,identifiers.type,identifiers.value,identifiers.system_name,identifiers.hcs_id,stu_sub_demographics.id from identifiers join participants on participants.id= identifiers.prt_id join stu_sub_demographics on participants.id = stu_sub_demographics.prt_id)");
		   	execute("ALTER SEQUENCE stu_sub_demographics_id_seq RESTART WITH 1");
		   	execute("update study_subjects set stu_sub_dmgphcs_id = nextval('stu_sub_demographics_id_seq') where id in (select  study_subjects.id as study_subjects_id from participants join study_subjects on participants.id= study_subjects.prt_id where study_subjects.id not in (select study_subjects.id from study_subjects join stu_sub_associations on study_subjects.id = stu_sub_associations.child_stu_sub_id) order by study_subjects.id)");
		   	execute("update stu_sub_demographics set valid ='1' where id in (select stu_sub_demographics.id from stu_sub_demographics join study_subjects on study_subjects.stu_sub_dmgphcs_id = stu_sub_demographics.id where study_subjects.reg_workflow_status != 'PENDING' group by stu_sub_demographics.id)");
		   	
		   	
	 	}
	 	if (databaseMatches('oracle')) {
	 		execute("ALTER table stu_sub_demographics add study_subjects_id_temp integer");
	 		execute("insert into stu_sub_demographics (id,version,grid_id,retired_indicator,first_name,last_name,middle_name,maiden_name,administrative_gender_code,birth_date,ethnic_group_code,marital_status_code,race_code,prt_id,add_id,study_subjects_id_temp) (select stu_sub_demographics_ID_SEQ.NEXTVAL,0,participants.grid_id,participants.retired_indicator,first_name,last_name,middle_name,maiden_name,administrative_gender_code,birth_date,ethnic_group_code,marital_status_code,race_code,prt_id,add_id,study_subjects.id from participants join study_subjects on participants.id= study_subjects.prt_id where study_subjects.id not in (select study_subjects.id from study_subjects join stu_sub_associations on study_subjects.id = stu_sub_associations.child_stu_sub_id ))");
	 		
	 		 // updated after testing Baylor Oracle Migration
	 		execute("update study_subjects ss set stu_sub_dmgphcs_id = ( select id from stu_sub_demographics sdmgphcstemp where sdmgphcstemp.study_subjects_id_temp = ss.id)");
	 		
	 		execute("ALTER table stu_sub_demographics drop column study_subjects_id_temp");
	 		execute("update study_subjects set stu_sub_dmgphcs_id = (select stu_sub_dmgphcs_id from (select study_subjects.id , study_subjects.stu_sub_dmgphcs_id,stu_sub_associations.child_stu_sub_id,stu_sub_associations.parent_stu_sub_id from study_subjects  join stu_sub_associations on study_subjects.id = stu_sub_associations.parent_stu_sub_id) where study_subjects.id = child_stu_sub_id) where id in (select study_subjects.id from study_subjects join stu_sub_associations on study_subjects.id = stu_sub_associations.child_stu_sub_id)");
	 		execute("insert into contact_mechanisms (id,version,retired_indicator,dtype,type,value,stu_sub_dmgphcs_id) (select contact_mechanisms_ID_SEQ.NEXTVAL,0,contact_mechanisms.retired_indicator,contact_mechanisms.dtype,contact_mechanisms.type,contact_mechanisms.value,stu_sub_demographics.id from contact_mechanisms join participants on participants.id= contact_mechanisms.prt_id join stu_sub_demographics on participants.id = stu_sub_demographics.prt_id)");
	 		execute("insert into identifiers (id,version,retired_indicator,primary_indicator,dtype,type,value,system_name,hcs_id,stu_sub_dmgphcs_id) (select identifiers_ID_SEQ.NEXTVAL,0,identifiers.retired_indicator,identifiers.primary_indicator,identifiers.dtype,identifiers.type,identifiers.value,identifiers.system_name,identifiers.hcs_id,stu_sub_demographics.id from identifiers join participants on participants.id= identifiers.prt_id join stu_sub_demographics on participants.id = stu_sub_demographics.prt_id)");
	 		execute("DROP SEQUENCE stu_sub_demographics_id_seq");
	 		
	 		 // added after testing Baylor Oracle Migration
	 		execute("CREATE SEQUENCE stu_sub_demographics_id_seq INCREMENT BY 1 START WITH 15000 NOMAXVALUE MINVALUE 1 NOCYCLE NOCACHE NOORDER");
	 		execute("update stu_sub_demographics set valid ='1' where id in (select stu_sub_demographics.id from stu_sub_demographics join study_subjects on study_subjects.stu_sub_dmgphcs_id = stu_sub_demographics.id where study_subjects.reg_workflow_status != 'PENDING' group by stu_sub_demographics.id)");
	 	}
	 	
	}
	void down() {
	 	
    }
}


