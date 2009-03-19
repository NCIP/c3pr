class modifyIdentifiersAddDefaultSystemAssignedIdentifiersForParticipants extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
	   		execute("DELETE from identifiers where type='Participant Identifier'");
    }

    void down() {
    	
    }
}
