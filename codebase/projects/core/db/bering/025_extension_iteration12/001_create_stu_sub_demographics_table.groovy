class CreateStudySubjectDemographics extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
		createTable("stu_sub_demographics") { t ->
            t.addVersionColumn()
            t.addColumn("grid_id", "string")
            t.addColumn("first_name", "string", nullable: false)
            t.addColumn("last_name", "string", nullable: false)
            t.addColumn("middle_name", "string")
            t.addColumn("maiden_name", "string")
            t.addColumn("administrative_gender_code", "string", nullable: false)
            t.addColumn("ethnic_group_code", "string", nullable: false)
            t.addColumn("race_code", "string", nullable: false)
            t.addColumn("birth_date", "date", nullable: false)
            t.addColumn("marital_status_code", "string")
            t.addColumn("retired_indicator", "string",defaultValue: false,nullable:false)
            t.addColumn("valid", "boolean",defaultValue: false,nullable:false)
             
            t.addColumn("add_id", "integer")
            t.addColumn("prt_id", "integer",nullable:false)
         }
        
         if (databaseMatches('oracle')) {
		   	execute('RENAME sequence SEQ_stu_sub_demographics_ID to stu_sub_demographics_ID_SEQ');
	 	}
	}
	void down() {
		dropTable("stu_sub_demographics")
    }
}
