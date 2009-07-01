class MigrateToStudyVersion extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
   
    // changing data type of consent_validity_period field	
    	dropColumn('studies', 'consent_validity_period')
    	addColumn('studies', 'consent_validity_period', 'integer')
    
    // populating old studies with some default values	
    	execute("update studies set consent_required='ALL'");
		execute("update studies set consent_validity_period=90")
	
	// changing data type of version_status field		
    	dropColumn('study_versions', 'version_status')
    	addColumn('study_versions', 'version_status', 'string')
    
	    // changing reference 	
  		execute("update epochs set stu_version_id=stu_id");
		execute("update study_diseases set stu_version_id=study_id")
		
		//removing unused columns
		dropColumn('epochs', 'stu_id')
    	dropColumn('study_diseases', 'study_id')
    	
    }

	void down() {
	    
    }
} 