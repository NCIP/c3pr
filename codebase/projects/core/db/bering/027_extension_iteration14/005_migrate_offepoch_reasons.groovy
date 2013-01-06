class MigrateOffEpochReasons extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	execute("Update scheduled_epochs set sc_epoch_workflow_status = 'OFF_EPOCH' where off_epoch_date is not null");
    	execute("UPDATE scheduled_epochs SET sc_epoch_workflow_status = 'OFF_EPOCH' WHERE EXISTS ( SELECT study_subjects.id FROM study_subjects, study_subject_versions WHERE study_subjects.reg_workflow_status='OFF_STUDY' and study_subjects.id= study_subject_versions.spa_id and study_subject_versions.id=scheduled_epochs.study_subject_ver_id)");
    	execute("UPDATE scheduled_epochs SET off_epoch_reason_text = (SELECT study_subjects.off_study_reason_text FROM study_subjects, study_subject_versions WHERE study_subjects.reg_workflow_status='OFF_STUDY' and study_subjects.id= study_subject_versions.spa_id and study_subject_versions.id=scheduled_epochs.study_subject_ver_id) WHERE off_epoch_reason_text is null and sc_epoch_workflow_status = 'OFF_EPOCH'");
    	execute("UPDATE scheduled_epochs SET off_epoch_date = (SELECT study_subjects.off_study_date FROM study_subjects, study_subject_versions WHERE study_subjects.reg_workflow_status='OFF_STUDY' and study_subjects.id= study_subject_versions.spa_id and study_subject_versions.id=scheduled_epochs.study_subject_ver_id) WHERE off_epoch_date is null and sc_epoch_workflow_status = 'OFF_EPOCH'");
    	
    	//updated for Baylor Oracle migration
    	if(databaseMatches('postgres')){
    		execute("INSERT INTO off_epoch_reasons (description, SCEPH_ID) SELECT se.off_epoch_reason_text, se.id FROM scheduled_epochs se WHERE se.sc_epoch_workflow_status = 'OFF_EPOCH'");
    	}
    	if(databaseMatches('oracle')){
    		execute(" INSERT INTO off_epoch_reasons (id, description, SCEPH_ID) SELECT OFF_EPOCH_REASONS_ID_SEQ.nextval,se.off_epoch_reason_text, se.id FROM scheduled_epochs se WHERE se.sc_epoch_workflow_status = 'OFF_EPOCH'");
    	}
    	
    	dropColumn("scheduled_epochs","off_epoch_reason_text");
    	dropColumn("study_subjects","off_study_reason_text");
    	dropColumn("study_subjects","off_study_date");
    }

    void down() {
    }
}