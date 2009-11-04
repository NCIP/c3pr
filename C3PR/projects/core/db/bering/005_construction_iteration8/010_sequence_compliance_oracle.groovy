class SequenceComplianceOracle extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
       if (databaseMatches('oracle')) {
	    	    execute("rename SEQ_BOOK_RNDM_ENTRY_ID to BOOK_RNDM_ENTRY_ID_SEQ")
	    	    execute("rename SEQ_RANDOMIZATIONS_ID to RANDOMIZATIONS_ID_SEQ")
	    	    execute("rename SEQ_SCHEDULED_EPOCHS_ID to SCHEDULED_EPOCHS_ID_SEQ")
	    	    execute("rename SEQ_STRAT_CRI_ANS_CMB_ID to STRAT_CRI_ANS_CMB_ID_SEQ")
	    	    execute("rename SEQ_STRATUM_GROUPS_ID to STRATUM_GROUPS_ID_SEQ")
	    	    execute("rename SEQ_STUDY_AMENDMENTS_ID to STUDY_AMENDMENTS_ID_SEQ")
	 	    }
    }

    void down() {
        if (databaseMatches('oracle')) {
	    	 execute("rename BOOK_RNDM_ENTRY_ID_SEQ TO SEQUENCE SEQ_BOOK_RNDM_ENTRY_ID")
	    	 execute("rename RANDOMIZATIONS_ID_SEQ TO SEQUENCE SEQ_RANDOMIZATIONS_ID")
	    	 execute("rename SCHEDULED_EPOCHS_ID_SEQ TO SEQUENCE SEQ_SCHEDULED_EPOCHS_ID")
	    	 execute("rename STRAT_CRI_ANS_CMB_ID_SEQ TO SEQUENCE SEQ_STRAT_CRI_ANS_CMB_ID")
	    	 execute("rename STRATUM_GROUPS_ID_SEQ TO SEQUENCE SEQ_STRATUM_GROUPS_ID")
	    	 execute("rename STUDY_AMENDMENTS_ID_SEQ TO SEQUENCE SEQ_STUDY_AMENDMENTS_ID")	    	 	 		 
	 	}
    }
}