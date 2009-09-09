class MigrateToStudyVersionFropColumns extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
      	dropColumn('studies', 'short_title_text');
   		dropColumn('studies', 'long_title_text');
   		dropColumn('studies', 'description_text');
   		dropColumn('studies', 'precis_text');
   		dropColumn('studies', 'randomization_type');
   		dropColumn('studies', 'data_entry_status');
   		dropColumn('studies', 'consent_version');

      	addColumn('comp_stu_associations', 'parent_version_id', 'integer');
      	execute("update comp_stu_associations set parent_version_id=parent_study_id");
      	dropColumn('comp_stu_associations', 'parent_study_id');

      	dropColumn('study_organizations','irb_approval_date');
      	if (databaseMatches('oracle')) {
		   	execute('rename STUDY_AMENDMENTS_ID_SEQ to SEQ_STUDY_AMENDMENTS_ID');
	 	}
      	dropTable('study_amendments');
      	
      	dropColumn('study_organizations','start_date');
      	dropColumn('study_organizations','end_date');
      	dropColumn('study_organizations','site_study_status');
    }

	void down() {
		addColumn('studies', 'short_title_text');
   		addColumn('studies', 'long_title_text');
   		addColumn('studies', 'description_text');
   		addColumn('studies', 'precis_text');
   		addColumn('studies', 'randomization_type');
   		addColumn('studies', 'data_entry_status');
   		addColumn('studies', 'consent_version');

		addColumn('comp_stu_associations', 'parent_study_id');
		execute("update comp_stu_associations set parent_study_id=parent_version_id");
      	dropColumn('comp_stu_associations', 'parent_version_id', 'integer');
    }
}