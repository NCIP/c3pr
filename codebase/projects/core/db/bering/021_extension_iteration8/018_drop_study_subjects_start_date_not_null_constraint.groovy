class DropStudySubjectsStartDateNotNullConstraint extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	execute("alter table study_subjects alter column start_date drop not null");
    }

	void down() {
		execute("alter table study_subjects alter column start_date SET NOT NULL");
	}
}