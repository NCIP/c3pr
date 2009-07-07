class MigrateToStudyVersionFropColumns1 extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
      	dropColumn('studies', 'consent_version');
      	addColumn('comp_stu_associations', 'parent_version_id');
      	execute("update comp_stu_associations set parent_version_id=parent_study_id");
      	dropColumn('comp_stu_associations', 'parent_study_id');
    }

	void down() {
	    
    }
} 