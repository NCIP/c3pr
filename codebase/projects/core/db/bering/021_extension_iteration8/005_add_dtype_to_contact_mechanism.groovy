class AddDtypeToContactMechanism extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
        addColumn('contact_mechanisms','dtype','string');
    }

    void down() {
    	dropColumn('contact_mechanisms','ORG_ID');
    }
}