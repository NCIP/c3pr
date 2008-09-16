class RenameInvGroupsSiteAffiliationsSeqOracle extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
			
		if (databaseMatches('oracle')) {
	   		execute("rename SEQ_INVESTIGATOR_GROUPS_ID to INVESTIGATOR_GROUPS_ID_SEQ")
	   		execute("rename SEQ_ORG_INV_GR_AFFILIATIONS_ID to ORG_INV_GR_AFFILIATIONS_ID_SEQ")
 	    }
	}

	void down(){

	}
}