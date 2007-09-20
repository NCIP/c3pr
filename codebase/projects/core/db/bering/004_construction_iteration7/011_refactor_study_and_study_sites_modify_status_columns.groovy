class RefactorStudyAndStudySitesModifyStatusColumns extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	addColumn('STUDIES','DATA_ENTRY_STATUS','string');
    	setNullable('STUDIES','DATA_ENTRY_STATUS',false);
	   	addColumn('STUDIES','COORDINATING_CENTER_STUDY_STATUS','string');
    	addColumn('STUDY_ORGANIZATIONS','SITE_STUDY_STATUS','string');
    	dropColumn('STUDIES','STATUS');
    	dropColumn('STUDY_ORGANIZATIONS','STATUS_CODE');
    }

    void down() {
    	addColumn('STUDY_ORGANIZATIONS','STATUS_CODE','string');
    	setNullable('STUDY_ORGANIZATIONS','STATUS_CODE',false);
	   	addColumn('STUDIES','STATUS','string');
	   	setNullable('STUDIES','STATUS',false);
	   	
	    dropColumn('STUDY_ORGANIZATIONS','SITE_STUDY_STATUS');
    	setNullable('STUDIES','COORDINATING_CENTER_STUDY_STATUS',true);
	   	dropColumn('STUDIES','COORDINATING_CENTER_STUDY_STATUS');
	   	setNullable('STUDIES','DATA_ENTRY_STATUS',true);
    	dropColumn('STUDIES','DATA_ENTRY_STATUS')
    	
    }
}