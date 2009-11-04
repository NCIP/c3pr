class AddCCTSWorkflowStatusColumn extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	addColumn('STUDIES','CCTS_WORKFLOW_STATUS','string');
    	addColumn('STUDY_SUBJECTS','CCTS_WORKFLOW_STATUS','string');
    }

    void down() {
	    dropColumn('STUDY_SUBJECTS','CCTS_WORKFLOW_STATUS');
    	dropColumn('STUDIES','CCTS_WORKFLOW_STATUS')

    }
}