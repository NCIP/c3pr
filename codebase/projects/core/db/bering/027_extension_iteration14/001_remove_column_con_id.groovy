class RemoveColumnConId extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	removeColumn('research_staff', 'con_id');
    	removeColumn('investigators', 'con_id');
    	removeColumn('participants', 'con_id');
	}
	void down() {
    }
}
