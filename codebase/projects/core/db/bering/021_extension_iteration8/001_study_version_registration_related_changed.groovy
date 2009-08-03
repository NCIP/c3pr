class StudyVersionRegistrationRelatedChanges extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
        createTable('stu_sub_stu_versions') { t ->
        	t.addColumn('retired_indicator', 'string', nullable:true)
       		t.addColumn('grid_id' , 'string' , nullable:true)
       		t.addVersionColumn()
            t.addColumn('stu_sub_id', 'date')
            t.addColumn('stu_site_stu_ver_id', 'date')
        }

		addColumn('scheduled_epochs','stu_sub_stu_ver_id','integer');
		addColumn('stu_site_stu_versions','stu_site_id', 'integer');

	    if (databaseMatches('oracle')) {
		   	execute('rename SEQ_SUB_STU_VERSION_ID to STU_SUB_STU_VERSION_ID_SEQ');
	 	}
    }

	void down() {
	    dropTable('stu_sub_stu_versions');
		dropColumn('scheduled_epochs','stu_sub_stu_ver_id');
		dropColumn('stu_site_stu_versions','stu_site_id');
	}
}