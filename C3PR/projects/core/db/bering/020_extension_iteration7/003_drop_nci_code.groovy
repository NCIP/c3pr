class NciCodeMigration extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
		dropColumn('organizations','nci_institute_code')    	
    }

    void down() {
    	
	}
  }	
