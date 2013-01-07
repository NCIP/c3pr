class MigrateToStudyVersion extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
		//New column for number of consents reqd.
		addColumn('studies','consent_required', 'string');
		execute("update studies set consent_required='ALL'");

		// associating epoch to study version
        addColumn('epochs','stu_version_id', 'integer');
        execute("update epochs set stu_version_id=stu_id");
		execute("alter table epochs drop constraint FK_EPH_STU");
		execute("ALTER TABLE epochs drop CONSTRAINT UK_STU_EPH");
		execute("ALTER TABLE epochs ADD CONSTRAINT UK_STU_VER_EPH UNIQUE(name,stu_version_id)");
		dropColumn('epochs', 'stu_id');
        
        // associating disease to study version
        addColumn('study_diseases','stu_version_id', 'integer');
        execute("update study_diseases set stu_version_id=study_id");
		execute("alter table study_diseases drop constraint UK_STU_DTM");
		execute("ALTER TABLE study_diseases ADD CONSTRAINT UK_STU_VER_DTM UNIQUE(stu_version_id,DISEASE_TERM_ID)");
		dropColumn('study_diseases', 'study_id');
		
		// associating companion association to parent study version
      	addColumn('comp_stu_associations', 'parent_version_id', 'integer');
      	execute("update comp_stu_associations set parent_version_id=parent_study_id");
      	dropColumn('comp_stu_associations', 'parent_study_id');
      	
      		if (databaseMatches('postgres')){
    	// migrating consent version to new table consents from studies.
    			
		//migrate study to study version
		
		execute("insert into study_versions (id,version,retired_indicator,version_status,study_id,name,short_title_text,long_title_text,description_text,precis_text,randomization_type,data_entry_status,amendment_type,original_indicator,version_date)(select id,version,retired_indicator,'AC',id,'Original version',short_title_text,long_title_text,description_text,precis_text,randomization_type,data_entry_status,'IMMEDIATE','1','01/01/1909' from studies)");
		
		execute("update study_versions set version_status = 'IN' where data_entry_status ilike 'INCOMPLETE'");
		
		// Set  the consent_version to 'Original Consent' when it is not given
 		execute("update studies set consent_version = 'Original Consent' where consent_version is null or consent_version like ''");
		
		execute('insert into "consents"("id", "retired_indicator", "version", "name", "stu_version_id") select "id", "retired_indicator", 0, "consent_version", "id" from studies') ;
 		 		
 		// insert amendments into study versions. set study_versions sequence to the current max(id) value
 			
 		execute("select setval('study_versions_id_seq',max(id)) from study_versions");
 			
 		// setting default version in study_amendments table if it is null or blank
 			
 		execute("update study_amendments set amendment_version = 'Amended Version' where amendment_version is null or amendment_version like ''");
 			
 		execute("alter table study_amendments add column amendment_reasons_text text")

 		execute("update study_amendments set amendment_reasons_text=(select amend_reasons from (select id as study_parts_id, case when eligibility_changed_indicator='1' then ':ELIGIBILITY' else '' end || case when consent_changed_indicator='1' then ':CONSENT' else '' end || case when ea_changed_indicator='1' then ':DESIGN' else '' end || case when strat_changed_indicator='1' then ':STRATIFICATION' else '' end || case when diseases_changed_indicator='1' then ':DISEASE' else '' end || case when pi_changed_indicator='1' then ':DETAIL' else '' end || case when rndm_changed_indicator='1' then ':RANDOMIZATION' else '' end || case when comp_changed_indicator='1' then ':COMPANION' else '' end as amend_reasons from study_amendments) as study_parts where study_amendments.id=study_parts.study_parts_id)");

 		execute("update study_amendments set amendment_reasons_text = trim(leading ':' from amendment_reasons_text)")
 			
 		execute("alter table study_amendments add column version_status text");	
 		
 		// set status of all study versions to active
 		execute("update study_amendments set version_status = 'AC'");
 
 		// get the last study versions of all studies and if the study is in either 'PENDING' or 'AMENDMENT_PENDING' status, set the latest study version in 'INACTIVE' status.
      	execute("update study_amendments set version_status = 'IN' where id in (select study_amendments.id from study_amendments where id in (select max(study_amendment_id) from (select study_amendments.id as study_amendment_id, studies.id as studies_id from studies, study_amendments where study_amendments.stu_id = studies.id and studies.status like '%PENDING%') as study_study_amendments group by studies_id))");
				
		execute("alter table study_amendments add column data_entry_status text");
		  
		execute("update study_amendments set data_entry_status = 'COMPLETE'");
		  
		execute("update study_amendments set data_entry_status = 'INCOMPLETE' where id in (select study_amendments.id from study_amendments where id in (select max(study_amendment_id) from (select study_amendments.id as study_amendment_id, studies.id as studies_id from studies, study_amendments where study_amendments.stu_id = studies.id and studies.data_entry_status like 'INCOMPLETE' ) as study_study_amendments group by studies_id))");
				
				
				// copy study_amendments into study_versions

		execute("insert into study_versions (id,version,retired_indicator,study_id,version_date,name,original_indicator,amendment_type,amendment_reason,comments,version_status,data_entry_status) (select nextval('study_versions_id_seq'),version,retired_indicator,stu_id,amendment_date,amendment_version,false,'IMMEDIATE',amendment_reasons_text,comments,version_status,data_entry_status from study_amendments where study_amendments.amendment_date is not null)");
 											
 		execute("alter table study_amendments drop column version_status");
 			
 		execute("alter table study_amendments drop amendment_reasons_text");
 		
 		execute("alter table study_amendments drop column data_entry_status");
 		
 		execute("UPDATE study_versions SET (short_title_text,long_title_text,precis_text,randomization_type,description_text)=(sv.short_title_text,sv.long_title_text,sv.precis_text,sv.randomization_type,sv.description_text) FROM study_versions sv WHERE sv.study_id=study_versions.study_id and sv.short_title_text is not null and sv.short_title_text not like ''");
 
        execute("select setval('consents_id_seq',max(id)) from consents");
        
       	execute("insert into consents(id,version,retired_indicator,name,stu_version_id) (select nextval('consents_id_seq'),0,'false', study_amendments.consent_version,study_versions.id from study_versions, study_amendments where study_versions.study_id = study_amendments.stu_id and study_versions.version_date = study_amendments.amendment_date and study_amendments.consent_version is not null and study_amendments.consent_version not like '')");
 				
 		
 		// migrate epochs to latest study version based on version_date
 		
 		execute("create table max_study_version (version_date date, study_id int )");
 		execute("insert into max_study_version  (select max(version_date),study_id from study_versions group by study_id)");
 		execute("alter table study_versions add column latest_study_ver_id integer");
 		execute("update study_versions set latest_study_ver_id = temp.max_study_version_id from (select sv.id as max_study_version_id,sv.study_id as study_id from study_versions sv,max_study_version  where sv.study_id = max_study_version.study_id and sv.version_date = max_study_version.version_date)as temp where study_versions.study_id = temp.study_id");
 		execute("update epochs set stu_version_id = latest_study_ver_id from study_versions where epochs.stu_version_id =study_versions.id");
 		execute("update study_diseases set stu_version_id = latest_study_ver_id from study_versions where study_diseases.stu_version_id =study_versions.id");
 		execute("update comp_stu_associations set parent_version_id = latest_study_ver_id from study_versions where comp_stu_associations.parent_version_id =study_versions.id");
 		execute("drop table max_study_version");
 		execute("alter table study_versions drop column latest_study_ver_id");
 		
 		
 		// migrate study site to study site study status history

 		execute("insert into stu_site_status_history(id,retired_indicator,version,start_date,site_study_status,sto_id) (select nextVal('stu_site_status_history_id_seq'),retired_indicator,0,current_date - interval '100 years','PENDING',id from study_organizations where type='SST')");

 		execute("update stu_site_status_history set end_date=(select end_date from (select id as sites_status_id, start_date - interval '1 day' as end_date from study_organizations where study_organizations.type = 'SST' and study_organizations.site_study_status not like'PENDING') as sites_status where stu_site_status_history.sto_id=sites_status.sites_status_id)");

 		execute("insert into stu_site_status_history(id,retired_indicator,version,start_date,site_study_status,sto_id) (select nextVal('stu_site_status_history_id_seq'),retired_indicator,0,start_date,'ACTIVE',id from study_organizations where type='SST'and site_study_status not like'PENDING')");

 		execute("update stu_site_status_history set end_date=(select end_date from (select id as sites_status_id, CURRENT_DATE - interval '1 day' as end_date from study_organizations where study_organizations.type = 'SST' and study_organizations.site_study_status not like'PENDING' and site_study_status not like 'ACTIVE' and site_study_status not like 'AMENDMENT_PENDING') as sites_status where stu_site_status_history.sto_id=sites_status.sites_status_id) where stu_site_status_history.site_study_status like 'ACTIVE'");

 		execute("insert into stu_site_status_history(id,retired_indicator,version,start_date,site_study_status,sto_id) (select nextVal('stu_site_status_history_id_seq'),retired_indicator,0,CURRENT_DATE,'ACTIVE',id from study_organizations where type='SST'and site_study_status not like'PENDING' and site_study_status not like 'ACTIVE' and site_study_status not like 'AMENDMENT_PENDING')");
		
			
		// migrate study site to study site study version
		execute("insert into study_site_versions (id, retired_indicator, version, start_date, irb_approval_date, stu_version_id, sto_id) select id , retired_indicator, 0 , start_date, irb_approval_date, study_id, id from study_organizations where type='SST'")
		
		}
		
    	if (databaseMatches('oracle')){
	    	//migrate study to study version
			
			 // updated for Baylor Oracle Migration
			execute("insert into study_versions (id,version,retired_indicator,version_status,study_id,name,short_title_text,long_title_text,description_text,precis_text,randomization_type,data_entry_status,amendment_type,original_indicator,version_date)(select id,version,retired_indicator,'AC',id,'Original version',short_title_text,long_title_text,description_text,precis_text,randomization_type,data_entry_status,'IMMEDIATE','1',to_date('01/01/1909', 'dd/mm/yyyy') from studies)");
			
			execute("update study_versions set version_status = 'IN' where data_entry_status like 'INCOMPLETE'");
			
			// Set  the consent_version to 'Original Consent' when it is not given
			execute("update studies set consent_version = 'Original Consent' where consent_version is null or consent_version like ''");
	 			 		
	 		execute('insert into consents(id, retired_indicator, version, name, stu_version_id) select id, retired_indicator, 0, consent_version, id from studies') ;
			
			// insert amendments into study versions. set study_versions sequence to the current max(id) value
	 			
	 		execute("alter sequence study_versions_id_seq increment by 5000");
	 		
	 		execute("DROP SEQUENCE study_versions_id_seq");
          	execute("CREATE SEQUENCE study_versions_id_seq start with 15000 increment by 1 NOMAXVALUE minvalue 1 nocycle nocache noorder");
	 		
	 		
	 		// setting default version in study_amendments table if it is null or blank
	 			
	 		execute("update study_amendments set amendment_version = 'Amended Version' where amendment_version is null or amendment_version like ''");
	 		
	 		execute("alter table study_amendments add amendment_reasons_text varchar2(200)");
			execute("update study_amendments set amendment_reasons_text=(select amend_reasons from (select id as study_parts_id, case when eligibility_changed_indicator='1' then ':ELIGIBILITY' else '' end || case when consent_changed_indicator='1' then ':CONSENT' else '' end || case when ea_changed_indicator='1' then ':DESIGN' else '' end || case when strat_changed_indicator='1' then ':STRATIFICATION' else '' end || case when diseases_changed_indicator='1' then ':DISEASE' else '' end || case when pi_changed_indicator='1' then ':DETAIL' else '' end || case when rndm_changed_indicator='1' then ':RANDOMIZATION' else '' end || case when comp_changed_indicator='1' then ':COMPANION' else '' end as amend_reasons from study_amendments) where study_amendments.id=study_parts_id)");
			
			execute("update study_amendments set amendment_reasons_text = trim(leading ':' from amendment_reasons_text)")
	 			
	 		execute("alter table study_amendments add version_status varchar2(10)");	
	 		
	 		
	 		
	 		// set status of all study versions to active
	 		execute("update study_amendments set version_status = 'AC'");
	 
	 		// get the last study versions of all studies and if the study is in either 'PENDING' or 'AMENDMENT_PENDING' status, set the latest study version in 'INACTIVE' status.
	      	execute("update study_amendments set version_status = 'IN' where exists( select amendment_version from study_amendments,studies where study_amendments.id in (select max(study_amendment_id) from (select study_amendments.id as study_amendment_id, studies.id as studies_id from studies, study_amendments where study_amendments.stu_id = studies.id ) group by studies_id)and studies.status like '%PENDING%') and id in (select study_amendments.id from study_amendments, study_versions where study_amendments.id in (select max(study_amendment_id) from (select study_amendments.id as study_amendment_id, studies.id as studies_id from studies, study_amendments where study_amendments.stu_id = studies.id) group by studies_id) and study_versions.study_id=study_amendments.stu_id and study_amendments.amendment_version is not null)");
					
			execute("alter table study_amendments add data_entry_status varchar2(50)");
			  
			execute("update study_amendments set data_entry_status = 'COMPLETE'");
			  
			execute("update study_amendments set data_entry_status = 'INCOMPLETE' where exists( select amendment_version from study_amendments,studies where study_amendments.id in (select max(study_amendment_id) from (select study_amendments.id as study_amendment_id, studies.id as studies_id from studies, study_amendments where study_amendments.stu_id = studies.id ) group by studies_id)and studies.data_entry_status like 'INCOMPLETE')and id in (select study_amendments.id from study_amendments, study_versions where study_amendments.id in (select max(study_amendment_id) from (select study_amendments.id as study_amendment_id, studies.id as studies_id from studies, study_amendments where study_amendments.stu_id = studies.id) group by studies_id) and study_versions.study_id=study_amendments.stu_id and study_amendments.amendment_version is not null) ");
					
				
					// copy study_amendments into study_versions
	
			execute("insert into study_versions (id,version,retired_indicator,study_id,version_date,name,original_indicator,amendment_type,amendment_reason,comments,version_status,data_entry_status) (select study_versions_id_seq.nextval,version,retired_indicator,stu_id,amendment_date,amendment_version,0,'IMMEDIATE',amendment_reasons_text,comments,version_status,data_entry_status from study_amendments where study_amendments.amendment_date is not null and study_amendments.amendment_date not like '')");
	 											
	 		execute("alter table study_amendments drop column version_status");
	 			
	 		execute("alter table study_amendments drop column amendment_reasons_text");
	 		
	 		execute("alter table study_amendments drop column data_entry_status");
	 		
	 		execute("update study_versions set (short_title_text,long_title_text,precis_text,randomization_type,description_text) = (select sv.short_title_text,sv.long_title_text,sv.precis_text,sv.randomization_type,sv.description_text from study_versions sv WHERE sv.study_id=study_versions.study_id and sv.short_title_text is not null and sv.short_title_text not like '') where original_indicator != 1");
	 
	        execute("DROP SEQUENCE consents_id_seq");
          	execute("CREATE SEQUENCE consents_id_seq start with 15000 increment by 1 NOMAXVALUE minvalue 1 nocycle nocache noorder");
	        
	       	execute("insert into consents(id,version,retired_indicator,name,stu_version_id) (select consents_id_seq.nextval,0,'false', study_amendments.consent_version,study_versions.id from study_versions, study_amendments where study_versions.study_id = study_amendments.stu_id and study_versions.version_date = study_amendments.amendment_date and study_amendments.consent_version is not null and study_amendments.consent_version not like '')");
	 				
			
			
	 		// migrate epochs to latest study version based on version_date
	 		
	 		execute("create table max_study_version (version_date date, study_id int )");
	 		execute("insert into max_study_version  (select max(version_date),study_id from study_versions group by study_id)");
	 		execute("alter table study_versions add latest_study_ver_id integer");
			
			execute("create table max_study_study_version_temp (max_study_version_id int, study_id int )");
			execute("insert into max_study_study_version_temp (select sv.id as max_study_version_id,sv.study_id as study_id from study_versions sv,max_study_version where sv.study_id = max_study_version.study_id and sv.version_date = max_study_version.version_date)");
			execute("update study_versions set latest_study_ver_id =  (select tmp.max_study_version_id from max_study_study_version_temp tmp where study_versions.study_id = tmp.study_id)");
			 
			 	 		
	 		execute("update epochs set stu_version_id = (select latest_study_ver_id from study_versions where epochs.stu_version_id =study_versions.id)");
	 		execute("update study_diseases set stu_version_id = (select latest_study_ver_id from study_versions where study_diseases.stu_version_id =study_versions.id)");
	 		execute("update comp_stu_associations set parent_version_id = (select latest_study_ver_id from study_versions where comp_stu_associations.parent_version_id =study_versions.id)");
	 		
	 		execute("drop table max_study_study_version_temp");
	 		execute("drop table max_study_version");
	 		execute("alter table study_versions drop column latest_study_ver_id");
	 		
	 		// migrate study site to study site study status history

 			execute("insert into stu_site_status_history(id,retired_indicator,version,start_date,site_study_status,sto_id) (SELECT stu_site_status_history_id_seq.NEXTVAL ,retired_indicator,0,current_date - interval '100' year(3),'PENDING',id from study_organizations where type='SST')");
 			execute("update stu_site_status_history set end_date=(select end_date from (select id as sites_status_id, start_date - interval '1' day as end_date from study_organizations where study_organizations.type = 'SST' and study_organizations.site_study_status not like'PENDING') where stu_site_status_history.sto_id=sites_status_id)");
 			execute("insert into stu_site_status_history(id,retired_indicator,version,start_date,site_study_status,sto_id) (select stu_site_status_history_id_seq.NEXTVAL,retired_indicator,0,start_date,'ACTIVE',id from study_organizations where type='SST'and site_study_status not like'PENDING')");
 			execute("update stu_site_status_history set end_date=(select end_date from (select id as sites_status_id, CURRENT_DATE - interval '1' day as end_date from study_organizations where study_organizations.type = 'SST' and study_organizations.site_study_status not like'PENDING' and site_study_status not like 'ACTIVE' and site_study_status not like 'AMENDMENT_PENDING') where stu_site_status_history.sto_id=sites_status_id) where stu_site_status_history.site_study_status like 'ACTIVE'");
 			execute("insert into stu_site_status_history(id,retired_indicator,version,start_date,site_study_status,sto_id) (select stu_site_status_history_id_seq.NEXTVAL,retired_indicator,0,CURRENT_DATE,'ACTIVE',id from study_organizations where type='SST'and site_study_status not like'PENDING' and site_study_status not like 'ACTIVE' and site_study_status not like 'AMENDMENT_PENDING')");
			
			
			// migrate study site to study site study version
			execute("insert into study_site_versions (id, retired_indicator, version, start_date, irb_approval_date, stu_version_id, sto_id) select id , retired_indicator, 0 , start_date, irb_approval_date, study_id, id from study_organizations where type='SST'")
		
		}
		
		execute("ALTER TABLE epochs ADD CONSTRAINT FK_EPH_STU_VER FOREIGN KEY (stu_version_id) REFERENCES study_versions (ID)");
		
    }

	void down() {
    }
}