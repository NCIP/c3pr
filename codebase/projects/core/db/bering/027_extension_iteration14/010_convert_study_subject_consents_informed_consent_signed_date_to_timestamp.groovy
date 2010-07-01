class ConvertStudySubjectConsentsInformedConsentSignedDateToTimestamp extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	if (databaseMatches('oracle')){
       		addColumn('study_subject_consents','informed_consent_signed_tstamp', 'timestamp');
        }
        if (databaseMatches('postgresql')){
       		addColumn('study_subject_consents','informed_consent_signed_timestamp', 'timestamp');
        }
   	}
	void down() {
    }
}
