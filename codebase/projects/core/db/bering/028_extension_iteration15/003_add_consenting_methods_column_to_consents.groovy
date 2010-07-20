class AddConsentingMethodsColumnToConsents extends edu.northwestern.bioinformatics.bering.Migration {
    void up() {
    	addColumn('consents', 'consenting_methods', 'string',nullable:false);
    	execute("update consents set consenting_methods = 'WRITTEN'");
    	
	}
	void down() {
		dropColumn('consents','consenting_methods');
    }
}
