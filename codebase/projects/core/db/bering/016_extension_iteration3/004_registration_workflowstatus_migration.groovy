class RegistrationWorkflowStatusMigration extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
	    execute("update study_subjects set reg_workflow_status='PENDING' where reg_workflow_status='UNREGISTERED'")
	   	execute("update study_subjects set reg_workflow_status='REGISTERED_BUT_NOT_ENROLLED' where reg_workflow_status='READY_FOR_REGISTRATION'")
	   	execute("update study_subjects set reg_workflow_status='ENROLLED' where reg_workflow_status='REGISTERED'")
    }

    void down() {
    	execute("update study_subjects set reg_workflow_status='UNREGISTERED' where reg_workflow_status='PENDING'")
	   	execute("update study_subjects set reg_workflow_status='READY_FOR_REGISTRATION' where reg_workflow_status='REGISTERED_BUT_NOT_ENROLLED'")
	   	execute("update study_subjects set reg_workflow_status='REGISTERED' where reg_workflow_status='ENROLLED'")
    }
}
