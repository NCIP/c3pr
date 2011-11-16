class CreateTableCorrespondences extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    
    	createTable("correspondences") { t ->
		 	t.addVersionColumn()
		 	t.addColumn("grid_id", "string")
            t.addColumn("text", "string")
		 	t.addColumn("time", "date", nullable: false )
            t.addColumn("notes", "string")
            t.addColumn("action", "string")
            t.addColumn("purpose", "string")
            t.addColumn("type", "string", nullable: false)
            t.addColumn("stu_sub_id", "integer", nullable: false)
            t.addColumn("retired_indicator", "string", defaultValue: "false")
         }
    	
		if (databaseMatches('oracle	')){
			execute("RENAME SEQ_correspondences_id to correspondences_id_SEQ");
		}
		
		execute('ALTER TABLE correspondences ADD CONSTRAINT FK_COR_STU_SUB FOREIGN KEY (stu_sub_id) REFERENCES study_subjects(ID)');
		
	}
	void down() {
    }
}
