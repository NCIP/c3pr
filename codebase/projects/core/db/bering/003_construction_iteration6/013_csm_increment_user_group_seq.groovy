class CSMIncrememntUserGroupSeq extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
         if (databaseMatches('postgres')) {
		    execute("ALTER SEQUENCE csm_user_grou_user_group_i_seq restart with 4");
		}
         if (databaseMatches('oracle')) {
            execute("DROP SEQUENCE csm_user_grou_user_group_i_seq");
            execute("CREATE SEQUENCE csm_user_grou_user_group_i_seq start with 4 increment by 1 NOMAXVALUE minvalue 1 nocycle nocache noorder");
         }


	}

	void down(){

	}
}