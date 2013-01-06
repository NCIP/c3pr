class UpdateSiteCoordinatorForStudySubject extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
	 // added after testing Baylor Oracle Migration
		execute("update csm_user_group_role_pg set user_group_role_pg_id=(user_group_role_pg_id-15000) where user_group_role_pg_id>0");
		
		insert('csm_user_group_role_pg',[USER_GROUP_ROLE_PG_ID: 37, GROUP_ID: -1005, ROLE_ID: 8, PROTECTION_GROUP_ID: 3],primaryKey: false)
	}
	
	void down(){
	
	}
}