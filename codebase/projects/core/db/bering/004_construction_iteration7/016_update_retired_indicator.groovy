class UpdateRetiredIndicator extends edu.northwestern.bioinformatics.bering.Migration {
    
    void up() {
   		execute("update addresses set retired_indicator = 'false' where retired_indicator is null")
   		execute("update anatomic_sites set retired_indicator = 'false' where retired_indicator is null")
   		execute("update arms set retired_indicator = 'false' where retired_indicator is null")
   		execute("update book_rndm_entry set retired_indicator = 'false' where retired_indicator is null")
   		execute("update contact_mechanisms set retired_indicator = 'false' where retired_indicator is null")
   		execute("update disease_categories set retired_indicator = 'false' where retired_indicator is null")
   		execute("update disease_history set retired_indicator = 'false' where retired_indicator is null")
   		execute("update disease_terms set retired_indicator = 'false' where retired_indicator is null")
   		execute("update eligibility_criteria set retired_indicator = 'false' where retired_indicator is null")
   		execute("update epochs set retired_indicator = 'false' where retired_indicator is null")
   		execute("update hc_site_investigators set retired_indicator = 'false' where retired_indicator is null")
   		execute("update identifiers set retired_indicator = 'false' where retired_indicator is null")
   		execute("update investigators set retired_indicator = 'false' where retired_indicator is null")
   		execute("update organizations set retired_indicator = 'false' where retired_indicator is null")
   		execute("update participants set retired_indicator = 'false' where retired_indicator is null")
   		execute("update randomizations set retired_indicator = 'false' where retired_indicator is null")
   		execute("update research_staff set retired_indicator = 'false' where retired_indicator is null")
   		execute("update scheduled_arms set retired_indicator = 'false' where retired_indicator is null")
   		execute("update scheduled_epochs set retired_indicator = 'false' where retired_indicator is null")
   		execute("update strat_cri_ans_cmb set retired_indicator = 'false' where retired_indicator is null")
   		execute("update strat_cri_per_ans set retired_indicator = 'false' where retired_indicator is null")
   		execute("update strat_criteria set retired_indicator = 'false' where retired_indicator is null")
   		execute("update stratum_groups set retired_indicator = 'false' where retired_indicator is null")
   		execute("update studies set retired_indicator = 'false' where retired_indicator is null")
   		execute("update study_amendments set retired_indicator = 'false' where retired_indicator is null")
   		execute("update study_investigators set retired_indicator = 'false' where retired_indicator is null")
   		execute("update study_organizations set retired_indicator = 'false' where retired_indicator is null")
   		execute("update study_personnel set retired_indicator = 'false' where retired_indicator is null")
   		execute("update study_subjects set retired_indicator = 'false' where retired_indicator is null")
   		execute("update subject_eligibility_ans set retired_indicator = 'false' where retired_indicator is null")
   		execute("update subject_strat_ans set retired_indicator = 'false' where retired_indicator is null")    		
   		
   	 }

    void down() {
    	
	}
	
}