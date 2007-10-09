class StudyAmendmentColumnAdd extends edu.northwestern.bioinformatics.bering.Migration {
    
    void up() {
   		addColumn('study_amendments', 'ea_changed_indicator', 'boolean');
   		addColumn('study_amendments', 'strat_changed_indicator', 'boolean');
   		addColumn('study_amendments', 'consent_changed_indicator', 'boolean');
   		addColumn('study_amendments', 'eligibility_changed_indicator', 'boolean');
   		addColumn('study_amendments', 'diseases_changed_indicator', 'boolean');
   		addColumn('study_amendments', 'pi_changed_indicator', 'boolean');
   	 }

    void down() {
    	dropColumn('study_amendments', 'ea_changed_indicator');
   		dropColumn('study_amendments', 'strat_changed_indicator');
   		dropColumn('study_amendments', 'consent_changed_indicator');
   		dropColumn('study_amendments', 'eligibility_changed_indicator');
   		dropColumn('study_amendments', 'diseases_changed_indicator');
   		dropColumn('study_amendments', 'pi_changed_indicator');  		
	}
	
}