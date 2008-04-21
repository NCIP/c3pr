class InvestigatorUniqueConstraintsModification extends edu.northwestern.bioinformatics.bering.Migration {
    
    void up() {
        execute(" ALTER TABLE INVESTIGATORS drop CONSTRAINT uk_inv")
    	execute("ALTER TABLE INVESTIGATORS add CONSTRAINT uk_inv UNIQUE(nci_identifier)")
   	 }

    void down() {
   		 execute(" ALTER TABLE INVESTIGATORS drop CONSTRAINT uk_inv")
    	 execute("ALTER TABLE INVESTIGATORS add CONSTRAINT uk_inv UNIQUE(nci_identifier, first_name, last_name, maiden_name)")
   	 }
