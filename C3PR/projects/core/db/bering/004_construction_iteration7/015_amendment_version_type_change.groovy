class AmendmentVersionTypeChange extends edu.northwestern.bioinformatics.bering.Migration {
    
    void up() {
   		dropColumn('study_amendments','amendment_version');
   		addColumn('study_amendments','amendment_version','string');	
   	 }

    void down() {
    	dropColumn('study_amendments','amendment_version');
    	addColumn('study_amendments', 'amendment_version','integer', nullable:true)
    	
	}
	
}