class MigrateToStudyVersionFropColumns extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
      	dropColumn('studies', 'short_title_text');
   		dropColumn('studies', 'long_title_text');
   		dropColumn('studies', 'description_text');
   		dropColumn('studies', 'precis_text');
   		dropColumn('studies', 'randomization_type');
   		dropColumn('studies', 'target_accrual_number');
   		dropColumn('studies', 'data_entry_status');
    }

	void down() {
	    
    }
} 