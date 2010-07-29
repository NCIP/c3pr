class SetDefaultValuesForNewParticipantFields extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {    	
    	execute("update participants set death_indicator = false, state_code='ACTIVE'");
	}
	void down() {		
    }
	
}
