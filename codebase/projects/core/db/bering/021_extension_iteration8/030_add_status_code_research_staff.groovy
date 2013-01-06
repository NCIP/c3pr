class AddStatusCodeResearchStaff extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
			addColumn('research_staff','status_code','string');
	}

	void down(){
			dropColumn('research_staff','status_code');
	}
}