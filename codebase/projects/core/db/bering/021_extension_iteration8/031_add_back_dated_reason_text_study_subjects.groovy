class AddBackDatedReasonTextStudySubjects extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
			addColumn('study_subjects','back_dated_reason_text','string',limit: 2000);
	}

	void down(){
			dropColumn('study_subjects','back_dated_reason_text');
	}
}