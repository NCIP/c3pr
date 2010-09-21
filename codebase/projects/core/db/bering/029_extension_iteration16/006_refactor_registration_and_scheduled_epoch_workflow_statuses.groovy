class RefactorRegistrationAndScheduledEpochWorkFlowStatuses extends edu.northwestern.bioinformatics.bering.Migration {
	
    void up() {
            execute("update scheduled_epochs set sc_epoch_workflow_status='ON_EPOCH' where sc_epoch_workflow_status like 'REGISTERED'")
            execute("update scheduled_epochs set sc_epoch_workflow_status='PENDING_RANDOMIZATION_ON_EPOCH' where sc_epoch_workflow_status like 'REGISTERED_BUT_NOT_RANDOMIZED'")
            execute("update scheduled_epochs set sc_epoch_workflow_status='PENDING_ON_EPOCH' where sc_epoch_workflow_status like 'PENDING'")
            execute("update study_subjects set reg_workflow_status='ON_STUDY' where reg_workflow_status like 'ENROLLED'")
            execute("update study_subjects set reg_workflow_status='PENDING_ON_STUDY' where reg_workflow_status like 'REGISTERED_BUT_NOT_ENROLLED'")
    }

    void down() {
    }
}