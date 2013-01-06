class CreateRaceCodeAssociationsTable extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	// Creating association table
		createTable("contact_use_assocns") { t ->
		 	t.addVersionColumn()
            t.addColumn("grid_id", "string")
		 	t.addColumn("cntct_id", "integer" )
            t.addColumn("use", "string")
            t.addColumn("retired_indicator", "string", defaultValue: "false")
         }
		
		if (databaseMatches('oracle')) {
	   		execute("RENAME SEQ_contact_use_assocns_id to contact_use_assocns_id_SEQ");
	 	}
	 	
	 	createTable("address_use_assocns") { t ->
		 	t.addVersionColumn()
            t.addColumn("grid_id", "string")
		 	t.addColumn("add_id", "integer" )
            t.addColumn("use", "string")
            t.addColumn("retired_indicator", "string", defaultValue: "false")
         }
		
		if (databaseMatches('oracle')) {
	   		execute("RENAME SEQ_address_use_assocns_id to address_use_assocns_id_SEQ");
	 	}
	 		 	
	 	execute('ALTER TABLE contact_use_assocns ADD CONSTRAINT FK_CNTCTUSE_CNTCT FOREIGN KEY (cntct_id) REFERENCES contact_mechanisms(ID)');
		execute('ALTER TABLE address_use_assocns ADD CONSTRAINT FK_ADDUSE_ADDRESS FOREIGN KEY (add_id) REFERENCES addresses(ID)');
		
	}
	void down() {
		dropTable("race_code_assocn")
    }
}
