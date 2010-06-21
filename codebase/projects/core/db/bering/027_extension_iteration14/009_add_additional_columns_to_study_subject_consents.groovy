class AddAdditionalColumnsToStudySubjectConsents extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	addColumn('study_subject_consents', 'consenting_method', 'string');
    	addColumn('study_subject_consents', 'consent_presenter', 'string');
    	addColumn('study_subject_consents', 'consent_delivery_date', 'date');
   	}
	void down() {
    }
}
