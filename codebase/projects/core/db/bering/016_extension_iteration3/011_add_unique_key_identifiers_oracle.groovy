class AddUniqueKeyIdentifiersOracle extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {

        if (databaseMatches('oracle')) {
	 	}     
    }
  
    void down() {
    	if (databaseMatches('oracle')) {
	 	}   
    }
}
