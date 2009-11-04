class RetiredIndicatorAdd extends edu.northwestern.bioinformatics.bering.Migration {
    
    void up() {
   		
   		addColumn('addresses', 'retired_indicator', 'string' );
   		addColumn('anatomic_sites', 'retired_indicator', 'string' );
   		addColumn('arms', 'retired_indicator', 'string' );
   		addColumn('book_rndm_entry', 'retired_indicator', 'string' );
   		addColumn('contact_mechanisms', 'retired_indicator', 'string' );
   		addColumn('disease_categories', 'retired_indicator', 'string' );
   		addColumn('disease_history', 'retired_indicator', 'string' );
   		addColumn('disease_terms', 'retired_indicator', 'string' );
   		addColumn('eligibility_criteria', 'retired_indicator', 'string');
   		addColumn('epochs', 'retired_indicator', 'string' );
   		addColumn('hc_site_investigators', 'retired_indicator', 'string' );
   		addColumn('identifiers', 'retired_indicator', 'string' );
   		addColumn('investigators', 'retired_indicator', 'string' );
   		addColumn('organizations', 'retired_indicator', 'string' );
   		addColumn('participants', 'retired_indicator', 'string' );
   		addColumn('randomizations', 'retired_indicator', 'string' );
   		addColumn('research_staff', 'retired_indicator', 'string' );
   		addColumn('scheduled_arms', 'retired_indicator', 'string' );
   		addColumn('scheduled_epochs', 'retired_indicator', 'string' );
   		addColumn('strat_cri_ans_cmb', 'retired_indicator', 'string' );
   		addColumn('strat_cri_per_ans', 'retired_indicator', 'string' );
   		addColumn('strat_criteria', 'retired_indicator', 'string' );
   		addColumn('stratum_groups', 'retired_indicator', 'string' );
   		addColumn('studies', 'retired_indicator', 'string' );
   		addColumn('study_amendments', 'retired_indicator', 'string' );
   		addColumn('study_diseases', 'retired_indicator', 'string' );
   		addColumn('study_investigators', 'retired_indicator', 'string' );
   		addColumn('study_organizations', 'retired_indicator', 'string' );
   		addColumn('study_personnel', 'retired_indicator', 'string' );
   		addColumn('study_subjects', 'retired_indicator', 'string' );
   		addColumn('subject_eligibility_ans', 'retired_indicator', 'string' );
   		addColumn('subject_strat_ans', 'retired_indicator', 'string' );	   		
   	 }

    void down() {
    	
    	dropColumn('addresses', 'retired_indicator');
   		dropColumn('anatomic_sites', 'retired_indicator');
   		dropColumn('arms', 'retired_indicator');
   		dropColumn('book_rndm_entry', 'retired_indicator');
   		dropColumn('contact_mechanisms', 'retired_indicator');
   		dropColumn('disease_categories', 'retired_indicator');
   		dropColumn('disease_history', 'retired_indicator');
   		dropColumn('disease_terms', 'retired_indicator');
   		dropColumn('eligibility_criteria', 'retired_indicator');
   		dropColumn('epochs', 'retired_indicator');
   		dropColumn('hc_site_investigators', 'retired_indicator');
   		dropColumn('identifiers', 'retired_indicator');
   		dropColumn('investigators', 'retired_indicator');
   		dropColumn('organizations', 'retired_indicator');
   		dropColumn('participants', 'retired_indicator');
   		dropColumn('randomizations', 'retired_indicator');
   		dropColumn('research_staff', 'retired_indicator');
   		dropColumn('scheduled_arms', 'retired_indicator');
   		dropColumn('scheduled_epochs', 'retired_indicator');
   		dropColumn('strat_cri_ans_cmb', 'retired_indicator');
   		dropColumn('strat_cri_per_ans', 'retired_indicator');
   		dropColumn('strat_criteria', 'retired_indicator');
   		dropColumn('stratum_groups', 'retired_indicator');
   		dropColumn('studies', 'retired_indicator');
   		dropColumn('study_amendments', 'retired_indicator');
   		dropColumn('study_diseases', 'retired_indicator');
   		dropColumn('study_investigators', 'retired_indicator');
   		dropColumn('study_organizations', 'retired_indicator');
   		dropColumn('study_personnel', 'retired_indicator');
   		dropColumn('study_subjects', 'retired_indicator');
   		dropColumn('subject_eligibility_ans', 'retired_indicator');
   		dropColumn('subject_strat_ans', 'retired_indicator'); 		
	}
	
}