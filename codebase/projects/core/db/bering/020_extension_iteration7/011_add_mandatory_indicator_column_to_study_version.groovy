class addAmendmentReasonColumnToStudyVersion extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
      	addColumn('study_versions', 'mandatory_indicator', 'boolean')
      	addColumn('studies', 'target_accrual_number', 'integer')
      	dropColumn('study_versions', 'target_accrual_number')
    }

	void down() {
	    dropColumn('study_versions', 'mandatory_indicator')
	    dropColumn('studies', 'target_accrual_number')
	    addColumn('study_versions', 'target_accrual_number', 'integer')
    }
}