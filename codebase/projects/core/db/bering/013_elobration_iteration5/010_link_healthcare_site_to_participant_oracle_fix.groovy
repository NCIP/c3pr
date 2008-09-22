class LinkHealthcareSiteToParticipantOracleFix extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
	
		execute(" ALTER TABLE participants drop CONSTRAINT FK_PRT_PRT_ORG_ASSOC");
    	execute(" ALTER TABLE organizations drop CONSTRAINT FK_ORG_PRT_ORG_ASSOC");
    	
		dropColumn("participants", "prt_prt_org_assoc_id");
    	dropColumn("organizations", "org_prt_org_assoc_id");
    	
		if (databaseMatches('oracle')) {
	   		execute("rename SEQ_prt_org_associations_ID to prt_org_associations_ID_SEQ");
 	    }
 	    
 	    renameColumn('prt_org_associations', 'prt_stu_sub_id', 'prt_id');
 	    renameColumn('prt_org_associations', 'org_stu_sub_id', 'org_id');
 	    
    	
    	execute("ALTER TABLE prt_org_associations ADD CONSTRAINT FK_PRT_PRT_ORG_ASSOC FOREIGN KEY (prt_id) REFERENCES participants (ID)");
        execute("ALTER TABLE prt_org_associations ADD CONSTRAINT FK_ORG_PRT_ORG_ASSOC FOREIGN KEY (org_id) REFERENCES organizations (ID)");
	}

	void down(){
		
	}
}
