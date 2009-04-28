class updateDescriptionTextlength extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
	    if (databaseMatches('oracle')){
		    execute("ALTER TABLE ARMS MODIFY (description_text  VARCHAR2(2000))");
		    execute("ALTER TABLE EPOCHS MODIFY (description_text  VARCHAR2(2000))");
		    execute("ALTER TABLE organizations MODIFY (description_text  VARCHAR2(2000))");
	   	 }else if(databaseMatches('postgres')){
	   	 	execute("ALTER TABLE ARMS ALTER description_text TYPE VARCHAR(2000)");
	   	 	execute("ALTER TABLE EPOCHS ALTER description_text TYPE VARCHAR(2000)");
	   	 	execute("ALTER TABLE ORGANIZATIONS ALTER description_text TYPE VARCHAR(2000)");
	   	 }
    }

    void down() {
    	if (databaseMatches('oracle')){
		    execute("ALTER TABLE ARMS MODIFY (description_text  VARCHAR2(50))");
		    execute("ALTER TABLE EPOCHS MODIFY (description_text  VARCHAR2(200))");
		    execute("ALTER TABLE organizations MODIFY (description_text  VARCHAR2(200))");
	   	 }else if(databaseMatches('postgres')){
	   	 	execute("ALTER TABLE ARMS ALTER description_text TYPE VARCHAR(50)");
	   	 	execute("ALTER TABLE EPOCHS ALTER description_text TYPE VARCHAR(200)");
	   	 	execute("ALTER TABLE ORGANIZATIONS ALTER description_text TYPE VARCHAR(200)");
	   	 }
    }
}
