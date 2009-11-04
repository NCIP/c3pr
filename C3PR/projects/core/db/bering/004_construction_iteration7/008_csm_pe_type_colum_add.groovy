
class CSMIncrememntCSMSequences extends edu.northwestern.bioinformatics.bering.Migration {

	void up() {
               execute("ALTER TABLE CSM_PROTECTION_ELEMENT DROP COLUMN PROTECTION_ELEMENT_TYPE_ID");
               execute("ALTER TABLE CSM_PROTECTION_ELEMENT ADD PROTECTION_ELEMENT_TYPE VARCHAR(100)");
	}

	void down(){
           execute("ALTER TABLE CSM_PROTECTION_ELEMENT DROP COLUMN PROTECTION_ELEMENT_TYPE");
           addColumn("CSM_PROTECTION_ELEMENT", "PROTECTION_ELEMENT_TYPE_ID", "integer");
    }   
}
