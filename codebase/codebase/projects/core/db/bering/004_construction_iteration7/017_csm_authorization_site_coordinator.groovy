class CSMAddSiteCoordinator extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
		insert('csm_group', [ group_id: -1005, group_name: 'site_coordinator', group_desc: 'Site coordinator group',  application_id: 1 ], primaryKey: false)

        //create on study
		insert('csm_user_group_role_pg',[USER_GROUP_ROLE_PG_ID: -1005, GROUP_ID: -1005, ROLE_ID: 4, PROTECTION_GROUP_ID: 2],primaryKey: false)

        //read on study
		insert('csm_user_group_role_pg',[USER_GROUP_ROLE_PG_ID: -1006, GROUP_ID: -1005, ROLE_ID: 6, PROTECTION_GROUP_ID: 2],primaryKey: false)

        //read on Participant
		insert('csm_user_group_role_pg',[USER_GROUP_ROLE_PG_ID: -1007, GROUP_ID: -1005, ROLE_ID: 3, PROTECTION_GROUP_ID: 1],primaryKey: false)

        //read on Registration
		insert('csm_user_group_role_pg',[USER_GROUP_ROLE_PG_ID: -1008, GROUP_ID: -1005, ROLE_ID: 9, PROTECTION_GROUP_ID: 3],primaryKey: false)

        //read on healthcaresite
		insert('csm_user_group_role_pg',[USER_GROUP_ROLE_PG_ID: -1009, GROUP_ID: -1005, ROLE_ID: 12, PROTECTION_GROUP_ID: 6],primaryKey: false)

        //read on research staff
		insert('csm_user_group_role_pg',[USER_GROUP_ROLE_PG_ID: -1010, GROUP_ID: -1005, ROLE_ID: 15, PROTECTION_GROUP_ID: 5],primaryKey: false)

	}

	void down(){

	}
}