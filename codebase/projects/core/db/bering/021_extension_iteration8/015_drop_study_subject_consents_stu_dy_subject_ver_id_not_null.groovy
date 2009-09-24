class DropStudySubjectConsentsStudySubjectVerIdNotNull extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	execute("alter table study_subject_consents alter column study_subject_ver_id drop not null")
    } 

	void down() {
	}
}