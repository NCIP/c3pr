class AddColumnsStudySubjectsScheduledEpochs extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	addColumn('STUDY_SUBJECTS','OFF_STUDY_REASON_TEXT','string');
    	addColumn('STUDY_SUBJECTS','OFF_STUDY_DATE','date');
    	addColumn('SCHEDULED_EPOCHS','DISAPPROVAL_REASON_TEXT','string');
    }

    void down() {
    	dropColumn('STUDY_SUBJECTS','OFF_STUDY_REASON_TEXT');
    	dropColumn('STUDY_SUBJECTS','OFF_STUDY_DATE');
    	dropColumn('SCHEDULED_EPOCHS','DISAPPROVAL_REASON_TEXT');
    }
}