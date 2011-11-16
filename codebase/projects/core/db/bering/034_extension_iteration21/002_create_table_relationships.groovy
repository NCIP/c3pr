class CreateTableRelationships extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    
    	createTable("relationships") { t ->
		 	t.addVersionColumn()
		 	t.addColumn("grid_id", "string")
            t.addColumn("category", "string",  nullable: false)
		 	t.addColumn("name", "string",  nullable: false )
		 	t.addColumn("other_name", "string")
            t.addColumn("pri_prt_id", "integer", nullable: false)
            t.addColumn("sec_prt_id", "integer", nullable: false)
            t.addColumn("retired_indicator", "string", defaultValue: "false")
         }
    	
		if (databaseMatches('oracle	')){
			execute("RENAME SEQ_relationships_id to relationships_id_SEQ");
		}
		
		execute('ALTER TABLE relationships ADD CONSTRAINT FK_REL_CHILD FOREIGN KEY (pri_prt_id) REFERENCES participants(ID)');
		execute('ALTER TABLE relationships ADD CONSTRAINT FK_REL_RELATIVE FOREIGN KEY (sec_prt_id) REFERENCES participants(ID)');
		
		
		
		
	}
	void down() {
    }
}
