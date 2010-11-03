class CreateStuPrsnlRoleTable extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	//Creating association table for study personnel and role
		createTable("stu_prsnl_role") { t ->
		 	t.addVersionColumn()
            t.addColumn("grid_id", "string")
            t.addColumn("stu_prsnl_id", "integer")
            t.addColumn("role", "string")
            t.addColumn("retired_indicator", "string", defaultValue: "false")
            
         }
		
		if (databaseMatches('oracle')) {
	   		execute("RENAME SEQ_stu_prsnl_role_id to stu_prsnl_role_id_seq");
	 	}
	 		 	
	 	execute('ALTER TABLE stu_prsnl_role ADD CONSTRAINT FK_STU_PRSNL_ROLE FOREIGN KEY (stu_prsnl_id) REFERENCES study_personnel(ID)');
		if (databaseMatches('postgres')) {
		 	execute("ALTER TABLE stu_prsnl_role ADD CONSTRAINT UK_STU_PRSNL_ROLE UNIQUE(role,stu_prsnl_id)");
	 	}
	}
	
	void down() {
		dropTable("stu_prsnl_role")
    }
}
