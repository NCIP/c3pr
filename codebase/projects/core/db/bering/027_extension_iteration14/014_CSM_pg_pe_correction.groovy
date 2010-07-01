class CSMPGPECorrection extends edu.northwestern.bioinformatics.bering.Migration {
	
    void up() {
    	if (databaseMatches('oracle')) {
           external("CSM_PG_PE_Correction_Oracle.sql")
        } else if (databaseMatches('postgresql')){
            external("CSM_PG_PE_Correction_Postgres.sql")
        }
    }

    void down() {
    }
}