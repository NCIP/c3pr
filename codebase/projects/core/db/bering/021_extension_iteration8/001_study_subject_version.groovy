class StudySubjectVersionChanges extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
        createTable('stu_sub_stu_versions') { t ->
        	t.addColumn('retired_indicator', 'string', nullable:true)
       		t.addColumn('grid_id' , 'string' , nullable:true)
       		t.addVersionColumn()
            t.addColumn('spa_id', 'integer',nullable:false)
            t.addColumn('stu_site_ver_id', 'integer',nullable:false)
        }
        
        createTable('stu_sub_cosnt_vers') { t ->
        	t.addColumn('retired_indicator', 'string', nullable:true)
       		t.addColumn('grid_id' , 'string' , nullable:true)
       		t.addVersionColumn()
            t.addColumn('informed_consent_signed_date', 'date')
            t.addColumn('stu_sub_stu_ver_id', 'integer',nullable:false)
            t.addColumn('consent_version_id', 'integer',nullable:false)
        }
		
	    if (databaseMatches('oracle')) {
		   	execute('rename SEQ_SUB_STU_VERSION_ID to STU_SUB_STU_VERSION_ID_SEQ');
		   	execute('rename SEQ_STU_SUB_COSNT_VERS_ID to STU_SUB_COSNT_VERS_ID_SEQ');
	 	}
    }

	void down() {
		dropTable('stu_sub_stu_versions');
	    dropTable('stu_sub_cosnt_vers')
	}
}