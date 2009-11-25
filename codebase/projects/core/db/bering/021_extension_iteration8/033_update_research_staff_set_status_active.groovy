class UpdateResearchStaffSetStatusActive extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	execute("update research_staff set status_code='AC'");
	}

	void down() {
		execute("update research_staff set status_code=NULL");
    }
}
