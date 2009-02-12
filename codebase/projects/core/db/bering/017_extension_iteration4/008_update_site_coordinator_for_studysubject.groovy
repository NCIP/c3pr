class UpdateSiteCoordinatorForStudySubject extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
		insert('csm_user_group_role_pg',[USER_GROUP_ROLE_PG_ID: 37, GROUP_ID: -1005, ROLE_ID: 8, PROTECTION_GROUP_ID: 3],primaryKey: false)
	}
	
	void down(){
	
	}
}