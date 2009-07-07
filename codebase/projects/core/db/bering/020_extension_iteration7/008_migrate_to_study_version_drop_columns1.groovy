class MigrateToStudyVersionFropColumns1 extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
      	dropColumn('studies', 'consent_version');
      	
    }

	void down() {
	    
    }
} 