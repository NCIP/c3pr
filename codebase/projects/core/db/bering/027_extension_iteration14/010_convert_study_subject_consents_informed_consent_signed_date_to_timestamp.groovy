class ConvertStudySubjectConsentsInformedConsentSignedDateToTimestamp extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	addColumn('study_subject_consents','informed_consent_signed_timestamp', 'timestamp');
	    execute("update study_subject_consents set informed_consent_signed_timestamp = informed_consent_signed_date")
   
   	}
	void down() {
    }
}
