class StudyConsentVersionColumnAdd extends edu.northwestern.bioinformatics.bering.Migration {
    
    void up() {
   		addColumn('epochs', 'randomized_indicator', 'boolean');   		
   	 }

    void down() {
    	dropColumn('epochs', 'randomized_indicator'); 		
	}
	
}