class CSM32TO42Migrate extends edu.northwestern.bioinformatics.bering.Migration {
	
    void up() {
    	if (databaseMatches('oracle')) {
            external("CSM_Triggers.sql")
        } 
    }

    void down() {
    }
}