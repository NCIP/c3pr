class addMultSiteWorkflowStatusStudySubjectAndStudy extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	//addColumn('STUDIES','CCTS_ERROR_STRING','string');
    	//addColumn('STUDY_SUBJECTS','CCTS_ERROR_STRING','string');
    }

    void down() {
	    dropColumn('STUDY_SUBJECTS','CCTS_ERROR_STRING');
    	dropColumn('STUDIES','CCTS_ERROR_STRING');
    }
}