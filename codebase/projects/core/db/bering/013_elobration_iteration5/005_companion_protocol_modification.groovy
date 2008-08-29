class companionProtocolUpdate extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	dropColumn("study_subjects", "comp_assoc_id")
		dropColumn("study_subjects", "parent_stu_sub_id")    	
    	
    	createTable("stu_sub_associations") { t ->
            t.addColumn("parent_stu_sub_id", "integer", nullable: false)
            t.addColumn("child_stu_sub_id", "integer", nullable: false)
            t.addColumn("version", "integer", defaultValue: 0, nullable: false)
			t.addColumn("grid_id", "string")
			t.addColumn("retired_indicator", 'string' );
        }
		
		execute("ALTER TABLE stu_sub_associations ADD CONSTRAINT FK_STU_SUB_PAR FOREIGN KEY (parent_stu_sub_id) REFERENCES STUDY_SUBJECTS (ID)");
		execute("ALTER TABLE stu_sub_associations ADD CONSTRAINT FK_STU_SUB_CHILD FOREIGN KEY (child_stu_sub_id) REFERENCES STUDY_SUBJECTS (ID)");
    }
  
    void down() {
    	addColumn("study_subjects", "comp_assoc_id", "integer")
    	addColumn("study_subjects", "parent_stu_sub_id", "integer")
    	dropTable("stu_sub_associations")
    }
}
