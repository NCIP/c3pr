class StudyVersionConstraints extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	if (databaseMatches('postgres')) {
			execute("alter table STUDY_VERSIONS alter column name set not null");
			execute("alter table STUDY_VERSIONS alter column version_date set not null");
			execute("alter table STUDY_VERSIONS alter column study_id set not null");
		}else if (databaseMatches('oracle')) {
			execute("alter table STUDY_VERSIONS modify(name not null)");
			execute("alter table STUDY_VERSIONS modify(version_date not null)");
			execute("alter table STUDY_VERSIONS modify(study_id not null) ;");
		}
    }

	void down() {
		dropColumn('scheduled_epochs','study_subject_ver_id');
	}
}