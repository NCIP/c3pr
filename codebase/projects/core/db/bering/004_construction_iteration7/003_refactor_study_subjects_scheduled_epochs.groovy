class RefactorScheduledEpochs extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	addColumn('STUDY_SUBJECTS','REG_WORKFLOW_STATUS','string');
	   	addColumn('SCHEDULED_EPOCHS','SC_EPOCH_WORKFLOW_STATUS','string');
    	addColumn('STUDY_SUBJECTS','REG_DATA_ENTRY_STATUS','string');
	   	addColumn('SCHEDULED_EPOCHS','SC_EPOCH_DATA_ENTRY_STATUS','string');
    	dropColumn('STUDY_SUBJECTS','REGISTRATION_STATUS');
    	dropColumn('SCHEDULED_EPOCHS','REGISTRATION_STATUS');
    }

    void down() {
    	dropColumn('STUDY_SUBJECTS','REG_WORKFLOW_STATUS');
	   	dropColumn('SCHEDULED_EPOCHS','SC_EPOCH_WORKFLOW_STATUS');
    	dropColumn('STUDY_SUBJECTS','REG_DATA_ENTRY_STATUS');
	   	dropColumn('SCHEDULED_EPOCHS','SC_EPOCH_DATA_ENTRY_STATUS');
    	addColumn('STUDY_SUBJECTS','REGISTRATION_STATUS','string')
    	addColumn('SCHEDULED_EPOCHS','REGISTRATION_STATUS','string')
    }
}