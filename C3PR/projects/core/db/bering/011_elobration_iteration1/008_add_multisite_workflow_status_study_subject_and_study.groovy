class addMultSiteWorkflowStatusStudySubjectAndStudy extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	addColumn('STUDIES','MULTISITE_WORKFLOW_STATUS','string');
    	addColumn('STUDY_SUBJECTS','MULTISITE_WORKFLOW_STATUS','string');
    	addColumn('STUDY_SUBJECTS','DISAPPROVAL_REASON_TEXT','string');
    }

    void down() {
	    dropColumn('STUDY_SUBJECTS','MULTISITE_WORKFLOW_STATUS');
    	dropColumn('STUDIES','MULTISITE_WORKFLOW_STATUS');
		dropColumn('STUDIES','DISAPPROVAL_REASON_TEXT');
    }
}