class RemoveColumnConId extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	if (databaseMatches('postgres')){
	    	removeColumn('research_staff', 'con_id');
	    	removeColumn('investigators', 'con_id');
	    	removeColumn('participants', 'con_id');
	    }
	}
	void down() {
    }
}
