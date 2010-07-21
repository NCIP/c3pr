class AddConsentingMethodsColumnToConsents extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	addColumn('consents', 'consenting_methods', 'string');
    	execute("update consents set consenting_methods = 'WRITTEN'");
    	setNullable('consents','consenting_methods', false);
    	
	}
	void down() {
		dropColumn('consents','consenting_methods');
    }
}
