class ConvertStudySubjectConsentsInformedConsentSignedDateToTimestamp extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	if (databaseMatches('oracle')){
       		addColumn('study_subject_consents','informed_consent_signed_tstamp', 'timestamp');
       		execute("update study_subject_consents set informed_consent_signed_tstamp = informed_consent_signed_date");
        }
        if (databaseMatches('postgresql')){
       		addColumn('study_subject_consents','informed_consent_signed_timestamp', 'timestamp');
       		execute("update study_subject_consents set informed_consent_signed_timestamp = informed_consent_signed_date");
        }
       
   	}
	void down() {
    }
}
