class UpdateConsentVersionDataTypeInConsentHistory extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
	if (databaseMatches('oracle')){
	    execute("ALTER TABLE CONSENT_HISTORY MODIFY (CONSENT_VERSION  VARCHAR2(200))");
   	 }else if(databaseMatches('postgres')){
   	 	execute("ALTER TABLE CONSENT_HISTORY ALTER CONSENT_VERSION TYPE VARCHAR(200)");
   	 }
	}
	
	void down(){
	
	}
}