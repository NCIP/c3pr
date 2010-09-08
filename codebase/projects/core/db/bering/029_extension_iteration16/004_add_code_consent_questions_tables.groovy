class CreateSubjectRegistryTables extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
	 	addColumn("consent_questions", "code", "string");
	 	execute("Update consent_questions set code=text");
	 	setNullable('consent_questions', 'code', false);
	}
	void down() {
    }
}
