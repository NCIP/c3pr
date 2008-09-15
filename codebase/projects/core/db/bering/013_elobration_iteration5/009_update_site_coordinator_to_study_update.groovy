class UpdateRegForCsm extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
			insert('csm_user_group_role_pg',[USER_GROUP_ROLE_PG_ID: -1026,GROUP_ID: -1005, PROTECTION_GROUP_ID: 2, ROLE_ID: 5], primaryKey: false);
	}

	void down(){
	
	}
}
