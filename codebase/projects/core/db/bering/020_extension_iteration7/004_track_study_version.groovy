class TrackStudyVersion extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
        createTable('consents') { t ->
        	t.addColumn('retired_indicator', 'string', nullable:true)
       		t.addColumn('grid_id' , 'string' , nullable:true)
       		t.addVersionColumn()
            t.addColumn('name', 'string', nullable:false)
            t.addColumn('stu_version_id', 'integer', nullable:false)
        }

        createTable('study_versions') { t ->
        	t.addColumn('retired_indicator', 'string', nullable:true)
       		t.addColumn('grid_id' , 'string' , nullable:true)
       		t.addVersionColumn()
            t.addColumn('short_title_text', 'string', limit: 1024)
            t.addColumn('long_title_text', 'string', limit: 2000)
            t.addColumn('description_text', 'string', limit: 2000)
            t.addColumn('precis_text', 'string', limit: 200)
            t.addColumn('randomization_type', 'integer')
            t.addColumn('data_entry_status', 'string')
            t.addColumn('version_date', 'date')
            t.addColumn('version_status', 'string')
            t.addColumn('study_id', 'integer',nullable:false)
            t.addColumn('comments', 'string')
            t.addColumn('amendment_type', 'string',nullable:false)
            t.addColumn('grace_period', 'integer')
            t.addColumn('amendment_reason', 'string')
            t.addColumn('name', 'string',nullable:false)
            t.addColumn('original_indicator', 'boolean',nullable:false)
        }

        createTable('study_site_versions') { t ->
        	t.addColumn('retired_indicator', 'string', nullable:true)
       		t.addColumn('grid_id' , 'string' , nullable:true)
       		t.addVersionColumn()
            t.addColumn('start_date', 'date')
            t.addColumn('irb_approval_date', 'date')
            t.addColumn('end_date', 'date')
            t.addColumn('stu_version_id', 'integer')
            t.addColumn('sto_id', 'integer')
        }
		
		createTable('stu_site_status_history') { t ->
        	t.addColumn('retired_indicator', 'string', nullable:true)
       		t.addColumn('grid_id' , 'string' , nullable:true)
       		t.addVersionColumn()
            t.addColumn('start_date', 'date')
            t.addColumn('end_date', 'date')
            t.addColumn('site_study_status', 'string')
            t.addColumn('sto_id', 'integer')
        }
		
		execute("ALTER TABLE stu_site_status_history ADD CONSTRAINT FK_STU_SITE_STATUS_HIST FOREIGN KEY (sto_id) REFERENCES study_organizations (ID)");

	    if (databaseMatches('oracle')) {
		   	execute('rename SEQ_CONSENTS_ID to CONSENTS_ID_SEQ');
		   	execute('rename SEQ_STUDY_VERSIONS_ID to STUDY_VERSIONS_ID_SEQ');
		   	execute('rename SEQ_STUDY_SITE_VERSIONS_ID to STUDY_SITE_VERSIONS_ID_SEQ');
		   	execute('rename SEQ_STU_SITE_STATUS_HISTORY_ID to STU_SITE_STATUS_HISTORY_ID_SEQ');
	 	}
    }

	void down() {
	    
    }
}