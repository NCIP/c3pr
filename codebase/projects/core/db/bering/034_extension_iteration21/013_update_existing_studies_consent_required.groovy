class UpdateExistingStudiesConsentRequired extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
		execute("update studies set consent_required ='AS_MARKED_BELOW'");
	}
	void down() {
    }
}
