class CSMIncrememntUserGroupSeq extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
		execute("ALTER SEQUENCE csm_user_grou_user_group_i_seq restart with 4");
	}

	void down(){

	}
}