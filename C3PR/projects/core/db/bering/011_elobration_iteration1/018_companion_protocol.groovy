class companionProtocol extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	addColumn("studies", "standalone_indicator", "boolean")
    	addColumn("studies", "companion_indicator", "boolean")
    	addColumn("study_subjects", "comp_assoc_id", "integer", nullable : true)
    	addColumn("study_subjects", "parent_stu_sub_id", "integer", nullable : true)
    	
    	createTable("comp_stu_associations") { t ->
            t.addColumn("parent_study_id", "integer", nullable: false)
            t.addColumn("companion_study_id", "integer", nullable: false)
            t.addColumn("mandatory_indicator", "boolean")
            t.addColumn("version", "integer", defaultValue: 0, nullable: false)
			t.addColumn("grid_id", "string")
			t.addColumn("retired_indicator", 'string' );
        }
		
		execute("ALTER TABLE STUDY_SUBJECTS ADD CONSTRAINT FK_STU_SUB_PARENT FOREIGN KEY (parent_stu_sub_id) REFERENCES STUDY_SUBJECTS (ID)");
		execute("ALTER TABLE STUDY_SUBJECTS ADD CONSTRAINT FK_STU_SUB_COMP_ASSOC FOREIGN KEY (comp_assoc_id) REFERENCES comp_stu_associations (ID)");
		execute("ALTER TABLE comp_stu_associations ADD CONSTRAINT FK_STU_PARENT FOREIGN KEY (parent_study_id) REFERENCES STUDIES (ID)");
		execute("ALTER TABLE comp_stu_associations ADD CONSTRAINT FK_STU_COMPA FOREIGN KEY (companion_study_id) REFERENCES STUDIES (ID)");
    }
  
    void down() {
    	dropColumn("studies", "companion_indicator")
    	dropColumn("studies", "standalone_indicator")
    	dropTable("comp_stu_associations")
    	dropColumn("study_subjects", "comp_assoc_id")
    	dropColumn("study_subjects", "parent_stu_sub_id")
    	execute(" ALTER TABLE STUDY_SUBJECTS drop CONSTRAINT FK_STU_SUB_PARENT")
    	execute(" ALTER TABLE STUDY_SUBJECTS drop CONSTRAINT FK_STU_SUB_COMP_ASSOC")
    }
}
