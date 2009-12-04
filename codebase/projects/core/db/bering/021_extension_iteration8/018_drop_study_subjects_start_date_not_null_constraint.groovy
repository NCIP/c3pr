class DropStudySubjectsStartDateNotNullConstraint extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	if (databaseMatches('postgres')){
    		execute("alter table study_subjects alter column start_date drop not null");
    	}
    	if (databaseMatches('oracle')){
    		execute("alter table study_subjects modify (start_date null)");
    	}
    }

	void down() {
		if (databaseMatches('postgres')){
    		execute("alter table study_subjects alter column start_date SET NOT NULL");
    	}
    	if (databaseMatches('oracle')){
    		execute("alter table study_subjects modify (start_date not null)");
    	}
	}
}