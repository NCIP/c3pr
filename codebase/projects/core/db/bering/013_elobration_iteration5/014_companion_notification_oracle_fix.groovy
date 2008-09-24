class CompanionNotificationOracleFix extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
		
		ALTER TABLE prt_org_associations DROP column id;
		
		if (databaseMatches('oracle')) {
	   		execute("rename SEQ_COMP_STU_ASSOCIATIONS_ID to COMP_STU_ASSOCIATIONS_ID_SEQ");
	   		execute("rename SEQ_STU_SUB_ASSOCIATIONS_ID to STU_SUB_ASSOCIATIONS_ID_SEQ");
	   		execute("rename SEQ_SCHLD_NOTFNS_ID to SCHLD_NOTFNS_ID_SEQ");
	   		execute("rename SEQ_RPT_SCHLD_NOTFNS_ID to RPT_SCHLD_NOTFNS_ID_SEQ");
 	    }
	}

	void down(){
		
	}
}
