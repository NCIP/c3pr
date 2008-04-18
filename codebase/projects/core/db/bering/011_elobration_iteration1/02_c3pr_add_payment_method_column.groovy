class addpaymentMethodColumnInStudySubject extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
       if (databaseMatches('postgres')) {
    		execute("alter table STUDY_SUBJECTS add PAYMENT_METHOD varchar(50)")
	 	}
       if (databaseMatches('oracle')) {
	    	execute("alter table STUDY_SUBJECTS add PAYMENT_METHOD varchar2(50)")
	 	}
  
    }

    void down() {
        if (databaseMatches('postgres')) {	    	
	    	execute("alter table STUDY_SUBJECTS drop PAYMENT_METHOD")
	 	}
    	if (databaseMatches('oracle')) {
	    	execute("alter table STUDY_SUBJECTS drop column PAYMENT_METHOD")
	 	}
    }
}