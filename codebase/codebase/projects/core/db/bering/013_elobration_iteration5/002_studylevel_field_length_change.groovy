class StudyTableFieldLength extends edu.northwestern.bioinformatics.bering.Migration {
    
    void up() {
	    if (databaseMatches('oracle')){
		    execute("ALTER TABLE STUDIES MODIFY (LONG_TITLE_TEXT  VARCHAR2(1024))");
		    execute("ALTER TABLE ELIGIBILITY_CRITERIA MODIFY (QUESTION_TEXT  VARCHAR2(1024))");
	   	 }else if(databaseMatches('postgres')){
			execute("ALTER TABLE STUDIES ALTER LONG_TITLE_TEXT TYPE VARCHAR(1024)");
			execute("ALTER TABLE ELIGIBILITY_CRITERIA ALTER QUESTION_TEXT TYPE VARCHAR(1024)");	   	 	
	   	 }
	}
	
    void down() {
	    if (databaseMatches('oracle')){
		    execute("ALTER TABLE STUDIES MODIFY (LONG_TITLE_TEXT  VARCHAR2(200))");
		    execute("ALTER TABLE ELIGIBILITY_CRITERIA MODIFY (QUESTION_TEXT  VARCHAR2(500))");
	   	 }else if(databaseMatches('postgres')){
			execute("ALTER TABLE STUDIES ALTER LONG_TITLE_TEXT TYPE VARCHAR(200)");
			execute("ALTER TABLE ELIGIBILITY_CRITERIA ALTER QUESTION_TEXT TYPE VARCHAR(500)");	   	 	
	   	 }
   	}
}