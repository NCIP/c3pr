class CreateConsentQuestions extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
		createTable("consent_questions") { t ->
            t.addVersionColumn()
            t.addColumn("grid_id", "string")
            t.addColumn("retired_indicator", "string",defaultValue: false,nullable:false)
            t.addColumn("text", "string",nullable:false)
            t.addColumn("con_id", "integer",nullable:false)
         }
        
         if (databaseMatches('oracle')) {
		   		execute("RENAME SEQ_consent_questions_ID to consent_questions_ID_SEQ");
	 	}
	}
	void down() {
		dropTable("consent_questions")
    }
}
