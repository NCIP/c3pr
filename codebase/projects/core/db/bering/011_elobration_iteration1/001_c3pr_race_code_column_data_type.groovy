class IncreaseParticipantRaceCodeLength extends edu.northwestern.bioinformatics.bering.Migration {
    
    void up() {
	    if (databaseMatches('oracle')){
		    execute("ALTER TABLE PARTICIPANTS MODIFY (RACE_CODE  VARCHAR2(200))");
	   	 }else if(databaseMatches('postgres')){
	   	 	execute("ALTER TABLE PARTICIPANTS ALTER RACE_CODE TYPE VARCHAR(200)");
	   	 }
	}
	
    void down() {
	    if (databaseMatches('oracle')){
		    execute("ALTER TABLE PARTICIPANTS MODIFY (RACE_CODE  VARCHAR2(50))");
	   	 }else if(databaseMatches('postgres')){
	   	 	execute("ALTER TABLE PARTICIPANTS ALTER RACE_CODE TYPE VARCHAR(50)");
	   	 }
   	}
}