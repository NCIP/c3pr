class CreateRolePrivilegeTable extends edu.northwestern.bioinformatics.bering.Migration {
	
    void up() {
        
        if (databaseMatches('oracle')) {
		   	execute("RENAME SEQ_role_privileges_ID to role_privileges_ID_SEQ");
            external("Role_Privileges_DataUpdate_Oracle.sql")
        } else if (databaseMatches('postgresql')){
            external("Role_Privileges_DataUpdate_Postgres.sql")
        }
        
    }

    void down() {
    }
}