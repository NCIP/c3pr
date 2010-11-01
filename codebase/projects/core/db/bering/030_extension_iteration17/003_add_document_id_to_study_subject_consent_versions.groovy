class AddDocumentIdToStudySubjectConsentVersions extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
		addColumn('study_subject_consents','document_id','string');   		 	
	}
	void down() {
		dropColumn('study_subject_consents','document_id'); 
    }
}
