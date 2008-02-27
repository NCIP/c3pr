class AddSiteSecurityCsmUserGroupRole extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
	
		if (databaseMatches('oracle')){
			execute("insert into csm_user_group_role_pg using select group_id as x,null, group_id as y,19, group_id as z, null from csm_group where group_id <=-5000");
		}
		
		if (databaseMatches('postgres')){
			execute("insert into csm_user_group_role_pg select group_id as x,null, group_id as y,19, group_id as z, null from csm_group where group_id <=-5000");
		}
	
	}

	void down(){

	}
}