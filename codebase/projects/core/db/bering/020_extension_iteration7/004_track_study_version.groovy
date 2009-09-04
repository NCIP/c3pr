class TrackStudyVersion extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
        createTable('consents') { t ->
        	t.addColumn('retired_indicator', 'string', nullable:true)
       		t.addColumn('grid_id' , 'string' , nullable:true)
       		t.addVersionColumn()
            t.addColumn('name', 'string', nullable:false)
            t.addColumn('effective_date' , 'date' , nullable:false)
            t.addColumn('stu_version_id', 'integer', nullable:false)
        }

        createTable('study_versions') { t ->
        	t.addColumn('retired_indicator', 'string', nullable:true)
       		t.addColumn('grid_id' , 'string' , nullable:true)
       		t.addVersionColumn()
            t.addColumn('short_title_text', 'string', limit: 200)
            t.addColumn('long_title_text', 'string', limit: 1000)
            t.addColumn('description_text', 'string', limit: 2000)
            t.addColumn('precis_text', 'string', limit: 200)
            t.addColumn('randomization_type', 'integer')
            t.addColumn('data_entry_status', 'string')
            t.addColumn('version_date', 'date')
            t.addColumn('version_status', 'string')
            t.addColumn('study_id', 'integer')
            t.addColumn('comments', 'string')
            t.addColumn('amendment_type', 'string',nullable:false)
            t.addColumn('grace_period', 'integer')
            t.addColumn('amendment_reason', 'string')
            t.addColumn('name', 'string')
        }

        createTable('study_site_versions') { t ->
        	t.addColumn('retired_indicator', 'string', nullable:true)
       		t.addColumn('grid_id' , 'string' , nullable:true)
       		t.addVersionColumn()
            t.addColumn('start_date', 'date')
            t.addColumn('irb_approval_date', 'date')
            t.addColumn('end_date', 'date')
            t.addColumn('target_accrual', 'integer')
            t.addColumn('stu_version_id', 'integer')
            t.addColumn('sto_id', 'integer')
        }

		addColumn('studies','consent_required', 'string');
        addColumn('studies','consent_validity_period', 'integer');

        addColumn('epochs','stu_version_id', 'integer');
        addColumn('study_diseases','stu_version_id', 'integer');

        execute("update studies set consent_required='ALL'");
		execute("update studies set consent_validity_period=90");
		execute("update epochs set stu_version_id=stu_id");
		execute("update study_diseases set stu_version_id=study_id");

		execute("alter table epochs drop constraint FK_EPH_STU");
		execute("ALTER TABLE epochs drop CONSTRAINT UK_STU_EPH");
		dropColumn('epochs', 'stu_id');
		dropColumn('study_organizations', 'end_date');
		dropColumn('study_organizations', 'role_code');
		execute("ALTER TABLE epochs ADD CONSTRAINT UK_STU_VER_EPH UNIQUE(name,stu_version_id)");
		execute("ALTER TABLE epochs ADD CONSTRAINT FK_EPH_STU_VER FOREIGN KEY (stu_version_id) REFERENCES study_versions (ID)");

		execute("alter table study_diseases drop constraint UK_STU_DTM");
		dropColumn('study_diseases', 'study_id');
		execute("ALTER TABLE study_diseases ADD CONSTRAINT UK_STU_VER_DTM UNIQUE(stu_version_id,DISEASE_TERM_ID)");

	    if (databaseMatches('oracle')) {
		   	execute('rename SEQ_CONSENTS_ID to CONSENTS_ID_SEQ');
		 	execute('rename SEQ_CONSENT_VERSIONS_ID to CONSENT_VERSIONS_ID_SEQ');
		   	execute('rename SEQ_STUDY_VERSIONS_ID to STUDY_VERSIONS_ID_SEQ');
		   	execute('rename SEQ_STUDY_SITE_VERSIONS_ID to STUDY_SITE_VERSIONS_ID_SEQ');
	 	}

	 	addColumn('study_organizations','status_change_dates','string')
    }

	void down() {
	    dropTable('consent_versions')
	    dropTable('consents')
	    dropTable('study_versions')
	    dropTable('study_site_versions')

	    dropColumn('studies','consent_required');
        dropColumn('studies','consent_validity_period');
        dropColumn('epochs','stu_version_id');
        dropColumn('study_diseases','stu_version_id');
    }
}