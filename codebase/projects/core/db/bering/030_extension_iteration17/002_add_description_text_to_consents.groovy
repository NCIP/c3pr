class AddDescriptionTextToConsents extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
		addColumn('consents','description_text','string');   		 	
	}
	void down() {
		dropColumn('consents','description_text'); 
    }
}
