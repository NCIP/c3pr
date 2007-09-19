class StudyAmendmentColumnAdd extends edu.northwestern.bioinformatics.bering.Migration {
    
    void up() {
   		addColumn('study_amendments', 'epoch_and_arms_changed_indicator', 'boolean');
   		addColumn('study_amendments', 'stratification_changed_indicator', 'boolean');
   		addColumn('study_amendments', 'consent_changed_indicator', 'boolean');
   		addColumn('study_amendments', 'eligibility_changed_indicator', 'boolean');
   		addColumn('study_amendments', 'diseases_changed_indicator', 'boolean');
   		addColumn('study_amendments', 'principal_investigator_changed_indicator', 'boolean');
   	 }

    void down() {
    	dropColumn('study_amendments', 'epoch_and_arms_changed_indicator');
   		dropColumn('study_amendments', 'stratification_changed_indicator');
   		dropColumn('study_amendments', 'consent_changed_indicator');
   		dropColumn('study_amendments', 'eligibility_changed_indicator');
   		dropColumn('study_amendments', 'diseases_changed_indicator');
   		dropColumn('study_amendments', 'principal_investigator_changed_indicator');  		
	}
	
}