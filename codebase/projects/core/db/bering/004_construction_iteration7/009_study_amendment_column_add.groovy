class StudyAmendmentColumnAdd extends edu.northwestern.bioinformatics.bering.Migration {
    
    void up() {
   		addColumn('study_amendments', 'epoch_and_arms_indicator', 'boolean');
   		addColumn('study_amendments', 'stratification_indicator', 'boolean');
   		addColumn('study_amendments', 'consent_indicator', 'boolean');
   		addColumn('study_amendments', 'eligibility_indicator', 'boolean');
   		addColumn('study_amendments', 'diseases_indicator', 'boolean');
   		addColumn('study_amendments', 'principal_investigator_indicator', 'boolean');
   	 }

    void down() {
    	dropColumn('randomizations','phone_number')  
    	dropColumn('study_amendments', 'epoch_and_arms_indicator');
   		dropColumn('study_amendments', 'stratification_indicator');
   		dropColumn('study_amendments', 'consent_indicator');
   		dropColumn('study_amendments', 'eligibility_indicator');
   		dropColumn('study_amendments', 'diseases_indicator');
   		dropColumn('study_amendments', 'principal_investigator_indicator');  		
	}
	
}