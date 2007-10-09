class StudyConsentVersionColumnAdd extends edu.northwestern.bioinformatics.bering.Migration {
    
    void up() {
   		addColumn('studies', 'consent_version', 'string');   		
   	 }

    void down() {
    	dropColumn('studies', 'consent_version'); 		
	}
	
}