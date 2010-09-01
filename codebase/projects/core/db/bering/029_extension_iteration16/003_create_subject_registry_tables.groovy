class CreateSubjectRegistryTables extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
		createTable("REGISTRY_STATUSES") { t ->
		 	t.addVersionColumn()
            t.addColumn("grid_id", "string")
		 	t.addColumn("code", "string" )
            t.addColumn("description", "string")
            t.addColumn("retired_indicator", "string",defaultValue: false,nullable:false)
         }
		
		if (databaseMatches('oracle')) {
	   		execute("RENAME SEQ_registry_statuses_id to registry_statuses_id_seq");
	 	}
	 	
	 	createTable("PERMISSIBLE_REG_STATS") { t ->
		 	t.addVersionColumn()
            t.addColumn("grid_id", "string")
		 	t.addColumn("registry_st_id", "integer", nullable: false )
		 	t.addColumn("study_id", "integer", nullable: false )
		 	t.addColumn("retired_indicator", "string",defaultValue: false,nullable:false)
         }
		
		if (databaseMatches('oracle')) {
	   		execute("RENAME SEQ_PERMISSIBLE_REG_STATS_id to PERMISSIBLE_REG_STATS_id_seq");
	 	}
		
		execute('ALTER TABLE PERMISSIBLE_REG_STATS ADD CONSTRAINT FK_REG_STATUS FOREIGN KEY (registry_st_id) REFERENCES REGISTRY_STATUSES(ID)');
		execute('ALTER TABLE PERMISSIBLE_REG_STATS ADD CONSTRAINT FK_PER_REG_STUDY FOREIGN KEY (study_id) REFERENCES STUDIES(ID)');
	 	
	 	createTable("STU_SUB_REG_STATUSES") { t ->
		 	t.addVersionColumn()
            t.addColumn("grid_id", "string")
		 	t.addColumn("effective_date", "date" )
            t.addColumn("per_reg_st_id", "integer", nullable: false)
            t.addColumn("stu_sub_id", "integer", nullable: false)
            t.addColumn("retired_indicator", "string",defaultValue: false,nullable:false)
         }
         
         if (databaseMatches('oracle')) {
	   		execute("RENAME SEQ_STU_SUB_REG_STATUSES_id to STU_SUB_REG_STATUSES_id_seq");
	 	}
	 	
	 	execute('ALTER TABLE STU_SUB_REG_STATUSES ADD CONSTRAINT FK_STU_SUB_REG_ST FOREIGN KEY (per_reg_st_id) REFERENCES PERMISSIBLE_REG_STATS(ID)');
	 	execute('ALTER TABLE STU_SUB_REG_STATUSES ADD CONSTRAINT FK_STU_SUB_STUDY_SUBJECT FOREIGN KEY (stu_sub_id) REFERENCES STUDY_SUBJECTS(ID)');
	 	
	 	addColumn("reasons", "parent_id", "integer");
	 	addColumn("reasons", "registry_st_id", "integer");
	 	addColumn("reasons", "per_reg_st_id", "integer");
	 	addColumn("reasons", "stu_sub_reg_st_id", "integer");
	 	addColumn("reasons", "primary_indicator", "boolean");
	 	
	 	execute('ALTER TABLE reasons ADD CONSTRAINT FK_PARENT_REASON FOREIGN KEY (parent_id) REFERENCES reasons(ID)');
	 	execute('ALTER TABLE reasons ADD CONSTRAINT FK_PER_REG_STATUS FOREIGN KEY (per_reg_st_id) REFERENCES PERMISSIBLE_REG_STATS(ID)');
	 	execute('ALTER TABLE reasons ADD CONSTRAINT FK_REASON_REG_STATUS FOREIGN KEY (registry_st_id) REFERENCES REGISTRY_STATUSES(ID)');
	 	execute('ALTER TABLE reasons ADD CONSTRAINT FK_STU_SUB_REG_STATUS FOREIGN KEY (stu_sub_reg_st_id) REFERENCES STU_SUB_REG_STATUSES(ID)');
			
	}
	void down() {
    }
}
