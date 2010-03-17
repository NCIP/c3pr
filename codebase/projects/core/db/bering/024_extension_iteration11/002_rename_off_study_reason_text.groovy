class RenameOffStudyReasonTextToReasonText extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	execute("alter table study_subjects rename column off_study_reason_text to reason_text");
	}

	void down() {
		execute("alter table study_subjects rename column reason_text to off_study_reason_text");
    }
}