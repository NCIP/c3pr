class StudyConsentVersionColumnAdd extends edu.northwestern.bioinformatics.bering.Migration {
    
    void up() {
   		addColumn('scheduled_arms', 'kit_number', 'string');   		
   	 }

    void down() {
    	dropColumn('scheduled_arms', 'kit_number'); 		
	}
	
}