class addAmendmentReasonColumnToStudyVersion extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
      	addColumn('study_versions', 'amendment_reason', 'string');
    }

	void down() {
	    dropColumn('study_versions', 'amendment_reason');
    }
}