class CreateOffEpochReasonTables extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
		createTable("reasons") { t ->
            t.addVersionColumn()
            t.addColumn("grid_id", "string")
            t.addColumn("code", "string", nullable: false)
            t.addColumn("description", "string", nullable: false)
            t.addColumn("dtype", "string", nullable:false)
            t.addColumn('retired_indicator', 'string' );
         }
         if (databaseMatches('oracle')) {
		   	execute("RENAME SEQ_reasons_ID to reasons_ID_SEQ");
	 	}
	 	createTable("off_epoch_reasons") { t ->
            t.addVersionColumn()
            t.addColumn("grid_id", "string")
            t.addColumn("description", "string")
            t.addColumn("reason_id", "integer")
            t.addColumn("SCEPH_ID", "integer", nullable:false)
            t.addColumn('retired_indicator', 'string' );
         }
         if (databaseMatches('oracle')) {
		   	execute("RENAME SEQ_off_epoch_reasons_ID to off_epoch_reasons_ID_SEQ");
	 	}
	 	execute('ALTER TABLE off_epoch_reasons ADD CONSTRAINT FK_OER_REASON FOREIGN KEY (reason_id) REFERENCES reasons (ID)');
	 	execute('ALTER TABLE off_epoch_reasons ADD CONSTRAINT FK_OER_SCEPH FOREIGN KEY (SCEPH_ID) REFERENCES scheduled_epochs (ID)');
	 	
	 	insert('reasons', [ id: -1, code: 'ALTERNATIVE_TREATMENT', description: 'Alternative treatment', dtype: 'OFF_TREATMENT'])
	 	insert('reasons', [ id: -2, code: 'DISEASE_PROGRESSION_BEFORE_TREATMENT', description: 'Disease progression before treatment', dtype: 'OFF_TREATMENT'])
	 	insert('reasons', [ id: -3, code: 'STUDY_COMPLETE', description: 'Study complete', dtype: 'OFF_TREATMENT'])
	 	insert('reasons', [ id: -4, code: 'DEATH_ON_STUDY', description: 'Death on study', dtype: 'OFF_TREATMENT'])
	 	insert('reasons', [ id: -5, code: 'BEGAN_PROTOCOL_SPECIFIED_FOLLOW_UP', description: 'Began Protocol-Specified Follow-up', dtype: 'OFF_TREATMENT'])
	 	insert('reasons', [ id: -6, code: 'CYTOGENETIC_RESISTANCE', description: 'Cytogenetic resistance', dtype: 'OFF_TREATMENT'])
	 	insert('reasons', [ id: -7, code: 'UNABLE_TO_SCHEDULE_VISIT', description: 'Unable to schedule visit ', dtype: 'OFF_TREATMENT'])
	 	insert('reasons', [ id: -8, code: 'INELIGIBLE', description: 'Ineligible', dtype: 'OFF_TREATMENT'])
	 	insert('reasons', [ id: -9, code: 'LOST_TO_FOLLOWUP', description: 'Lost to followup', dtype: 'OFF_TREATMENT'])
	 	insert('reasons', [ id: -10, code: 'PI_DISCRETION', description: 'PI Discretion', dtype: 'OFF_TREATMENT'])
	 	insert('reasons', [ id: -11, code: 'OTHER', description: 'Other', dtype: 'OFF_TREATMENT'])
	 	insert('reasons', [ id: -12, code: 'DISEASE_PROGRESSION', description: 'Disease progression', dtype: 'OFF_TREATMENT'])
	 	insert('reasons', [ id: -13, code: 'TREATMENT_PERIOD_COMPLETED', description: 'Treatment period completed', dtype: 'OFF_TREATMENT'])
	 	insert('reasons', [ id: -14, code: 'REFUSED_FURTHER_TREATMENT', description: 'Refused further treatment', dtype: 'OFF_TREATMENT'])
	 	insert('reasons', [ id: -15, code: 'COMPLICATING_DISEASE', description: 'Complicating disease', dtype: 'OFF_TREATMENT'])
	 	insert('reasons', [ id: -16, code: 'TOXICITY', description: 'Toxicity', dtype: 'OFF_TREATMENT'])
	 	insert('reasons', [ id: -17, code: 'NOT_TREATED_OTHER_REASONS', description: 'Not treated - other reasons', dtype: 'OFF_TREATMENT'])
	 	insert('reasons', [ id: -18, code: 'PROTOCOL_VIOLATION', description: 'Protocol violation', dtype: 'OFF_TREATMENT'])
	 	insert('reasons', [ id: -19, code: 'DECLINED_TO_PARTICIPATE', description: 'Declined to participate', dtype: 'OFF_TREATMENT'])
	 	insert('reasons', [ id: -20, code: 'TREATMENT_COMPLETE_BUT_PATIENT_REFUSED_FOLLOWUP', description: 'Treatment complete but patient refused followup', dtype: 'OFF_TREATMENT'])
	 	insert('reasons', [ id: -21, code: 'PATIENT_NONCOMPLIANCE', description: 'Patient Noncompliance', dtype: 'OFF_STUDY'])
	 	insert('reasons', [ id: -22, code: 'ALTERNATIVE_TREATMENT', description: 'Alternative treatment', dtype: 'OFF_STUDY'])
	 	insert('reasons', [ id: -23, code: 'DISEASE_PROGRESSION_BEFORE_TREATMENT', description: 'Disease progression before treatment', dtype: 'OFF_STUDY'])
	 	insert('reasons', [ id: -24, code: 'STUDY_COMPLETE', description: 'Study complete', dtype: 'OFF_STUDY'])
	 	insert('reasons', [ id: -25, code: 'DEATH_ON_STUDY', description: 'Death on study', dtype: 'OFF_STUDY'])
	 	insert('reasons', [ id: -26, code: 'LATE_ADVERSE_EVENT_SIDE_EFFECT', description: 'Late adverse event / side effect', dtype: 'OFF_STUDY'])
	 	insert('reasons', [ id: -27, code: 'CYTOGENETIC_RESISTANCE', description: 'Cytogenetic resistance', dtype: 'OFF_STUDY'])
	 	insert('reasons', [ id: -28, code: 'FOLLOWUP_PERIOD_COMPLETE', description: 'Followup period complete', dtype: 'OFF_STUDY'])
	 	insert('reasons', [ id: -29, code: 'INELIGIBLE', description: 'Ineligible', dtype: 'OFF_STUDY'])
	 	insert('reasons', [ id: -30, code: 'LOST_TO_FOLLOWUP', description: 'Lost to followup', dtype: 'OFF_STUDY'])
	 	insert('reasons', [ id: -31, code: 'DEATH_DURING_FOLLOWUP_PERIOD', description: 'Death during followup period', dtype: 'OFF_STUDY'])
	 	insert('reasons', [ id: -32, code: 'NOT_TREATED', description: 'Not treated', dtype: 'OFF_STUDY'])
	 	insert('reasons', [ id: -33, code: 'OTHER', description: 'Other', dtype: 'OFF_STUDY'])
	 	insert('reasons', [ id: -34, code: 'DISEASE_PROGRESSION', description: 'Disease progression', dtype: 'OFF_STUDY'])
	 	insert('reasons', [ id: -35, code: 'REFUSED_FURTHER_TREATMENT', description: 'Refused further treatment', dtype: 'OFF_STUDY'])
	 	insert('reasons', [ id: -36, code: 'COMPLICATING_DISEASE', description: 'Complicating disease', dtype: 'OFF_STUDY'])
	 	insert('reasons', [ id: -37, code: 'TOXICITY', description: 'Toxicity', dtype: 'OFF_STUDY'])
	 	insert('reasons', [ id: -38, code: 'NOT_TREATED_OTHER_REASONS', description: 'Not treated - other reasons', dtype: 'OFF_STUDY'])
	 	insert('reasons', [ id: -39, code: 'PROTOCOL_VIOLATION', description: 'Protocol violation', dtype: 'OFF_STUDY'])
	 	insert('reasons', [ id: -40, code: 'REFUSED_FURTHER_FOLLOWUP', description: 'Refused further followup', dtype: 'OFF_STUDY'])
	 	insert('reasons', [ id: -41, code: 'PHYSICIAN_DECIDED_NOT_TO_REGISTER_PATIENT', description: 'Physician decided not to register patient', dtype: 'OFF_SCREENING'])
	 	insert('reasons', [ id: -42, code: 'STUDY_CLOSED', description: 'Study closed', dtype: 'OFF_SCREENING'])
	 	insert('reasons', [ id: -43, code: 'LACK_OF_INSURANCE_COVERAGE', description: 'Lack of insurance coverage', dtype: 'OFF_SCREENING'])
	 	insert('reasons', [ id: -44, code: 'REFERRED_TO_HOSPICE', description: 'Referred to hospice', dtype: 'OFF_SCREENING'])
	 	insert('reasons', [ id: -45, code: 'REGISTERED_TO_ANOTHER_STUDY', description: 'Registered to another study', dtype: 'OFF_SCREENING'])
	 	insert('reasons', [ id: -46, code: 'REGISTERED_TO_THE_STUDY_AT_ANOTHER_SITE', description: 'Registered to the study at another site', dtype: 'OFF_SCREENING'])
	 	insert('reasons', [ id: -47, code: 'UNABLE_TO_REGISTER_DUE_TO_LACK_OF_RADIATION_ONCOLOGIST', description: 'Unable to register due to lack of Radiation Oncologist', dtype: 'OFF_SCREENING'])
	 	insert('reasons', [ id: -48, code: 'UNABLE_TO_SHIP_SPECIMEN_ON_A_FRIDAY', description: 'Unable to ship specimen on a Friday', dtype: 'OFF_SCREENING'])
	 	insert('reasons', [ id: -49, code: 'DRUG_BECAME_COMMERCIALLY_AVAILABLE', description: 'Drug became commercially available', dtype: 'OFF_SCREENING'])
	 	insert('reasons', [ id: -50, code: 'SPONSOR_DECISION', description: 'Sponsor decision (to include study closing before registration or study put on hold)', dtype: 'OFF_SCREENING'])
	 	insert('reasons', [ id: -51, code: 'DOES_NOT_MEET_SURGICAL_REQUIREMENTS', description: 'Does not meet surgical requirements', dtype: 'OFF_SCREENING'])
	 	insert('reasons', [ id: -52, code: 'EXTENT_OF_DISEASE', description: 'Extent of disease', dtype: 'OFF_SCREENING'])
	 	insert('reasons', [ id: -53, code: 'PRIOR_OR_CURRENT_MEDICATIONS', description: 'Prior or current medications', dtype: 'OFF_SCREENING'])
	 	insert('reasons', [ id: -54, code: 'TIME_FORM_PRIOR_TREATMENT', description: 'Time form prior treatment', dtype: 'OFF_SCREENING'])
	 	insert('reasons', [ id: -55, code: 'LABORATORY_VALUES', description: 'Laboratory values', dtype: 'OFF_SCREENING'])
	 	insert('reasons', [ id: -56, code: 'LENGTH_OF_TIME_FROM_SURGERY', description: 'Length of time from surgery', dtype: 'OFF_SCREENING'])
	 	insert('reasons', [ id: -57, code: 'PATIENT_UNABLE_TO_READ_AND_WRITE', description: 'Patient unable to read and write', dtype: 'OFF_SCREENING'])
	 	insert('reasons', [ id: -58, code: 'SECOND_PRIMARY', description: 'Second primary', dtype: 'OFF_SCREENING'])
	 	insert('reasons', [ id: -59, code: 'NO_SIBLING_MATCH_FOR_TRANSPLANT', description: 'No sibling match for transplant', dtype: 'OFF_SCREENING'])
	 	insert('reasons', [ id: -60, code: 'CONCOMITANT_ILLNESS', description: 'Concomitant illness', dtype: 'OFF_SCREENING'])
	 	insert('reasons', [ id: -61, code: 'AGE', description: 'Age', dtype: 'OFF_SCREENING'])
	 	insert('reasons', [ id: -62, code: 'DOES_NOT_SPEAK_OR_READ_ENGLISH', description: 'Does not speak or read English', dtype: 'OFF_SCREENING'])
	 	insert('reasons', [ id: -63, code: 'UNABLE_TO_COMPLETE_REQUIRED_EVALUATIONS', description: 'Unable to complete required evaluations', dtype: 'OFF_SCREENING'])
	 	insert('reasons', [ id: -64, code: 'HISTORY_OF_NON_COMPLIANCE', description: 'History of non-compliance', dtype: 'OFF_SCREENING'])
	 	insert('reasons', [ id: -65, code: 'UNABLE_TO_COMPLY_WITH_STUDY_REQUIREMENTS', description: 'Unable to comply with study requirements', dtype: 'OFF_SCREENING'])
	 	insert('reasons', [ id: -66, code: 'ALLERGY', description: 'Allergy', dtype: 'OFF_SCREENING'])
	 	insert('reasons', [ id: -67, code: 'REQUIRES_OTHER_TREATMENT', description: 'Requires other treatment', dtype: 'OFF_SCREENING'])
	 	insert('reasons', [ id: -68, code: 'PATIENT_DOES_NOT_HAVE_A_TELEPHONE', description: 'Patient does not have a telephone', dtype: 'OFF_SCREENING'])
	 	insert('reasons', [ id: -69, code: 'OTHER', description: 'Other', dtype: 'OFF_RESERVING'])
	 	insert('reasons', [ id: -70, code: 'OTHER', description: 'Other', dtype: 'OFF_FOLLOWUP'])
	 	
	}
	void down() {
		dropTable("reasons")
    }
}
