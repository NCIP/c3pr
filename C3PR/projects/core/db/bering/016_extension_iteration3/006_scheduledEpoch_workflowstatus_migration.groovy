class ScheduledEpochWorkflowStatusMigration extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
	    execute("update scheduled_epochs set sc_epoch_workflow_status='REGISTERED' where sc_epoch_workflow_status='APPROVED'")
	   	execute("update scheduled_epochs set sc_epoch_workflow_status='PENDING' where sc_epoch_workflow_status='UNAPPROVED' or sc_epoch_workflow_status='DISAPPROVED'")
    }

    void down() {
    	execute("update scheduled_epochs set sc_epoch_workflow_status='APPROVED' where sc_epoch_workflow_status='REGISTERED'")
	   	execute("update scheduled_epochs set sc_epoch_workflow_status='UNAPPROVED' where sc_epoch_workflow_status='PENDING'")
    }
}
