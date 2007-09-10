class CSMIncrememntGroupSeq extends edu.northwestern.bioinformatics.bering.Migration {

	void up() {
         if (databaseMatches('postgres')) {
		    execute("ALTER SEQUENCE CSM_GROUP_GROUP_ID_SEQ restart with 4");
		}
         if (databaseMatches('oracle')) {
            execute("DROP SEQUENCE CSM_GROUP_GROUP_ID_SEQ");
            execute("CREATE SEQUENCE CSM_GROUP_GROUP_ID_SEQ start with 4 increment by 1 NOMAXVALUE minvalue 1 nocycle nocache noorder");
         }
	}

	void down(){

	}
}