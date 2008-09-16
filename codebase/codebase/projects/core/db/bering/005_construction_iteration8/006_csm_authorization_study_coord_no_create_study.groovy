class CSMAuthStudyCoordNoCreateStudy extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
	//no create on study for study coord
		execute("delete from csm_user_group_role_pg where USER_GROUP_ROLE_PG_ID IN (27)");
	}

	void down(){

	}
}