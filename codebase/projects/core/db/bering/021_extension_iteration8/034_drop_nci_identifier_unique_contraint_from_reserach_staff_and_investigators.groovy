class DropNCIIdentifierUniqueConstraintFromResearchStaffAndInvestigators extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	execute("alter table research_staff drop constraint uk_rsf");
    	execute("alter table investigators drop constraint uk_inv");
	}

	void down() {
    }
}
