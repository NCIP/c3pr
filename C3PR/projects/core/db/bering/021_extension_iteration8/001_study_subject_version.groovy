class StudySubjectVersionChanges extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
        createTable('study_subject_versions') { t ->
        	t.addColumn('retired_indicator', 'string', nullable:true)
       		t.addColumn('grid_id' , 'string' , nullable:true)
       		t.addVersionColumn()
            t.addColumn('spa_id', 'integer',nullable:false)
            t.addColumn('study_site_ver_id', 'integer',nullable:false)
        }
        
        createTable('study_subject_consents') { t ->
        	t.addColumn('retired_indicator', 'string', nullable:true)
       		t.addColumn('grid_id' , 'string' , nullable:true)
       		t.addVersionColumn()
            t.addColumn('informed_consent_signed_date', 'date')
            t.addColumn('study_subject_ver_id', 'integer',nullable:false)
            t.addColumn('consent_id', 'integer',nullable:false)
        }
		
	    if (databaseMatches('oracle')) {
		   	execute('rename SEQ_study_subject_versions_ID to study_subject_versions_ID_SEQ');
		   	execute('rename SEQ_study_subject_consents_ID to study_subject_consents_ID_SEQ');
	 	}
	 	
	 	dropTable('consent_history')
    }

	void down() {
		dropTable('stu_sub_stu_versions');
	    dropTable('stu_sub_cosnt_vers')
	}
}