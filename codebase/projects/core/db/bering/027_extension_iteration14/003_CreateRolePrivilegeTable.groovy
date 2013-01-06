class CreateRolePrivilegeTable extends edu.northwestern.bioinformatics.bering.Migration {
	
    void up() {
 		createTable("role_privileges") { t ->
            t.addVersionColumn()
            t.addColumn("role_name", "string", nullable:false)
            t.addColumn("object_id", "string", nullable:false)
            t.addColumn("privilege", "string", nullable:false)
            t.addColumn("grid_id", "string")
            t.addColumn("retired_indicator", 'string' );
        }
        
        if (databaseMatches('oracle')) {
		   	execute("RENAME SEQ_role_privileges_ID to role_privileges_ID_SEQ");
            external("Role_Privileges_DataAuthoring_Oracle.sql")
        } else if (databaseMatches('postgresql')){
            external("Role_Privileges_DataAuthoring_Postgres.sql")
        }
        
    }

    void down() {
    }
}