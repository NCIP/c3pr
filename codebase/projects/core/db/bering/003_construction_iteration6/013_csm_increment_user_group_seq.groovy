class CSMIncrememntUserGroupSeq extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
         if (databaseMatches('postgres')) {
		    execute("ALTER SEQUENCE csm_user_grou_user_group_i_seq restart with 4");
		}
         if (databaseMatches('oracle')) {
            execute("DROP SEQUENCE csm_user_grou_user_group_i_seq");
            execute("CREATE SEQUENCE csm_user_grou_user_group_i_seq start with 4 increment by 1 NOMAXVALUE minvalue 1 nocycle nocache noorder");
         }
         if (databaseMatches('sqlserver')) {
		    execute("DBCC CHECKIDENT ( 'CSM_USER_GROUP', RESEED, 4 )");
		}


	}

	void down(){

	}
}