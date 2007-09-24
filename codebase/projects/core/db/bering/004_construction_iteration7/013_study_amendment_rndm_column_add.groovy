class StudyAmendmentRndmColumnAdd extends edu.northwestern.bioinformatics.bering.Migration {
    
    void up() {
   		addColumn('study_amendments', 'randomization_changed_indicator', 'boolean');   		
   	 }

    void down() {
    	dropColumn('study_amendments', 'randomization_changed_indicator'); 		
	}
	
}