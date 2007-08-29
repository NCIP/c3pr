class RefactorStudyParticipantAssignment extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	if (databaseMatches('postgres')) {
	    	execute("alter table study_participant_assig_id_seq rename to study_participant_assignments_id_seq")
	 	}
    	if (databaseMatches('oracle')) {
	    	execute("rename study_participant_assig_id_seq to seq_study_participant_assig_id")
	 	}
        renameTable('study_participant_assignments', 'study_subjects')
        dropColumn('study_subjects','eligibility_indicator')
        dropColumn('study_subjects','eligibility_waiver_reason_text')   
        dropColumn('study_subjects','study_participant_identifier')   
		if (databaseMatches('postgres')) {
	 		 execute("ALTER TABLE study_subjects ALTER COLUMN id SET DEFAULT nextval('STUDY_SUBJECTS_ID_seq'::regclass)")	   
	 	}
    }

    void down() {
        renameTable('study_subjects','study_participant_assignments')
        addColumn('study_participant_assignments','eligibility_indicator')
        addColumn('study_participant_assignments','eligibility_waiver_reason_text')   
		if (databaseMatches('postgres')) {
	 		 execute("ALTER TABLE study_participant_assignments ALTER COLUMN id SET DEFAULT nextval('study_participant_assignments_id_seq'::regclass)")	   
	    	 execute("alter table study_participant_assignments_id_seq RENAME TO SEQUENCE STUDY_PARTICIPANT_ASSIG_ID_seq")	 		 
	 	}
    	if (databaseMatches('oracle')) {
	    	 execute("rename study_participant_assignments_id_seq TO SEQUENCE STUDY_PARTICIPANT_ASSIG_ID_seq")	 		 
	 	}
	 	
    }
}

