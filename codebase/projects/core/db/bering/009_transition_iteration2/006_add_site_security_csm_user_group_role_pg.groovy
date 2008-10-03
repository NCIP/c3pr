class AddSiteSecurityCsmUserGroupRole extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
	
		if (databaseMatches('oracle')){
			execute("insert into csm_user_group_role_pg using select group_id as x,null, group_id as y,19, group_id as z, null from csm_group where group_id <=-5000");
		}
		
		if (databaseMatches('postgres')){
			execute("insert into csm_user_group_role_pg select group_id as x,null, group_id as y,19, group_id as z, null from csm_group where group_id <=-5000");
		}
		
		if (databaseMatches('sqlserver')){
			execute("SET IDENTITY_INSERT csm_user_group_role_pg ON");
			execute("insert into csm_user_group_role_pg (USER_GROUP_ROLE_PG_ID, USER_ID, GROUP_ID, ROLE_ID, PROTECTION_GROUP_ID, UPDATE_DATE) select group_id as x,null, group_id as y,19, group_id as z, null from csm_group where group_id <=-5000");
			execute("SET IDENTITY_INSERT csm_user_group_role_pg OFF");
		}
	
	}

	void down(){

	}
}