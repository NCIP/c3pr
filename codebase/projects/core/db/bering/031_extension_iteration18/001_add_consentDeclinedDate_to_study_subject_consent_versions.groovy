class AddDocumentIdToStudySubjectConsentVersions extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
		addColumn('study_subject_consents','consent_declined_date','date');   		 	
	}
	void down() {
		dropColumn('study_subject_consents','consent_declined_date'); 
    }
}
