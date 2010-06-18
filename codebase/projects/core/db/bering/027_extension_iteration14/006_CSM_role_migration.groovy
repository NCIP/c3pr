class CSMRoleMigration extends edu.northwestern.bioinformatics.bering.Migration {
	
    void up() {
    	external("CSM_Role_Migration.sql")
    	if (databaseMatches('oracle')) {
            external("CSM_PG_PE_Migration_Oracle.sql")
        } else if (databaseMatches('postgresql')){
            external("CSM_PG_PE_Migration_Postgres.sql")
        }
    }

    void down() {
    }
}