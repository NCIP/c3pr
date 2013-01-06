class UpdateInvestigatorStatusCode extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
	    execute("update study_investigators set status_code='AC' where status_code='Active' or status_code='ACTIVE'");
	    execute("update study_investigators set status_code='IN' where status_code='Inactive' or status_code='INACTIVE'");
    }

    void down() {
    	execute("update study_investigators set status_code='Active' where status_code='AC'");
    	execute("update study_investigators set status_code='Inactive' where status_code='IN'");
    }
}
