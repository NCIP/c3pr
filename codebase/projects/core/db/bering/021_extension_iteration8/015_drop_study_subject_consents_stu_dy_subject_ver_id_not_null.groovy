class DropStudySubjectConsentsStudySubjectVerIdNotNull extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	if (databaseMatches('postgres')){
    		execute("alter table study_subject_consents alter column study_subject_ver_id drop not null");
    	}
    	if (databaseMatches('oracle')){
    		execute("alter table study_subject_consents modify (study_subject_ver_id null)");
    	}
    } 

	void down() {
	}
}