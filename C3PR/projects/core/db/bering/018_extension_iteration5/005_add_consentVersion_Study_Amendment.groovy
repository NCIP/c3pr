class AddConsentVersionStudyAmendment extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
		addColumn("study_amendments","consent_version","string");
	}
	
	void down() {
		dropColumn("study_amendments","consent_version");
	}
} 