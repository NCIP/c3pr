class AddCCTSWorkflowStatusColumn extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	addColumn('STUDIES','CCTS_WORKFLOW_STATUS','string');
    	setNullable('STUDIES','CCTS_WORKFLOW_STATUS',true);
    	addColumn('STUDY_SUBJECTS','CCTS_WORKFLOW_STATUS','string');
    	setNullable('STUDY_SUBJECTS','CCTS_WORKFLOW_STATUS',true);

    }

    void down() {
	    dropColumn('STUDY_SUBJECTS','CCTS_WORKFLOW_STATUS');
    	dropColumn('STUDIES','CCTS_WORKFLOW_STATUS')

    }
}