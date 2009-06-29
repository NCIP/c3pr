class TrackStudyVersion extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
        createTable('consents') { t ->
        	t.addColumn('retired_indicator', 'string', nullable:true)
       		t.addColumn('grid_id' , 'string' , nullable:true)
       		t.addVersionColumn()
            t.addColumn('name', 'string', nullable:false)
            t.addColumn('stu_version_id', 'integer', nullable:false)
        }

        createTable('consent_versions') { t ->
            t.addColumn('retired_indicator', 'string', nullable:true)
       		t.addColumn('grid_id' , 'string' , nullable:true)
       		t.addVersionColumn()
            t.addColumn('name' , 'string' , nullable:false)
            t.addColumn('effective_date' , 'date' , nullable:false)
            t.addColumn('consent_id', 'integer',nullable:false)
        }

        createTable('stu_sub_cosnt_vers') { t ->
        	t.addColumn('retired_indicator', 'string', nullable:true)
       		t.addColumn('grid_id' , 'string' , nullable:true)
       		t.addVersionColumn()
            t.addColumn('informed_consent_signed_date', 'date')
            t.addColumn('stu_sub_id', 'integer',nullable:false)
            t.addColumn('consent_version_id', 'integer',nullable:false)
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
            t.addColumn('target_accrual_number', 'integer')
            t.addColumn('data_entry_status', 'string')
            t.addColumn('version_date', 'date')
            t.addColumn('version_status', 'boolean')
            t.addColumn('study_id', 'integer')
            t.addColumn('comments', 'string')
        }
        
        createTable('amendment_reasons') { t ->
        	t.addColumn('retired_indicator', 'string', nullable:true)
       		t.addColumn('grid_id' , 'string' , nullable:true)
       		t.addVersionColumn()
            t.addColumn('study_part', 'string')
            t.addColumn('stu_version_id', 'integer')
        }
        
        createTable('stu_site_stu_versions') { t ->
        	t.addColumn('retired_indicator', 'string', nullable:true)
       		t.addColumn('grid_id' , 'string' , nullable:true)
       		t.addVersionColumn()
            t.addColumn('end_date', 'date')
            t.addColumn('start_date', 'date')
            t.addColumn('irb_approval_date', 'date')
            t.addColumn('target_accrual', 'integer')
            t.addColumn('stu_version_id', 'integer')
        }
        
		addColumn('studies','consent_required', 'string');
        addColumn('studies','consent_validity_period', 'string');
        addColumn('epochs','stu_version_id', 'integer');
        addColumn('study_diseases','stu_version_id', 'integer');

	    if (databaseMatches('oracle')) {
		   	execute('rename SEQ_CONSENTS_ID to CONSENTS_ID_SEQ');
		 	execute('rename SEQ_CONSENT_VERSIONS_ID to CONSENT_VERSIONS_ID_SEQ');
		  	execute('rename SEQ_STU_SUB_COSNT_VERS_ID to STU_SUB_COSNT_VERS_ID_SEQ');
		   	execute('rename SEQ_STUDY_VERSIONS_ID to STUDY_VERSIONS_ID_SEQ');
		   	execute('rename SEQ_AMENDMENT_REASON_ID to AMENDMENT_REASON_ID_SEQ');
		   	execute('rename SEQ_STU_SITE_STU_VERSION_ID to STU_SITE_STU_VERSION_ID_SEQ');
	 	}
    }

	void down() {
	    dropTable('stu_sub_cosnt_vers')
	    dropTable('consent_versions')
	    dropTable('consents')
	    dropTable('study_versions')
	    dropTable('amendment_reasons')
	    dropTable('stu_site_stu_versions')
	    
	    dropColumn('studies','consent_required');
        dropColumn('studies','consent_valid_period');
        dropColumn('epochs','stu_version_id');
        dropColumn('study_diseases','stu_version_id');
    }
} 