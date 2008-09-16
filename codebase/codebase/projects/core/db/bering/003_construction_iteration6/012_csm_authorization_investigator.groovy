class CSMAddInvestigator extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
		insert('csm_group', [ group_id: 4, group_name: 'investigator', group_desc: 'investigator group',  application_id: 1 ], primaryKey: false)
		insert('csm_user_group_role_pg',[USER_GROUP_ROLE_PG_ID: 31, GROUP_ID: 4, ROLE_ID: 3, PROTECTION_GROUP_ID: 3],primaryKey: false)
		insert('csm_user_group_role_pg',[USER_GROUP_ROLE_PG_ID: 32, GROUP_ID: 4, ROLE_ID: 6, PROTECTION_GROUP_ID: 2],primaryKey: false)
	}

	void down(){

	}
}