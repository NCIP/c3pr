class AddDeathRelatedColumnsToParticipant extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	addColumn('participants', 'death_date', 'timestamp');    	
    	//setNullable('participants','death_date', true);
    	
    	addColumn('participants', 'death_indicator', 'boolean');    	
    	//setNullable('participants','death_indicator', true);
    	
    	
	}
	void down() {		
    }
}
