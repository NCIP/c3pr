class SetDefaultValuesForNewParticipantFields extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
       
        if (databaseMatches('postgres')){ 	
    		execute("update participants set death_indicator = false, state_code='ACTIVE'");
    	}
    	
     	if (databaseMatches('oracle')){
       		execute("update participants set death_indicator = 0, state_code='ACTIVE'");
       	}   	
    	
	}
	void down() {		
    }
	
}
