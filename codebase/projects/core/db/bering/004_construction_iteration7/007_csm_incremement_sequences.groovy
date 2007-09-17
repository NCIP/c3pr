class CSMIncrememntCSMSequences extends edu.northwestern.bioinformatics.bering.Migration {

	void up() {
         if (databaseMatches('postgres')) {
    		   execute("ALTER SEQUENCE CSM_GROUP_GROUP_ID_SEQ restart with 5");
		       execute("ALTER SEQUENCE CSM_PROTECTIO_PROTECTION_G_SEQ restart with 7");
		       execute("ALTER SEQUENCE CSM_PROTECTIO_PROTECTION_E_SEQ restart with 9");
		       execute("ALTER SEQUENCE CSM_PG_PE_ID_SEQ restart with 9");
		       execute("ALTER SEQUENCE CSM_USER_GROU_USER_GROUP_R_SEQ restart with 34");
    		}
		 

         if (databaseMatches('oracle')) {
            execute("DROP SEQUENCE CSM_GROUP_GROUP_ID_SEQ");
            execute("CREATE SEQUENCE CSM_GROUP_GROUP_ID_SEQ start with 5 increment by 1 NOMAXVALUE minvalue 1 nocycle nocache noorder");

            execute("DROP SEQUENCE CSM_PROTECTIO_PROTECTION_G_SEQ");
            execute("CREATE SEQUENCE CSM_PROTECTIO_PROTECTION_G_SEQ start with 7 increment by 1 NOMAXVALUE minvalue 1 nocycle nocache noorder");

            execute("DROP SEQUENCE CSM_PROTECTIO_PROTECTION_E_SEQ");
            execute("CREATE SEQUENCE CSM_PROTECTIO_PROTECTION_E_SEQ start with 9 increment by 1 NOMAXVALUE minvalue 1 nocycle nocache noorder");

            execute("DROP SEQUENCE CSM_PG_PE_ID_SEQ");
            execute("CREATE SEQUENCE CSM_PG_PE_ID_SEQ start with 9 increment by 1 NOMAXVALUE minvalue 1 nocycle nocache noorder");

            execute("DROP SEQUENCE CSM_USER_GROU_USER_GROUP_R_SEQ");
            execute("CREATE SEQUENCE CSM_USER_GROU_USER_GROUP_R_SEQ start with 34 increment by 1 NOMAXVALUE minvalue 1 nocycle nocache noorder");

         }
	}

	void down(){

	}
}