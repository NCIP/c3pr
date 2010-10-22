class MigrateResearchStaffWithTextLoginIDs extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	// create temporary table to copy user_id and login_ids in research_staff which are still text
    	createTable("user_research_staff_migration") { t ->
            t.addColumn("user_id", "integer")
            t.addColumn("login_name", "string")
         }
         
		if (databaseMatches('postgres')){
			//  insert all user_ids and login_names that are in text 
         	execute("insert into user_research_staff_migration (user_id,login_name) (select user_id,login_name from csm_user where login_name in (select research_staff.login_id from research_staff where research_staff.login_id not in (select research_staff.login_id from research_staff,csm_user where research_staff.login_id = csm_user.user_id::text)))");
       		
       		// update the research_staff login_id
       		execute("update research_staff set login_id = (user_research_staff_migration.user_id)from user_research_staff_migration where user_research_staff_migration.login_name = research_staff.login_id");
       	}
     	if (databaseMatches('oracle')){
     		//  insert all user_ids and login_names that are in text 
         	execute("insert into user_research_staff_migration (user_id,login_name) (select user_id,login_name from csm_user where login_name in (select research_staff.login_id from research_staff where research_staff.login_id not in (select research_staff.login_id from research_staff,csm_user where research_staff.login_id = csm_user.user_id)))");
       		
       		// update the research_staff login_id
       		execute("update research_staff set login_id = (select user_research_staff_migration.user_id from user_research_staff_migration where user_research_staff_migration.login_name = research_staff.login_id)");
       	}
		 
	
		// drop the temporary table 
		 execute("drop table user_research_staff_migration");
	}
	void down() {
    }
}
