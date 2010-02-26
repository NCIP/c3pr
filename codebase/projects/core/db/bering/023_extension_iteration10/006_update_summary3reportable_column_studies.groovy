class UpdateNCIIdentidifierInvestigators extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
		execute("ALTER TABLE studies RENAME COLUMN summary_3_reportable to therapeutic_intent_indicator");
	}
	void down() {
    }
}
