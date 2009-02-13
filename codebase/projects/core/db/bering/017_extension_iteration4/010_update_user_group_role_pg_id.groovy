class UpdateSiteCoordinatorForStudySubject extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
		execute("update csm_user_group_role_pg set USER_GROUP_ROLE_PG_ID = -1030 where group_id=-1005 and role_id=8 and protection_group_id=3");
	}
	
	void down(){
	
	}
}