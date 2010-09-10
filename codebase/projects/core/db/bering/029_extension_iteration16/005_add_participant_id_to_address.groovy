class AddParticipantIdToAddress extends edu.northwestern.bioinformatics.bering.Migration {

    void up() {
        	
        // A new requirement is that a participant can now have more than one address, so we are introducing
        // many-to-one from address to participant. This is NOT the cleanest way to achieve this from a design perspective --
        // it's probably not normalized -- but it is definitely simple to implement. After some discussions, we decided to go with it.
    	addColumn('addresses','participant_id','integer');    			
		execute('ALTER TABLE addresses ADD CONSTRAINT FK_ADDR_PART FOREIGN KEY (participant_id) REFERENCES participants(ID)');
		execute('UPDATE addresses SET participant_id=(SELECT id from participants where participants.add_id=addresses.id)');
		dropColumn('participants','add_id');
			
	}
	
	void down() {
    }
}
