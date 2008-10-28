class UpdateSiteCoordinatorForCsm extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
			insert('csm_user_group_role_pg',[USER_GROUP_ROLE_PG_ID: -1027, GROUP_ID: -1005, ROLE_ID: 1, PROTECTION_GROUP_ID: 1],primaryKey: false)
			insert('csm_user_group_role_pg',[USER_GROUP_ROLE_PG_ID: -1028, GROUP_ID: -1005, ROLE_ID: 2, PROTECTION_GROUP_ID: 1],primaryKey: false)
			insert('csm_user_group_role_pg',[USER_GROUP_ROLE_PG_ID: -1029, GROUP_ID: -1005, ROLE_ID: 7, PROTECTION_GROUP_ID: 3],primaryKey: false)
	}

	void down(){
	
	}
}
