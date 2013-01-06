class RenameInformedConsentSignedTimeStampColumn extends edu.northwestern.bioinformatics.bering.Migration {
	
    void up() {
        
       if (databaseMatches('postgresql')){
       		renameColumn('study_subject_consents','informed_consent_signed_timestamp','informed_consent_signed_tstamp')
        }
    }

    void down() {
    }
}