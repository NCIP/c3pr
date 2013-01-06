class RenameReasonTextToOffStudyReasonText extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	execute("alter table study_subjects rename column reason_text to off_study_reason_text");
    	addColumn('study_subjects','invalidation_reason_text', "string");
	}

	void down() {
		execute("alter table study_subjects rename column off_study_reason_text to reason_text");
		dropColumn('study_subjects','invalidation_reason_text')
    }
}