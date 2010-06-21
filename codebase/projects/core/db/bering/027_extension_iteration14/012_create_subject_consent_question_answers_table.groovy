class CreateSubjectConsentQuestionAnswers extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
		createTable("sub_con_que_ans") { t ->
            t.addVersionColumn()
            t.addColumn("grid_id", "string")
            t.addColumn("retired_indicator", "string",defaultValue: false,nullable:false)
            t.addColumn("agreement_indicator", "boolean",nullable:false)
            t.addColumn("con_que_id", "integer",nullable:false)
            t.addColumn("stu_sub_con_ver_id", "integer",nullable:false)
         }
        
         if (databaseMatches('oracle')) {
		   	execute("RENAME SEQ_sub_con_que_ans_ID to sub_con_que_ans_ID_SEQ");
	 	}
	}
	void down() {
		dropTable("sub_con_que_ans")
    }
}
