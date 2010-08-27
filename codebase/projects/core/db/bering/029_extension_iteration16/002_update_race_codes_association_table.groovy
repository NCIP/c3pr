class UpdateRaceCodeAssociationsTable extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
		addColumn("race_code_assocn", "retired_indicator", "string", defaultValue: "false")
	 	
	}
	void down() {
		
    }
}
 