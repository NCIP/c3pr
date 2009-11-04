class RenameOracleEndpointsPropsSequence extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {

        if (databaseMatches('oracle')) {
	    	execute("rename ENDPOINTS_PROPS_ID_SEQ to ENDPOINT_PROPS_ID_SEQ")
	 	}     
    }
  
    void down() {
    	if (databaseMatches('oracle')) {
	    	execute("rename ENDPOINT_PROPS_ID_SEQ to ENDPOINTS_PROPS_ID_SEQ")
	 	}   
    }
}
