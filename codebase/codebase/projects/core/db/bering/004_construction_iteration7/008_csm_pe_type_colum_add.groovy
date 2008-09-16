
class CSMIncrememntCSMSequences extends edu.northwestern.bioinformatics.bering.Migration {

	void up() {
               execute("ALTER TABLE CSM_PROTECTION_ELEMENT DROP COLUMN PROTECTION_ELEMENT_TYPE_ID");
               execute("ALTER TABLE CSM_PROTECTION_ELEMENT ADD PROTECTION_ELEMENT_TYPE VARCHAR(100)");
	}

	void down(){
           execute("ALTER TABLE CSM_PROTECTION_ELEMENT DROP COLUMN PROTECTION_ELEMENT_TYPE");
          if (databaseMatches('postgres')) {
            execute("ALTER TABLE CSM_PROTECTION_ELEMENT ADD PROTECTION_ELEMENT_TYPE_ID INTEGER");
          }
          if (databaseMatches('oracle')) {
            execute("ALTER TABLE CSM_PROTECTION_ELEMENT ADD PROTECTION_ELEMENT_TYPE_ID NUMBER(10)");
	     }
    }   
}
