class InvestigatorUniqueConstraintsModification extends edu.northwestern.bioinformatics.bering.Migration {
    
    void up() {
        execute("ALTER TABLE INVESTIGATORS DROP CONSTRAINT UK_INV");
    	execute("ALTER TABLE INVESTIGATORS ADD CONSTRAINT UK_INV UNIQUE(nci_identifier)");
   	 }

    void down() {
   		 execute("ALTER TABLE INVESTIGATORS DROP CONSTRAINT UK_INV");
    	 execute("ALTER TABLE INVESTIGATORS ADD CONSTRAINT UK_INV UNIQUE(nci_identifier,first_name,last_name,maiden_name)");
   	 }
}