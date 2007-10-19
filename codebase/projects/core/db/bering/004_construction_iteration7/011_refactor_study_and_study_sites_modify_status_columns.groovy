class RefactorStudyAndStudySitesModifyStatusColumns extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	addColumn('STUDIES','DATA_ENTRY_STATUS','string');
    	addColumn('STUDY_ORGANIZATIONS','SITE_STUDY_STATUS','string');
    	dropColumn('STUDY_ORGANIZATIONS','STATUS_CODE');
    	setNullable('STUDIES','STATUS',true);
    }

    void down() {
    	addColumn('STUDY_ORGANIZATIONS','STATUS_CODE','string');
	    dropColumn('STUDY_ORGANIZATIONS','SITE_STUDY_STATUS');
    	dropColumn('STUDIES','DATA_ENTRY_STATUS')
    	
    }
}