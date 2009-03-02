class ConsentVersionMigration extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    if (databaseMatches('postgres')) {
        	execute('insert into "consent_history" ("stu_sub_id","consent_version","consent_signed_date") select "id","informed_consent_version","informed_consent_signed_date" from study_subjects')
        }
	if (databaseMatches('oracle')) {
	   		execute("insert into consent_history (stu_sub_id,consent_version,consent_signed_date) select id,informed_consent_version,informed_consent_signed_date from study_subjects")
	   		execute("rename CONSENT_HISTORY_ID_SEQ to SEQ_CONSENT_HISTORY_ID")
	    }
    }

    void down() {
    	if (databaseMatches('oracle')) {
	   		execute("rename SEQ_CONSENT_HISTORY_ID to CONSENT_HISTORY_ID_SEQ")
	    }
	}
