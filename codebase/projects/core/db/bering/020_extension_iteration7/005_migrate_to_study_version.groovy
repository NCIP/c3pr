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
		execute("ALTER TABLE epochs ADD CONSTRAINT FK_EPH_STU_VER FOREIGN KEY (stu_version_id) REFERENCES study_versions (ID)");
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
    
    	// migrating consent version to new table consents from studies.
    	execute('insert into "consents"("id", "retired_indicator", "version", "name", "stu_version_id") select "id", "retired_indicator", 0, "consent_version", "id" from studies') ;
    	
		//migrate study to study version
		
		// migrate study site to study site study version
		execute('insert into "study_site_versions" ("id", "retired_indicator", "version", "start_date", "irb_approval_date", "stu_version_id", "sto_id") select "id" , "retired_indicator", 0 , "start_date", "irb_approval_date", "study_id", "id" from study_organizations where type="SST"')
		
		// migrate study site to study site study status history
		
    }

	void down() {
    }
}