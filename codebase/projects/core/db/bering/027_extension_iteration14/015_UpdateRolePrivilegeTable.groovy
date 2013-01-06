class CreateRolePrivilegeTable extends edu.northwestern.bioinformatics.bering.Migration {
	
    void up() {
        
        if (databaseMatches('oracle')) {
            external("Role_Privileges_DataUpdate_Oracle.sql")
        } else if (databaseMatches('postgresql')){
            external("Role_Privileges_DataUpdate_Postgres.sql")
        }
        
    }

    void down() {
    }
}