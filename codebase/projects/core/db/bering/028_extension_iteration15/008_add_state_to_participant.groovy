class AddStateToParticipant extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	addColumn('participants', 'state_code', 'string');
	}
	void down() {		
    }
}
