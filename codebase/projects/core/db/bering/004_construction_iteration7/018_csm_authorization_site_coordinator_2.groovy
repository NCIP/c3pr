class CSMUpdateSiteCoordinator extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
        //create on research staff
		insert('csm_user_group_role_pg',[USER_GROUP_ROLE_PG_ID: -1011, GROUP_ID: -1005, ROLE_ID: 13, PROTECTION_GROUP_ID: 5],primaryKey: false)

        //update on research staff
		insert('csm_user_group_role_pg',[USER_GROUP_ROLE_PG_ID: -1012, GROUP_ID: -1005, ROLE_ID: 14, PROTECTION_GROUP_ID: 5],primaryKey: false)

        //create on Investigator
		insert('csm_user_group_role_pg',[USER_GROUP_ROLE_PG_ID: -1013, GROUP_ID: -1005, ROLE_ID: 10, PROTECTION_GROUP_ID: 4],primaryKey: false)

        //update on Investigator
		insert('csm_user_group_role_pg',[USER_GROUP_ROLE_PG_ID: -1014, GROUP_ID: -1005, ROLE_ID: 11, PROTECTION_GROUP_ID: 4],primaryKey: false)

        //read on Investigator
		insert('csm_user_group_role_pg',[USER_GROUP_ROLE_PG_ID: -1015, GROUP_ID: -1005, ROLE_ID: 12, PROTECTION_GROUP_ID: 4],primaryKey: false)

	}

	void down(){

	}
}