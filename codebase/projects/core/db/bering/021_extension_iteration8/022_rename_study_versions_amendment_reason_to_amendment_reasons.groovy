class RenameStudyVersionsAmendmentReasonToAmendmentReasons extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	execute("alter table study_versions rename column amendment_reason to amendment_reasons");
	}

	void down() {
		execute("alter table study_versions rename column amendment_reasons to amendment_reason");
    }
}
