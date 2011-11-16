class DeleteSystemAssignedHouseholdIdentifiers extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
		execute("delete from identifiers where dtype like 'SAI' and type like 'HOUSEHOLD_IDENTIFIER'");
	}
	void down() {
    }
}
