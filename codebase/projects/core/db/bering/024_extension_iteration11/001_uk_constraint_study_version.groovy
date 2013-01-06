class UniqueKeyConstraintForStudyVersionTablete extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
		execute("alter table study_versions add constraint uk_studyversion_name unique (name, study_id)");
		execute("alter table study_versions add constraint uk_studyversion_date unique (version_date, study_id)");
	}
	void down() {
    }
}
