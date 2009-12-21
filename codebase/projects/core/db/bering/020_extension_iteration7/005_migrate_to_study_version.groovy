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
    	execute('insert into "consents"("id", "retired_indicator", "version", "name", "stu_version_id") select "id", "retired_indicator", 0, "consent_version", "id" from studies') ;
    			
		//migrate study to study version
		
		execute("insert into study_versions (id,version,retired_indicator,version_status,study_id,name,short_title_text,long_title_text,description_text,precis_text,randomization_type,data_entry_status,amendment_type,original_indicator)(select id,version,retired_indicator,'AC',id,'Original version',short_title_text,long_title_text,description_text,precis_text,randomization_type,data_entry_status,'IMMEDIATE','1' from studies)");

 		execute("update study_versions set version_status='IN' where id in (select studies.id from study_versions,studies where study_versions.study_id=studies.id and studies.status='PENDING')");

 		execute("update study_versions set original_indicator ='0' where id in  ( select distinct studies.id from studies, study_amendments where study_amendments.stu_id = studies.id)");

 		execute("update study_versions set comments = (select comments from study_amendments where id in (select max(study_amendment_id) from (select study_amendments.id as study_amendment_id, studies.id as studies_id from studies, study_amendments where study_amendments.stu_id = studies.id) as study_study_amendments group by studies_id) and study_versions.study_id=study_amendments.stu_id)");

 		execute("update study_versions set name ='Amended Version' where id in  ( select distinct studies.id from studies, study_amendments where study_amendments.stu_id = studies.id)")

 		execute("update study_versions set name = (select amendment_version from study_amendments where id in (select max(study_amendment_id) from (select study_amendments.id as study_amendment_id, studies.id as studies_id from studies, study_amendments where study_amendments.stu_id = studies.id) as study_study_amendments group by studies_id) and study_versions.study_id=study_amendments.stu_id and study_amendments.amendment_version is not null) where exists( select amendment_version from study_amendments where id in (select max(study_amendment_id) from (select study_amendments.id as study_amendment_id, studies.id as studies_id from studies, study_amendments where study_amendments.stu_id = studies.id) as study_study_amendments group by studies_id) and study_versions.study_id=study_amendments.stu_id and study_amendments.amendment_version is not null)");

 		execute("update study_versions set version_date = (select amendment_date from study_amendments where id in (select max(study_amendment_id) from (select study_amendments.id as study_amendment_id, studies.id as studies_id from studies, study_amendments where study_amendments.stu_id = studies.id) as study_study_amendments group by studies_id) and study_versions.study_id=study_amendments.stu_id and study_amendments.amendment_date is not null) where exists( select amendment_version from study_amendments where id in (select max(study_amendment_id) from (select study_amendments.id as study_amendment_id, studies.id as studies_id from studies, study_amendments where study_amendments.stu_id = studies.id) as study_study_amendments group by studies_id) and study_versions.study_id=study_amendments.stu_id and study_amendments.amendment_date is not null)");

 		execute("update study_versions set version_date = ((select min(irb_approval_date) - interval '1 year 1 day' from (select studies.id as study_id, study_organizations.id as site_id,study_organizations.irb_approval_date from studies,study_organizations where study_organizations.study_id = studies.id and study_organizations.site_study_status not like 'PENDING' and study_versions.study_id = studies.id) as study_study_organizations group by study_id) )");

 		execute("update study_versions set version_date = current_date - interval '1 year 1 day' where version_date is null");

 		execute("update study_versions set version_date = current_date - interval '100 years' where study_versions.id in (select study_versions.id from studies,study_versions where study_versions.study_id=studies.id and studies.status like'PENDING')");

 		execute("alter table study_amendments add column amendment_reasons_text text")

 		execute("update study_amendments set amendment_reasons_text=(select amend_reasons from (select id as study_parts_id, case when eligibility_changed_indicator='1' then 'ELIGIBILITY' else '' end || case when consent_changed_indicator='1' then ':CONSENT' else '' end || case when ea_changed_indicator='1' then 'DESIGN' else '' end || case when strat_changed_indicator='1' then ':STRATIFICATION' else '' end || case when diseases_changed_indicator='1' then 'DISEASE' else '' end || case when pi_changed_indicator='1' then ':DETAIL' else '' end || case when rndm_changed_indicator='1' then 'RANDOMIZATION' else '' end || case when comp_changed_indicator='1' then ':COMPANION' else '' end as amend_reasons from study_amendments) as study_parts where study_amendments.id=study_parts.study_parts_id)");

 		execute("update study_amendments set amendment_reasons_text = trim(leading ':' from amendment_reasons_text)")

 		execute("update study_versions set amendment_reason = (select amendment_reasons_text from study_amendments where id in (select max(study_amendment_id) from (select study_amendments.id as study_amendment_id, studies.id as studies_id from studies, study_amendments where study_amendments.stu_id = studies.id) as study_study_amendments group by studies_id) and study_versions.study_id=study_amendments.stu_id and study_amendments.amendment_date is not null) where exists( select amendment_version from study_amendments where id in (select max(study_amendment_id) from (select study_amendments.id as study_amendment_id, studies.id as studies_id from studies, study_amendments where study_amendments.stu_id = studies.id) as study_study_amendments group by studies_id) and study_versions.study_id=study_amendments.stu_id and study_amendments.amendment_date is not null)");

 		execute("alter table study_amendments drop amendment_reasons_text");
 		
 		// migrate study site to study site study status history

 		execute("insert into stu_site_status_history(id,retired_indicator,version,start_date,site_study_status,sto_id) (select nextVal('stu_site_status_history_id_seq'),retired_indicator,0,current_date - interval '100 years','PENDING',id from study_organizations where type='SST')");

 		execute("update stu_site_status_history set end_date=(select end_date from (select id as sites_status_id, start_date - interval '1 day' as end_date from study_organizations where study_organizations.type = 'SST' and study_organizations.site_study_status not like'PENDING') as sites_status where stu_site_status_history.sto_id=sites_status.sites_status_id)");

 		execute("insert into stu_site_status_history(id,retired_indicator,version,start_date,site_study_status,sto_id) (select nextVal('stu_site_status_history_id_seq'),retired_indicator,0,start_date,'ACTIVE',id from study_organizations where type='SST'and site_study_status not like'PENDING')");

 		execute("update stu_site_status_history set end_date=(select end_date from (select id as sites_status_id, CURRENT_DATE - interval '1 day' as end_date from study_organizations where study_organizations.type = 'SST' and study_organizations.site_study_status not like'PENDING' and site_study_status not like 'ACTIVE' and site_study_status not like 'AMENDMENT_PENDING') as sites_status where stu_site_status_history.sto_id=sites_status.sites_status_id) where stu_site_status_history.site_study_status like 'ACTIVE'");

 		execute("insert into stu_site_status_history(id,retired_indicator,version,start_date,site_study_status,sto_id) (select nextVal('stu_site_status_history_id_seq'),retired_indicator,0,CURRENT_DATE,'ACTIVE',id from study_organizations where type='SST'and site_study_status not like'PENDING' and site_study_status not like 'ACTIVE' and site_study_status not like 'AMENDMENT_PENDING')");
		
			
		// migrate study site to study site study version
		execute("insert into study_site_versions (id, retired_indicator, version, start_date, irb_approval_date, stu_version_id, sto_id) select id , retired_indicator, 0 , start_date, irb_approval_date, study_id, id from study_organizations where type='SST'")
		
		}
		
		execute("ALTER TABLE epochs ADD CONSTRAINT FK_EPH_STU_VER FOREIGN KEY (stu_version_id) REFERENCES study_versions (ID)");
		
    }

	void down() {
    }
}