class LinkHealthcareSiteToParticipant extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
	
		addColumn("participants", "prt_prt_org_assoc_id", "integer", nullable : true)
    	addColumn("organizations", "org_prt_org_assoc_id", "integer", nullable : true)
    	
    	if(databaseMatches('sqlserver')){
			createTable("prt_org_associations") { t ->
				t.setIncludePrimaryKey(false)
	            t.addColumn("prt_stu_sub_id", "integer", nullable: false)
	            t.addColumn("org_stu_sub_id", "integer", nullable: false)
	            t.addColumn("version", "integer", defaultValue: 0, nullable: false)
				t.addColumn("grid_id", "string")
				t.addColumn("retired_indicator", 'string' );
	        }
        } else {
        		createTable("prt_org_associations") { t ->
	            t.addColumn("prt_stu_sub_id", "integer", nullable: false)
	            t.addColumn("org_stu_sub_id", "integer", nullable: false)
	            t.addColumn("version", "integer", defaultValue: 0, nullable: false)
				t.addColumn("grid_id", "string")
				t.addColumn("retired_indicator", 'string' );
	        }
        
        }
        
        createTable("ctc_version_categories") { t ->
		t.setIncludePrimaryKey(false)
		t.addColumn("version_id", "integer", nullable: false)
		t.addColumn("category_id", "integer", nullable: false)
	}
        
    	if(databaseMatches('sqlserver')){
    		// do Nothing
    	} else  {
	        execute("ALTER TABLE participants ADD CONSTRAINT FK_PRT_PRT_ORG_ASSOC FOREIGN KEY (prt_prt_org_assoc_id) REFERENCES prt_org_associations (ID)");
	        execute("ALTER TABLE organizations ADD CONSTRAINT FK_ORG_PRT_ORG_ASSOC FOREIGN KEY (org_prt_org_assoc_id) REFERENCES prt_org_associations (ID)");
        }
	}

	void down(){
		dropColumn("participants", "prt_prt_org_assoc_id");
    	dropColumn("organizations", "org_prt_org_assoc_id");
    	dropTable("prt_org_associations");
    	execute(" ALTER TABLE participants drop CONSTRAINT FK_PRT_PRT_ORG_ASSOC");
    	execute(" ALTER TABLE organizations drop CONSTRAINT FK_ORG_PRT_ORG_ASSOC");
	}
}
