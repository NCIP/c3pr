class RefactorInvestigatorsResearchStaffParticipants extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
      	addColumn("INVESTIGATORS", "Middle_Name", "string", limit: 200)
		addColumn("PARTICIPANTS", "Middle_Name", "string", limit: 200)
		addColumn("RESEARCH_STAFF", "Middle_Name", "string", limit: 200)
		addColumn("RESEARCH_STAFF", "NCI_IDENTIFIER", "string", limit: 200)
		addColumn("STUDIES", "RANDOMIZATION_TYPE", "integer")
    }

    void down() {
        dropColumn("STUDIES", "RANDOMIZATION_TYPE")
		dropColumn("RESEARCH_STAFF", "NCI_IDENTIFIER")
		dropColumn("RESEARCH_STAFF", "Middle_Name")
		dropColumn("PARTICIPANTS", "Middle_Name")
		dropColumn("INVESTIGATORS", "Middle_Name")
    }
}