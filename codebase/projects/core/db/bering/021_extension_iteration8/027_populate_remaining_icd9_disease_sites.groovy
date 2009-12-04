class PopulateRemainingICD9DiseaseSites extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	if (databaseMatches('oracle')) {
            external("populate_icd9_disease_sites_oracle.sql")
        } else if (databaseMatches('postgresql')){
            external("populate_icd9_disease_sites_postgres.sql")
        }
    }

   void down() {
    }
}

