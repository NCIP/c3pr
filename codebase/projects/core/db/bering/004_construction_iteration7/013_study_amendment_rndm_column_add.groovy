class StudyAmendmentRndmColumnAdd extends edu.northwestern.bioinformatics.bering.Migration {
    
    void up() {
   		addColumn('study_amendments', 'rndm_changed_indicator', 'boolean');   		
   	 }

    void down() {
    	dropColumn('study_amendments', 'rndm_changed_indicator'); 		
	}
	
}