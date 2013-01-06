class MigrateScriptForPersonUserType extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	
    	if (databaseMatches('postgres')) {
    		execute("update persons_users set person_user_type='STAFF' where assigned_identifier is not null and login_id is null")
    		execute("update persons_users set person_user_type='USER' where assigned_identifier is null and login_id is not null")
    		execute("update persons_users set person_user_type='STAFF_USER' where assigned_identifier is not null and login_id is not null")
    	}

    }

    void down() {
		execute("update persons_users set person_user_type=''")
    }
}

