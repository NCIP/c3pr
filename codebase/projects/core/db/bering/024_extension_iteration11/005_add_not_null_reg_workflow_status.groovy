class addNotNullConstraintToRegistrationWorkflowStatus extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	  if (databaseMatches('postgres')){
	    		execute("alter table study_subjects alter column reg_workflow_status set not null");
	    	}
	    	
	    	if (databaseMatches('oracle')){
	    		execute("alter table study_subjects modify reg_workflow_status not null");
	    	}
    }

	void down() {
	
	}
}