class AddEmailToOrganization extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    
        addColumn('contact_mechanisms','ORG_ID','integer');
        addColumn('organizations','coppa_status_code','string');
        execute("ALTER TABLE contact_mechanisms ADD CONSTRAINT FK_ORG_CM FOREIGN KEY (ORG_ID) REFERENCES organizations(ID)");
        
        addColumn('identifiers','ORG_ID','integer');
        execute("ALTER TABLE identifiers ADD CONSTRAINT FK_ORG_ID FOREIGN KEY (ORG_ID) REFERENCES organizations(ID)");
    }

    void down() {
    	dropColumn('contact_mechanisms','ORG_ID');
    	dropColumn('organizations','coppa_status');
    	execute(" ALTER TABLE contact_mechanisms drop CONSTRAINT FK_ORG_CM")
    }
}